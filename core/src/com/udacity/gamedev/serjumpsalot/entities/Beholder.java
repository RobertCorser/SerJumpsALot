package com.udacity.gamedev.serjumpsalot.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.udacity.gamedev.serjumpsalot.Level;
import com.udacity.gamedev.serjumpsalot.util.Assets;
import com.udacity.gamedev.serjumpsalot.util.Constants;
import com.udacity.gamedev.serjumpsalot.util.Enums.Direction;
import com.udacity.gamedev.serjumpsalot.util.Utils;

public class Beholder extends WalkingEnemy{

    private Level level;

    public Beholder(Platform platform, Level level){
        super(platform);
        health = Constants.ENEMY_HEALTH * 2;
        this.level = level;
    }

    public void update(float delta){
        super.update(delta);

        if(findSerJumpsALot() == Direction.LEFT){
            setDirection(Direction.RIGHT);
        }
        else {
            setDirection(Direction.LEFT);
        }

    }

    public void render(SpriteBatch batch){

        TextureRegion region;

        float walkTimeSeconds = Utils.secondsSince(startTime);
        if(getDirection() == Direction.RIGHT){
            region = Assets.instance.beholderAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
        }
        else {
            region = Assets.instance.beholderAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
        }

        batch.draw(region, position.x, position.y, region.getRegionWidth(), region.getRegionHeight());
    }

    private Direction findSerJumpsALot(){
        if(level.getSerJumpsALot().getPosition().x > position.x){
            return Direction.RIGHT;
        }
        else {
            return Direction.LEFT;
        }
    }

}
