package com.mygdx.barbie.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.barbie.BARBIE;

import Screens.PlayScreen;

public class Barbie extends Sprite {
    // Enumeration is to represent the possible states of the Barbie character
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD };
    public State currentState;
    public State previousState;
    public World world; //world that barbie will live in
    public Body b2body; //box2d
    private TextureRegion barbieStand;
    private TextureRegion barbieDead;
    private Animation barbieRun;
    private Animation barbieJump;
    private float stateTimer; //see how long you in a state
    private boolean runningRight; // flag to check if Barbie is running to the right

    private boolean canJump; //flag if barbie can jump

    private boolean isAlive = true;
    private boolean barbieIsDead;



    public Barbie(PlayScreen screen){
        // calling constructor of the superclass (Sprite) with the texture region for Barbie
        super(screen.getAtlas().findRegion("barbie_newsize"));
        this.world = screen.getWorld(); // get the world from the PlayScreen
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;    // initial running direction to right

        // Create an array to store frames for the running animation
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 70,  27, 20, 33));

        barbieRun = new Animation(0.2f, frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(), 23,  27, 22, 33));
        barbieJump = new Animation(0.2f, frames);
        frames.clear();

        // set the region for the dead state using a specific region of the texture atlas
        barbieDead = new TextureRegion(screen.getAtlas().findRegion("barbie_newsize"), 46 , 26 , 21 , 33);

        // set the region for the standing state using a specific region of the texture atlas
        barbieStand = new TextureRegion(getTexture(), 46 , 26 , 21 , 33);

        // Define the properties and create the Box2D body for Barbie
        defineBarbie();

        // Set the bounds and initial region for the Sprite
        setBounds(0, 0, 21 / BARBIE.PPM, 35 / BARBIE.PPM);
        setRegion(barbieStand);

        canJump = true;


    }
    public boolean canJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    // update method to be called in the game loop to update Barbie's position and animation
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));

    }

    // method to get the current frame of the animation based on the state
    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;

        switch (currentState){
            case DEAD:
                region = barbieDead;
                break;
            case JUMPING:
                region = (TextureRegion) barbieJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) barbieRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = barbieStand;
                break;
        }
    if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
        region.flip( true, false);
        runningRight = false;
    }
    else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
        region.flip(true, false);
        runningRight = true;
    }
    stateTimer = currentState == previousState ? stateTimer + dt : 0;
    previousState = currentState;
    return region;
    }

    // method to get the current state of Barbie
    public State getState(){
        if(barbieIsDead)
            return  State.DEAD;
        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return  State.STANDING;

    }


    public boolean isDead(){
        return barbieIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    // method to handle when Barbie is hit
    public void hit(){
        Gdx.app.log("contact", "hit");  // logging a message to the console when Barbie is hit. this was for me to fix my issue
        BARBIE.manager.get("barbiedie.wav", Sound.class).play(); //Play a sound effect when Barbie is hit
        barbieIsDead = true;
        Filter filter = new Filter();
        filter.maskBits = BARBIE.NOTHING_BIT;
        // setting the filter data for all fixtures of Barbie's body to disable collisions
        for (Fixture fixture : b2body.getFixtureList()) {
            fixture.setFilterData(filter);
        }

        }

    // method to define the Box2D body for Barbie
    public void defineBarbie() {
        BodyDef bdef = new BodyDef(); // sett the initial position of Barbie
        bdef.position.set(32 / BARBIE.PPM, 50 / BARBIE.PPM); // set the body type to DynamicBody for dynamic physics
        bdef.type = BodyDef.BodyType.DynamicBody; // create  Box2D body for Barbie
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5f / BARBIE.PPM); // radius of the circular shape for Barbie
        shape.setPosition(new Vector2(0, -7 / BARBIE.PPM));  // Adjust the Y-coordinate as needed
        fdef.filter.categoryBits = BARBIE.BARBIE_BIT;
        fdef.filter.maskBits = BARBIE.GROUND_BIT|
                BARBIE.ENEMY_BIT |
                BARBIE.OBJECT_BIT |
                BARBIE.ENEMY_HEAD_BIT;

        fdef.shape = shape;  // shape for Barbie's fixture
        b2body.createFixture(fdef); //  a fixture for Barbie's body
        Fixture fixture = b2body.createFixture(fdef); //  another fixture (for additional configuration, if needed)
        fixture.setUserData(this); // 'this' refers to the current instance of Barbie

    }

}









