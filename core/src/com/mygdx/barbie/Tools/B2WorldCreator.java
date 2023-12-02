package com.mygdx.barbie.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.barbie.BARBIE;
import com.mygdx.barbie.Sprites.Brick;
import com.mygdx.barbie.Sprites.Coin;
import com.mygdx.barbie.Sprites.Snowman;

import java.lang.reflect.Array;

import Screens.PlayScreen;

// class responsible for creating Box2D bodies for various elements in the game world
public class B2WorldCreator {

    // constructor for B2WorldCreator, which initializes Box2D bodies for elements in the game world
    public B2WorldCreator(PlayScreen screen){

        World world = screen.getWorld();  // geting the Box2D world from the PlayScreen
        TiledMap map = screen.getMap();  // getting the TiledMap from the PlayScreen

        // define Box2D body, shape, and fixture for the objects in the map
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef(); //defining a fixture so that you can add a body
        Body body;

        // creating bodies and fixtures for objects in the "ground" layer of the map
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;  // set the body type to StaticBody for static physics
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BARBIE.PPM, (rect.getY() + rect.getHeight() / 2) / BARBIE.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / BARBIE.PPM, rect.getHeight() / 2 / BARBIE.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // creating bodies and fixtures for objects in the "pipe" layer of the map
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;  // set the body type to StaticBody for static physics
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BARBIE.PPM, (rect.getY() + rect.getHeight() / 2) / BARBIE.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / BARBIE.PPM, rect.getHeight() / 2 / BARBIE.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = BARBIE.OBJECT_BIT;  // set the category bits for the fixture
            body.createFixture(fdef);
        }

        // creating bodies and fixtures for objects in the "bricks" layer of the map
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;  // set the body type to StaticBody for static physics
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BARBIE.PPM, (rect.getY() + rect.getHeight() / 2) / BARBIE.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / BARBIE.PPM, rect.getHeight() / 2 / BARBIE.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // creating bodies and fixtures for objects in the "coins" layer of the map
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;  // set the body type to StaticBody for static physics
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BARBIE.PPM, (rect.getY() + rect.getHeight() / 2) / BARBIE.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / BARBIE.PPM, rect.getHeight() / 2 / BARBIE.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
}