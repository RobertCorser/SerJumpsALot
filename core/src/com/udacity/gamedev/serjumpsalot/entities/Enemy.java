package com.udacity.gamedev.serjumpsalot.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.serjumpsalot.util.Constants;
import com.udacity.gamedev.serjumpsalot.util.Enums.Direction;


public class Enemy {

    //Public fields
    public int health;
    public Vector2 position;

    //Package-private fields
    long startTime;

    //Private fields
    private Direction direction;
    private final Platform platform;



    Enemy(Platform platform) {
        this.platform = platform;
        direction = Direction.RIGHT;
        position = new Vector2(platform.left, platform.top);
        startTime = TimeUtils.nanoTime();
        health = Constants.ENEMY_HEALTH;
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
        } else if (position.x + Constants.ENEMY_SIZE > platform.right) {
            position.x = platform.right - Constants.ENEMY_SIZE;
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

    protected Platform getPlatform(){
        return platform;
    }

}
