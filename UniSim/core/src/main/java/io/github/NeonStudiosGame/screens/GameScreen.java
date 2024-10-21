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
    final UniSim uniSim;
    Stage stage;
    OrthographicCamera camera;
    TiledMap tiledMap;
    TiledMapTileLayer grassLayer;
    TiledMapTileLayer buildingLayer;
    TiledMapTileLayer selector;
    float unitScale;
    OrthogonalTiledMapRenderer renderer;
    FitViewport viewport;
    boolean buildMode;
    BuildMaster build;
    TiledMapTileLayer.Cell hovered;
    public GameScreen(UniSim uniSim){
        this.uniSim = uniSim;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 32, 18);
        viewport = new FitViewport(32, 18, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        generateMap();
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
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            buildMode = !buildMode;
            System.out.println(buildMode);
        }
        if (buildMode) {
            TiledMapTileLayer.Cell cell = this.getCell();
            if (cell != null) {
                if (cell != hovered & hovered != null) {
                    hovered.setTile(tiledMap.getTileSets().getTile(0));
                }
                cell.setTile(tiledMap.getTileSets().getTile(3));
                hovered = cell;
            }
        }
        else {
            if (hovered != null) {
                hovered.setTile(tiledMap.getTileSets().getTile(0));
                hovered = null;
            }
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

    private TiledMapTileLayer.Cell getCell(){
       int y = Gdx.input.getY();
       int x = Gdx.input.getX();
       Vector2 vector = viewport.unproject(new Vector2(x,y));
       try {
           TiledMapTileLayer.Cell cell = grassLayer.getCell((int) vector.x, (int) vector.y);
           return cell;
       } catch (Exception e) {
           return null;
       }
    }
}
