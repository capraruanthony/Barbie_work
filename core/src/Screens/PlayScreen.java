package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.barbie.BARBIE;
import com.mygdx.barbie.Sprites.Barbie;
import com.mygdx.barbie.Sprites.Snowman;
import com.mygdx.barbie.Tools.B2WorldCreator;
import com.mygdx.barbie.Tools.WorldContactListener;
import Scenes.Hud;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


public class PlayScreen implements Screen {

    private BARBIE game;
    private TextureAtlas atlas;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Barbie player;
    private Music music;
    private Snowman snowman;
    private Array<Snowman> snowmen;
    private boolean hasPlayerReachedEnd = false;

    private Stage stage;
    private Skin skin;

    private Texture upTexture;
    private Texture leftTexture;
    private Texture rightTexture;

    // Constructor for initializing the PlayScreen
    public PlayScreen(BARBIE game) {
        this.game = game;
        atlas = new TextureAtlas("Barbie_Stuff.pack"); // Add this line to initialize the atlas
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(BARBIE.V_WIDTH / BARBIE.PPM, BARBIE.V_HEIGHT / BARBIE.PPM, gamecam);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("barbie1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / BARBIE.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(this);

        // Ensure that atlas is initialized before creating the Barbie instance
        player = new Barbie(this);

        world.setContactListener(new WorldContactListener());
        music = BARBIE.manager.get("barbie_music.ogg", Music.class);
        music.setLooping(true);
        music.play();
        snowmen = new Array<>();
        for (int i = 1; i < 7; i++) {
            snowmen.add(new Snowman(this, i * 2.7f, 0.32f));
        }

        stage = new Stage(new FitViewport(BARBIE.V_WIDTH, BARBIE.V_HEIGHT, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(); // You need to initialize your skin

        // Call the create method to set up UI elements
        create();
    }

    private void create() {
        upTexture = new Texture("up.png");
        leftTexture = new Texture("left.png");
        rightTexture = new Texture("right.png");

        // Create Image instances with the textures
        Image upImage = new Image(new TextureRegion(upTexture));
        Image leftImage = new Image(new TextureRegion(leftTexture));
        Image rightImage = new Image(new TextureRegion(rightTexture));

        // Set the image positions
        float buttonSize = 30f;
        upImage.setSize(buttonSize, buttonSize);
        leftImage.setSize(buttonSize, buttonSize);
        rightImage.setSize(buttonSize, buttonSize);

        // Set the image positions
        float padding = 10f;
        upImage.setPosition(padding, 2 * padding + buttonSize); // Switched positions of up and left buttons
        leftImage.setPosition(padding, padding);
        rightImage.setPosition(2 * padding + buttonSize, padding);
        // Add click listeners to handle the character movement
        upImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
            }
        });

        leftImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.b2body.applyLinearImpulse(new Vector2(-1f, 0), player.b2body.getWorldCenter(), true);
            }
        });

        rightImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.b2body.applyLinearImpulse(new Vector2(1f, 0), player.b2body.getWorldCenter(), true);
            }
        });

        // Add images to the stage
        stage.addActor(upImage);
        stage.addActor(leftImage);
        stage.addActor(rightImage);
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

        // Check if the player has reached the end of the map
        if (player.b2body.getPosition().x >= 3800 / BARBIE.PPM) {
            hasPlayerReachedEnd = true;
        }

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
        // Separate our update logic from render
        update(delta);

        // Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render our game map
        renderer.render();

        // Render our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        // Draw game entities
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        if (player.currentState != Barbie.State.DEAD) {
            player.draw(game.batch);
            for (Snowman snowman : snowmen) {
                snowman.draw(game.batch);
            }
        }
        game.batch.end();

        // Draw Hud (Stage)
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        // Draw stage (buttons)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Check if the player has reached the end of the map
        if (hasPlayerReachedEnd) {
            game.setScreen(new WinScreen(game));
            dispose();
        } else if (gameOver()) {
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
        upTexture.dispose();
        leftTexture.dispose();
        rightTexture.dispose();
        stage.dispose();
        skin.dispose();
    }
}