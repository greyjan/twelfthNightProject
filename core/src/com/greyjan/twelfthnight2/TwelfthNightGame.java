package com.greyjan.twelfthnight2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.greyjan.twelfthnight2.screens.LoadingScreen;
import com.greyjan.twelfthnight2.screens.MainMenuScreen;
import java.util.Scanner;

public class TwelfthNightGame extends Game {

    MainMenuScreen mainMenu;
    FileHandle saveFile;
    FileHandle[] saves;

    @Override
    public void create() {

        saveFile = Gdx.files.external("twelfthNightGame/saves.txt");
        saves = new FileHandle[3];
        System.out.println(saveFile.exists());
        System.out.println(saveFile.file().getAbsolutePath());
        if (saveFile.exists()) {
            readSaves();
        } else {
            saveFile.writeString("0", false);
        }
        
        

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

    public void readSaves() {
        String data = saveFile.readString();
        Scanner scan = new Scanner(data);
        int savesRecorded = Integer.parseInt(scan.next());
        for(int i = 0; i < savesRecorded; i++) {
            int coins = Integer.parseInt(scan.next());
            int highScore = Integer.parseInt(scan.nextLine());
            String name = scan.nextLine();
            System.out.println("Loading saves");
            System.out.println("Loading..." + name + "'s save");
            System.out.println("Name: " + name);
            System.out.println("Coins: " + coins);
            System.out.println("Highscore: " + highScore);
        }
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
