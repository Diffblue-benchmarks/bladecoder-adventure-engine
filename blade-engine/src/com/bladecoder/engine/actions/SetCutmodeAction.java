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
import com.bladecoder.engine.model.World;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@ActionDescription("Set/Unset the cutmode.")
public class SetCutmodeAction implements Action {
	@JsonProperty(required = true, defaultValue = "true")
	@JsonPropertyDescription("when 'true' sets the scene in 'cutmode'")
	@ActionPropertyType(Type.BOOLEAN)
	private boolean value = true;

	@Override
	public boolean run(ActionCallback cb) {
		World.getInstance().setCutMode(value);
		
		return false;
	}

}
