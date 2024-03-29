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

import com.badlogic.gdx.math.Vector2;

public class Squirrel extends DynamicGameObject {
	public static final float SQUIRREL_WIDTH = 1;
	public static final float SQUIRREL_HEIGHT = 0.6f;
	public static final float SQUIRREL_VELOCITY = 0.8f;
    public static final float SQUIRREL_MOVE = Platform.PLATFORM_WIDTH;

	float stateTime = 0;
    final Vector2 initPosition;

	public Squirrel (float x, float y) {
		super(x, y, SQUIRREL_WIDTH, SQUIRREL_HEIGHT);
		velocity.set(SQUIRREL_VELOCITY, 0);
        initPosition = position.cpy();
	}

	public void update (float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - SQUIRREL_WIDTH / 2;
		bounds.y = position.y - SQUIRREL_HEIGHT / 2;

		if (position.x < SQUIRREL_WIDTH / 2 || position.x < initPosition.x - SQUIRREL_MOVE) {
			velocity.x = SQUIRREL_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - SQUIRREL_WIDTH / 2 || position.x > initPosition.x + SQUIRREL_MOVE/2) {
			velocity.x = -SQUIRREL_VELOCITY;
		}
		stateTime += deltaTime;
	}
}
