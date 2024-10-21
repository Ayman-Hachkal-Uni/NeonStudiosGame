package io.github.NeonStudiosGame.buildings;

import java.io.Console;

public class BaseBuilding {
    static String buildingsFolder = "buildings/";
    protected int[] position;
    protected int timeToBuild = 0;
    protected String buildingTextureLocation = buildingsFolder + "PLACEHOLDER.png";
    public static void main(String[] args) {
    }

    public BaseBuilding(int[] position) {
        this.position = position;
    }

    public int[] getPosition() {
        int[] cloneArray = new int[2];
        System.arraycopy(position, 0, cloneArray, 0, 2);
        return cloneArray;
    }

    public int getTimeToBuild() {
        return timeToBuild;
    }

    public String getBuildingTextureLocation() {
        return buildingTextureLocation;
    }
}
