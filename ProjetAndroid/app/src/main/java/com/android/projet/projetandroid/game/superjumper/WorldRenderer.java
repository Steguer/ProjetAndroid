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

import android.graphics.Bitmap;

import com.android.projet.projetandroid.game.GameController;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;

    private Bitmap image;
    private Texture texture;
    private Pixmap pixmap;
    private boolean enaLoadBackground;

	public WorldRenderer (SpriteBatch batch, World world) {
		this.world = world;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		this.batch = batch;
        enaLoadBackground = true;
	}

	public void render () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
	}

	public void renderBackground () {
		batch.disableBlending();
		batch.begin();

        if(enaLoadBackground) {
            loadBackground();
            enaLoadBackground = false;
        }

		batch.draw(texture, cam.position.x - FRUSTUM_WIDTH / 2, cam.position.y - FRUSTUM_HEIGHT / 2, FRUSTUM_WIDTH,
			FRUSTUM_HEIGHT);
		batch.end();
	}

	public void renderObjects () {
		batch.enableBlending();
		batch.begin();
		renderBob();
		renderPlatforms();
		renderItems();
		renderSquirrels();
		renderCastle();
		batch.end();

        // Test bounding boxs to debug
        /*ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(world.castle.bounds.x, world.castle.bounds.y, world.castle.bounds.width, world.castle.bounds.height);
        shapeRenderer.rect(world.bob.bounds.x, world.bob.bounds.y, world.bob.bounds.width, world.bob.bounds.height);

        for(int i = 0; i<world.springs.size(); ++i) {
            shapeRenderer.rect(world.springs.get(i).bounds.x, world.springs.get(i).bounds.y, world.springs.get(i).bounds.width, world.springs.get(i).bounds.height);
        }
        for(int i = 0; i<world.platforms.size(); ++i) {
            shapeRenderer.rect(world.platforms.get(i).bounds.x, world.platforms.get(i).bounds.y, world.platforms.get(i).bounds.width, world.platforms.get(i).bounds.height);
        }
        for(int i = 0; i<world.squirrels.size(); ++i) {
            shapeRenderer.rect(world.squirrels.get(i).bounds.x, world.squirrels.get(i).bounds.y, world.squirrels.get(i).bounds.width, world.squirrels.get(i).bounds.height);
        }
        for(int i = 0; i<world.coins.size(); ++i) {
            shapeRenderer.rect(world.coins.get(i).bounds.x, world.coins.get(i).bounds.y, world.coins.get(i).bounds.width, world.coins.get(i).bounds.height);
        }

        shapeRenderer.end();*/
	}

	private void renderBob () {
		TextureRegion keyFrame;
		switch (world.bob.state) {
		case Bob.BOB_STATE_FALL:
			keyFrame = Assets.bobFall.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Bob.BOB_STATE_JUMP:
			keyFrame = Assets.bobJump.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Bob.BOB_STATE_HIT:
		default:
			keyFrame = Assets.bobHit;
		}

		float side = world.bob.velocity.x < 0 ? -1 : 1;
		if (side < 0)
			batch.draw(keyFrame, world.bob.position.x + 0.5f, world.bob.position.y - 0.5f, side * 1, 1);
		else
			batch.draw(keyFrame, world.bob.position.x - 0.5f, world.bob.position.y - 0.5f, side * 1, 1);
	}

	private void renderPlatforms () {
		int len = world.platforms.size();
		for (int i = 0; i < len; i++) {
			Platform platform = world.platforms.get(i);
			TextureRegion keyFrame = Assets.platform;
			if (platform.state == Platform.PLATFORM_STATE_PULVERIZING) {
				keyFrame = Assets.brakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_NONLOOPING);
			}

			batch.draw(keyFrame, platform.position.x - Platform.PLATFORM_WIDTH / 2, platform.position.y - Platform.PLATFORM_HEIGHT / 2, Platform.PLATFORM_WIDTH, Platform.PLATFORM_HEIGHT);
		}
	}

	private void renderItems () {
		int len = world.springs.size();
		for (int i = 0; i < len; i++) {
			Spring spring = world.springs.get(i);
			batch.draw(Assets.spring, spring.position.x - (Spring.SPRING_WIDTH + 0.3f) / 2, spring.position.y - Platform.PLATFORM_HEIGHT + 0.1f, Spring.SPRING_WIDTH + 0.3f, Spring.SPRING_HEIGHT + 1);
		}

		len = world.coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = world.coins.get(i);
			TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
			batch.draw(keyFrame, coin.position.x - Coin.COIN_WIDTH / 2, coin.position.y - Coin.COIN_HEIGHT / 2, Coin.COIN_WIDTH, Coin.COIN_HEIGHT);
		}
	}

	private void renderSquirrels () {
		int len = world.squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = world.squirrels.get(i);
			TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(squirrel.stateTime, Animation.ANIMATION_LOOPING);
			float side = squirrel.velocity.x < 0 ? -1 : 1;
			if (side < 0)
				batch.draw(keyFrame, squirrel.position.x + 0.5f, squirrel.position.y - 0.5f, side * 1, 1);
			else
				batch.draw(keyFrame, squirrel.position.x - 0.5f, squirrel.position.y - 0.5f, side * 1, 1);
		}
	}

	private void renderCastle () {
		Castle castle = world.castle;
		batch.draw(Assets.castle, castle.position.x - Castle.CASTLE_WIDTH / 2, castle.position.y - Castle.CASTLE_HEIGHT / 2, Castle.CASTLE_WIDTH, Castle.CASTLE_HEIGHT);
	}

    private void loadBackground() {
        int dimension = GameController.getIsntance().getBackground().getWidth();
        int dimension2 = GameController.getIsntance().getBackground().getHeight();
        image = GameController.getIsntance().getBackground();

        int[] pixels = new int[GameController.getIsntance().getBackground().getWidth() * GameController.getIsntance().getBackground().getHeight()];
        GameController.getIsntance().getBackground().getPixels(pixels, 0, dimension, 0, 0, dimension, dimension2);
        // Convert from ARGB to RGBA
        for (int i = 0; i< pixels.length; i++) {
            int pixel = pixels[i];
            pixels[i] = (pixel << 8) | ((pixel >> 24) & 0xFF);
        }
        pixmap = new Pixmap(GameController.getIsntance().getBackground().getWidth(), GameController.getIsntance().getBackground().getHeight(), Pixmap.Format.RGBA8888);
        pixmap.getPixels().asIntBuffer().put(pixels);
        texture = new Texture(pixmap);
    }
}
