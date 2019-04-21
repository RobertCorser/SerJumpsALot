package com.udacity.gamedev.serjumpsalot.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.udacity.gamedev.serjumpsalot.util.Assets;
import com.udacity.gamedev.serjumpsalot.util.Enums.Direction;
import com.udacity.gamedev.serjumpsalot.util.Utils;

public class WalkingEnemy extends Enemy{

    private TextureRegion region;

    public WalkingEnemy(Platform platform){
        super(platform);
        region = Assets.instance.enemyAssets.blueEnemyRight1;
    }

    public void render(SpriteBatch batch){


        if(getDirection() == Direction.RIGHT){
            float walkTimeSeconds = Utils.secondsSince(startTime);
            region = Assets.instance.enemyAssets.blueEnemyWalkingRight.getKeyFrame(walkTimeSeconds);
        }
        else {
            float walkTimeSeconds = Utils.secondsSince(startTime);
            region = Assets.instance.enemyAssets.blueEnemyWalkingLeft.getKeyFrame(walkTimeSeconds);
        }

        batch.draw(region, position.x, position.y, region.getRegionWidth(), region.getRegionHeight());
    }

    public TextureRegion getTextureRegion(){
        return region;
    }

}
