package io.github.NeonStudiosGame.timer;

import io.github.NeonStudiosGame.BuildMaster.BuildMaster;
import io.github.NeonStudiosGame.buildings.BaseBuilding;

/**
 * This is an extension of task, it's specialised to refer back to the build master to complete the building that it's
 * given at a specified time.
 */
public class BuildTask extends Task {
    BaseBuilding building;
    BuildMaster buildMaster;

    /**
     * This is the constructor for the BuildTask. It sets the building to be finalised and the build master that it
     * should report back to. It also makes a super call to task, in order to update the timeToPerform.
     * @param timeToPerform The game-time the run method should be called.
     * @param building The building that will be constructed at the given time.
     * @param buildMaster The build master that the building belongs to and this task should refer back to.
     */
    public BuildTask(float timeToPerform, BaseBuilding building, BuildMaster buildMaster) {
        super(timeToPerform);
        this.building = building;
        this.buildMaster = buildMaster;
    }

    /**
     * To be called by the timer schedule at timeToPerform. It requests the build master to complete the construction of
     * the stored building.
     */
    @Override
    public void run() {
        buildMaster.completeConstruction(building);
    }
}
