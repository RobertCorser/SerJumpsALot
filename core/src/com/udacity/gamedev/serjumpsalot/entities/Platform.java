package com.udacity.gamedev.serjumpsalot.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.udacity.gamedev.serjumpsalot.util.Assets;
import com.udacity.gamedev.serjumpsalot.util.Enums.PlatformRegion;
import com.udacity.gamedev.serjumpsalot.util.Enums.PlatformType;


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

        //Default value
        TextureRegion platformTexture = null;
        TextureRegion dirtTexture = null;

        if (length > 1) {
            for (int x = 0; x < length; x++) {
                if (type == PlatformType.THICK) {
                    if (region == PlatformRegion.FOREST) {
                        platformTexture = Assets.instance.platformAssets.forestPlatformSolid;
                        dirtTexture = Assets.instance.platformAssets.platformDirt;
                    } else if (region == PlatformRegion.CASTLE) {
                        platformTexture = Assets.instance.platformAssets.castlePlatformSolid;
                        dirtTexture = Assets.instance.platformAssets.castlePlatformDirt;
                    }

                    //Draw top of platform
                    batch.draw(platformTexture,
                            left + (platformTexture.getRegionWidth() * x),
                            bottom,
                            platformTexture.getRegionWidth(),
                            platformTexture.getRegionHeight());

                    //Draw dirt below platform
                    for (int y = 1; y < 7; y++) {
                        batch.draw(dirtTexture,
                                left + (dirtTexture.getRegionWidth() * x),
                                bottom - dirtTexture.getRegionHeight() * y,
                                dirtTexture.getRegionWidth(),
                                dirtTexture.getRegionHeight());
                    }


                }
            }
        } else {
            if (width == 32) {
                if (region == PlatformRegion.FOREST) {
                    platformTexture = Assets.instance.platformAssets.forestPlatformThinQuarter;
                }
                else {
                    platformTexture = Assets.instance.platformAssets.castlePlatformThinQuarter;
                }

            } else if (width == 64) {
                if (region == PlatformRegion.FOREST) {
                    platformTexture = Assets.instance.platformAssets.forestPlatformThinHalf;
                }
                else {
                    platformTexture = Assets.instance.platformAssets.forestPlatformThinHalf;
                }

            } else {
                if (region == PlatformRegion.FOREST) {
                    platformTexture = Assets.instance.platformAssets.forestPlatformThin;
                } else {
                    platformTexture = Assets.instance.platformAssets.castlePlatformThin;
                }
            }

            batch.draw(platformTexture,
                    left,
                    bottom,
                    width,
                    height);

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
