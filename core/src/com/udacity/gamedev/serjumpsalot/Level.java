package com.udacity.gamedev.serjumpsalot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.serjumpsalot.entities.Beholder;
import com.udacity.gamedev.serjumpsalot.entities.Bullet;
import com.udacity.gamedev.serjumpsalot.entities.Enemy;
import com.udacity.gamedev.serjumpsalot.entities.ExitPortal;
import com.udacity.gamedev.serjumpsalot.entities.Explosion;
import com.udacity.gamedev.serjumpsalot.entities.JumpingEnemy;
import com.udacity.gamedev.serjumpsalot.entities.SerJumpsALot;
import com.udacity.gamedev.serjumpsalot.entities.Platform;
import com.udacity.gamedev.serjumpsalot.entities.Powerup;
import com.udacity.gamedev.serjumpsalot.entities.WalkingEnemy;
import com.udacity.gamedev.serjumpsalot.util.Constants;
import com.udacity.gamedev.serjumpsalot.util.Enums.Direction;

public class Level {

    public static final String TAG = Level.class.getName();
    public boolean gameOver;
    public boolean victory;
    public Viewport viewport;
    public int score;
    private SerJumpsALot serJumpsALot;
    private ExitPortal exitPortal;
    private Array<Platform> platforms;
    private DelayedRemovalArray<WalkingEnemy> enemies;
    private DelayedRemovalArray<JumpingEnemy> jumpingEnemies;
    private Beholder beholder;
    private DelayedRemovalArray<Bullet> bullets;
    private DelayedRemovalArray<Explosion> explosions;
    private DelayedRemovalArray<Powerup> powerups;
    private TextureRegion background;

    public Level() {
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        serJumpsALot = new SerJumpsALot(new Vector2(50, 50), this);
        platforms = new Array<Platform>();
        enemies = new DelayedRemovalArray<WalkingEnemy>();
        jumpingEnemies = new DelayedRemovalArray<JumpingEnemy>();
        bullets = new DelayedRemovalArray<Bullet>();
        explosions = new DelayedRemovalArray<Explosion>();
        powerups = new DelayedRemovalArray<Powerup>();
        exitPortal = new ExitPortal(new Vector2(200, 200));
        beholder = null;

        gameOver = false;
        victory = false;
        score = 0;


    }

    public static Level debugLevel() {
        Level level = new Level();
        //level.initializeDebugLevel();
        return level;
    }

    public void update(float delta) {

        // TODO: If SerJumpsALot has less than 0 lives, set gameOver to true
        if(serJumpsALot.getLives() < 0){
            gameOver = true;
        }

        if (serJumpsALot.getPosition().dst(exitPortal.position) < Constants.EXIT_PORTAL_RADIUS) {
            victory = true;
        }

        if (!gameOver && !victory) {



            serJumpsALot.update(delta, platforms);

            // Update Bullets
            bullets.begin();
            for (Bullet bullet : bullets) {
                bullet.update(delta);
                if (!bullet.active) {
                    bullets.removeValue(bullet, false);
                }
            }
            bullets.end();

            // Update Enemies
            enemies.begin();
            for (int i = 0; i < enemies.size; i++) {
                WalkingEnemy enemy = enemies.get(i);
                enemy.update(delta);
                if (enemy.health < 1) {
                    spawnExplosion(enemy.position);
                    enemies.removeIndex(i);
                    score += Constants.ENEMY_KILL_SCORE;
                }
            }
            enemies.end();

            // Update Jumping Enemies
            jumpingEnemies.begin();
            for (int i = 0; i < jumpingEnemies.size; i++) {
                JumpingEnemy jumpingEnemy = jumpingEnemies.get(i);
                jumpingEnemy.update(delta);
                if (jumpingEnemy.health < 1) {
                    spawnExplosion(jumpingEnemy.position);
                    jumpingEnemies.removeIndex(i);
                    score += Constants.ENEMY_KILL_SCORE;
                }
            }
            jumpingEnemies.end();

            // Update Explosions
            explosions.begin();
            for (int i = 0; i < explosions.size; i++) {
                if (explosions.get(i).isFinished()) {
                    explosions.removeIndex(i);
                }
            }
            explosions.end();
        }

        if(beholder != null){
            beholder.update(delta);
            if(beholder.health == 0){
                beholder = null;
            }
        }

    }

    public void render(SpriteBatch batch) {

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        //batch.draw(Assets.instance.backgroundImg, -500, 0, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());

        for (Platform platform : platforms) {
            platform.render(batch);
        }

        exitPortal.render(batch);

        for (Powerup powerup : powerups) {
            powerup.render(batch);
        }


        for (WalkingEnemy enemy : enemies) {
            enemy.render(batch);
        }

        for (JumpingEnemy enemy : jumpingEnemies) {
            enemy.render(batch);
        }


        serJumpsALot.render(batch);

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }

        for (Explosion explosion : explosions) {
            explosion.render(batch);
        }

        if(beholder != null){
            beholder.render(batch);
        }

        batch.end();
    }

    private void initializeDebugLevel() {

        serJumpsALot = new SerJumpsALot(new Vector2(15, 40), this);

        exitPortal = new ExitPortal(new Vector2(150, 150));

        platforms = new Array<Platform>();
        bullets = new DelayedRemovalArray<Bullet>();
        enemies = new DelayedRemovalArray<WalkingEnemy>();
        explosions = new DelayedRemovalArray<Explosion>();
        powerups = new DelayedRemovalArray<Powerup>();


        platforms.add(new Platform(15, 100, 30, 20));

        Platform enemyPlatform = new Platform(75, 90, 100, 65);

        enemies.add(new WalkingEnemy(enemyPlatform));

        platforms.add(enemyPlatform);
        platforms.add(new Platform(35, 55, 50, 20));
        platforms.add(new Platform(10, 20, 20, 9));

        powerups.add(new Powerup(new Vector2(20, 110)));
    }

    public void setBeholder(Platform platform){
        beholder = new Beholder(platform, this);
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public DelayedRemovalArray<WalkingEnemy> getEnemies() {
        return enemies;
    }

    public DelayedRemovalArray<Powerup> getPowerups() {
        return powerups;
    }

    public ExitPortal getExitPortal() {
        return exitPortal;
    }

    public void setExitPortal(ExitPortal exitPortal) {
        this.exitPortal = exitPortal;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public SerJumpsALot getSerJumpsALot() {
        return serJumpsALot;
    }

    public void setSerJumpsALot(SerJumpsALot serJumpsALot) {
        this.serJumpsALot = serJumpsALot;
    }

    public void spawnBullet(Vector2 position, Direction direction) {
        bullets.add(new Bullet(this, position, direction));
    }

    public void spawnExplosion(Vector2 position) {
        explosions.add(new Explosion(position));
    }

    public DelayedRemovalArray<JumpingEnemy> getJumpingEnemies(){
        return jumpingEnemies;
    }

    public Beholder getBeholder() {
        return beholder;
    }
}
