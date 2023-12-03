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

    private Texture helpButtonTexture;
    private Texture helpScreenTexture;
    private Texture exitButtonTexture;

    private boolean isHelpScreenOpen = false;

    private float howToPlayButtonX;
    private float howToPlayButtonY;
    private float howToPlayButtonWidth;
    private float howToPlayButtonHeight;

    private float exitButtonX;
    private float exitButtonY;
    private float exitButtonWidth;
    private float exitButtonHeight;

    private float helpScreenWidth;
    private float helpScreenHeight;

    public OpeningScreen(BARBIE game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("open_screen.png"));
        playButtonTexture = new Texture(Gdx.files.internal("PlayBtn.png"));
        helpScreenTexture = new Texture(Gdx.files.internal("help_screen.png"));
        helpButtonTexture = new Texture(Gdx.files.internal("help_button.png"));
        exitButtonTexture = new Texture(Gdx.files.internal("exit_button.png"));

        // Sizing and positions of help and exit help buttons
        howToPlayButtonWidth = Gdx.graphics.getWidth() / 10; //adjust dims was 10
        howToPlayButtonHeight = Gdx.graphics.getHeight() / 4; // was 4
        howToPlayButtonX = Gdx.graphics.getWidth()  - howToPlayButtonWidth - 20; //positions to adjust
        howToPlayButtonY = Gdx.graphics.getHeight() - howToPlayButtonHeight - 20;

        exitButtonWidth = Gdx.graphics.getWidth() / 9; //was 4: adjusted size
        exitButtonHeight = Gdx.graphics.getHeight() / 9; // was 8
        exitButtonX = Gdx.graphics.getWidth() - exitButtonWidth - 20; //coordinates of exit button
        exitButtonY = Gdx.graphics.getHeight() - exitButtonHeight - 20;
    }

    @Override
    public void show() {
        // Additional setup when the screen is shown (if needed)
    }

    @Override
    public void render(float delta) {
        //System.out.println("Touched"); //testing line
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float buttonWidth = Gdx.graphics.getWidth() / 2;
        float buttonHeight = Gdx.graphics.getHeight() / 1;
        float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
        float buttonY = Gdx.graphics.getHeight() / 2 - buttonHeight / 2 - 20;
        //float helpScreenX = (Gdx.graphics.getWidth() - helpScreenTexture.getWidth()) / 2;// to center the help screen

        float helpScreenX = (Gdx.graphics.getWidth() - helpScreenTexture.getWidth()) / 2f; //float
        //float helpScreenY = (Gdx.graphics.getHeight() - helpScreenTexture.getHeight()) / 2f;

        // Check if the help screen is open
        if (isHelpScreenOpen) {
            // Draw the help screen
            batch.draw(helpScreenTexture, helpScreenX-350, 0, Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight());

            // Show exit button for the help screen
            batch.draw(exitButtonTexture, exitButtonX, exitButtonY, exitButtonWidth, exitButtonHeight);
        } else {
            // Draw  Play button
            batch.draw(playButtonTexture, buttonX, buttonY, buttonWidth, buttonHeight);

            // Drawing the How to Play button
            batch.draw(helpButtonTexture, howToPlayButtonX, howToPlayButtonY, howToPlayButtonWidth, howToPlayButtonHeight);
        }

        batch.end();

        //if (Gdx.input.isTouched()) {
        //    game.setScreen(new PlayScreen(game));
        //}

        // input
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invert Y axis

            if (!isHelpScreenOpen) {
                // Check if touch is on the Play button
                if (touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                        touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                    game.setScreen(new PlayScreen(game));
                }

                // Check if touch is on the How to Play button
                if (touchX >= howToPlayButtonX && touchX <= howToPlayButtonX + howToPlayButtonWidth &&
                        touchY >= howToPlayButtonY && touchY <= howToPlayButtonY + howToPlayButtonHeight) {
                    // Set the help screen to open
                    isHelpScreenOpen = true;
                }
            } else {
                // Check if the touch is inside the "Exit" button when the help screen is open
                if (touchX >= exitButtonX && touchX <= exitButtonX + exitButtonWidth &&
                        touchY >= exitButtonY && touchY <= exitButtonY + exitButtonHeight) {
                    // Close help screen
                    isHelpScreenOpen = false;
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // resize
    }

    @Override
    public void pause() {
        // screen pause
    }

    @Override
    public void resume() {
        //screen resume
    }

    @Override
    public void hide() {
        // Additional actions when screen is hidden
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playButtonTexture.dispose();
        helpButtonTexture.dispose();
        exitButtonTexture.dispose(); // cleaning up
        batch.dispose();
    }
}