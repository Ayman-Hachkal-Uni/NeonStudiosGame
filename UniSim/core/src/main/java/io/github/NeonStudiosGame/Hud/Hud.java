package io.github.NeonStudiosGame.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * The type Hud.
 */
public class Hud {
    /**
     * The Stage.
     */
    public Stage stage;
    /**
     * The Viewport.
     */
    public FitViewport viewport;
    /**
     * The Width.
     */
    float width;
    /**
     * The Height.
     */
    float height;
    private int worldTime;
    private int score;
    /**
     * The Skin.
     */
    Skin skin;
    /**
     * The Time label.
     */
    Label timeLabel;
    /**
     * The Score label.
     */
    Label scoreLabel;
    /**
     * The Teaching year progress bar.
     */
    ProgressBar teachingYearProgressBar;
    /**
     * The Summer progress bar.
     */
    ProgressBar summerProgressBar;
    /**
     * The Play button.
     */
    ImageButton playButton;
    /**
     * The Central hall image button.
     */
    ImageButton centralHallImageButton;
    /**
     * The Bar image button.
     */
    ImageButton barImageButton;
    /**
     * The Lecture theatre image button.
     */
    ImageButton lectureTheatreImageButton;
    /**
     * The Restaurant image button.
     */
    ImageButton restaurantImageButton;
    /**
     * The Road image button.
     */
    ImageButton roadImageButton;
    /**
     * The SportsHall image button.
     */
    ImageButton sportsHallImageButton;
    /**
     * The Build buttons.
     */
    public ButtonGroup<ImageButton> buildButtons;

    /**
     * The Table.
     */
    Table table;
    /**
     * The Build table.
     */
    Table buildTable;
    /**
     * The Progress bar stack.
     */
    Stack progressBarStack;

    /**
     * Instantiates a new Hud.
     *
     * @param sb the sprite batch
     */
    public Hud(SpriteBatch sb){
        //Initial value for score
        score = 0;
        //Width and height of viewport and camera
        width = 1280;
        height = 720;

        viewport = new FitViewport(1280,720, new OrthographicCamera());

        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);

        //SKIN we are using which will be changed
        skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json"));


        //Table at the top of the hud
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        //Sets it to the top
        table.center().top();

        //The build menu at the bottom of the hud
        buildTable = new Table();
        stage.addActor(buildTable);
        buildTable.setFillParent(true);
        //sets it to the bottom center;
        buildTable.center().bottom();
        buildTable.setVisible(false);

        //Button group -- Very important (used to make sure only one of the buildings is selectable;
        buildButtons = new ButtonGroup<>();
        //sets max checked to 1
        buildButtons.setMaxCheckCount(1);
        buildButtons.setMinCheckCount(0);
        //Makes sure no buildings are checked
        buildButtons.uncheckAll();

        //Time and score label
        timeLabel = new Label(String.format("Time: %03d", worldTime), skin);
        scoreLabel = new Label(String.format("Score: %07d", score), skin);

        //Progress bars for the time
        progressBarStack = new Stack();
        teachingYearProgressBar = new ProgressBar(0f, 55f, 1f, false, skin);
        summerProgressBar = new ProgressBar(0f, 25f, 1f, false, skin);

        //adds it to a stack so they overlap
        progressBarStack.add(teachingYearProgressBar);
        progressBarStack.add(summerProgressBar);

        //Textures for the play and pause button
        Texture playTexture  = new Texture(Gdx.files.internal("Sprites/Play.png"));
        Texture pauseTexture  = new Texture(Gdx.files.internal("Sprites/Pause.png"));

        TextureRegion playTextureRegion = new TextureRegion(playTexture);
        TextureRegion pauseTextureRegion = new TextureRegion(pauseTexture);

        TextureRegionDrawable playTextureRegionDrawable = new TextureRegionDrawable(playTextureRegion);
        TextureRegionDrawable pauseTextureRegionDrawable = new TextureRegionDrawable(pauseTextureRegion);

        playButton = new ImageButton(playTextureRegionDrawable);
        //sets textures as the checked state (so when pause is enabled)
        playButton.getStyle().checked = pauseTextureRegionDrawable;
        playButton.getStyle().imageChecked = pauseTextureRegionDrawable;

        //Building Buttons
        //Central Hall
        Texture centralHallTexture = new Texture(Gdx.files.internal("buildings/halls.png"));
        TextureRegion centralHallRegion = new TextureRegion(centralHallTexture);
        TextureRegionDrawable centralHallRegionDrawable = new TextureRegionDrawable(centralHallRegion);
        centralHallImageButton = new ImageButton(centralHallRegionDrawable);
        centralHallImageButton.addListener(new TextTooltip("Central Hall", skin));

        //Bar
        Texture barTexture = new Texture(Gdx.files.internal("buildings/bar.png"));
        TextureRegion barRegion = new TextureRegion(barTexture);
        TextureRegionDrawable barRegionDrawable = new TextureRegionDrawable(barRegion);
        barImageButton = new ImageButton(barRegionDrawable);

