/*******************************************************************************
 * Copyright 2014 Rafael Garcia Moreno.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.bladecoder.engine.actions;

import com.bladecoder.engine.actions.Param.Type;
import com.bladecoder.engine.assets.EngineAssetManager;
import com.bladecoder.engine.model.InteractiveActor;
import com.bladecoder.engine.model.Scene;
import com.bladecoder.engine.model.World;
import com.bladecoder.engine.util.EngineLogger;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@ActionDescription("Move the actor to the selected scene")
public class MoveToSceneAction implements Action {
	@JsonProperty
	@JsonPropertyDescription("The selected actor")
	@ActionPropertyType(Type.SCENE_ACTOR)
	private SceneActorRef actor;

	@JsonProperty
	@JsonPropertyDescription("The target scene")
	@ActionPropertyType(Type.SCENE)
	private String scene;

	@Override
	public boolean run(ActionCallback cb) {			
		Scene s = actor.getScene();

		final String actorId = actor.getActorId();
		if (actorId == null) {
			// if called in a scene verb and no actor is specified, we do nothing
			EngineLogger.error(getClass() + ": No actor specified");
			return false;
		}

		InteractiveActor a = (InteractiveActor)s.getActor(actorId, false);

		s.removeActor(a);
		if(s == World.getInstance().getCurrentScene())
			a.dispose();
		
		Scene ts =  (scene != null && !scene.isEmpty())? World.getInstance().getScene(scene): World.getInstance().getCurrentScene();
		
		if(ts == World.getInstance().getCurrentScene()) {
			a.loadAssets();
			EngineAssetManager.getInstance().finishLoading();
			a.retrieveAssets();
		}
		
		ts.addActor(a);
		
		return false;
	}


}
