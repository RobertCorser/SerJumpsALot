package com.udacity.gamedev.serjumpsalot.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    // World/Camera
    public static final Color BACKGROUND_COLOR = Color.SKY;
    public static final float WORLD_SIZE = 160;
    public static final float KILL_PLANE = -50;
    public static final float GRAVITY = 10;
    public static final float CHASE_CAM_MOVE_SPEED = WORLD_SIZE;
    public static final String TEXTURE_ATLAS = "images/serjumpsalot.pack.atlas";

    //Touch Controls
    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 200;

    // SerJumpsALot
    public static final Vector2 GIGAGAL_EYE_POSITION = new Vector2(16, 24);
    public static final float GIGAGAL_EYE_HEIGHT = 16.0f;
    public static final float GIGAGAL_STANCE_WIDTH = 19.0f;
    public static final Vector2 GIGAGAL_CANNON_OFFSET = new Vector2(12, -7);
    public static final float GIGAGAL_HEIGHT = 23.0f;
    public static final float GIGAGAL_MOVE_SPEED = 100;

    public static final float JUMP_SPEED = 200;
    public static final Vector2 KNOCKBACK_VELOCITY = new Vector2(200, 200);
    //ORIGINAL VALUE public static final float MAX_JUMP_DURATION = .1f;
    public static final float MAX_JUMP_DURATION = .25f;
    public static final int INITIAL_AMMO = 10;
    public static final int INITIAL_LIVES = 3;

    public static final String STANDING_RIGHT = "standing-right";
    public static final String STANDING_LEFT = "standing-left";
    public static final String JUMPING_RIGHT = "jumping-right";
    public static final String JUMPING_LEFT = "jumping-left";
    public static final String WALKING_RIGHT_1 = "walk-1-right";
    public static final String WALKING_LEFT_1 = "walk-1-left";
    public static final String WALKING_RIGHT_2 = "walk-2-right";
    public static final String WALKING_LEFT_2 = "walk-2-left";
    public static final String WALKING_RIGHT_3 = "walk-3-right";
    public static final String WALKING_LEFT_3 = "walk-3-left";
    public static final float WALK_LOOP_DURATION = 0.25f;

    // Platform

    public static final String FOREST_PLATFORM_SOLID = "forest_platform_solid";
    public static final String FOREST_PLATFORM_THIN = "forest_platform_thin";
    public static final String FOREST_PLATFORM_THIN_HALF = "forest_platform_thin_half";
    public static final String FOREST_PLATFORM_THIN_QUARTER = "forest_platform_thin_quarter";
    public static final String PLATFORM_DIRT = "forest_platform_dirt";

    public static final String CASTLE_PLATFORM_SOLID = "castle_platform_solid";
    public static final String CASTLE_PLATFORM_THIN = "castle_platform_thin";
    public static final String CASTLE_PLATFORM_THIN_HALF = "castle_platform_thin_half";
    public static final String CASTLE_PLATFORM_THIN_QUARTER = "castle_platform_thin_quarter";
    public static final String CASTLE_PLATFORM_DIRT = "castle_platform_black_darkness_of_death";

    // Enemy
    public static final int ENEMY_SIZE = 16;
    public static final String BLUE_ENEMY_LEFT_1 = "azul-left-1";
    public static final String BLUE_ENEMY_LEFT_2 = "azul-left-2";
    public static final String BLUE_ENEMY_LEFT_3 = "azul-left-3";
    public static final String BLUE_ENEMY_RIGHT_1 = "azul-right-1";
    public static final String BLUE_ENEMY_RIGHT_2 = "azul-right-2";
    public static final String BLUE_ENEMY_RIGHT_3 = "azul-right-3";

    public static final String GREEN_ENEMY_RIGHT_1 = "verde-1";
    public static final String GREEN_ENEMY_RIGHT_2 = "verde-2";
    public static final String GREEN_ENEMY_RIGHT_3 = "verde-3";


    public static final float ENEMY_MOVEMENT_SPEED = 10;
    public static final float ENEMY_BOB_AMPLITUDE = 2;
    public static final float ENEMY_BOB_PERIOD = 3.0f;
    public static final int ENEMY_HEALTH = 5;
    public static final float ENEMY_COLLISION_RADIUS = 15;
    public static final float ENEMY_SHOT_RADIUS = 17;

    //Beholder
    public static final String BEHOLDER_LEFT_1 = "beholder-left-1";
    public static final String BEHOLDER_LEFT_2 = "beholder-left-2";
    public static final String BEHOLDER_LEFT_3 = "beholder-left-3";

    public static final String BEHOLDER_RIGHT_1 = "beholder-right-1";
    public static final String BEHOLDER_RIGHT_2 = "beholder-right-2";
    public static final String BEHOLDER_RIGHT_3 = "beholder-right-3";

    // Bullet
    public static final String BULLET_SPRITE_1 = "missile-1";
    public static final String BULLET_SPRITE_2 = "missile-2";
    public static final String BULLET_SPRITE_3 = "missile-3";
    public static final String BULLET_SPRITE_4 = "missile-4";
    public static final float BULLET_MOVE_SPEED = 150;
    public static final Vector2 BULLET_CENTER = new Vector2(3, 2);
    public static final float BULLET_LOOP_DURATION = 0.15f;

    // Explosion
    public static final String EXPLOSION_LARGE = "explosion-large";
    public static final String EXPLOSION_MEDIUM = "explosion-medium";
    public static final String EXPLOSION_SMALL = "explosion-small";
    public static final Vector2 EXPLOSION_CENTER = new Vector2(8, 8);
    public static final float EXPLOSION_DURATION = 0.5f;

    // Powerup
    public static final String POWERUP_SPRITE = "powerup";
    public static final Vector2 POWERUP_CENTER = new Vector2(7, 5);
    public static final int POWERUP_AMMO = 10;

    // Exit Portal
    public static final String EXIT_PORTAL_SPRITE_1 = "exit-portal-1";
    public static final String EXIT_PORTAL_SPRITE_2 = "exit-portal-2";
    public static final String EXIT_PORTAL_SPRITE_3 = "exit-portal-3";
    public static final String EXIT_PORTAL_SPRITE_4 = "exit-portal-4";
    public static final String EXIT_PORTAL_SPRITE_5 = "exit-portal-5";
    public static final String EXIT_PORTAL_SPRITE_6 = "exit-portal-6";
    public static final Vector2 EXIT_PORTAL_CENTER = new Vector2(31, 31);
    public static final float EXIT_PORTAL_RADIUS = 28;
    public static final float EXIT_PORTAL_FRAME_DURATION = 0.1f;

    // Level Loading
    public static final String LEVEL_COMPOSITE = "composite";
    public static final String LEVEL_9PATCHES = "sImage9patchs";
    public static final String LEVEL_IMAGES = "sImages";
    public static final String LEVEL_ERROR_MESSAGE = "There was a problem loading the level.";
    public static final String LEVEL_IMAGENAME_KEY = "imageName";
    public static final String LEVEL_X_KEY = "x";
    public static final String LEVEL_Y_KEY = "y";
    public static final String LEVEL_WIDTH_KEY = "width";
    public static final String LEVEL_HEIGHT_KEY = "height";
    public static final String LEVEL_IDENTIFIER_KEY = "itemIdentifier";
    public static final String LEVEL_WALKING_ENEMY_TAG = "wenemy";
    public static final String LEVEL_JUMPING_ENEMY_TAG = "jenemy";
    public static final String[] LEVELS = {"levels/Level1.dt", "levels/Level2.dt"};

    // HUD
    public static final float HUD_VIEWPORT_SIZE = 480;
    public static final float HUD_MARGIN = 20;
    public static final String HUD_AMMO_LABEL = "Ammo: ";
    public static final String HUD_SCORE_LABEL = "Score: ";

    // Victory/Game Over screens
    public static final float LEVEL_END_DURATION = 5;
    public static final String VICTORY_MESSAGE = "You Win! Onward brave Knight!";
    public static final String GAME_OVER_MESSAGE = "You Lose! Good Day Ser!";
    public static final int EXPLOSION_COUNT =500;
    public static final int ENEMY_COUNT =200;
    public static final String FONT_FILE = "font/header.fnt";

    // Scoring
    public static final int ENEMY_KILL_SCORE = 100;
    public static final int ENEMY_HIT_SCORE = 25;
    public static final int POWERUP_SCORE = 50;

    public static final String BACKGROUND_PATH = "images/cloud_background.png";

    // Onscreen Controls
    public static final String MOVE_LEFT_BUTTON = "button-move-left";
    public static final String MOVE_RIGHT_BUTTON = "button-move-right";
    public static final String SHOOT_BUTTON = "button-shoot";
    public static final String JUMP_BUTTON = "button-jump";
    public static final Vector2 BUTTON_CENTER = new Vector2(15, 15);
    public static final float BUTTON_RADIUS = 32;

}
