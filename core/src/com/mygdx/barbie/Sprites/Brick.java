package com.mygdx.barbie.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import Screens.PlayScreen;

public class Brick extends InteractiveTileObject{
    public  Brick(PlayScreen screen, TiledMap map, Rectangle bounds){
        super(screen, bounds);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        System.out.println("Brick's head hit!");
    }
}