        //Lecture Theatre
        Texture lectureTheatreTexture = new Texture(Gdx.files.internal("buildings/lecture_theatre.png"));
        TextureRegion lectureTheatreRegion = new TextureRegion(lectureTheatreTexture);
        TextureRegionDrawable lectureTheatreRegionDrawable = new TextureRegionDrawable(lectureTheatreRegion);
        lectureTheatreImageButton = new ImageButton(lectureTheatreRegionDrawable);

        //Restaurant
        Texture restaurantTextureTexture = new Texture(Gdx.files.internal("buildings/restaurant.png"));
        TextureRegion restaurantRegion = new TextureRegion(restaurantTextureTexture);
        TextureRegionDrawable restaurantRegionDrawable = new TextureRegionDrawable(restaurantRegion);
        restaurantImageButton = new ImageButton(restaurantRegionDrawable);

        //Road
        Texture roadTextureTexture = new Texture(Gdx.files.internal("buildings/road.png"));
        TextureRegion roadRegion = new TextureRegion(roadTextureTexture);
        TextureRegionDrawable roadRegionDrawable = new TextureRegionDrawable(roadRegion);
        roadImageButton = new ImageButton(roadRegionDrawable);

        //Sports Hall
        Texture sportsHallTexture = new Texture(Gdx.files.internal("buildings/sports_hall.png"));
        TextureRegion sportsHallRegion = new TextureRegion(sportsHallTexture);
        TextureRegionDrawable sportsHallRegionDrawable = new TextureRegionDrawable(sportsHallRegion);
        sportsHallImageButton = new ImageButton(sportsHallRegionDrawable);


        //adding buidings image button to button group
        buildButtons = new ButtonGroup<>();
        buildButtons.add(centralHallImageButton);
        buildButtons.add(barImageButton);
        buildButtons.add(lectureTheatreImageButton);
        buildButtons.add(restaurantImageButton);
        buildButtons.add(roadImageButton);
        buildButtons.add(sportsHallImageButton);


        //making top hud table
        table.add(scoreLabel).pad(15);
        table.add(timeLabel).pad(15).padLeft(60).expandX();
        table.add(playButton).pad(15).size(50, 50).expandX().align(Align.right);
        table.row();
        table.add(progressBarStack).colspan(3).center();
        table.row();

        //BuildTable Properties;
        buildTable.defaults().padBottom(15).size(50,50);
        //adding each building
        buildTable.add(centralHallImageButton);
        buildTable.add(barImageButton);
        buildTable.add(lectureTheatreImageButton);
        buildTable.add(restaurantImageButton);
        buildTable.add(roadImageButton);
        buildTable.add(sportsHallImageButton);

        //Allows the images to be transformable so we can change the size
        centralHallImageButton.getImage().setOrigin(Align.center);
        centralHallImageButton.getImage().setFillParent(true);

        barImageButton.getImage().setOrigin(Align.center);
        barImageButton.getImage().setFillParent(true);

        lectureTheatreImageButton.getImage().setOrigin(Align.center);
        lectureTheatreImageButton.getImage().setFillParent(true);

        restaurantImageButton.getImage().setOrigin(Align.center);
        restaurantImageButton.getImage().setFillParent(true);

        roadImageButton.getImage().setOrigin(Align.center);
        roadImageButton.getImage().setFillParent(true);

        sportsHallImageButton.getImage().setOrigin(Align.center);
        sportsHallImageButton.getImage().setFillParent(true);

    }

    /**
     * Hide build mode.
     */
//if buildmode disabled and enabled it hide and displays the buildTable respectively
    public void hideBuildMode () {
        buildTable.setVisible(false);
    }

    /**
     * Show build mode.
     */
    public void showBuildMode () {
        buildTable.setVisible(true);
    }

    /**
     * Is paused boolean.
     *
     * @return the boolean
     */
//checks if the state is paused by getting the imagebutton state
    public boolean isPaused() {
        return playButton.isChecked();
    }

    /**
     * Update time.
     *
     * @param time the time
     */
    public void updateTime(float time) {
        long minute = (long) ((time % 3600) / 60);
        long seconds = (long) (time % 60);
        timeLabel.setText(String.format("Time : %01d:%02d", minute, seconds));
        float season = (long) (time % 80);
        if (season <= 25) {
            updateSummerProgress(season);
        }
        else {
            updateTeachingProgress(season);
        }
    }

    /**
     * Update score.
     *
     * @param score the score
     */
    public void updateScore(long  score) {
        scoreLabel.setText(String.format("Score : %07d", score));
    }

    /**
     * Update teaching progress.
     *
     * @param time the time
     */
    public void updateTeachingProgress(float time) {
        teachingYearProgressBar.setVisible(true);
        summerProgressBar.setVisible(false);
        time = time - 25;
        teachingYearProgressBar.setValue(time);
    }

    /**
     * Update summer progress.
     *
     * @param time the time
     */
    public void updateSummerProgress(float time) {
        summerProgressBar.setVisible(true);
        teachingYearProgressBar.setVisible(false);
        summerProgressBar.setValue(time);
    }
}
