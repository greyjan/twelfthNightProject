/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greyjan.twelfthnight2.map.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.greyjan.twelfthnight2.assets.Assets;

/**
 *
 * @author Jan Fic
 */
public class Tiles {
    
    private static Tiles tiles;
    
    private final Texture tileFile;
    private final TiledMapTileSet tileSet;
    
    private Tiles() {
        tileFile = Assets.GetInstance().get("data/map/tiles.png");
        
        TextureRegion[][] splitTiles = TextureRegion.split(tileFile, 32, 32);
        
        tileSet = new TiledMapTileSet();
            for (int ty = 0; ty < splitTiles.length; ty++) {
                for (int tx = 0; tx < splitTiles[ty].length; tx++) {
                    StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[ty][tx]);
                    tile.setOffsetY(-11);
                    tileSet.putTile(ty * splitTiles[ty].length + tx, tile);
                }
            }
    }
    
    public static Tiles getInstance() {
        if(tiles == null) {
            tiles = new Tiles();
        }
        return tiles;
    }
    
    public TiledMapTileSet getTileSet() {
        return tileSet;
    }
    
}
