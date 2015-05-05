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

import com.android.projet.projetandroid.game.GameController;
import com.android.projet.projetandroid.markerAugReality.markers.Marker;
import com.android.projet.projetandroid.markerAugReality.markers.MarkerType;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
	public interface WorldListener {
		public void jump();

		public void highJump();

		public void hit();

		public void coin();
	}

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 6;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);

	public final Bob bob;
	public final List<Platform> platforms;
	public final List<Spring> springs;
	public final List<Squirrel> squirrels;
	public final List<Coin> coins;
    public final List<Marker> markersPosition;
	public Castle castle;
	public final WorldListener listener;
	public final Random rand;

	public float heightSoFar;
	public int score;
	public int state;

    private Vector2 startPosition;

	public World (WorldListener listener) {
		this.platforms = new ArrayList<Platform>();
		this.springs = new ArrayList<Spring>();
		this.squirrels = new ArrayList<Squirrel>();
		this.coins = new ArrayList<Coin>();
		this.listener = listener;
        this.markersPosition = new ArrayList<Marker>(GameController.getIsntance().getMarkersList());
		rand = new Random();

        // Test with random values
        /*Marker marker = new Marker(new Point(0, 0) , 10, MarkerType.START);
        markersPosition.add(marker);
        marker = new Marker(new Point(5, 0) , 10, MarkerType.ENEMY);
        markersPosition.add(marker);
        marker = new Marker(new Point(10, 0) , 10, MarkerType.END);
        markersPosition.add(marker);*/

		generateLevel();

		this.bob = new Bob(this.startPosition.x-Bob.BOB_WIDTH,this.startPosition.y+Bob.BOB_HEIGHT);

		this.heightSoFar = 0;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}

    /**
     * Generate the good asset depending to markers
     */
	private void generateLevel () {
		for(int i=0; i<markersPosition.size(); ++i) {
            Platform  platform;
            if(markersPosition.get(i).getType() == MarkerType.MOVING) {
                platform = new Platform(Platform.PLATFORM_TYPE_MOVING, markersPosition.get(i).getPosition().x + Platform.PLATFORM_WIDTH / 2, markersPosition.get(i).getPosition().y - Platform.PLATFORM_HEIGHT / 3);
                platforms.add(platform);
            }
            else{
                platform = new Platform(Platform.PLATFORM_TYPE_STATIC, markersPosition.get(i).getPosition().x + Platform.PLATFORM_WIDTH / 2, markersPosition.get(i).getPosition().y - Platform.PLATFORM_HEIGHT / 3);
                platforms.add(platform);
                if(markersPosition.get(i).getType() == MarkerType.START) {
                    this.startPosition = new Vector2(platform.position.x, platform.position.y);
                }
                if (markersPosition.get(i).getType() == MarkerType.TRAMPOLINE) {
                    Spring spring = new Spring(platform.position.x - Spring.SPRING_WIDTH*2, platform.position.y + Spring.SPRING_HEIGHT);
                    springs.add(spring);
                }
                if(markersPosition.get(i).getType() == MarkerType.END) {
                    this.castle = new Castle(platform.position.x, platform.position.y + Castle.CASTLE_HEIGHT / 2 + rand.nextFloat() / 2);
                }
                if (markersPosition.get(i).getType() == MarkerType.ENEMY) {
                    Squirrel squirrel = new Squirrel(platform.position.x, platform.position.y
                            + Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() / 2);
                    squirrels.add(squirrel);
                }
                if (markersPosition.get(i).getType() == MarkerType.COIN) {
                    Coin coin = new Coin(platform.position.x - Coin.COIN_WIDTH, platform.position.y + Coin.COIN_HEIGHT + rand.nextFloat() / 2);
                    coins.add(coin);
                }
            }
		}
	}

	public void update (float deltaTime, float accelX) {
		updateBob(deltaTime, accelX);
		updatePlatforms(deltaTime);
		updateSquirrels(deltaTime);
		updateCoins(deltaTime);
		if (bob.state != Bob.BOB_STATE_HIT) checkCollisions();
		checkGameOver();
	}

	private void updateBob (float deltaTime, float accelX) {
		//if (bob.state != Bob.BOB_STATE_HIT && bob.position.y <= 0.5f) bob.hitPlatform();
		if (bob.state != Bob.BOB_STATE_HIT) bob.velocity.x = -accelX / 10 * Bob.BOB_MOVE_VELOCITY;
		bob.update(deltaTime);
		heightSoFar = Math.max(bob.position.y, heightSoFar);
	}

	private void updatePlatforms (float deltaTime) {
		int len = platforms.size();
		for (int i = 0; i < len; i++) {
			Platform platform = platforms.get(i);
			platform.update(deltaTime);
			if (platform.state == Platform.PLATFORM_STATE_PULVERIZING && platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
				platforms.remove(platform);
				len = platforms.size();
			}
		}
	}

	private void updateSquirrels (float deltaTime) {
		int len = squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = squirrels.get(i);
			squirrel.update(deltaTime);
		}
	}

	private void updateCoins (float deltaTime) {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
	}

	private void checkCollisions () {
		checkPlatformCollisions();
		checkSquirrelCollisions();
		checkItemCollisions();
		checkCastleCollisions();
	}

	private void checkPlatformCollisions () {
		if (bob.velocity.y > 0) return;

		int len = platforms.size();
		for (int i = 0; i < len; i++) {
			Platform platform = platforms.get(i);
			if (bob.position.y > platform.position.y) {
				if (bob.bounds.overlaps(platform.bounds)) {
					bob.hitPlatform();
					listener.jump();
					break;
				}
			}
		}
	}

	private void checkSquirrelCollisions () {
		int len = squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = squirrels.get(i);
			if (squirrel.bounds.overlaps(bob.bounds)) {
				bob.hitSquirrel();
				listener.hit();
			}
		}
	}

	private void checkItemCollisions () {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			if (bob.bounds.overlaps(coin.bounds)) {
				coins.remove(coin);
				len = coins.size();
				listener.coin();
				score += Coin.COIN_SCORE;
			}

		}

		if (bob.velocity.y > 0) return;

		len = springs.size();
		for (int i = 0; i < len; i++) {
			Spring spring = springs.get(i);
			if (bob.position.y > spring.position.y) {
				if (bob.bounds.overlaps(spring.bounds)) {
					bob.hitSpring();
					listener.highJump();
				}
			}
		}
	}

	private void checkCastleCollisions () {
		if (castle.bounds.overlaps(bob.bounds)) {
			state = WORLD_STATE_NEXT_LEVEL;
		}
	}

	private void checkGameOver () {
		if (-7.0f > bob.position.y) {
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
