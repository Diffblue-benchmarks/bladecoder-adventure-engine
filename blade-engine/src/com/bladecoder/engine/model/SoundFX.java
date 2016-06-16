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
package com.bladecoder.engine.model;

import com.badlogic.gdx.audio.Sound;
import com.bladecoder.engine.assets.AssetConsumer;
import com.bladecoder.engine.assets.EngineAssetManager;

public class SoundFX implements AssetConsumer {
	transient private Sound s;
	private String id;
	private boolean loop;
	private String filename;
	private float volume = 1f;
	private float pan = 0f;
	private boolean preload;
	
	public SoundFX() {
		
	}
	
	public SoundFX(String id, String filename, boolean loop, float volume, float pan, boolean preload) {
		this.id = id;
		this.filename = filename;
		this.loop = loop;
		this.volume = volume;
		this.pan = pan;
	}
	
	public void play() {
		if(s==null) {
			if(!preload) {
				loadAssets();
				EngineAssetManager.getInstance().finishLoading();
				retrieveAssets();
				
				if(s == null)
					return;
			} else {
				return;
			}
		}
		
		if(loop) s.loop();
		else s.play(volume, 1, pan);
	}

	public void stop() {
		if(s==null)
			return;
		
		s.stop();
	}
	
	public void pause() {
		if(s==null)
			return;
		
		s.pause();
	}
	
	public void resume() {
		if(s==null)
			return;
		
		s.resume();
	}
	
	public boolean getLoop() {
		return loop;
	}
	
	public String getId() {
		return id != null? id: filename;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getPan() {
		return pan;
	}

	public void setPan(float pan) {
		this.pan = pan;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isPreload() {
		return preload;
	}

	public void setPreload(boolean preload) {
		this.preload = preload;
	}

	@Override
	public void loadAssets() {
//		EngineLogger.debug("LOADING SOUND: " + id + " - " + filename);
		EngineAssetManager.getInstance().loadSound(getFilename());
	}
	
	@Override
	public void retrieveAssets() {
		s = EngineAssetManager.getInstance().getSound(getFilename());
	}
	
	@Override
	public void dispose() {
//		EngineLogger.debug("DISPOSING SOUND: " + id + " - " + filename);
		stop();
		EngineAssetManager.getInstance().disposeSound(getFilename());
	}
}
