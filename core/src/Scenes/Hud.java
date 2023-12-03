package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.barbie.BARBIE;

// the Hud class is responsible for displaying the Heads-Up Display (HUD)
// on the screen, which includes information like score, timer, and labels.
public class Hud implements Disposable {
    public Stage stage; // the Stage for holding HUD elements
    private Viewport viewport; //we need another viewport because when our
                              // gameworld moves we want our hud to stay the same
    private Integer worldTimer; // timer for the game world
    private float timeCount; // timer for tracking elapsed time

    private Integer score; // player's score
    private float scoreCount;

    // labels for displaying countdown, score, level, and other information
    Label countdownLabel;
    Label scoreLabel;
    Label nameLabel;
    Label timeLabel;
    Label levelLabel;
    Label barbielabel;

    // Constructor for initializing HUD elements
    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        // Set up the viewport for HUD
        viewport = new FitViewport(BARBIE.V_WIDTH, BARBIE.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        // create a table for organizing HUD elements
        Table table = new Table();
        table.top();    //making it at the top of our stage
        table.setFillParent(true); //the table is the size of our stage

        // create labels for countdown, score, level, and other informa tion
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        nameLabel = new Label("Level", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        levelLabel =new Label("1", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        barbielabel = new Label("BARBIE", new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        // add labels to the table with padding and expansion settings
        table.add(barbielabel).expandX().padTop(5).align(Align.top);
        table.add(nameLabel).expandX().padTop(5).align(Align.top);
        table.add(timeLabel).expandX().padTop(5).align(Align.top);
        table.row();
        table.add(scoreLabel).expandX().padTop(5).align(Align.top);
        table.add(levelLabel).expandX().padTop(5).align(Align.top);
        table.add(countdownLabel).expand().padTop(5).align(Align.top);

        stage.addActor(table); //adding the table to stage
    }

    public void update(float dt){
      timeCount += dt;
      if (timeCount >= 1){
          worldTimer--;
          countdownLabel.setText(String.format("%03d", worldTimer));
          timeCount = 0;
      }
      scoreCount += dt;
      if (scoreCount >= 1){
          score = score+ 10;
          scoreLabel.setText(String.format("%06d", score));
          scoreCount = 0;
      }


    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}









