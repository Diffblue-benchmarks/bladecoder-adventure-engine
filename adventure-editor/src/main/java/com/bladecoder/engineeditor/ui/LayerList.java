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
package com.bladecoder.engineeditor.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.bladecoder.engine.model.Scene;
import com.bladecoder.engine.model.SceneLayer;
import com.bladecoder.engineeditor.Ctx;
import com.bladecoder.engineeditor.common.Message;
import com.bladecoder.engineeditor.ui.components.CellRenderer;
import com.bladecoder.engineeditor.ui.components.EditModelDialog;
import com.bladecoder.engineeditor.ui.components.ModelList;

public class LayerList extends ModelList<Scene, SceneLayer> {

	private ImageButton upBtn;
	private ImageButton downBtn;
	private ImageButton visibilityBtn;

	public LayerList(Skin skin) {
		super(skin, false);

		list.setCellRenderer(listCellRenderer);

		visibilityBtn = new ImageButton(skin);
		toolbar.addToolBarButton(visibilityBtn, "ic_eye", "Toggle Visibility", "Toggle Visibility");

		visibilityBtn.setDisabled(false);

		visibilityBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				toggleVisibility();
			}
		});

		upBtn = new ImageButton(skin);
		downBtn = new ImageButton(skin);

		toolbar.addToolBarButton(upBtn, "ic_up", "Move up", "Move up");
		toolbar.addToolBarButton(downBtn, "ic_down", "Move down", "Move down");
		toolbar.pack();

		list.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				int pos = list.getSelectedIndex();

				toolbar.disableEdit(pos == -1);
				upBtn.setDisabled(pos == -1 || pos == 0);
				downBtn.setDisabled(pos == -1 || pos == list.getItems().size - 1);
			}
		});

		upBtn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				up();
			}
		});

		downBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				down();
			}
		});
	}

	private void toggleVisibility() {

		SceneLayer e = list.getSelected();

		if (e == null)
			return;

		e.setVisible(!e.isVisible());

		Ctx.project.setModified();
	}

	private void up() {
		int pos = list.getSelectedIndex();

		if (pos == -1 || pos == 0)
			return;

		Array<SceneLayer> items = list.getItems();
		SceneLayer e = items.get(pos);
		SceneLayer e2 = items.get(pos - 1);

		parent.getLayers().set(pos, e2);
		parent.getLayers().set(pos - 1, e);

		items.removeIndex(pos);
		items.insert(pos - 1, e);
		list.setSelectedIndex(pos - 1);
		upBtn.setDisabled(list.getSelectedIndex() == 0);
		downBtn.setDisabled(list.getSelectedIndex() == list.getItems().size - 1);

		Ctx.project.setModified();
	}

	private void down() {
		int pos = list.getSelectedIndex();
		Array<SceneLayer> items = list.getItems();

		if (pos == -1 || pos == items.size - 1)
			return;

		SceneLayer e = items.get(pos);
		SceneLayer e2 = pos + 1 < items.size ? items.get(pos + 1) : null;

		parent.getLayers().set(pos, e2);
		parent.getLayers().set(pos + 1, e);

		items.removeIndex(pos);
		items.insert(pos + 1, e);
		list.setSelectedIndex(pos + 1);
		upBtn.setDisabled(list.getSelectedIndex() == 0);
		downBtn.setDisabled(list.getSelectedIndex() == list.getItems().size - 1);

		Ctx.project.setModified();
	}

	@Override
	protected EditModelDialog<Scene, SceneLayer> getEditElementDialogInstance(SceneLayer l) {
		return new EditLayerDialog(skin, parent, l);
	}

	@Override
	protected void delete() {

		int pos = list.getSelectedIndex();

		if (pos == -1)
			return;

		if (list.getItems().size < 2) {
			String msg = "The layer will not be deleted, at least one layer must exist";
			Message.showMsg(getStage(), msg, 3);

			return;
		}

		SceneLayer e = list.getSelected();

		if (e.getActors().isEmpty()) {
			SceneLayer l = removeSelected();
			parent.getLayers().remove(l);
			Ctx.project.setModified();
		} else {
			String msg = "The layer is not empty.";
			Message.showMsg(getStage(), msg, 3);
		}

	}
	
	@Override
	protected void copy() {
		// TODO
	}
	
	@Override
	protected void paste() {
		// TODO
	}

	// -------------------------------------------------------------------------
	// ListCellRenderer
	// -------------------------------------------------------------------------
	private static final CellRenderer<SceneLayer> listCellRenderer = new CellRenderer<SceneLayer>() {

		@Override
		protected String getCellTitle(SceneLayer l) {
			return l.getName();
		}

		@Override
		protected String getCellSubTitle(SceneLayer l) {

			StringBuilder sb = new StringBuilder();

			sb.append("dynamic: ").append(l.isDynamic());
			sb.append(" visible: ").append(l.isVisible());
			sb.append(" parallax: ").append(l.getParallaxMultiplier());

			return sb.toString();
		}

		@Override
		public TextureRegion getCellImage(SceneLayer l) {
			String u = null;

			if (l.isVisible()) {
				u = "eye";
			} else {
				u = "eye_disabled";
			}

			return Ctx.assetManager.getIcon(u);
		}

		@Override
		protected boolean hasSubtitle() {
			return true;
		}

		@Override
		protected boolean hasImage() {
			return true;
		}
	};
}
