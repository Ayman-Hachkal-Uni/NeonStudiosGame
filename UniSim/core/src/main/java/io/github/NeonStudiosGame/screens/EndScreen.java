package io.github.NeonStudiosGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.NeonStudiosGame.Scorer;
import io.github.NeonStudiosGame.UniSim;

public class EndScreen implements Screen {
    UniSim parent;
    Stage stage;
    Skin skin;
    Scorer scorer;
    Label score;
    TextButton restartGame;
    TextButton mainMenu;
    TextButton quit;
    public EndScreen(UniSim uniSim, Scorer scorer) {
        parent = uniSim;
        this.stage = new Stage(new FitViewport(1920f, 1080f));
        this.scorer = scorer;
        skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json"));
        Gdx.input.setInputProcessor(stage); // Prepares the screen to register user inputs
    }

    @Override
    public void show() {
            Table table = new Table();
            stage.addActor(table);
            table.setVisible(true);
            table.setFillParent(true);

            score = new Label(String.format("Score: %07d", this.scorer.getScore()), skin);
            score.setAlignment(Align.center);
            restartGame = new TextButton("Restart Game", skin);
            mainMenu = new TextButton("Main Menu", skin);
            quit = new TextButton("Quit", skin);

            restartGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    parent.changeScreen(UniSim.GAME);
                    stage.clear();
                }
            });
            mainMenu.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    parent.changeScreen(UniSim.MENU);
                    stage.clear();
                }
            });
            quit.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    Gdx.app.exit();
                }
            });

            table.add(score).size(100).center();
            table.row();
            table.add(restartGame).center().size(100);
            table.row();
            table.add(mainMenu).center().size(100);
            table.row();
            table.add(quit).center().size(100);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();

    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1);

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
    public void dispose() {stage.dispose();}
}
