package com.mygdx.barbie.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.barbie.BARBIE;
import com.mygdx.barbie.Sprites.Barbie;
import com.mygdx.barbie.Sprites.Enemy;
import com.mygdx.barbie.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {

    // called when two fixtures start colliding
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("ContactListener", "Begin contact");
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        // handle head-on collisions with interactive tile objects
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }
        // switch statement to handle different collision scenarios based on category bits
        switch (cDef){
            case BARBIE.ENEMY_HEAD_BIT | BARBIE.BARBIE_BIT:
                if(fixA.getFilterData().categoryBits == BARBIE.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;
            case BARBIE.ENEMY_BIT | BARBIE.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == BARBIE.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case BARBIE.BARBIE_BIT | BARBIE.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == BARBIE.BARBIE_BIT && fixA.getUserData() != null) //fix this because it is not getting the user data correctly
                    ((Barbie)fixA.getUserData()).hit();
                else if(fixB.getFilterData().categoryBits == BARBIE.BARBIE_BIT && fixB.getUserData() != null) //fix this because it is not getting the user data correctly
                    ((Barbie)fixB.getUserData()).hit();
                break;
            case BARBIE.ENEMY_BIT | BARBIE.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
        }
    }



    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
