package io.github.NeonStudiosGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {

    private static final String Settings_Music_Vol = "Volume";
    private static final String Settings_Music_Enabled = "Music?";
    private static final String Settings_Sound_Effects = "Sound_Effects?";
    private static final String Settings_Sound_Vol = "SFX Volume?";
    private static final String Settings_High_Contrast = "High_Contrast?";
    private static final String Settings_Large_Font = "Large_Font?";
    private static final String Settings_Name = "UniSim_Settings";

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
        getSettings().flush();
    }

    public boolean isMusicEnabled() {
        return getSettings().getBoolean(Settings_Music_Enabled, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getSettings().putBoolean(Settings_Music_Enabled, musicEnabled);
        getSettings().flush();
    }

    public boolean isSoundEffectsEnabled() {
        return getSettings().getBoolean(Settings_Sound_Effects, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getSettings().putBoolean(Settings_Sound_Effects, soundEffectsEnabled);
        getSettings().flush();
    }
    public float getSoundVolume() {
        return getSettings().getFloat(Settings_Sound_Vol, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getSettings().putFloat(Settings_Sound_Vol, volume);
        getSettings().flush();
    }


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
}
