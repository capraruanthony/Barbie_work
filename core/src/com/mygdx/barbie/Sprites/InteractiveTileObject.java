package com.mygdx.barbie.Sprites;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.barbie.BARBIE;

import Screens.PlayScreen;

// abstract class representing an interactive tile object in the game
public abstract class InteractiveTileObject {
    // fields to store the world, map, tile, bounds, and body of the interactive tile object
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    protected Fixture fixture;

    // constructor  for the InteractiveTileObject class, taking a PlayScreen and bounds
    public InteractiveTileObject(PlayScreen screen, Rectangle bounds){

        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody; // set the body type to StaticBody for static physics
        // set the position of the body at the center of the bounds
        bdef.position.set(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2);

        body = world.createBody(bdef); // creating the Box2D body for the interactive tile object

        // set the shape as a box with half of the width and height of the bounds
        shape.setAsBox(bounds.getWidth() / 2 / BARBIE.PPM, bounds.getHeight() / 2 / BARBIE.PPM);
        fdef.shape = shape;
        body.createFixture(fdef); // fixture for the body using the defined shape
    }

    public abstract void onHeadHit();

    //  set the category filter for the fixture
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }


}
