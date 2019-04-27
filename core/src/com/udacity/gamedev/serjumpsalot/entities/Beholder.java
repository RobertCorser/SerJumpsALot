package com.udacity.gamedev.serjumpsalot.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.serjumpsalot.Level;
import com.udacity.gamedev.serjumpsalot.util.Assets;
import com.udacity.gamedev.serjumpsalot.util.Constants;
import com.udacity.gamedev.serjumpsalot.util.Enums.Direction;
import com.udacity.gamedev.serjumpsalot.util.Utils;

public class Beholder extends WalkingEnemy{

    private Level level;
    private boolean isInactive;

    public Beholder(Platform platform, Level level){
        super(platform);
        health = Constants.ENEMY_HEALTH * 2;
        this.level = level;
        isInactive = true;
    }

    public void update(float delta){

        if(health < Constants.ENEMY_HEALTH * 2){
            if(isInactive){
                isInactive = false;
            }

            super.update(delta);

            if(findSerJumpsALot() == Direction.LEFT){
                setDirection(Direction.RIGHT);
            }
            else {
                setDirection(Direction.LEFT);
            }
        }
        if(health == 0){
            level.setExitPortal(new ExitPortal(new Vector2(position.x, position.y + 50)));
        }

    }

    public void render(SpriteBatch batch){

        TextureRegion region;

        if(isInactive){
            region = Assets.instance.beholderAssets.beholderInactive;
            batch.draw(region, position.x, position.y, region.getRegionWidth() * 4, region.getRegionHeight() * 4);
            return;
        }

        float walkTimeSeconds = Utils.secondsSince(startTime);
        if(getDirection() == Direction.RIGHT){
            region = Assets.instance.beholderAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
        }
        else {
            region = Assets.instance.beholderAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
        }

        batch.draw(region, position.x, position.y, region.getRegionWidth() * 4, region.getRegionHeight() * 4);
    }

    private Direction findSerJumpsALot(){
        if(level.getSerJumpsALot().getPosition().x < position.x){
            return Direction.RIGHT;
        }
        else {
            return Direction.LEFT;
        }
    }

    public boolean isInactive() {
        return isInactive;
    }

}
