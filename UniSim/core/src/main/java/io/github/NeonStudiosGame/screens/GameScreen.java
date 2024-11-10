package io.github.NeonStudiosGame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.NeonStudiosGame.BuildMaster.BuildMaster;
import io.github.NeonStudiosGame.Hud.Hud;
import io.github.NeonStudiosGame.InputHandler.MyGameInputProcessor;
import io.github.NeonStudiosGame.InputHandler.MyUiInputProcessor;
import io.github.NeonStudiosGame.Scorer;
import io.github.NeonStudiosGame.UniSim;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.NeonStudiosGame.buildings.*;
import io.github.NeonStudiosGame.timer.Timer;

import java.util.Arrays;


/**
 * The type Game screen.
 */
public class GameScreen implements Screen {
    /**
     * The Uni sim.
     */
//The main game instance
    final UniSim uniSim;
    /**
     * The Stage.
     */
//Stage for future UI
    Stage stage;
    /**
     * The Camera.
     */
//Camera that projects map onto the window
    OrthographicCamera camera;
    /**
     * The Tiled map.
     */
//the map that will later be loaded
    TiledMap tiledMap;
    /**
     * The Grass layer.
     */
//Background layer (index 0)
    TiledMapTileLayer grassLayer;
    /**
     * The Building layer.
     */
//Buildings layer (index 1)
    TiledMapTileLayer buildingLayer;
    /**
     * The Selector.
     */
//Selecting Layer that shows what tile is selected (index 2)
    TiledMapTileLayer selector;
    /**
     * The Unit scale.
     */
//Unit scale of 1/16f
    float unitScale;
    /**
     * The Renderer.
     */
//renders the map
    OrthogonalTiledMapRenderer renderer;
    /**
     * The Viewport.
     */
//fitviewport sets the map to be a 16:9 aspect ratio
    FitViewport viewport;
    /**
     * The Build mode.
     */
//fitviewport for UI
    //buildMode toggle
    boolean buildMode;
    /**
     * The Build.
     */
//reference to buildmaster that will handle build placement
    BuildMaster build;
    /**
     * The io.github.NeonStudiosGame.Timer.Timer.
     */
    Timer timer;
    /**
     * The Hovered.
     */
    TiledMapTileLayer.Cell hovered;
    /**
     * The Hud.
     */
//Remembers the last hovered tile
    Hud hud;
    /**
     * The Game time.
     */
    float gameTime;
    /**
     * The Current hovered cell.
     */
    /**
     * The Current mouse pos x.
     */
    int currentMousePosX;
    /**
     * The Current mouse pos y.
     */
    int currentMousePosY;
    /**
     * Instantiates a new Game screen.
     *
     * @param uniSim the uni sim
     */
    Scorer scorer;
    boolean endGame;
    InputMultiplexer multiplexer;
    public GameScreen(UniSim uniSim){
        this.uniSim = uniSim;
        gameTime = 0;
        endGame = false;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 360);
        viewport = new FitViewport(32, 18, camera);


        stage = new Stage(viewport);
        hud = new Hud(uniSim.batch, this.uniSim);

        multiplexer = new InputMultiplexer(new MyUiInputProcessor(hud.stage), new  MyGameInputProcessor(this));
        Gdx.input.setInputProcessor(multiplexer);

