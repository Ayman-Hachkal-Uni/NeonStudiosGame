package io.github.NeonStudiosGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.NeonStudiosGame.UniSim;

public class SettingsScreen implements Screen {

    private Stage stage;

    private Label TitleLabel;
    private Label MusicSliderLabel;
    private Label VolumeSliderLabel;
    private Label musicToggleLabel;
    private Label VolumeToggleLabel;
    private Label largeFontLabel;
    private Label highContrastLabel;
    private UniSim parent;

    Sound soundTest = Gdx.audio.newSound(Gdx.files.internal("game_pop_sound.wav"));


    public SettingsScreen(UniSim UniSim) {
        parent = UniSim; // Sets the parent class (used to call preferences, switch screens)
        stage = new Stage(new FitViewport(640f, 360f));
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false); // Used for testing; set this to TRUE to see button hitboxes
        stage.addActor(table); // Same table creation idea as main

        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
        TitleLabel = new Label( "Preferences", skin ); // Creating labels for the table
        MusicSliderLabel = new Label( "Music Volume", skin );
        VolumeSliderLabel = new Label( "SFX Volume", skin );
        musicToggleLabel = new Label( "Enable Music", skin );
        VolumeToggleLabel = new Label( "Enable SFX", skin );
        largeFontLabel = new Label( "Large Font", skin );
        highContrastLabel = new Label( "High Contrast", skin );

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
            soundTest.play(soundSlider.getValue());
            return false;
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( parent.getPreferences().isMusicEnabled() );
        // Activated on checkbox interaction
        musicCheckbox.addListener(event -> { // Sets value to a BOOLEAN based on checkbox state
            boolean enabled = musicCheckbox.isChecked();
            parent.getPreferences().setMusicEnabled(enabled);
            return false;
        });

        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked( parent.getPreferences().isSoundEffectsEnabled());
        soundCheckbox.addListener(event -> {
            boolean enabled = soundCheckbox.isChecked();
            parent.getPreferences().setSoundEffectsEnabled( enabled );
            return false;
        });

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



        final TextButton backButton = new TextButton("Back", skin, "default");
        backButton.addListener(new ChangeListener() { // Activates on button press
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.clear(); // Clears the stage to prevent a duplicate stage creation through repeated menu switch
                parent.changeScreen(UniSim.MENU); // Returns to the title screen
            }
        });



        table.add(TitleLabel);
        table.row().pad(20, 10, 20, 10); // Sets gap between table rows
        table.row();
        table.add(MusicSliderLabel);
        table.add(volumeMusicSlider);
        table.row();
        table.add(musicToggleLabel);
        table.add(musicCheckbox);
        table.row();
        table.add(VolumeSliderLabel);
        table.add(soundSlider);
        table.row();
        table.add(VolumeToggleLabel);
        table.add(soundCheckbox);
        table.row();
        table.add(largeFontLabel);
        table.add(largeFontCheckbox);
        table.row();
        table.add(highContrastLabel);
        table.add(highContrastCheckbox);
        table.add(backButton);

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0f, 2f, 3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }

}
