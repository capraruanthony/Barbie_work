package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.barbie.BARBIE;

import java.awt.desktop.ScreenSleepListener;

public class WinScreen implements Screen {
    private BARBIE game;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture winImage;

    public WinScreen(BARBIE game) {
        this.game = game;
        this.viewport = new FitViewport(BARBIE.V_WIDTH, BARBIE.V_HEIGHT, new OrthographicCamera());
        this.batch = new SpriteBatch();
        this.winImage = new Texture("Win_Screen.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw your win image
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(winImage, 0, 0, BARBIE.V_WIDTH, BARBIE.V_HEIGHT);
        batch.end();

        if(Gdx.input.justTouched()){
            game.setScreen(new PlayScreen(game));
            //dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        winImage.dispose();
    }
}
