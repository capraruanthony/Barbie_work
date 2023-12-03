package com.mygdx.barbie.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import Screens.PlayScreen;

// abstract class representing an enemy in the game
public abstract class Enemy extends Sprite {
    // fields to store the world, screen, Box2D body, and velocity of the enemy
    protected World world;
    protected  PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    // Constructor for the Enemy class, taking a PlayScreen, and initial position (x, y)
    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1, 0);
    }

    protected abstract void defineEnemy();
    public abstract void hitOnHead();

    // Method to reverse the velocity of the enemy based on boolean parameters
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}









