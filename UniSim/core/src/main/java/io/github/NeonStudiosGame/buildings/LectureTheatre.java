package io.github.NeonStudiosGame.buildings;

public class LectureTheatre extends BaseBuilding{
    /**
     * This is the constructor for the LectureTheatre, just calls the constructor for BaseBuilding via super, and then sets the
     * time to build and texture locations to predetermined ones.
     * @param position should be an array of two integers [x,y]. These represent the cell in which the building is to be
     * placed.
     */
    public LectureTheatre(int[] position) {
        super(position);
        this.timeToBuild = 30;
        this.buildingTextureLocation = buildingsFolder + "lecture_theatre.png";
    }



}
