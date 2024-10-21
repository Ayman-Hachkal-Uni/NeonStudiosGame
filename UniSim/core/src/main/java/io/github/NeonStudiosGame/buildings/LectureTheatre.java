package io.github.NeonStudiosGame.buildings;

public class LectureTheatre extends BaseBuilding{
    public LectureTheatre(int[] position) {
        super(position);
        this.timeToBuild = 30;
        this.buildingTextureLocation = buildingsFolder + "lecture_theatre.png";
    }



}
