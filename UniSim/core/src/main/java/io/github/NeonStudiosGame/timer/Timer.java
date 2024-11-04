package io.github.NeonStudiosGame.timer;


import io.github.NeonStudiosGame.BuildMaster.BuildMaster;
import io.github.NeonStudiosGame.Hud.Hud;
import io.github.NeonStudiosGame.buildings.BaseBuilding;
import io.github.NeonStudiosGame.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class Timer{
    float runningGameTime = 0;
    float exactRealTime;

    List<Task> scheduledTasks;

    GameScreen gameScreen;
    Hud hud;
    BuildMaster buildMaster;
    public Timer(GameScreen gameScreen , Hud hud, BuildMaster buildMaster) {
        this.gameScreen = gameScreen;
        this.hud = hud;
        this.buildMaster = buildMaster;
        this.scheduledTasks = new ArrayList<>();
    }

    public float getGameTime() {
        return runningGameTime;
    }

    public void updateTime (float deltaTime, float currentTime) {
        updateRunningGameTime(deltaTime);
        exactRealTime = currentTime;
        schedulerCheck();
    }

    private void updateRunningGameTime (float deltaTime) {
        runningGameTime += deltaTime;
        hud.updateTime(runningGameTime);
    }

    private void finishConstruction(BaseBuilding building) {
        buildMaster.completeConstruction(building);
    }

    public void scheduleTask(Task task) {
        scheduledTasks.add(task);
    }

    private void schedulerCheck() {
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : scheduledTasks) {
            if (runningGameTime > task.timeToPerform) {
                try {
                    task.run();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                completedTasks.add(task);
            }
        }
        for (Task task : completedTasks) {
            scheduledTasks.remove(task);
        }
    }

}
