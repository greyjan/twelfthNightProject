package com.greyjan.twelfthnight2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.greyjan.twelfthnight2.screens.LoadingScreen;
import com.greyjan.twelfthnight2.screens.MainMenuScreen;

public class TwelfthNightGame extends Game {

    MainMenuScreen mainMenu;
    
    @Override
    public void create() {
        LoadingScreen loading = new LoadingScreen(this);
        loading.setILoadingListener(new LoadingScreen.ILoadingListener() {

            @Override
            public void OnFinished() {
                mainMenu = new MainMenuScreen(TwelfthNightGame.this);
                
                Gdx.input.setInputProcessor(mainMenu.stage);
                TwelfthNightGame.this.setScreen(mainMenu);

            }
        });
        this.setScreen(loading);
        Gdx.input.setOnscreenKeyboardVisible(true);
    }

   
    
    @Override
    public void render() {
        Color c = Color.SKY;
        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        super.render();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

    }
    
    

}
