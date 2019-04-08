package com.udacity.gamedev.gigagal.util;


public class Enums {


    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED,
        RECOILING
    }

    public enum WalkState {
        NOT_WALKING,
        WALKING
    }

    public enum PlatformRegion{
        FOREST,
        CASTLE,
        MOUNTAIN
    }

    public enum PlatformType{
        THICK,
        THIN
    }
}
