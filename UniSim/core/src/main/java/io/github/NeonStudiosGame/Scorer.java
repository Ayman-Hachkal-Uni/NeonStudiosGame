package io.github.NeonStudiosGame;

import io.github.NeonStudiosGame.Hud.Hud;
import io.github.NeonStudiosGame.screens.GameScreen;

public class Scorer {
    Hud hud;
    int score = 0;
    public Scorer(Hud hud) {
        this.hud = hud;
    }

    private void updateScoreUI() {
        hud.updateScore(this.score);
    }

    public void addScore(int score) {
        System.out.println(score);
        this.score += score;
        updateScoreUI();
    }
    public int getScore() {
        return score;
    }
}
