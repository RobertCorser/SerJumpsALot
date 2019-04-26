package com.udacity.gamedev.serjumpsalot.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.serjumpsalot.util.Assets;
import com.udacity.gamedev.serjumpsalot.util.Constants;
import com.udacity.gamedev.serjumpsalot.util.Enums;
import com.udacity.gamedev.serjumpsalot.util.Utils;

public class JumpingEnemy extends Enemy {


    private Vector2 velocity;
    private Vector2 lastFramePosition;
    private TextureRegion region;

    public JumpingEnemy(Platform platform){
        super(platform);

        velocity = new Vector2();
        lastFramePosition = new Vector2();

        //Default value
        region = Assets.instance.enemyAssets.greenEnemyRight1;
    }

    public void render(SpriteBatch batch){

        if(getDirection() == Enums.Direction.RIGHT){
            float walkTimeSeconds = Utils.secondsSince(startTime);
            region = Assets.instance.enemyAssets.greenEnemyWalkingRight.getKeyFrame(walkTimeSeconds);
            batch.draw(region, position.x, position.y, region.getRegionWidth(), region.getRegionHeight());
        }
        else {
            float walkTimeSeconds = Utils.secondsSince(startTime);
            region = Assets.instance.enemyAssets.greenEnemyWalkingRight.getKeyFrame(walkTimeSeconds);
            Utils.drawTextureRegionFlipped(batch, region, position.x, position.y);
        }

    }

    public void update(float delta){
        super.update(delta);

        lastFramePosition.set(position);
        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);


        if(position.y < super.getPlatform().top){
            position.y = super.getPlatform().top;
        }

        if(Utils.secondsSince(startTime) > 3){
            jump();
            startTime = TimeUtils.nanoTime();
            Gdx.app.log("", "ENEMY JUMPED");
        }

    }

    private void jump(){
        velocity.y = 200;
    }

    public TextureRegion getTextureRegion() {
        return region;
    }
}
