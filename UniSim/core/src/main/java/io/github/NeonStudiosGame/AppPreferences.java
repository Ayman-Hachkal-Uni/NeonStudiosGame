package io.github.NeonStudiosGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class AppPreferences {
    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("game-music.mp3")); // PLACEHOLDER menu music

    private static final String Settings_Music_Vol = "Volume";
    private static final String Settings_Sound_Vol = "SFX Volume?";
    private static final String Settings_High_Contrast = "High_Contrast?";
    private static final String Settings_Large_Font = "Large_Font?";
    private static final String Settings_Name = "UniSim_Settings";
    private static final String Settings_Fullscreen = "Fullscreen_Enabled?";

    Sound soundTest = Gdx.audio.newSound(Gdx.files.internal("game_pop_sound.wav"));

    /*
    This class is constructed of a list of getters and setters which are called by the
    SettingsScreen class through the parent class UniSim. TEMP not fully implemented yet,
    and needs some more work - sliders reset value when settings is closed.
     */

    protected Preferences getSettings() {
        return Gdx.app.getPreferences(Settings_Name);
    }


    public float getMusicVolume() {
        return getSettings().getFloat(Settings_Music_Vol, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getSettings().putFloat(Settings_Music_Vol, volume);
        music.setVolume(volume);
        getSettings().flush();
    }
    public float getSoundVolume() {
        return getSettings().getFloat(Settings_Sound_Vol, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getSettings().putFloat(Settings_Sound_Vol, volume);
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            soundTest.play();
        }
        getSettings().flush();
    }

    /*
    public boolean isHighContrastEnabled() {
        return getSettings().getBoolean(Settings_High_Contrast, true);
    }

    public void setHighContrastEnabled(boolean soundEffectsEnabled) {
        getSettings().putBoolean(Settings_High_Contrast, soundEffectsEnabled);
        getSettings().flush();
    }

    public boolean isLargeFontEnabled() {
        return getSettings().getBoolean(Settings_Large_Font, true);
    }

    public void setLargeFontEnabled(boolean soundEffectsEnabled) {
        getSettings().putBoolean(Settings_Large_Font, soundEffectsEnabled);
        getSettings().flush();
    }

     */
    public boolean isFullscreenEnabled() {
        return getSettings().getBoolean(Settings_Fullscreen, true);
    }
    public void setFullscreenEnabled(boolean fullscreenEnabled) {
        getSettings().putBoolean(Settings_Fullscreen, fullscreenEnabled);
        if(fullscreenEnabled) {
            Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
            Gdx.graphics.setFullscreenMode(currentMode);
        }
        else {
            Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        getSettings().flush();
    }
}
