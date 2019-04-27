package com.udacity.gamedev.serjumpsalot.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;


public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public GigaGalAssets gigaGalAssets;
    public PlatformAssets platformAssets;
    public BulletAssets bulletAssets;
    public EnemyAssets enemyAssets;
    public BeholderAssets beholderAssets;
    public ExplosionAssets explosionAssets;
    public PowerupAssets powerupAssets;
    public ExitPortalAssets exitPortalAssets;
    public OnscreenControlsAssets onscreenControlsAssets;

    private AssetManager assetManager;

    public Texture backgroundImg;


    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        gigaGalAssets = new GigaGalAssets(atlas);
        platformAssets = new PlatformAssets(atlas);
        bulletAssets = new BulletAssets(atlas);
        enemyAssets = new EnemyAssets(atlas);
        beholderAssets = new BeholderAssets(atlas);
        explosionAssets = new ExplosionAssets(atlas);
        powerupAssets = new PowerupAssets(atlas);
        exitPortalAssets = new ExitPortalAssets(atlas);
        onscreenControlsAssets = new OnscreenControlsAssets(atlas);

        backgroundImg = new Texture(Constants.BACKGROUND_PATH);
        backgroundImg.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class GigaGalAssets {

        public final AtlasRegion standingLeft;
        public final AtlasRegion standingRight;
        public final AtlasRegion walkingLeft;
        public final AtlasRegion walkingRight;
        public final AtlasRegion jumpingLeft;
        public final AtlasRegion jumpingRight;

        public final Animation walkingLeftAnimation;
        public final Animation walkingRightAnimation;


        public GigaGalAssets(TextureAtlas atlas) {
            standingLeft = atlas.findRegion(Constants.STANDING_LEFT);
            standingRight = atlas.findRegion(Constants.STANDING_RIGHT);
            walkingLeft = atlas.findRegion(Constants.WALKING_LEFT_2);
            walkingRight = atlas.findRegion(Constants.WALKING_RIGHT_2);

            jumpingLeft = atlas.findRegion(Constants.JUMPING_LEFT);
            jumpingRight = atlas.findRegion(Constants.JUMPING_RIGHT);

            Array<AtlasRegion> walkingLeftFrames = new Array<AtlasRegion>();
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_1));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_3));
            walkingLeftAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingLeftFrames, PlayMode.LOOP);

            Array<AtlasRegion> walkingRightFrames = new Array<AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_1));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_3));
            walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRightFrames, PlayMode.LOOP);
        }
    }

    public class PlatformAssets {

        public final AtlasRegion forestPlatformSolid;
        public final AtlasRegion forestPlatformThin;
        public final AtlasRegion forestPlatformThinHalf;
        public final AtlasRegion forestPlatformThinQuarter;
        public final AtlasRegion platformDirt;

        public final AtlasRegion castlePlatformSolid;
        public final AtlasRegion castlePlatformThin;
        public final AtlasRegion castlePlatformThinHalf;
        public final AtlasRegion castlePlatformThinQuarter;

        public final AtlasRegion castlePlatformDirt;

        public PlatformAssets(TextureAtlas atlas) {
            forestPlatformSolid = atlas.findRegion(Constants.FOREST_PLATFORM_SOLID);
            forestPlatformThin = atlas.findRegion(Constants.FOREST_PLATFORM_THIN);
            forestPlatformThinHalf = atlas.findRegion(Constants.FOREST_PLATFORM_THIN_HALF);
            forestPlatformThinQuarter = atlas.findRegion(Constants.FOREST_PLATFORM_THIN_QUARTER);

            platformDirt = atlas.findRegion(Constants.PLATFORM_DIRT);

            castlePlatformSolid = atlas.findRegion(Constants.CASTLE_PLATFORM_SOLID);
            castlePlatformThin = atlas.findRegion(Constants.CASTLE_PLATFORM_THIN);
            castlePlatformThinHalf = atlas.findRegion(Constants.CASTLE_PLATFORM_THIN_HALF);
            castlePlatformThinQuarter = atlas.findRegion(Constants.CASTLE_PLATFORM_THIN_QUARTER);

            castlePlatformDirt = atlas.findRegion(Constants.CASTLE_PLATFORM_DIRT);
            //int edge = Constants.PLATFORM_EDGE;
            //platformNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class BulletAssets {

        public final AtlasRegion bullet1;
        public final AtlasRegion bullet2;
        public final AtlasRegion bullet3;
        public final AtlasRegion bullet4;

        public final Animation bulletAnimation;

        public BulletAssets(TextureAtlas atlas) {
            bullet1 = atlas.findRegion(Constants.BULLET_SPRITE_1);
            bullet2 = atlas.findRegion(Constants.BULLET_SPRITE_2);
            bullet3 = atlas.findRegion(Constants.BULLET_SPRITE_3);
            bullet4 = atlas.findRegion(Constants.BULLET_SPRITE_4);

            Array<AtlasRegion> bulletFrames = new Array<AtlasRegion>();
            bulletFrames.addAll(bullet1, bullet2, bullet3, bullet4);
            bulletAnimation = new Animation(Constants.BULLET_LOOP_DURATION, bulletFrames, PlayMode.LOOP);
        }

    }

    public class EnemyAssets {

        public final AtlasRegion blueEnemyLeft1;
        public final AtlasRegion blueEnemyLeft2;
        public final AtlasRegion blueEnemyLeft3;
        public final Animation blueEnemyWalkingLeft;

        public final AtlasRegion blueEnemyRight1;
        public final AtlasRegion blueEnemyRight2;
        public final AtlasRegion blueEnemyRight3;
        public final Animation blueEnemyWalkingRight;

        public final AtlasRegion greenEnemyRight1;
        public final AtlasRegion greenEnemyRight2;
        public final AtlasRegion greenEnemyRight3;
        public final Animation greenEnemyWalkingRight;

        public EnemyAssets(TextureAtlas atlas) {
            blueEnemyLeft1 = atlas.findRegion(Constants.BLUE_ENEMY_LEFT_1);
            blueEnemyLeft2 = atlas.findRegion(Constants.BLUE_ENEMY_LEFT_2);
            blueEnemyLeft3 = atlas.findRegion(Constants.BLUE_ENEMY_LEFT_3);
            Array<AtlasRegion> walkingLeft = new Array<AtlasRegion>();
            walkingLeft.addAll(blueEnemyLeft1, blueEnemyLeft2, blueEnemyLeft3);
            blueEnemyWalkingLeft = new Animation(Constants.WALK_LOOP_DURATION, walkingLeft, PlayMode.LOOP);

            blueEnemyRight1 = atlas.findRegion(Constants.BLUE_ENEMY_RIGHT_1);
            blueEnemyRight2 = atlas.findRegion(Constants.BLUE_ENEMY_RIGHT_2);
            blueEnemyRight3 = atlas.findRegion(Constants.BLUE_ENEMY_RIGHT_3);
            Array<AtlasRegion> walkingRight = new Array<AtlasRegion>();
            walkingRight.addAll(blueEnemyRight1, blueEnemyRight2, blueEnemyRight3);
            blueEnemyWalkingRight = new Animation(Constants.WALK_LOOP_DURATION, walkingRight, PlayMode.LOOP);

            greenEnemyRight1 = atlas.findRegion(Constants.GREEN_ENEMY_RIGHT_1);
            greenEnemyRight2 = atlas.findRegion(Constants.GREEN_ENEMY_RIGHT_2);
            greenEnemyRight3 = atlas.findRegion(Constants.GREEN_ENEMY_RIGHT_3);
            Array<AtlasRegion> greenWalkingRight = new Array<AtlasRegion>();
            greenWalkingRight.addAll(greenEnemyRight1, greenEnemyRight2, greenEnemyRight3);
            greenEnemyWalkingRight = new Animation(Constants.WALK_LOOP_DURATION, greenWalkingRight, PlayMode.LOOP);

        }
    }

    public class BeholderAssets{

        public final AtlasRegion beholderInactive;

        public final AtlasRegion beholderLeft1;
        public final AtlasRegion beholderLeft2;
        public final AtlasRegion beholderLeft3;
        public final AtlasRegion beholderRight1;
        public final AtlasRegion beholderRight2;
        public final AtlasRegion beholderRight3;

        public final Animation walkingLeftAnimation;
        public final Animation walkingRightAnimation;

        public BeholderAssets(TextureAtlas atlas){
            beholderInactive = atlas.findRegion(Constants.BEHOLDER_INACTIVE);

            beholderLeft1 = atlas.findRegion(Constants.BEHOLDER_LEFT_1);
            beholderLeft2 = atlas.findRegion(Constants.BEHOLDER_LEFT_2);
            beholderLeft3 = atlas.findRegion(Constants.BEHOLDER_LEFT_3);
            Array<AtlasRegion> walkingLeft = new Array<AtlasRegion>();
            walkingLeft.addAll(beholderLeft1, beholderLeft2, beholderLeft3);
            walkingLeftAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingLeft, PlayMode.LOOP_PINGPONG);


            beholderRight1 = atlas.findRegion(Constants.BEHOLDER_RIGHT_1);
            beholderRight2 = atlas.findRegion(Constants.BEHOLDER_RIGHT_2);
            beholderRight3 = atlas.findRegion(Constants.BEHOLDER_RIGHT_3);
            Array<AtlasRegion> walkingRight = new Array<AtlasRegion>();
            walkingRight.addAll(beholderRight1, beholderRight2, beholderRight3);
            walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRight, PlayMode.LOOP_PINGPONG);
        }
    }

    public class ExplosionAssets {

        public final Animation explosion;

        public ExplosionAssets(TextureAtlas atlas) {

            Array<AtlasRegion> explosionRegions = new Array<AtlasRegion>();
            explosionRegions.add(atlas.findRegion(Constants.EXPLOSION_LARGE));
            explosionRegions.add(atlas.findRegion(Constants.EXPLOSION_MEDIUM));
            explosionRegions.add(atlas.findRegion(Constants.EXPLOSION_SMALL));

            explosion = new Animation(Constants.EXPLOSION_DURATION / explosionRegions.size,
                    explosionRegions, PlayMode.NORMAL);
        }
    }

    public class PowerupAssets {
        public final AtlasRegion powerup;

        public PowerupAssets(TextureAtlas atlas) {
            powerup = atlas.findRegion(Constants.POWERUP_SPRITE);
        }
    }

    public class ExitPortalAssets {

        public final Animation exitPortal;

        public ExitPortalAssets(TextureAtlas atlas) {
            final AtlasRegion exitPortal1 = atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_1);
            final AtlasRegion exitPortal2 = atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_2);
            final AtlasRegion exitPortal3 = atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_3);
            final AtlasRegion exitPortal4 = atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_4);
            final AtlasRegion exitPortal5 = atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_5);
            final AtlasRegion exitPortal6 = atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_6);

            Array<AtlasRegion> exitPortalFrames = new Array<AtlasRegion>();
            exitPortalFrames.addAll(exitPortal1, exitPortal2, exitPortal3, exitPortal4, exitPortal5, exitPortal6);

            exitPortal = new Animation(Constants.EXIT_PORTAL_FRAME_DURATION, exitPortalFrames);
        }
    }


    public class OnscreenControlsAssets {

        public final AtlasRegion moveRight;
        public final AtlasRegion moveLeft;
        public final AtlasRegion shoot;
        public final AtlasRegion jump;

        public OnscreenControlsAssets(TextureAtlas atlas) {
            moveRight = atlas.findRegion(Constants.MOVE_RIGHT_BUTTON);
            moveLeft = atlas.findRegion(Constants.MOVE_LEFT_BUTTON);
            shoot = atlas.findRegion(Constants.SHOOT_BUTTON);
            jump = atlas.findRegion(Constants.JUMP_BUTTON);
        }


    }

}
