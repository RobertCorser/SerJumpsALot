package com.udacity.gamedev.serjumpsalot;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.serjumpsalot.overlays.GameOverOverlay;
import com.udacity.gamedev.serjumpsalot.overlays.OnscreenControls;
import com.udacity.gamedev.serjumpsalot.overlays.SerJumpsALotHud;
import com.udacity.gamedev.serjumpsalot.overlays.VictoryOverlay;
import com.udacity.gamedev.serjumpsalot.util.Assets;
import com.udacity.gamedev.serjumpsalot.util.ChaseCam;
import com.udacity.gamedev.serjumpsalot.util.Constants;
import com.udacity.gamedev.serjumpsalot.util.LevelLoader;
import com.udacity.gamedev.serjumpsalot.util.Utils;


public class GameplayScreen extends ScreenAdapter {

    public static final String TAG = GameplayScreen.class.getName();

    private OnscreenControls onscreenControls;
    private SpriteBatch batch;
    private long levelEndOverlayStartTime;
    private Level level;
    private ChaseCam chaseCam;
    private SerJumpsALotHud hud;
    private VictoryOverlay victoryOverlay;
    private GameOverOverlay gameOverOverlay;
    private String currLevel;
    private Music forestMusic;
    private Music castleMusic;

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        batch = new SpriteBatch();
        chaseCam = new ChaseCam();
        hud = new SerJumpsALotHud();
        victoryOverlay = new VictoryOverlay();
        gameOverOverlay = new GameOverOverlay();

        onscreenControls = new OnscreenControls();

        currLevel = "levels/Level2.dt";

        forestMusic = Gdx.audio.newMusic(Gdx.files.internal(Constants.FOREST_BGM));
        castleMusic = Gdx.audio.newMusic(Gdx.files.internal(Constants.CASTLE_BGM));

        Gdx.input.setInputProcessor(onscreenControls);
        /*if (onMobile()) {
            Gdx.input.setInputProcessor(onscreenControls);
        }*/

        startNewLevel();
    }

    private boolean onMobile(){
        return Gdx.app.getType() == Application.ApplicationType.Android;
    }

    @Override
    public void resize(int width, int height) {
        hud.viewport.update(width, height, true);
        victoryOverlay.viewport.update(width, height, true);
        gameOverOverlay.viewport.update(width, height, true);
        level.viewport.update(width, height, true);
        chaseCam.camera = level.viewport.getCamera();
        onscreenControls.viewport.update(width, height, true);
        onscreenControls.recalculateButtonPositions();
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        forestMusic.dispose();
        castleMusic.dispose();
    }

    @Override
    public void render(float delta) {

        level.update(delta);
        chaseCam.update(delta);


        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        level.render(batch);

        onscreenControls.render(batch);
        /*if (onMobile()) {
            onscreenControls.render(batch);
        }*/

        hud.render(batch, level.getSerJumpsALot().getLives(), level.getSerJumpsALot().getAmmo(), level.score);
        renderLevelEndOverlays(batch);
    }

    private void renderLevelEndOverlays(SpriteBatch batch) {
        if (level.victory) {
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                victoryOverlay.init();
            }
            victoryOverlay.render(batch);
            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                levelComplete();
            }
        }

        if (level.gameOver) {
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                gameOverOverlay.init();
            }

            gameOverOverlay.render(batch);
            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                levelFailed();
            }
        }

    }

    private void startNewLevel() {

        //level = Level.debugLevel();

       //String levelName = Constants.LEVELS[MathUtils.random(Constants.LEVELS.length - 1)];


        if(currLevel.equals("levels/Level1.dt")){
            level = LevelLoader.load("levels/Level2.dt");
            currLevel = "levels/Level2.dt";
            forestMusic.stop();
            castleMusic.setVolume(Constants.CASTLE_BGM_VOL);
            castleMusic.setLooping(true);
            castleMusic.play();
        }
        else if(currLevel.equals("levels/Level2.dt")){
            level = LevelLoader.load("levels/Level1.dt");
            currLevel = "levels/Level1.dt";
            castleMusic.stop();
            forestMusic.setVolume(Constants.FOREST_BGM_VOL);
            forestMusic.setLooping(true);
            forestMusic.play();
        }

        chaseCam.camera = level.viewport.getCamera();
        chaseCam.target = level.getSerJumpsALot();
        onscreenControls.serJumpsALot = level.getSerJumpsALot();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void restartLevel(){
        if(currLevel.equals("levels/Level1.dt")){
            level = LevelLoader.load("levels/Level1.dt");
        }
        else if(currLevel.equals("levels/Level2.dt")){
            level = LevelLoader.load("levels/Level2.dt");
        }

        chaseCam.camera = level.viewport.getCamera();
        chaseCam.target = level.getSerJumpsALot();
        onscreenControls.serJumpsALot = level.getSerJumpsALot();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void levelComplete() {
        startNewLevel();
    }

    public void levelFailed() {
        restartLevel();
    }
}