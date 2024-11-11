package io.github.NeonStudiosGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.NeonStudiosGame.UniSim;

/**
 * The settings screen of the application, used to tweak peripherals within the application
 * to the user's preference
 */
public class SettingsScreen implements Screen {

    private final Stage stage;
    private final UniSim parent;
    private final Texture settingsbg;
    private final SpriteBatch batch;





    public SettingsScreen(UniSim UniSim) {
        parent = UniSim; // Sets the parent class (used to call preferences, switch screens)
        stage = new Stage(new FitViewport(640f, 360f));
        Gdx.input.setInputProcessor(stage);
        settingsbg = new Texture(Gdx.files.internal("Background.png"));
        batch = new SpriteBatch();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false); // Used for testing; set this to TRUE to see button hitboxes
        stage.addActor(table); // Same table creation idea as main

        Skin skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json"));
        Label titleLabel = new Label("Preferences", skin); // Creating labels for the table
        Label musicSliderLabel = new Label("Music Volume", skin);
        Label volumeSliderLabel = new Label("SFX Volume", skin);
        //Label largeFontLabel = new Label("Large Font", skin);
        //Label highContrastLabel = new Label("High Contrast", skin);
        Label fullscreenLabel = new Label("Fullscreen", skin);

        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue( parent.getPreferences().getMusicVolume());
        // Activated on slider adjustment
        volumeMusicSlider.addListener(event -> { // Sets value to slider selection
            parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
            return false;
        });

        final Slider soundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        soundSlider.setValue( parent.getPreferences().getSoundVolume());
        soundSlider.addListener(event -> {
            parent.getPreferences().setSoundVolume(soundSlider.getValue());
            return false;
        });

        /*
        final CheckBox highContrastCheckbox = new CheckBox(null, skin);
        highContrastCheckbox.setChecked( parent.getPreferences().isHighContrastEnabled());
        highContrastCheckbox.addListener(event -> {
            boolean enabled = highContrastCheckbox.isChecked();
            parent.getPreferences().setHighContrastEnabled(enabled);
            return false;
        });

        final CheckBox largeFontCheckbox = new CheckBox(null, skin);
        largeFontCheckbox.setChecked( parent.getPreferences().isLargeFontEnabled());
        largeFontCheckbox.addListener(event -> {
            boolean enabled = largeFontCheckbox.isChecked();
            parent.getPreferences().setLargeFontEnabled(enabled);
            return false;
        });

         */
        final CheckBox fullscreenCheckbox = new CheckBox(null, skin);
        fullscreenCheckbox.addListener(event -> {
            boolean enabled = fullscreenCheckbox.isChecked();
            parent.getPreferences().setFullscreenEnabled(enabled);
            return false;
        });




        final TextButton backButton = new TextButton("Back", skin, "default");
        backButton.addListener(new ChangeListener() { // Activates on button press
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(UniSim.MENU); // Returns to the title screen
                stage.clear(); // Clears the stage to prevent a duplicate stage creation through repeated menu switch
            }
        });



        table.add(titleLabel).pad(0, 10, 20, 10);
        table.row(); // Sets gap between table rows
        table.row();
        table.add(musicSliderLabel);
        table.add(volumeMusicSlider);
        table.row();
        table.add(volumeSliderLabel);
        table.add(soundSlider);
        table.row();
        table.add(fullscreenLabel).padTop(10);
        table.add(fullscreenCheckbox).padTop(10);
        table.add(backButton).padTop(10);

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(settingsbg, 0, 0);
        batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }

}
