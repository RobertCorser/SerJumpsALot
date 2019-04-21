package com.udacity.gamedev.serjumpsalot;

import com.badlogic.gdx.Game;

public class SerJumpsALotGame extends Game {

    @Override
    public void create() {
        setScreen(new GameplayScreen());
    }

}
