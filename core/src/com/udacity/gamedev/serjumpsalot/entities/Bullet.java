package com.udacity.gamedev.serjumpsalot.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.serjumpsalot.Level;
import com.udacity.gamedev.serjumpsalot.util.Assets;
import com.udacity.gamedev.serjumpsalot.util.Constants;
import com.udacity.gamedev.serjumpsalot.util.Enums.Direction;
import com.udacity.gamedev.serjumpsalot.util.Utils;

public class Bullet {

    private final Direction direction;
    private final Level level;
    public boolean active;
    private Vector2 position;
    private long bulletStartTime;
    private TextureRegion region;
    private Sound hitSound;

    public Bullet(Level level, Vector2 position, Direction direction) {
        this.level = level;
        this.position = position;
        this.direction = direction;
        active = true;
        bulletStartTime = TimeUtils.nanoTime();

        hitSound = Gdx.audio.newSound(Gdx.files.internal(Constants.ENEMY_HIT_SOUND));

        //Default value
        region = Assets.instance.bulletAssets.bullet1;
    }

    public void update(float delta) {
        switch (direction) {
            case LEFT:
                position.x -= delta * Constants.BULLET_MOVE_SPEED;
                break;
            case RIGHT:
                position.x += delta * Constants.BULLET_MOVE_SPEED;
                break;
        }

        for (WalkingEnemy enemy : level.getEnemies()) {
            if (position.dst(enemy.position) < Constants.ENEMY_SHOT_RADIUS) {
                level.spawnExplosion(position);
                active = false;
                enemy.health -= 1;
                level.score += Constants.ENEMY_HIT_SCORE;
                hitSound.play(Constants.ENEMY_HIT_SOUND_VOL);
            }
        }

        for (JumpingEnemy enemy : level.getJumpingEnemies()) {
            if (position.dst(enemy.position) < Constants.ENEMY_SHOT_RADIUS) {
                level.spawnExplosion(position);
                active = false;
                enemy.health -= 1;
                level.score += Constants.ENEMY_HIT_SCORE;
            }
        }

        if(level.getBeholder() != null){
            Rectangle enemyBounds = new Rectangle(
                    level.getBeholder().position.x,
                    level.getBeholder().position.y,
                    level.getBeholder().getTextureRegion().getRegionWidth() * 4,
                    level.getBeholder().getTextureRegion().getRegionHeight() * 4
            );
            Rectangle bulletBounds = new Rectangle(position.x, position.y, region.getRegionWidth(), region.getRegionHeight());
            if (bulletBounds.overlaps(enemyBounds)) {
                level.spawnExplosion(position);
                active = false;
                level.getBeholder().health -= 1;
                level.score += Constants.ENEMY_HIT_SCORE;
            }
        }

        final float worldWidth = level.getViewport().getWorldWidth();
        final float cameraX = level.getViewport().getCamera().position.x;

        if (position.x < cameraX - worldWidth / 2 || position.x > cameraX + worldWidth / 2) {
            active = false;
        }
    }

    public void render(SpriteBatch batch) {
        float timeElapsed = Utils.secondsSince(bulletStartTime);
        region = Assets.instance.bulletAssets.bulletAnimation.getKeyFrame(timeElapsed);

        if(direction == Direction.RIGHT){
            Utils.drawTextureRegion(batch, region, position, Constants.BULLET_CENTER);
        }
        else {
            Utils.drawTextureRegionFlipped(batch, region, position, Constants.BULLET_CENTER);
        }
    }
}
