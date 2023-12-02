package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.barbie.BARBIE;
import com.mygdx.barbie.Sprites.Barbie;
import com.mygdx.barbie.Sprites.Snowman;
import com.mygdx.barbie.Tools.B2WorldCreator;
import com.mygdx.barbie.Tools.WorldContactListener;
import com.sun.org.apache.xpath.internal.operations.Or;


// Import the Hud class from the Scenes package
import Scenes.Hud;

// PlayScreen class implements the Screen interface and represents the main game screen
public class PlayScreen implements Screen {
    private BARBIE game; // Reference to the main game class
    private TextureAtlas atlas;
    private OrthographicCamera gamecam; //gamecam is what follows along our game world and
                                        // what the viewport actually displays
    private Viewport gamePort; // Viewport for displaying the game world
    private Hud hud; // HUD for showing game information

    private TmxMapLoader maploader;
    private TiledMap map; //reference to the map itself
    private OrthogonalTiledMapRenderer renderer; //renders the map to the screen

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr; //gives us a graphical representation of our fixtures and bodies
                                    //inside of our box 2d world; so we can see whats going on

    private Barbie player;

    private Music music;


    private Snowman snowman;
    private Array<Snowman> snowmen;


    // Constructor for initializing the PlayScreen
    public PlayScreen(BARBIE game){
        atlas = new TextureAtlas("Barbie_Stuff.pack");

        this.game = game;
        gamecam = new OrthographicCamera(); // Create an OrthographicCamera for following the game world
        gamePort = new FitViewport( BARBIE.V_WIDTH / BARBIE.PPM, BARBIE.V_HEIGHT / BARBIE.PPM, gamecam); // Create a FitViewport
        // for displaying the game world, using BARBIE.V_WIDTH and BARBIE.V_HEIGHT
        hud = new Hud(game.batch);  // Create a new Hud instance, passing the game's batch to render HUD elements

        //load the map and setup the map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("barbie1.tmx"); // changed be carefull
        renderer = new OrthogonalTiledMapRenderer(map, 1 / BARBIE.PPM);
        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-10), true); //0 -10 gravity for now, second parameter is
                                                                //if you want to sleep objects that are at rest
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(this);

        //create barbie in our game world
        player = new Barbie(this);

        world.setContactListener(new WorldContactListener());

        music = BARBIE.manager.get("barbie_music.ogg", Music.class);
        music.setLooping(true);
        music.play();

        //snowman = new Snowman(this, 5.3f, .32f);
        snowmen = new Array<>();
        for (int i = 1; i < 7; i++) { // You can adjust the number of snowmen as needed
            snowmen.add(new Snowman(this, i * 2.7f, 0.32f)); // Adjust the positions as needed
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }
    @Override
    public void show() {

    }


    public void handleInput(float dt){

        if(player.currentState != Barbie.State.DEAD) {
            if (true && Gdx.input.isKeyPressed((Input.Keys.UP))) {
                player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
                player.b2body.applyLinearImpulse(new Vector2(1f, 0), player.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
                player.b2body.applyLinearImpulse(new Vector2(-1f, 0), player.b2body.getWorldCenter(), true);
            }
        }
    }
    //this is where were gonna do all the updating of the world like clicking on the screen, keys, etc)
    public void update(float dt){
         //this handles the user input first
        handleInput(dt);

        //60 times a second
        world.step(1/60f, 6, 2 );

        player.update(dt);
        //snowman.update(dt);
        for (Snowman snowman : snowmen) {
            snowman.update(dt);
        }
        hud.update(dt);

        //attach our gamecam to our players.x coordinate
        if(player.currentState != Barbie.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }
        //update the gamecam with correct cordinates after change
         gamecam.update();
         renderer.setView(gamecam);  //only render what our gamecam can see


    }

    @Override
    public void render(float delta) {
        //separate our update logic from render
         update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //render our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        //snowman.draw(game.batch);
        for (Snowman snowman : snowmen) {
            snowman.draw(game.batch);
        }
        game.batch.end();

        // Set the projection matrix of the game batch to that of the HUD's camera
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public boolean gameOver(){
        if(player.currentState == Barbie.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }
    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height); // Update the gamePort to handle screen resizing
    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
