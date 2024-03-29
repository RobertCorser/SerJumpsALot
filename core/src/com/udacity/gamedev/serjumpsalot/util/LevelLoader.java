package com.udacity.gamedev.serjumpsalot.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.udacity.gamedev.serjumpsalot.Level;
import com.udacity.gamedev.serjumpsalot.entities.Beholder;
import com.udacity.gamedev.serjumpsalot.entities.Enemy;
import com.udacity.gamedev.serjumpsalot.entities.ExitPortal;
import com.udacity.gamedev.serjumpsalot.entities.JumpingEnemy;
import com.udacity.gamedev.serjumpsalot.entities.SerJumpsALot;
import com.udacity.gamedev.serjumpsalot.entities.Platform;
import com.udacity.gamedev.serjumpsalot.entities.Powerup;
import com.udacity.gamedev.serjumpsalot.entities.WalkingEnemy;
import com.udacity.gamedev.serjumpsalot.util.Enums.PlatformType;
import com.udacity.gamedev.serjumpsalot.util.Enums.PlatformRegion;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Comparator;


public class LevelLoader {

    public static final String TAG = LevelLoader.class.toString();


    public static Level load(String path) {
        Level level = new Level();


        FileHandle file = Gdx.files.internal(path);

        JSONParser parser = new JSONParser();
        JSONObject rootJsonObject;

        try {
            rootJsonObject = (JSONObject) parser.parse(file.reader());

            //EXPERIMENTAL CODE
            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);

            JSONArray platforms = (JSONArray) composite.get("sComposites");

            loadPlatforms(platforms, level);

            JSONArray nonPlatformObjects = (JSONArray) composite.get("sImages");
            loadNonPlatformEntities(level, nonPlatformObjects);


            /*  //ORIGINAL CODE
            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);

            JSONArray platforms = (JSONArray) composite.get(Constants.LEVEL_9PATCHES);
            loadPlatforms(platforms, level);

            JSONArray nonPlatformObjects = (JSONArray) composite.get(Constants.LEVEL_IMAGES);
            loadNonPlatformEntities(level, nonPlatformObjects);
            */

        } catch (Exception ex) {
            Gdx.app.log(TAG, ex.getMessage());
            Gdx.app.log(TAG, Constants.LEVEL_ERROR_MESSAGE);
            ex.printStackTrace();
        }

