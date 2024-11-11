package io.github.NeonStudiosGame;

import io.github.NeonStudiosGame.Hud.Hud;

/**
 * This class handles the game score, it takes requests to increment the score and return the score, it also sends
 * requests to the hud to update the score displayed on this screen.
 */
public class Scorer {
    Hud hud;
    int score = 0;

    /**
     * Constructor that sets up the scorer with the given hud.
     * @param hud the hud to send UI update requests to.
     */
    public Scorer(Hud hud) {
        this.hud = hud;
    }

    /**
     * Sends a request to the hud to change the displayed score to the current stored score.
     */
    private void updateScoreUI() {
        hud.updateScore(this.score);
    }

    /**
     * Increments the score with the given score and calls updateScoreUI to change the display.
     * @param score The score to increment the total score by.
     */
    public void addScore(int score) {
        this.score += score;
        updateScoreUI();
    }
    public int getScore() {
        return score;
    }
}
