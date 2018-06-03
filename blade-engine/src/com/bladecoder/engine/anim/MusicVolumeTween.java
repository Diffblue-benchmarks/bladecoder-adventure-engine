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
import com.bladecoder.engine.model.MusicManager;
import com.bladecoder.engine.util.InterpolationMode;

/**
 * Tween for Music fade in/out
 */
public class MusicVolumeTween extends Tween<MusicManager> {
	
	public float startVolume;
	public float targetVolume;
	
	public MusicVolumeTween() {
	}

	public void start(MusicManager mm, float tVolume, float duration, InterpolationMode interpolation, ActionCallback cb) {	
		this.target = mm;
		
		startVolume = mm.getVolume();
		targetVolume = tVolume;
		
		setDuration(duration);
		setType(Type.NO_REPEAT);
		setCount(1);
		setInterpolation(interpolation);

		if (cb != null) {
			setCb(cb);
		}
		
		restart();
	}

	@Override
	public void updateTarget() {
		target.setVolume(startVolume + getPercent() * (targetVolume - startVolume));
	}
}
