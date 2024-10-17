package io.github.NeonStudiosGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.NeonStudiosGame.UniSim;


/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    private UniSim parent; // Specifies our parent class
    private Stage stage;






    public FirstScreen (UniSim UniSim) {
        parent = UniSim; // Used for purposes such as switching screens

        stage = new Stage(new FitViewport(640f, 360f)); // Creates a new stage object for the current screen
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Prepares the screen to register user inputs
        Table table = new Table(); // Similar to a statistical table; used to position buttons
        table.setFillParent(true);
        table.setDebug(false); // Used for testing; set this to TRUE to see button hitboxes
        stage.addActor(table); // Adds a table to our screen

        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json")); // PLACEHOLDER contains menu textures
        TextButton startGameButton = new TextButton("New Game", skin); // Interactive buttons
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit", skin);


        table.add(startGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0); // Sets gap between table rows
        table.add(settingsButton).fillX().uniformX();
        table.row();
        table.add(exitButton).fillX().uniformX();

        Music music = Gdx.audio.newMusic(Gdx.files.internal("game-music.mp3")); // PLACEHOLDER menu music
        music.play();
        music.setVolume(0.2f); // Sets the music to be quieter

        startGameButton.addListener(new ChangeListener() { // Activates when the specified button is clicked
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(UniSim.GAME); // Changes screens to the game screen
            }
        });

        settingsButton.addListener(new ChangeListener() { // Activates when the specified button is clicked
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(UniSim.SETTINGS); // Changes screens to the settings screen
            }
        });


        exitButton.addListener(new ChangeListener() { // Activates when the specified button is clicked
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // Closes the application
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 2f, 5f, 1); // PLACEHOLDER determines background colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // updates stage based on interaction
        stage.draw(); // Draws our "stage" to the screen
    }

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

    @Override
    public void dispose() {
        stage.dispose();
    } // Removes the current screen
}
