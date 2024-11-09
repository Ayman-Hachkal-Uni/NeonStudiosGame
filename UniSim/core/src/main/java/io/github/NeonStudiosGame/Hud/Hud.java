package io.github.NeonStudiosGame.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.NeonStudiosGame.UniSim;

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
    int year;
    String season;
    /**
     * The Play button.
     */
    ImageButton playButton;
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
    Label buildingCounterLabel;
    Table buildToggleTable;
    public ImageButton buildMenu;
    ImageButton closeBuildMenu;

    ButtonGroup<ImageButton> closeAndOpenButtonGroup;
    /**
     * Instantiates a new Hud.
     *
     * @param sb the sprite batch
     */
    public Hud(SpriteBatch sb, UniSim uniSim){
        //Initial value for score
        score = 0;
        //Width and height of viewport and camera
        width = 1280;
        height = 720;
        year = 1;
        this.season = "S";

        viewport = new FitViewport(1280,720, new OrthographicCamera());

        stage = new Stage(viewport, sb);

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

        buildToggleTable = new Table();
        stage.addActor(buildToggleTable);
        buildToggleTable.setFillParent(true);
        buildToggleTable.left().bottom();
        buildToggleTable.setVisible(true);

        buildMenu = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Sprites/hammer.png")))));

        buildToggleTable.add(buildMenu).size(70,70).pad(15);

        //Button group -- Very important (used to make sure only one of the buildings is selectable;
        buildButtons = new ButtonGroup<>();
        //sets max checked to 1
        buildButtons.setMaxCheckCount(1);
        buildButtons.setMinCheckCount(0);
        //Makes sure no buildings are checked
        buildButtons.uncheckAll();

        //Time and score label
        timeLabel = new Label(String.format("Time: %03d", worldTime), skin);
        timeLabel.setAlignment(Align.center);
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

        buildingCounterLabel = new Label(String.format("Buildings: 0"), skin);

        //Building Buttons
        //Central Hall
        Texture centralHallTexture = new Texture(Gdx.files.internal("buildings/halls.png"));
        TextureRegion centralHallRegion = new TextureRegion(centralHallTexture);
        TextureRegionDrawable centralHallRegionDrawable = new TextureRegionDrawable(centralHallRegion);
        ImageButton hallImageButton = new ImageButton(centralHallRegionDrawable);

        //Bar
        Texture barTexture = new Texture(Gdx.files.internal("buildings/bar.png"));
        TextureRegion barRegion = new TextureRegion(barTexture);
        TextureRegionDrawable barRegionDrawable = new TextureRegionDrawable(barRegion);
        ImageButton barImageButton = new ImageButton(barRegionDrawable);

        //Lecture Theatre
        Texture lectureTheatreTexture = new Texture(Gdx.files.internal("buildings/lecture_theatre.png"));
        TextureRegion lectureTheatreRegion = new TextureRegion(lectureTheatreTexture);
        TextureRegionDrawable lectureTheatreRegionDrawable = new TextureRegionDrawable(lectureTheatreRegion);
        ImageButton lectureImageButton = new ImageButton(lectureTheatreRegionDrawable);

        //Restaurant
        Texture restaurantTextureTexture = new Texture(Gdx.files.internal("buildings/restaurant.png"));
        TextureRegion restaurantRegion = new TextureRegion(restaurantTextureTexture);
        TextureRegionDrawable restaurantRegionDrawable = new TextureRegionDrawable(restaurantRegion);
        ImageButton restaurantImageButton = new ImageButton(restaurantRegionDrawable);

        //Road
        Texture roadTextureTexture = new Texture(Gdx.files.internal("buildings/road.png"));
        TextureRegion roadRegion = new TextureRegion(roadTextureTexture);
        TextureRegionDrawable roadRegionDrawable = new TextureRegionDrawable(roadRegion);
        ImageButton roadImageButton = new ImageButton(roadRegionDrawable);

        //Sports Hall
        Texture sportsHallTexture = new Texture(Gdx.files.internal("buildings/sports_hall.png"));
        TextureRegion sportsHallRegion = new TextureRegion(sportsHallTexture);
        TextureRegionDrawable sportsHallRegionDrawable = new TextureRegionDrawable(sportsHallRegion);
        ImageButton sportImageButton = new ImageButton(sportsHallRegionDrawable);

        //adding buidings image button to button group
        buildButtons = new ButtonGroup<>();
        buildButtons.add(hallImageButton);
        buildButtons.add(barImageButton);
        buildButtons.add(lectureImageButton);
        buildButtons.add(restaurantImageButton);
        buildButtons.add(roadImageButton);
        buildButtons.add(sportImageButton);

        //making top hud table
        table.add(scoreLabel).pad(15).left();
        table.add(timeLabel).pad(15).expandX();
        table.add(playButton).pad(15).size(50, 50).expandX().align(Align.right);
        table.row();
        table.add(buildingCounterLabel).left().pad(15);
        table.add(progressBarStack).center();
        table.row();


        table.debug();

        VerticalGroup hallVert = new VerticalGroup();
        VerticalGroup barVert = new VerticalGroup();
        VerticalGroup lectureVert = new VerticalGroup();
        VerticalGroup restaurantVert = new VerticalGroup();
        VerticalGroup roadVert = new VerticalGroup();
        VerticalGroup sportVert = new VerticalGroup();

        Label hallLabel = new Label("Hall", skin);
        hallLabel.setFontScale(0.5f);
        Label barLabel = new Label("Bar", skin);
        barLabel.setFontScale(0.5f);
        Label lectureLabel = new Label("Lecture", skin);
        lectureLabel.setFontScale(0.5f);
        Label restaurantLabel = new Label("Food", skin);
        restaurantLabel.setFontScale(0.5f);
        Label roadLabel = new Label("Road", skin);
        roadLabel.setFontScale(0.5f);
        Label sportLabel = new Label("Sports", skin);
        sportLabel.setFontScale(0.5f);

        closeBuildMenu = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Sprites/close.png")))));

        closeAndOpenButtonGroup = new ButtonGroup<>();

        closeAndOpenButtonGroup.add(buildMenu);
        closeAndOpenButtonGroup.add(closeBuildMenu);
        closeAndOpenButtonGroup.setMaxCheckCount(1);
        closeAndOpenButtonGroup.uncheckAll();

        hallImageButton.getImage().setSize(50,50);
        hallImageButton.getImage().setFillParent(true);

        hallVert.addActor(hallImageButton);
        hallVert.addActor(hallLabel);

        barVert.addActor(barImageButton);
        barVert.addActor(barLabel);

        lectureVert.addActor(lectureImageButton);
        lectureVert.addActor(lectureLabel);

        restaurantVert.addActor(restaurantImageButton);
        restaurantVert.addActor(restaurantLabel);

        roadVert.addActor(roadImageButton);
        roadVert.addActor(roadLabel);

        sportVert.addActor(sportImageButton);
        sportVert.addActor(sportLabel);
        //BuildTable Properties;
        buildTable.defaults().padBottom(15).padLeft(20).padRight(20).size(50,50);
        //adding each building
        buildTable.add(closeBuildMenu).size(20,20).right().colspan(6).row();
        buildTable.add(hallVert);
        buildTable.add(barVert);
        buildTable.add(lectureVert);
        buildTable.add(restaurantVert);
        buildTable.add(roadVert);
        buildTable.add(sportVert);


        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.GRAY);
        TextureRegionDrawable tx = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        //buildTable.background(tx);


    }

    /**
     * Hide build mode.
     */
//if buildmode disabled and enabled it hide and displays the buildTable respectively
    public void hideBuildMode () {
        buildTable.setVisible(false);
        buildToggleTable.setVisible(true);
    }

    /**
     * Show build mode.
     */
    public void showBuildMode () {
        buildTable.setVisible(true);
        buildToggleTable.setVisible(false);
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
        year = (int) (time / 80) + 1;
        timeLabel.setText(String.format("Time: %01d:%02d, Y/S: %01d:%s", minute, seconds, year, this.season));
        float season = (long) (time % 80);
        if (season <= 25) {
            updateSummerProgress(season);
            this.season = "Summer";
        }
        else {
            updateTeachingProgress(season);
            this.season = "Academic";
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
    public void updateBuildingCounter(int count) {
        buildingCounterLabel.setText(String.format("Buildings: %02d", count));
    }

}
