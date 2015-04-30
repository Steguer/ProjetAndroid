/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
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

package com.android.projet.projetandroid.game.superjumper;

import com.android.projet.projetandroid.map.MapsActivity;
import com.android.projet.projetandroid.markerAugReality.MarkerActivity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends ScreenAdapter {
	SuperJumper game;
	OrthographicCamera guiCam;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Rectangle mapBounds;
    Rectangle saveBounds;
	Vector3 touchPoint;

	public MainMenuScreen (SuperJumper game) {
		this.game = game;

		guiCam = new OrthographicCamera(480, 320);
		guiCam.position.set(480 / 2, 320 / 2, 0);
		soundBounds = new Rectangle(0, 0, 64, 64);
		playBounds = new Rectangle(240 - 300 / 2, 320 - 120 - 36, 300, 36);
		highscoresBounds = new Rectangle(240 - 300 / 2, 320 - 120 - 36 * 2, 300, 36);
		mapBounds = new Rectangle(240 - 300 / 2, 320 - 120 - 36 * 3, 300, 36);
        saveBounds = new Rectangle(240 - 300 / 2, 320 - 120 - 36 * 4, 300, 36);
		touchPoint = new Vector3();
	}

	public void update () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
                game.actionResolver.launchActivity(MarkerActivity.class);
				game.setScreen(new GameScreen(game));
				return;
			}
			if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HighscoresScreen(game));
				return;
			}
			if (mapBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
                game.actionResolver.launchActivity(MapsActivity.class);
                // Add here the behavior fot a click on map

				return;
			}
            if (saveBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);

                // Add here the behavior fot a click on save

                return;
            }
			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
			}
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0, 480, 320);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.logo, 240 - 450 / 2, 320 - 30 - 60, 450, 60);
		game.batcher.draw(Assets.mainMenu, 240 - 300 / 2, 320 - 60 - 60 - 145, 300, 145);
		game.batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);

		game.batcher.end();

        // To debug menu
        /*ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(guiCam.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(playBounds.x, playBounds.y, playBounds.width, playBounds.height);
        shapeRenderer.rect(highscoresBounds.x, highscoresBounds.y, highscoresBounds.width, highscoresBounds.height);
        shapeRenderer.rect(mapBounds.x, mapBounds.y, mapBounds.width, mapBounds.height);
        shapeRenderer.rect(saveBounds.x, saveBounds.y, saveBounds.width, saveBounds.height);
        shapeRenderer.end();*/

	}

	@Override
	public void render (float delta) {
		update();
		draw();
	}

	@Override
	public void pause () {
		Settings.save();
	}
}