        scorer = new Scorer(hud);
        timer = new Timer(this, hud, build);
        //loads the map and layers
        generateMap();
        //sets buildmode false (Later will change)
        buildMode = false;
    }

    /**
     * Generate map.
     */
    public void generateMap(){
        tiledMap = new TmxMapLoader().load("DraftMap1.tmx");
        grassLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        buildingLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1);
        build = new BuildMaster(this);
        build.setMapArray(buildingLayer);
        build.setTimer(timer);
        build.setScorer(scorer);
        selector = (TiledMapTileLayer)tiledMap.getLayers().get(2);
        unitScale = 1/16f;
        renderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        //clears all the UI gunk from the settings/main menu
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //renders the map
        renderer.setView(camera);
        renderer.render();

        hud.stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (!hud.isPaused()) {
            timer.updateTime(Gdx.graphics.getDeltaTime(), gameTime);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            timer.setTimer(290);
        }

        if (timer.getGameTime() >= 300) {
            uniSim.dispose();
            uniSim.changeScreen(UniSim.END, scorer);
        }

        hud.updateBuildingCounter(build.getCounter());

        //toggles buildmode on and off
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            if (hud.buildMenu.isChecked()) {
                hud.buildMenu.setChecked(false);
                hud.closeBuildMenu.setChecked(true);
            }
            else {
                hud.buildMenu.setChecked(true);
                hud.closeBuildMenu.setChecked(false);
            }
        }

        if (hud.buildMenu.isChecked()) {
            hud.showBuildMode();
        }
        else {
            hud.hideBuildMode();
        }

        buildMode = hud.buildMenu.isChecked();

        if (hud.buildMenu.isChecked() & !hud.isPaused()) {
            //Updates the hovered cell when build mode True
            this.UpdateHover();
            //If mouse button is pressed when in build mode place building
        }
        else {
            //IMPORTANT makes sure that after buildmode is off it removes the last hovered cell
            if (hovered != null) {
                hovered.setTile(tiledMap.getTileSets().getTile(5));
                hovered = null;
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        hud.viewport.update(width, height);
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

    /**
     * Updated the hovered cell to show whether a building can be placed or not
     */
    private void UpdateHover() {
        //Gets x,y coord of mouse
        int y = Gdx.input.getY();
        int x = Gdx.input.getX();

        //Unprojects (complicated word for converts) x,y coords to the map coords
        Vector2 vector = viewport.unproject(new Vector2(x,y));

        currentMousePosX = (int) vector.x;
        currentMousePosY = (int) vector.y;

        //Gets the selector layer cell
        TiledMapTileLayer.Cell cell = this.getCell((int) vector.x, (int) vector.y);

        //Checks if the hovered cell is also a building
        boolean building = CheckIfBuilding((int) vector.x, (int) vector.y);

        //IF buildingcell then make cell X
        //ELSE notbuildingcell then make cell 
        if (building) {
            if (cell != null) {
                if (cell != hovered & hovered != null) {
                    //Tile 5 is a transparent texture
                    //Tile 6 is a X texture
                    hovered.setTile(tiledMap.getTileSets().getTile(5));
                }
                cell.setTile(tiledMap.getTileSets().getTile(6));
                hovered = cell;
            }
        }
        else {
            if (cell!= null) {
                if (cell != hovered & hovered != null) {
                    hovered.setTile(tiledMap.getTileSets().getTile(5));
                }
                // Tile 4 is a pinkSquare texture
                cell.setTile(tiledMap.getTileSets().getTile(hud.buildButtons.getCheckedIndex() + 7));
                hovered = cell;
            }
        }
    }
    private TiledMapTileLayer.Cell getCell(int x, int y){
       try {
           //Gets the selector cell with x, y coords and returns
           return selector.getCell(x, y);
       } catch (Exception e) {
           //error if no do this :(
           return null;
       }
    }

    /**
     * Checks if the building layer has a cell already there
     * @param unprojectedX the mouse X position unprojected
     * @param unprojectedY the mouse Y position unprojected
     * @return returns true if there is a cell, false otherwise
     */
    private boolean CheckIfBuilding(int unprojectedX, int unprojectedY) {
        //simple boolean to check if cell is 0 or not
        return buildingLayer.getCell(unprojectedX, unprojectedY) != null;
    }

    /**
     * This is where the buildings will be placed, logic is not done yet however visually buildings can be placed
     * @param buildingIndex the id of the building selected in the HUD
     */
    private void placeBuilding (int buildingIndex) {
        if (build.createBuilding(new int[]{(int) (currentMousePosX),
            (int) (currentMousePosY)}, BuildingEnum.values()[buildingIndex + 1])) {
            renderBuilding(buildingIndex, new int[]{currentMousePosX, currentMousePosY});
        }

    }

    public void renderBuilding (int buildingIndex, int[] position) {
        buildingLayer.setCell(position[0], position[1], new TiledMapTileLayer.Cell());
        buildingLayer.getCell(position[0], position[1]).setTile(tiledMap.getTileSets().getTile(21));

    }
    public void renderFullyCompletedBuilding (BaseBuilding building) {
        int x = building.getPosition()[0];
        int y = building.getPosition()[1];
        int buildingIndex = switch (building.getClass().getSimpleName()) {
            case "Halls" -> 7;
            case "Bar" -> 8;
            case "LectureTheatre" -> 9;
            case "Restaurant" -> 10;
            case "Road" -> 11;
            case "SportsHall" -> 12;
            case "BusStop" -> 13;
            default -> 5;
        };

        buildingLayer.getCell(x, y).setTile(tiledMap.getTileSets().getTile(buildingIndex));
    }

    public void place() {
        if (buildMode) {
            placeBuilding(hud.buildButtons.getCheckedIndex());
        }
    }

}