        return level;
    }

    private static Vector2 extractXY(JSONObject object) {

        Number x = (Number) object.get(Constants.LEVEL_X_KEY);
        Number y = (Number) object.get(Constants.LEVEL_Y_KEY);

        return new Vector2(
                (x == null) ? 0 : x.floatValue(),
                (y == null) ? 0 : y.floatValue()
        );
    }

    private static void loadNonPlatformEntities(Level level, JSONArray nonPlatformObjects) {
        for (Object o : nonPlatformObjects) {
            JSONObject item = (JSONObject) o;

            final Vector2 imagePosition = extractXY(item);

            if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.POWERUP_SPRITE)) {
                final Vector2 powerupPosition = imagePosition.add(Constants.POWERUP_CENTER);
                Gdx.app.log(TAG, "Loaded a powerup at " + powerupPosition);
                level.getPowerups().add(new Powerup(powerupPosition));
            } else if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.STANDING_RIGHT)) {
                final Vector2 gigaGalPosition = imagePosition.add(Constants.GIGAGAL_EYE_POSITION);
                Gdx.app.log(TAG, "Loaded SerJumpsALot at " + gigaGalPosition);
                level.setSerJumpsALot(new SerJumpsALot(gigaGalPosition, level));
            } else if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.EXIT_PORTAL_SPRITE_1)) {
                final Vector2 exitPortalPosition = imagePosition.add(Constants.EXIT_PORTAL_CENTER);
                Gdx.app.log(TAG, "Loaded the exit portal at " + exitPortalPosition);
                level.setExitPortal(new ExitPortal(exitPortalPosition));
            }

        }
    }


    private static void loadPlatforms(JSONArray array, Level level) {

        Array<Platform> platformArray = new Array<Platform>();

        for (int x = 0; x < array.size(); x++) {
            JSONObject platformObject = (JSONObject) array.get(x);

            Vector2 bottomLeft = extractXY(platformObject);

            final float width = ((Number) platformObject.get(Constants.LEVEL_WIDTH_KEY)).floatValue();
            final float height = ((Number) platformObject.get(Constants.LEVEL_HEIGHT_KEY)).floatValue();

            Platform platform = new Platform(bottomLeft.x, bottomLeft.y + height, width, height);

            JSONObject platformComposite = (JSONObject) platformObject.get("composite");

            JSONArray platformSImages = (JSONArray) platformComposite.get("sImages");

            getType(platformSImages, platform);
            getRegion(platformSImages, platform);

            if (platformSImages.size() > 1) {
                platform.setLength(platformSImages.size());
            }

            platformArray.add(platform);

            final String identifier = (String) platformObject.get(Constants.LEVEL_IDENTIFIER_KEY);


            if (identifier != null && identifier.equals(Constants.LEVEL_WALKING_ENEMY_TAG)) {
                final WalkingEnemy enemy = new WalkingEnemy(platform);
                level.getEnemies().add(enemy);
            }
            if (identifier != null && identifier.equals(Constants.LEVEL_JUMPING_ENEMY_TAG)) {
                final JumpingEnemy enemy = new JumpingEnemy(platform);
                level.getJumpingEnemies().add(enemy);
            }
            if (identifier != null && identifier.equals(Constants.LEVEL_BEHOLDER_TAG)) {
                level.setBeholder(platform);
            }
        }

        /*

        for (Object object : array) {
            final JSONObject platformObject = (JSONObject) object;

            Vector2 bottomLeft = extractXY(platformObject);

            final float width = ((Number) platformObject.get(Constants.LEVEL_WIDTH_KEY)).floatValue();
            final float height = ((Number) platformObject.get(Constants.LEVEL_HEIGHT_KEY)).floatValue();


            final Platform platform = new Platform(bottomLeft.x, bottomLeft.y + height, width, height);

            platformArray.add(platform);


            final String identifier = (String) platformObject.get(Constants.LEVEL_IDENTIFIER_KEY);


            if (identifier != null && identifier.equals(Constants.LEVEL_ENEMY_TAG)) {
                final Enemy enemy = new Enemy(platform);
                level.getEnemies().add(enemy);
            }


        }
        */
        platformArray.sort(new Comparator<Platform>() {
            @Override
            public int compare(Platform o1, Platform o2) {
                if (o1.top < o2.top) {
                    return 1;
                } else if (o1.top > o2.top) {
                    return -1;
                }
                return 0;
            }
        });

        level.getPlatforms().addAll(platformArray);

    }

    private static void getType(JSONArray sImages, Platform platform) {
        JSONObject rootPlatform = (JSONObject) sImages.get(0);

        String platformType = (String) rootPlatform.get("imageName");

        if (platformType.contains("solid")) {
            platform.setType(PlatformType.THICK);
        } else if (platformType.contains("thin")) {
            platform.setType(PlatformType.THIN);
        } else {
            Gdx.app.error(TAG, "Invalid platform type");
        }
    }

    private static void getRegion(JSONArray sImages, Platform platform) {
        JSONObject rootPlatform = (JSONObject) sImages.get(0);

        String platformRegion = (String) rootPlatform.get("imageName");

        if (platformRegion.contains("forest")) {
            platform.setRegion(PlatformRegion.FOREST);
        } else if (platformRegion.contains("castle")) {
            platform.setRegion(PlatformRegion.CASTLE);
        } else if (platformRegion.contains("mountain")) {
            platform.setRegion(PlatformRegion.MOUNTAIN);
        } else {
            Gdx.app.error(TAG, "Invalid platform region");
        }

    }


}
