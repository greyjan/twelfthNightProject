/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greyjan.twelfthnight2.map.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.greyjan.twelfthnight2.IsometricActor;
import com.greyjan.twelfthnight2.assets.Assets;

/**
 *
 * @author Jan Fic
 */
public class Letter extends IsometricActor {

    public Sprite sprite;
    public static TextureRegion[] letters;

    public Letter(char c, int layer) {
        super(layer);
        if (letters == null) {
            makeLetters();
        }
        sprite = new Sprite(letters[c - 32]);
        this.setBounds(0, 0, sprite.getWidth(), sprite.getHeight());
        this.addListener(new InputListener() {

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                System.out.println(character);
                if (character > 32) {
                    sprite = new Sprite(letters[("" + character).toUpperCase().charAt(0) - 32]);
                }
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = getColor();
        batch.setColor(c);
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    private void makeLetters() {
        TextureRegion[][] tiles = TextureRegion.split((Texture) Assets.GetInstance().get("data/map/letters.png"), 32, 32);
        letters = new TextureRegion[tiles.length * tiles[0].length];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                letters[y * tiles[y].length + x] = tiles[y][x];
            }
        }
    }

}
