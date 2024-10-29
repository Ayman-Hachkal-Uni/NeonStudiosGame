package io.github.NeonStudiosGame.buildings;

import java.io.Console;

/**
 * This is a class that should be the base building to be used in the NeonStudios Game. It stores data about the
 * building and interfaces with the Build master and map to allow users to place it.
 */
public class BaseBuilding {
    static String buildingsFolder = "buildings/";
    protected int[] position;
    protected int timeToBuild = 0;
    protected String buildingTextureLocation = buildingsFolder + "PLACEHOLDER.png";
    public static void main(String[] args) {
    }

    /**
     * This is the constructor for the BaseBuilding, just sets the position of the building to the specified one.
     * @param position should be an array of two integers [x,y]. These represent the cell in which the building is to be
     * placed.
     */
    public BaseBuilding(int[] position) {
        this.position = position;
    }

    /**
     * Gets a clone of the position array to return.
     * @return a shallow copy of the position array.
     */
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
