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
    private boolean runningRight;

    private boolean canJump;

    private boolean isAlive = true;
    private boolean barbieIsDead;



    public Barbie(PlayScreen screen){
        super(screen.getAtlas().findRegion("barbie_newsize"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 70,  27, 20, 33)); //fix this first two points is the start
                                                        // of the top left of the image then the other two go to the bottom right of the image
        barbieRun = new Animation(0.2f, frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(), 23,  27, 22, 33)); //fix this first two points is the start
        // of the top left of the image then the other two go to the bottom right of the image
        barbieJump = new Animation(0.2f, frames);
        frames.clear();

        barbieDead = new TextureRegion(screen.getAtlas().findRegion("barbie_newsize"), 46 , 26 , 21 , 33); //fix this to put barbie just standing on the screen without animations


        barbieStand = new TextureRegion(getTexture(), 46 , 26 , 21 , 33); //fix this to put barbie just standing on the screen without animations
        defineBarbie();
        setBounds(0, 0, 21 / BARBIE.PPM, 35 / BARBIE.PPM);  //fix this
        setRegion(barbieStand);

        canJump = true;


    }
    public boolean canJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));


    }

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
    public void hit(){
        Gdx.app.log("contact", "hit");
        BARBIE.manager.get("barbiedie.wav", Sound.class).play();
        barbieIsDead = true;
        Filter filter = new Filter();
        filter.maskBits = BARBIE.NOTHING_BIT;
        for (Fixture fixture : b2body.getFixtureList()) {
            fixture.setFilterData(filter);
        }

        }

    public void defineBarbie() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / BARBIE.PPM, 50 / BARBIE.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5f / BARBIE.PPM);
        shape.setPosition(new Vector2(0, -7 / BARBIE.PPM));  // Adjust the Y-coordinate as needed
        fdef.filter.categoryBits = BARBIE.BARBIE_BIT;
        fdef.filter.maskBits = BARBIE.GROUND_BIT|
                BARBIE.ENEMY_BIT |
                BARBIE.OBJECT_BIT |
                BARBIE.ENEMY_HEAD_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        Fixture fixture = b2body.createFixture(fdef);
        fixture.setUserData(this); // 'this' refers to the current instance of Barbie

    }

}









