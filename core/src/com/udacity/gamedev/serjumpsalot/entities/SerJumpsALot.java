package com.udacity.gamedev.serjumpsalot.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.serjumpsalot.Level;
import com.udacity.gamedev.serjumpsalot.util.Assets;
import com.udacity.gamedev.serjumpsalot.util.Constants;
import com.udacity.gamedev.serjumpsalot.util.Enums;
import com.udacity.gamedev.serjumpsalot.util.Enums.Direction;
import com.udacity.gamedev.serjumpsalot.util.Enums.JumpState;
import com.udacity.gamedev.serjumpsalot.util.Enums.WalkState;
import com.udacity.gamedev.serjumpsalot.util.Utils;

public class SerJumpsALot {

    public final static String TAG = SerJumpsALot.class.getName();
    public boolean jumpButtonPressed;
    public boolean leftButtonPressed;
    public boolean rightButtonPressed;
    private Level level;
    private Vector2 spawnLocation;
    private Vector2 position;
    private Vector2 lastFramePosition;
    private Vector2 velocity;
    private Direction facing;
    private JumpState jumpState;
    private WalkState walkState;
    private long walkStartTime;
    private long jumpStartTime;
    private int ammo;
    private int lives;

    public SerJumpsALot(Vector2 spawnLocation, Level level) {
        this.spawnLocation = spawnLocation;
        this.level = level;
        position = new Vector2();
        lastFramePosition = new Vector2();
        velocity = new Vector2();
        init();
    }

    public int getAmmo() {
        return ammo;
    }

    public int getLives() {
        return lives;
    }

    public void init() {
        ammo = Constants.INITIAL_AMMO;
        lives = Constants.INITIAL_LIVES;
        respawn();
    }

