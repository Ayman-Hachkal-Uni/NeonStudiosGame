package io.github.NeonStudiosGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.NeonStudiosGame.screens.EndScreen;
import io.github.NeonStudiosGame.screens.FirstScreen;
import io.github.NeonStudiosGame.screens.GameScreen;
import io.github.NeonStudiosGame.screens.SettingsScreen;

import static io.github.NeonStudiosGame.AppPreferences.music;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class UniSim extends Game {

    /*
    The parent class, used to load the initial assets for the game and also referred to
    for various tasks such as to switch screens or fetch values. All classes should implement
    a parent object of UniSim, used to call back to this class.
     */

    public SpriteBatch batch;
    public BitmapFont font;



    private SettingsScreen SettingsScreen;
    private FirstScreen FirstScreen;
    private GameScreen GameScreen;
    private EndScreen EndScreen;

    public final static int MENU = 0;
    public final static int SETTINGS = 1;
    public final static int GAME = 2;
    public final static int END = 3;

    private AppPreferences preferences;
    public AppPreferences getPreferences() { // Called by the settings screen to load current settings
        return this.preferences;
    }



    public void changeScreen(int screen){
        switch(screen){ // Switch case which changes current screen based on selected INT value
            case MENU:
                if(FirstScreen == null) FirstScreen = new FirstScreen(this);
                this.setScreen(FirstScreen);
                break;
            case SETTINGS:
                if(SettingsScreen == null) SettingsScreen = new SettingsScreen(this);
                this.setScreen(SettingsScreen);
                break;
            case GAME:
                if(GameScreen == null) GameScreen = new GameScreen(this);
                this.setScreen(GameScreen);
                break;
            case END:
                if (EndScreen == null) EndScreen = new EndScreen(this);
                this.setScreen(EndScreen);
                break;
        }

    }
    @Override
    public void create() {
        music.play();
        music.setVolume(0.2f); // Sets the music to be quieter
        preferences = new AppPreferences();
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new FirstScreen(this));


        setScreen(new FirstScreen(this)); // Loads the first screen to be shown when the app opens
        preferences.setFullscreenEnabled(false);
    }

    public void render() {
        super.render();
    } // Renders all assets
}
