package com.greyjan.twelfthnight2;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Random;

public class TiledMapStage extends Stage {

    private TiledMap map;

    public TiledMapStage(TiledMap map, Viewport v) {
        super(v);
        this.map = map;
        for (MapLayer layer : map.getLayers()) {
            TiledMapTileLayer l = (TiledMapTileLayer) layer;
            createActorsForLayer(l);
        }
    }

    private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                TiledMapActor actor = new TiledMapActor(map, tiledLayer, cell);

                actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(),
                        tiledLayer.getTileWidth(), tiledLayer.getTileHeight());
                addActor(actor);
            }
        }
    }

    class TiledMapActor extends Actor {

        protected TiledMap tiledMap;

        protected TiledMapTileLayer tiledLayer;

        protected TiledMapTileLayer.Cell cell;

        
        Sprite sprite;
        Random rand;
        Vector2 speed, acc;
        int rate;
        public TiledMapActor(TiledMap tiledMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell) {
            this.tiledMap = tiledMap;
            this.tiledLayer = tiledLayer;
            this.cell = cell;
            speed = new Vector2();
            acc = new Vector2();
            rand = new Random();
//            speed.x = rand.nextInt(4) - 2;
//            speed.y = rand.nextInt(4) - 2;
            acc.x = rand.nextInt(4) - 2;
            acc.y = rand.nextInt(4) - 2;
            rate = 1;
        }

        @Override
        public void act(float delta) {
            for(Actor a : getStage().getActors()) {
                if(a instanceof TiledMapActor) {
                    TiledMapActor b = (TiledMapActor)(a);
                    if(b.sprite.getBoundingRectangle().overlaps(this.sprite.getBoundingRectangle())) {
                        this.addAction(Actions.scaleBy(0.5f, 0.5f,1f));
                       
                    }
                }
            }
        }
        
        
        
        public void move() {
            acc.setToRandomDirection();
            speed.add(acc);
            
            moveBy(speed.x, speed.y);
        }
    }
}
