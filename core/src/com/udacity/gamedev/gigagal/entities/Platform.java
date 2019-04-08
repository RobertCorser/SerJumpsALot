package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;
import com.udacity.gamedev.gigagal.util.Enums.Direction;
import com.udacity.gamedev.gigagal.util.Enums.PlatformRegion;
import com.udacity.gamedev.gigagal.util.Enums.PlatformType;


public class Platform {

    public float top;
    public float bottom;
    public float left;
    public float right;

    public float width;
    public float height;

    //This is used to determine how many platforms this platform in made of; default is 1
    public int length = 1;

    //Used to determine what type of platform and what region it belongs to, default is forest thin
    public PlatformType type = PlatformType.THIN;
    public PlatformRegion region = PlatformRegion.FOREST;

    // This is used by the level loading code to link enemies and platforms.
    String identifier;

    public Platform(float left, float top, float width, float height) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
    }

    public void render(SpriteBatch batch) {
        final float width = right - left;
        final float height = top - bottom;

        if (length > 1) {
            for (int x = 0; x < length; x++) {
                if (type == PlatformType.THICK) {
                    batch.draw(Assets.instance.platformAssets.forestPlatformSolid,
                            left + (Assets.instance.platformAssets.forestPlatformSolid.getRegionWidth() * x),
                            bottom,
                            Assets.instance.platformAssets.forestPlatformSolid.getRegionWidth(),
                            Assets.instance.platformAssets.forestPlatformSolid.getRegionHeight());
                }
            }
        } else {
            if(width == 32){
                batch.draw(Assets.instance.platformAssets.forestPlatformThinQuarter,
                        left,
                        bottom,
                        width,
                        height);
            }
            else if(width == 64){
                batch.draw(Assets.instance.platformAssets.forestPlatformThinHalf,
                        left,
                        bottom,
                        width,
                        height);
            }
            else {
                batch.draw(Assets.instance.platformAssets.forestPlatformThin,
                        left,
                        bottom,
                        width,
                        height);
            }

        }
        //Assets.instance.platformAssets.platformNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setRegion(PlatformRegion newRegion) {
        region = newRegion;
    }

    public void setType(PlatformType newType) {
        type = newType;
    }
}
