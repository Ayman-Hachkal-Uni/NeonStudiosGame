package io.github.NeonStudiosGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.NeonStudiosGame.UniSim;


/**
 * First screen of the application, or the "main menu". Displayed after
 * the application is created or when returned to from another screen.
 * */
public class FirstScreen implements Screen {
    private final UniSim parent; // Specifies our parent class
    private final Stage stage;

    private final Texture background1;
    private final SpriteBatch batch;
    //float xMax, xCoordBg, xCoordBg2;
    //final int BG_SCROLL_SPEED = 20;

    private final Image logo;

    /**
     * Method implemented in all screen classes to initialise the screen.
     * @param UniSim tells the method the parent class
     * code commented out makes the background scroll, useful if using an image
     * or more detailed background. Removed due to bugs that were difficult to solve
     */
    public FirstScreen (UniSim UniSim) {
        parent = UniSim; // Used for purposes such as switching screens
        stage = new Stage(new FitViewport(640f, 360f)); // Creates a new stage object for the
        // current screen
        background1 = new Texture(Gdx.files.internal("Background.png"));
        //background2 = new Texture(Gdx.files.internal("uniyork.jpg"));
        //xMax = 1500;
        //xCoordBg = xMax*(-1);
        batch = new SpriteBatch();
        logo = new Image(new Texture(Gdx.files.internal("UniSimTitleNew.png")));
    }

    /**
     * Method used to specify the things to display on the screen when it is loaded.
     * buttons on the menu are aligned using a table which can be tweaked as needed.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Prepares the screen to register user inputs
        Table table = new Table(); // Similar to a statistical table; used to position buttons
        table.setFillParent(true);
        table.setDebug(false); // Used for testing; set this to TRUE to see button hitboxes
        stage.addActor(table); // Adds a table to our screen

        Skin skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json")); // PLACEHOLDER contains menu textures
        TextButton startGameButton = new TextButton("New Game", skin); // Interactive buttons
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit", skin);


        table.add(logo);
        table.row().pad(20,0,20,0);
        table.add(startGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0); // Sets gap between table rows
        table.add(settingsButton).fillX().uniformX();
        table.row();
        table.add(exitButton).fillX().uniformX();

        startGameButton.addListener(new ChangeListener() { // Activates when the specified button is clicked
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(UniSim.GAME); // Changes screens to the game screen
                stage.clear();
            }
        });

        settingsButton.addListener(new ChangeListener() { // Activates when the specified button is clicked
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(UniSim.SETTINGS); // Changes screens to the settings screen
                stage.clear(); // Clears the stage to prevent a duplicate stage creation through repeated menu switch
            }
        });


        exitButton.addListener(new ChangeListener() { // Activates when the specified button is clicked
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // Closes the application
            }
        });
    }

    /**
     * displays content specified in the show method to the screen, created as a "stage".
     * @param delta tells the screen how often it should update, allowing it to respond
     * to user input as it occurs.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //xCoordBg += BG_SCROLL_SPEED * Gdx.graphics.getDeltaTime();
        //xCoordBg2 = xCoordBg + xMax;  // We move the background, not the camera
        //if (xCoordBg >= 0) {
        //    xCoordBg = xMax*(-1); xCoordBg2 = 0;
        //}
        batch.begin();
        batch.draw(background1, 0, 0);
        //batch.draw(background2, xCoordBg2, 0);
        batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // updates stage based on interaction
        stage.draw(); // Draws our "stage" to the screen
    }

    /**
     * Used when the window is being resized through any means. Ensures that the game is playable
     * at any resolution
     * @param width the current width of the window
     * @param height the current height of the window
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Adjusts screen elements in line with window
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    /**
     * Called when switching screens to prevent multiple screens being active at one time
     */
    @Override
    public void dispose() {
        stage.dispose();
    } // Removes the current screen
}
