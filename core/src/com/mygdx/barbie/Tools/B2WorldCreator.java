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

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef(); //defining a fixture so that you can add a body
        Body body;

        //creating a body and fixture at every corresponding object like ground layer, brick.
        //ground
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BARBIE.PPM, (rect.getY() + rect.getHeight() / 2) / BARBIE.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / BARBIE.PPM, rect.getHeight() / 2 / BARBIE.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //pipe
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BARBIE.PPM, (rect.getY() + rect.getHeight() / 2) / BARBIE.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / BARBIE.PPM, rect.getHeight() / 2 / BARBIE.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //bricks
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BARBIE.PPM, (rect.getY() + rect.getHeight() / 2) / BARBIE.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / BARBIE.PPM, rect.getHeight() / 2 / BARBIE.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //coins(not sure if we are gonna do this)
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BARBIE.PPM, (rect.getY() + rect.getHeight() / 2) / BARBIE.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / BARBIE.PPM, rect.getHeight() / 2 / BARBIE.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


    }
}
