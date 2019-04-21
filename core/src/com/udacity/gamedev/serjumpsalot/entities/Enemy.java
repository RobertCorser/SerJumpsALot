package com.udacity.gamedev.serjumpsalot.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.serjumpsalot.util.Assets;
import com.udacity.gamedev.serjumpsalot.util.Constants;
import com.udacity.gamedev.serjumpsalot.util.Enums.Direction;
import com.udacity.gamedev.serjumpsalot.util.Utils;


public class Enemy {

    final long startTime;
    //final float bobOffset;
    private final Platform platform;
    public Vector2 position;
    public int health;
    private Direction direction;

    public Enemy(Platform platform) {
        this.platform = platform;
        direction = Direction.RIGHT;
        //position = new Vector2(platform.left, platform.top + Constants.ENEMY_CENTER.y);
        position = new Vector2(platform.left, platform.top);
        startTime = TimeUtils.nanoTime();
        health = Constants.ENEMY_HEALTH;
        //bobOffset = MathUtils.random();
    }

    public void update(float delta) {
        switch (direction) {
            case LEFT:
                position.x -= Constants.ENEMY_MOVEMENT_SPEED * delta;
                break;
            case RIGHT:
                position.x += Constants.ENEMY_MOVEMENT_SPEED * delta;
        }

        if (position.x < platform.left) {
            position.x = platform.left;
            direction = Direction.RIGHT;
        } else if (position.x > platform.right) {
            position.x = platform.right;
            direction = Direction.LEFT;
        }

        //final float elapsedTime = Utils.secondsSince(startTime);
        //final float bobMultiplier = 1 + MathUtils.sin(MathUtils.PI2 * (bobOffset + elapsedTime / Constants.ENEMY_BOB_PERIOD));
        //position.y = platform.top + Constants.ENEMY_CENTER.y + Constants.ENEMY_BOB_AMPLITUDE * bobMultiplier;
    }

    protected Direction getDirection(){
        return direction;
    }

    protected void setDirection(Direction direction){
        this.direction = direction;
    }
}