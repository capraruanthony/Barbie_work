package com.mygdx.barbie.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.barbie.BARBIE;

import Screens.PlayScreen;

public class Snowman extends Enemy{
    private  float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private  boolean destroyed;
    public Snowman(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("pile"),91, 42, 16, 17));
        walkAnimation = new Animation(0.4f , frames);
        stateTime = 0;
        setBounds(getX(), getY(), 36 / BARBIE.PPM, 40 / BARBIE.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public  void update(float dt){
        Gdx.app.log("Snowman", "Update method called");
        stateTime += dt;
        if (setToDestroy && !destroyed){
            Gdx.app.log("Snowman", "if called");
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("pile"), 0, 0, 17, 17));
            stateTime = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true)); // Explicit cast to TextureRegion
        }
    }

    @Override
    protected void defineEnemy() {
            BodyDef bdef = new BodyDef();
            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();
            CircleShape shape = new CircleShape();
            shape.setRadius(7.5f / BARBIE.PPM);
            shape.setPosition(new Vector2(0, -4 / BARBIE.PPM));  // Adjust the Y-coordinate as needed
            fdef.filter.categoryBits = BARBIE.ENEMY_BIT;
            fdef.filter.maskBits = BARBIE.GROUND_BIT |
                BARBIE.ENEMY_BIT |
                BARBIE.OBJECT_BIT |
                BARBIE.BARBIE_BIT;

            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData(this);

            //Create the head here
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5,8).scl(1 / BARBIE.PPM);
        vertice[1] = new Vector2( 5,8).scl(1 / BARBIE.PPM);
        vertice[2] = new Vector2(-3,3).scl(1 / BARBIE.PPM);
        vertice[3] = new Vector2(3,3).scl(1 / BARBIE.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = BARBIE.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }
    @Override
    public void hitOnHead() {
        Gdx.app.log("Snowman", "Hit on head");
        setToDestroy = true;
    }
}











