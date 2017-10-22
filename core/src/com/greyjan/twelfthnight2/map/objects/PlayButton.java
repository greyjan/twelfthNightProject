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
import com.greyjan.twelfthnight2.IsometricActor;
import com.greyjan.twelfthnight2.assets.Assets;

/**
 *
 * @author Jan Fic
 */
public class PlayButton extends IsometricActor {

    Sprite sprite;

    public PlayButton(int l) {
        super(l);
        sprite = new Sprite((Texture) Assets.GetInstance().get("data/objects/playButton.png"));
        this.setBounds(0, 0, sprite.getWidth(), sprite.getHeight());
        setOffsetX(-13);
        setOffsetY(-9);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = getColor();
        batch.setColor(c);
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
