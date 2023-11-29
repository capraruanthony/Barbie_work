package com.mygdx.barbie.Sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.barbie.BARBIE;

import Screens.PlayScreen;

public class Barbie extends Sprite {
    public World world; //world that barbie will live in
    public Body b2body; //box2d
    private TextureRegion barbieStand;

    public Barbie(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("barbiefr"));
        this.world = world;
        defineBarbie();
        barbieStand = new TextureRegion(getTexture(), 1 , 503 , 20 , 525);
        setBounds(0, 0, 19 / BARBIE.PPM, 12 / BARBIE.PPM);
        setRegion(barbieStand);
    }

    public void defineBarbie() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / BARBIE.PPM, 32 / BARBIE.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / BARBIE.PPM);


        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}









