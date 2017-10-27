/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greyjan.twelfthnight2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.greyjan.twelfthnight2.IsometricActor;
import com.greyjan.twelfthnight2.IsometricTiledMapRendererWithSprites;
import com.greyjan.twelfthnight2.assets.Assets;
import com.greyjan.twelfthnight2.map.tiles.Tiles;
import java.util.ArrayList;

/**
 *
 * @author n080873
 */
public class GameScreen implements Screen {

    private final Stage gameStage, scoreStage;
    private final BitmapFont font;
    private IsometricTiledMapRendererWithSprites gameMapRenderer;
    private final TiledMap map;
    private final Game game;
    private final SpriteBatch batch;
    private Player player;
    private TiledMap scoreBoardMap;
    private IsometricTiledMapRendererWithSprites scoreBoardMapRenderer;
    OrthographicCamera scoreCamera;

    public GameScreen(Game game) {
        this.game = game;

        //init
        gameStage = new Stage(new ScreenViewport());
        scoreStage = new Stage(new ScreenViewport());

        font = new BitmapFont();
        batch = new SpriteBatch();
        scoreCamera = new OrthographicCamera();

        //map
        map = new TiledMap();
        makeMap();
        addObjects();
        makeScoreMap();

        //setCamera
        ((OrthographicCamera) (gameStage.getViewport().getCamera())).zoom = .25f / .75f;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        gameStage.getViewport().getCamera().position.set(layer.getWidth() * layer.getTileWidth() / 2, 0, 0);
        gameStage.getViewport().getCamera().update();

//       ((OrthographicCamera) (scoreStage.getViewport().getCamera())).zoom = .25f / .75f;
//        layer = (TiledMapTileLayer) scoreBoardMap.getLayers().get(0);
//        scoreStage.getViewport().getCamera().position.set(layer.getWidth() * layer.getTileWidth() / 2, 0, 0);
//        scoreStage.getViewport().getCamera().update();
    }

    private void addObjects() {
        ArrayList<IsometricActor> actors = new ArrayList<IsometricActor>();
        player = new Player(1);
        player.setIsoPosition(0, 0);
        actors.add(player);
        gameMapRenderer.addActors(actors);
    }

    private void makeMap() {
        map.getTileSets().addTileSet(Tiles.getInstance().getTileSet());

        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(8, 32, 32, 16);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                int id = 0;
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(map.getTileSets().getTile(id + y));
                if (x == 0 && y == 0) {
                    cell.setTile(map.getTileSets().getTile(13));
                }
                layer.setCell(x, y, cell);
            }
        }
        layers.add(layer);
        gameMapRenderer = new IsometricTiledMapRendererWithSprites(gameStage, map, 1);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void render(float delta) {

        gameMapRenderer.setView((OrthographicCamera) gameStage.getViewport().getCamera());
        gameMapRenderer.render();

        scoreBoardMapRenderer.setView((OrthographicCamera) scoreStage.getViewport().getCamera());
        scoreBoardMapRenderer.render();

        gameStage.act(delta);
        gameStage.draw();
        
        scoreStage.act();
        scoreStage.draw();

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "( " + player.getIsoX() + " , " + player.getIsoY() + " )", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 20);
        batch.end();

        input();
    }

    @Override
    public void resize(int width, int height) {
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
        gameStage.dispose();
        map.dispose();
        gameMapRenderer.dispose();
        scoreBoardMapRenderer.dispose();
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.moveIsoBy(-1, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.moveIsoBy(1, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.moveIsoBy(0, 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.moveIsoBy(0, -1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            ((OrthographicCamera) (scoreStage.getViewport().getCamera())).zoom /= .99f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            scoreStage.getViewport().getCamera().position.y++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            scoreStage.getViewport().getCamera().position.y--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            scoreStage.getViewport().getCamera().position.x--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            scoreStage.getViewport().getCamera().position.x++;
        }
        //scoreBoardMapRenderer.setToPosition(player);
    }

    static class Player extends IsometricActor {

        Sprite sprite;

        public Player(int l) {
            super(l);
            sprite = new Sprite((Texture) Assets.GetInstance().get("data/objects/player.png"));
            this.setBounds(0, 0, sprite.getWidth(), sprite.getHeight());
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            Color c = getColor();
            batch.setColor(c);
            batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        }

        @Override
        public void act(float delta) {
            super.act(delta); //To change body of generated methods, choose Tools | Templates.

        }

    }

    private void makeScoreMap() {
        scoreBoardMap = new TiledMap();
        scoreBoardMap.getTileSets().addTileSet(Tiles.getInstance().getTileSet());

        MapLayers layers = scoreBoardMap.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(6, 16, 32, 16);
        for (int x = 0; x < layer.getWidth(); x++) {
           
            for (int y = 0; y < layer.getHeight(); y++) {
                int id = 0;
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(scoreBoardMap.getTileSets().getTile(id));
                layer.setCell(x, y, cell);
            }
        }
        layers.add(layer);
        scoreBoardMapRenderer = new IsometricTiledMapRendererWithSprites(scoreStage, scoreBoardMap, 1);
    }

}
