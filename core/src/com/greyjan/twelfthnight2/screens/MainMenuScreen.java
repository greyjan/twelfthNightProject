package com.greyjan.twelfthnight2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.greyjan.twelfthnight2.IsometricTiledMapRendererWithSprites;
import com.greyjan.twelfthnight2.map.objects.Letter;
import com.greyjan.twelfthnight2.map.objects.PlayButton;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.greyjan.twelfthnight2.IsometricActor;
import com.greyjan.twelfthnight2.map.tiles.Tiles;
import java.util.ArrayList;

public class MainMenuScreen implements Screen {

    public Stage stage;
    private final TiledMap map;
    private IsometricTiledMapRendererWithSprites mapRenderer;
    private BitmapFont font;
    private SpriteBatch batch;
    private PlayButton playButton;
    private final Game game;

    public MainMenuScreen(Game g) {
        this.game = g;

        //init
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        batch = new SpriteBatch();

        //map
        map = new TiledMap();
        makeMap();
        addObjects();

        //setCamera
        ((OrthographicCamera) (stage.getViewport().getCamera())).zoom = .25f / .75f;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        stage.getViewport().getCamera().position.set(layer.getWidth() * layer.getTileWidth() / 2, 0, 0);
        stage.getViewport().getCamera().update();
    }

    private void makeMap() {
        map.getTileSets().addTileSet(Tiles.getInstance().getTileSet());

        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(16, 16, 32, 16);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                int id = 13;
                if (x > 8) {
                    id = 26;
                }
                if (x == 8) {
                    id = 14;
                }
                Cell cell = new Cell();
                cell.setTile(map.getTileSets().getTile(id));
                layer.setCell(x, y, cell);
            }
        }
        layers.add(layer);

        mapRenderer = new IsometricTiledMapRendererWithSprites(stage, map, 1);
    }

    private void addObjects() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        ArrayList<IsometricActor> actors;
        actors = new ArrayList<IsometricActor>();

        //objects
        //(title)
        String title = "RESCUING";
        for (int i = title.length() - 1; i >= 0; i--) {
            Letter l = new Letter(title.charAt(i), 1);
            l.setIsoPosition(0, i);
            l.setColor(Color.SALMON);
            l.addAction(sequence(fadeOut(0), delay(1 + i * 0.1f), fadeIn(0.3f),
                    sequence(delay(i / 8f), forever(sequence(color(Color.SALMON, 1f), color(Color.GOLD, 1))))));

            actors.add(l);
        }

        title = "SEBASTIAN";
        for (int i = title.length() - 1; i >= 0; i--) {
            Letter l = new Letter(title.charAt(i), 1);
            l.setIsoPosition(2, i);
            l.setColor(Color.SALMON);
            l.addAction(sequence(fadeOut(0), delay(1 + i * 0.1f), fadeIn(0.3f),
                    sequence(delay(i / 8f), forever(sequence(color(Color.SALMON, 1f), color(Color.GOLD, 1))))));
            actors.add(l);

        }
        title = "OR";
        for (int i = title.length() - 1; i >= 0; i--) {
            Letter l = new Letter(title.charAt(i), 1);
            l.setIsoPosition(4, i + 4);
            l.setColor(Color.MAROON);
            l.addAction(sequence(fadeOut(0), delay(3 + i * 0.1f), fadeIn(0.3f)));
            actors.add(l);
        }
        title = "WHAT YOU WILL";
        for (int i = title.length() - 1; i >= 0; i--) {
            Letter l = new Letter(title.charAt(i), 1);
            l.setIsoPosition(6, i + 1);
            l.setColor(Color.ROYAL);
            l.addAction(sequence(fadeOut(0), delay(4 + i * 0.1f), fadeIn(0.3f),
                    sequence(delay(i / 8f), forever(sequence(color(Color.ROYAL, 1f), color(Color.CYAN, 1f))))));
            actors.add(l);
        }

        playButton = new PlayButton(1);
        playButton.setIsoPosition(15, 0);
        playButton.addListener(new InputListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playButton.addAction(moveBy(0f, -2f, 1f, Interpolation.pow2InInverse));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                playButton.addAction(moveBy(0f, 2f, 1f, Interpolation.pow2InInverse));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }

        });
        actors.add(playButton);

        GameScreen.Player player = new GameScreen.Player(1);
        player.setIsoPosition(0, 0);
        //actors.add(player);
        mapRenderer.addActors(actors);
    }
    
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        mapRenderer.setView((OrthographicCamera) stage.getViewport().getCamera());
        mapRenderer.render();

        stage.act(delta);
        stage.draw();

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "MEM: " + Gdx.app.getJavaHeap() / 1000, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 20);

        batch.end();

        input();
    }

    private void input() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        };
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        stage.dispose();
        mapRenderer.dispose();
    }

    

}
