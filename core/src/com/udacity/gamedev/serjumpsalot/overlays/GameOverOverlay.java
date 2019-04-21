package com.udacity.gamedev.serjumpsalot.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.serjumpsalot.util.Constants;


public class GameOverOverlay {

    public final Viewport viewport;
    final BitmapFont font;


    public GameOverOverlay() {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        font = new BitmapFont(Gdx.files.internal(Constants.FONT_FILE));
        font.getData().setScale(1);
    }

    public void init() {

    }

    public void render(SpriteBatch batch) {

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        // TODO: Draw a game over message
        // Feel free to get more creative with this screen. Perhaps you could cover the screen in enemy robots?
        font.draw(batch, Constants.GAME_OVER_MESSAGE, viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0, Align.center, false);


        batch.end();

    }
}
