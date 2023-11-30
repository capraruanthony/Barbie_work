package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.barbie.BARBIE;
import com.badlogic.gdx.Input;

public class OpeningScreen implements Screen {
    private BARBIE game;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture playButtonTexture;

    public OpeningScreen(BARBIE game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("open_screen.png"));
        playButtonTexture = new Texture(Gdx.files.internal("PlayBtn.png"));
    }

    @Override
    public void show() {
        // Additional setup when the screen is shown (if needed)
    }

    @Override
    public void render(float delta) {
        System.out.println("Touched!");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float buttonWidth = Gdx.graphics.getWidth() / 2;
        float buttonHeight = Gdx.graphics.getHeight() / 1;
        float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
        float buttonY = Gdx.graphics.getHeight() / 2 - buttonHeight / 2 - 20; // moving the button down slightly to look better
        batch.draw(playButtonTexture, buttonX, buttonY, buttonWidth, buttonHeight);

        batch.end();

        // Handle input (e.g., touch events to start the game)
        if (Gdx.input.isTouched()) {
            game.setScreen(new PlayScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        // Handle screen resize if needed
    }

    @Override
    public void pause() {
        // Handle screen pause if needed
    }

    @Override
    public void resume() {
        // Handle screen resume if needed
    }

    @Override
    public void hide() {
        // Additional actions when the screen is hidden (if needed)
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playButtonTexture.dispose();
        batch.dispose();
    }
}