    private void respawn() {
        position.set(spawnLocation);
        lastFramePosition.set(spawnLocation);
        velocity.setZero();
        jumpState = Enums.JumpState.FALLING;
        facing = Direction.RIGHT;
        walkState = Enums.WalkState.NOT_WALKING;

    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float delta, Array<Platform> platforms) {

        lastFramePosition.set(position);
        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        if (position.y < Constants.KILL_PLANE) {
            lives--;
            if (lives > -1) {
                respawn();
            }
        }

        // Land on/fall off platforms
        if (jumpState != Enums.JumpState.JUMPING) {
            if (jumpState != JumpState.RECOILING) {
                jumpState = Enums.JumpState.FALLING;
            }

            for (Platform platform : platforms) {
                //TODO Fix this collision detection
                if (platform.type == Enums.PlatformType.THICK) {
                    if (position.y < platform.top) {
                        float entryX;
                        if (position.x < platform.right && position.x > platform.left) {

                            if (facing == Direction.RIGHT) {
                                position.x = lastFramePosition.x - 1;
                            }
                            if (facing == Direction.LEFT) {
                                position.x = lastFramePosition.x + 1;

                            }

                            position.y = lastFramePosition.y;
                            walkState = WalkState.NOT_WALKING;

                        }
                    }
                }

                if (landedOnPlatform(platform, delta)) {
                    jumpState = Enums.JumpState.GROUNDED;
                    velocity.y = 0;
                    velocity.x = 0;
                    position.y = platform.top + Constants.GIGAGAL_EYE_HEIGHT;

                }
            }
        }

        // Collide with enemies TODO Clean up this garbage
        Rectangle gigaGalBounds = new Rectangle(
                position.x - Constants.GIGAGAL_STANCE_WIDTH / 2,
                position.y - Constants.GIGAGAL_EYE_HEIGHT,
                Constants.GIGAGAL_STANCE_WIDTH,
                Constants.GIGAGAL_HEIGHT);

        for (WalkingEnemy enemy : level.getEnemies()) {
            Rectangle enemyBounds = new Rectangle(
                    enemy.position.x,
                    enemy.position.y,
                    enemy.getTextureRegion().getRegionWidth(),
                    enemy.getTextureRegion().getRegionHeight()
            );
            if (gigaGalBounds.overlaps(enemyBounds)) {

                if (position.x < enemy.position.x) {
                    recoilFromEnemy(Direction.LEFT);
                } else {
                    recoilFromEnemy(Direction.RIGHT);
                }
            }
        }

        for (JumpingEnemy enemy : level.getJumpingEnemies()) {
            Rectangle enemyBounds = new Rectangle(
                    enemy.position.x,
                    enemy.position.y,
                    enemy.getTextureRegion().getRegionWidth(),
                    enemy.getTextureRegion().getRegionHeight()
            );
            if (gigaGalBounds.overlaps(enemyBounds)) {

                if (position.x < enemy.position.x) {
                    recoilFromEnemy(Direction.LEFT);
                } else {
                    recoilFromEnemy(Direction.RIGHT);
                }
            }
        }

        if (level.getBeholder() != null) {
            if (!level.getBeholder().isInactive()) {
                Rectangle enemyBounds = new Rectangle(
                        level.getBeholder().position.x,
                        level.getBeholder().position.y,
                        level.getBeholder().getTextureRegion().getRegionWidth() * 4,
                        level.getBeholder().getTextureRegion().getRegionHeight() * 4
                );
                if (gigaGalBounds.overlaps(enemyBounds)) {

                    if (position.x < level.getBeholder().position.x) {
                        recoilFromEnemy(Direction.LEFT);
                    } else {
                        recoilFromEnemy(Direction.RIGHT);
                    }
                }
            }
        }


        // Move left/right
        if (jumpState != JumpState.RECOILING) {

            if (walkState == WalkState.WALKING) {
                if (facing == Direction.RIGHT) {
                    moveRight(delta);
                } else {
                    moveLeft(delta);
                }
            }

            /*
            boolean left = Gdx.input.isKeyPressed(Keys.LEFT) || leftButtonPressed;
            boolean right = Gdx.input.isKeyPressed(Keys.RIGHT) || rightButtonPressed;

            if (left && !right) {
                moveLeft(delta);
            } else if (right && !left) {
                moveRight(delta);
            } else {
                walkState = Enums.WalkState.NOT_WALKING;
            }
            */
        }


        // Jump
        if (Gdx.input.isKeyPressed(Keys.Z) || jumpButtonPressed) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
            }
        } else {
            endJump();
        }

        // Check powerups
        DelayedRemovalArray<Powerup> powerups = level.getPowerups();
        powerups.begin();
        for (int i = 0; i < powerups.size; i++) {
            Powerup powerup = powerups.get(i);
            Rectangle powerupBounds = new Rectangle(
                    powerup.position.x - Constants.POWERUP_CENTER.x,
                    powerup.position.y - Constants.POWERUP_CENTER.y,
                    Assets.instance.powerupAssets.powerup.getRegionWidth(),
                    Assets.instance.powerupAssets.powerup.getRegionHeight()
            );
            if (gigaGalBounds.overlaps(powerupBounds)) {
                ammo += Constants.POWERUP_AMMO;
                level.score += Constants.POWERUP_SCORE;
                powerups.removeIndex(i);
            }
        }
        powerups.end();

        // Shoot
        if (Gdx.input.isKeyJustPressed(Keys.X)) {
            shoot();
        }
    }


    public void shoot() {
        if (ammo > 0) {

            ammo--;
            Vector2 bulletPosition;

            if (facing == Direction.RIGHT) {
                bulletPosition = new Vector2(
                        position.x + Constants.GIGAGAL_CANNON_OFFSET.x,
                        position.y + Constants.GIGAGAL_CANNON_OFFSET.y
                );
            } else {
                bulletPosition = new Vector2(
                        position.x - Constants.GIGAGAL_CANNON_OFFSET.x,
                        position.y + Constants.GIGAGAL_CANNON_OFFSET.y
                );
            }
            level.spawnBullet(bulletPosition, facing);
        }
    }


    boolean landedOnPlatform(Platform platform, float delta) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.GIGAGAL_EYE_HEIGHT >= platform.top &&
                position.y - Constants.GIGAGAL_EYE_HEIGHT < platform.top) {

            float leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.GIGAGAL_STANCE_WIDTH / 2;

            leftFootIn = (platform.left < leftFoot && platform.right > leftFoot);
            rightFootIn = (platform.left < rightFoot && platform.right > rightFoot);
            straddle = (platform.left > leftFoot && platform.right < rightFoot);

        }
        return leftFootIn || rightFootIn || straddle;
    }


    private void moveLeft(float delta) {
        if (jumpState == Enums.JumpState.GROUNDED && walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = Enums.WalkState.WALKING;
        //facing = Direction.LEFT;
        position.x -= delta * Constants.GIGAGAL_MOVE_SPEED;
    }

    private void moveRight(float delta) {
        if (jumpState == Enums.JumpState.GROUNDED && walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = Enums.WalkState.WALKING;
        //facing = Direction.RIGHT;
        position.x += delta * Constants.GIGAGAL_MOVE_SPEED;
    }

    private void startJump() {
        jumpState = Enums.JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == Enums.JumpState.JUMPING) {
            if (Utils.secondsSince(jumpStartTime) < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == Enums.JumpState.JUMPING) {
            jumpState = Enums.JumpState.FALLING;
        }
    }

    private void recoilFromEnemy(Direction direction) {

        jumpState = JumpState.RECOILING;
        walkState = WalkState.NOT_WALKING;
        velocity.y = Constants.KNOCKBACK_VELOCITY.y;

        if (direction == Direction.LEFT) {
            velocity.x = -Constants.KNOCKBACK_VELOCITY.x;
        } else {
            velocity.x = Constants.KNOCKBACK_VELOCITY.x;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.gigaGalAssets.standingRight;

        if (facing == Direction.RIGHT && jumpState != Enums.JumpState.GROUNDED) {
            region = Assets.instance.gigaGalAssets.jumpingRight;
        } else if (facing == Direction.RIGHT && walkState == Enums.WalkState.NOT_WALKING) {
            region = Assets.instance.gigaGalAssets.standingRight;
        } else if (facing == Direction.RIGHT && walkState == Enums.WalkState.WALKING) {
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = Assets.instance.gigaGalAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
        } else if (facing == Direction.LEFT && jumpState != Enums.JumpState.GROUNDED) {
            region = Assets.instance.gigaGalAssets.jumpingLeft;
        } else if (facing == Direction.LEFT && walkState == Enums.WalkState.NOT_WALKING) {
            region = Assets.instance.gigaGalAssets.standingLeft;
        } else if (facing == Direction.LEFT && walkState == Enums.WalkState.WALKING) {
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = Assets.instance.gigaGalAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
        }

        Utils.drawTextureRegion(batch, region, position, Constants.GIGAGAL_EYE_POSITION);

    }

    public void setFacing(Direction direction) {
        facing = direction;
    }

    public void setWalkState(WalkState walkState) {
        this.walkState = walkState;
    }

    public Direction getFacing() {
        return facing;
    }

    public WalkState getWalkState() {
        return walkState;
    }

}
