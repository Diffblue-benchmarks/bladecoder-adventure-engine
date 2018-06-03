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
package com.bladecoder.engine.anim;

import com.bladecoder.engine.actions.ActionCallback;
import com.bladecoder.engine.model.AtlasRenderer;

/**
 * Tween for spriteactor position animation
 */
public class FATween extends Tween<AtlasRenderer> {

	public void start(AtlasRenderer target, Tween.Type repeatType, int count, float duration, ActionCallback cb) {
		this.target = target;
		
		setDuration(duration);
		setType(repeatType);
		setCount(count);

		if (cb != null) {
			setCb(cb);
		}
		
		restart();
	}
	
	public void updateTarget() {
		if(!isComplete() && getPercent() < 1.0f)
			target.setFrame((int)(getPercent() * target.getNumFrames()));
	}
}
