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

// class representing a Snowman enemy in the game, extending the Enemy class
public class Snowman extends Enemy{
    private float stateTime;                 // the time elapsed in the current state
    private Animation walkAnimation;          // animation for the snowman's walk
    private Array<TextureRegion> frames;      // array to store frames for the animation
    private boolean setToDestroy;            // flag to indicate if the snowman is set to be destroyed
    private boolean destroyed;               // flag to indicate if the snowman is already destroyed

    // constructor for the Snowman class, taking a PlayScreen, and initial position (x, y)
    public Snowman(PlayScreen screen, float x, float y) {
        super(screen, x, y);  //  constructor of the superclass (Enemy)
        frames = new Array<TextureRegion>();  // making the frames array
        // add a frame to the frames array using a specific region of the texture atlas
        frames.add(new TextureRegion(screen.getAtlas().findRegion("pile"), 91, 42, 16, 17));
        // create the walk animation with a frame duration of 0.4 seconds
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 36 / BARBIE.PPM, 40 / BARBIE.PPM);  //  the bounds of the snowman
        setToDestroy = false;
        destroyed = false;
    }

    // method to update the snowman's state and position
    public void update(float dt){
        Gdx.app.log("Snowman", "Update method called");
        stateTime += dt;  // update the state time
        if (setToDestroy && !destroyed){
            Gdx.app.log("Snowman", "if called");
            world.destroyBody(b2body);  // destroy the Box2D body of the snowman
            destroyed = true;
            // making the region to a specific region of the texture atlas for a destroyed snowman
            setRegion(new TextureRegion(screen.getAtlas().findRegion("pile"), 0, 0, 17, 17));
            stateTime = 0;
        }
        // update the position and animation of the snowman if not destroyed
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);  // set the linear velocity of the snowman
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            // set the region based on the current frame of the walk animation
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    // override the method to define the Box2D body for the snowman
    @Override
    protected void defineEnemy() {
        //  body for the snowman
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        // define the fixture for the circular body of the snowman
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5f / BARBIE.PPM);
        shape.setPosition(new Vector2(0, -4 / BARBIE.PPM));
        fdef.filter.categoryBits = BARBIE.ENEMY_BIT;
        fdef.filter.maskBits = BARBIE.GROUND_BIT |
                BARBIE.ENEMY_BIT |
                BARBIE.OBJECT_BIT |
                BARBIE.BARBIE_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        // create the head for the snowman
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

    // method to draw the snowman on the screen
    public void draw(Batch batch){
        // draw the snowman only if it's not destroyed or in the process of being destroyed
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    // override the method to handle the effect of being hit on the head
    @Override
    public void hitOnHead() {
        Gdx.app.log("Snowman", "Hit on head");
        setToDestroy = true;  // set the flag to indicate that the snowman should be destroyed
    }
}











