package io.github.NeonStudiosGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.NeonStudiosGame.BuildMaster.BuildMaster;
import io.github.NeonStudiosGame.UniSim;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameScreen implements Screen {
    //The main game instance
    final UniSim uniSim;
    //Stage for future UI
    Stage stage;
    //Camera that projects map onto the window
    OrthographicCamera camera;
    //the map that will later be loaded
    TiledMap tiledMap;
    //Background layer (index 0)
    TiledMapTileLayer grassLayer;
    //Buildings layer (index 1)
    TiledMapTileLayer buildingLayer;
    //Selecting Layer that shows what tile is selected (index 2)
    TiledMapTileLayer selector;
    //Unit scale of 1/16f
    float unitScale;
    //renders the map
    OrthogonalTiledMapRenderer renderer;
    //fitviewport sets the map to be a 16:9 aspect ratio
    FitViewport viewport;
    //buildMode toggle
    boolean buildMode;
    //reference to buildmaster that will handle build placement
    BuildMaster build;
    TiledMapTileLayer.Cell hovered;
    //Remembers the last hovered tile
    public GameScreen(UniSim uniSim){
        this.uniSim = uniSim;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 32, 18);
        viewport = new FitViewport(32, 18, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        //loads the map and layers
        generateMap();
        //sets buildmode false (Later will change)
        buildMode = false;
        build = new BuildMaster();
    }

    public void generateMap(){
        tiledMap = new TmxMapLoader().load("DraftMap1.tmx");
        grassLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        buildingLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1);
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

        //toggles buildmode on and off
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            buildMode = !buildMode;
        }

        if (buildMode) {
            //Updates the hovered cell when build mode True
            this.UpdateHover();
        }
        else {
            //IMPORTANT makes sure that after buildmode is off it removes the last hovered cell
            if (hovered != null) {
                hovered.setTile(tiledMap.getTileSets().getTile(5));
                hovered = null;
            }
        }
        //Quick close to make life easy
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
    private void UpdateHover() {
        //Gets x,y coord of mouse
        int y = Gdx.input.getY();
        int x = Gdx.input.getX();

        //Unprojects (complicated word for converts) x,y coords to the map coords
        Vector2 vector = viewport.unproject(new Vector2(x,y));

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
            if (cell != null) {
                if (cell != hovered & hovered != null) {
                    hovered.setTile(tiledMap.getTileSets().getTile(5));
                }
                // Tile 4 is a pinkSquare texture
                cell.setTile(tiledMap.getTileSets().getTile(4));
                hovered = cell;
            }
        }
    }
    private TiledMapTileLayer.Cell getCell(int x, int y){
       try {
           //Gets the selector cell with x, y coords and returns
           TiledMapTileLayer.Cell cell = selector.getCell(x, y);
           return cell;
       } catch (Exception e) {
           //error if no do this :(
           return null;
       }
    }
    private boolean CheckIfBuilding(int unprojectedX, int unprojectedY) {
        //simple boolean to check if cell is 0 or not
        if (buildingLayer.getCell(unprojectedX, unprojectedY) == null) {
            return false;
        }
        else {
            return true;
        }
    }
}
