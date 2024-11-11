package io.github.NeonStudiosGame.buildings;

public class BusStop extends Road {
    /**
     * Extension of the road class, the difference is that the bus stops should be connected together, functionally the
     * same as the road unless used as such elsewhere, the only difference being the default timeToBuild. Calls the
     * constructor for Road via super, and then sets the time to build and texture locations to predetermined ones.
     * @param position should be an array of two integers [x,y]. These represent the cell in which the building is to be
     * placed.
     */
    public BusStop(int[] position) {
        super(position);
        this.timeToBuild = 3;
        this.buildingTextureLocation = buildingsFolder + "bus_stop.png";
    }
}
