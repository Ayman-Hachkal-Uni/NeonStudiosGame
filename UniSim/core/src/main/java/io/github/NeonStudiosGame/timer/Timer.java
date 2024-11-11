package io.github.NeonStudiosGame.timer;


import io.github.NeonStudiosGame.BuildMaster.BuildMaster;
import io.github.NeonStudiosGame.Hud.Hud;
import io.github.NeonStudiosGame.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is built to handle the backend clock, it gets updated by the main rendering loop and from here updates the
 * Hud. It is also used to schedule tasks to be run at specified times.
 */
public class Timer{
    public static int TOTAL_GAME_TIME = 300;
    float runningGameTime = 0;
    float exactRealTime;

    List<Task> scheduledTasks;


    GameScreen gameScreen;
    Hud hud;
    BuildMaster buildMaster;

    /**
     * The constructor just sets up the game screen, hud and build master that the timer will be working with and then,
     * sets up the empty schedule.
     * @param gameScreen The GameScreen object to be interfaced with.
     * @param hud The Hud object to be interfaced with.
     * @param buildMaster The BuildMaster object to be interfaced with.
     */
    public Timer(GameScreen gameScreen , Hud hud, BuildMaster buildMaster) {
        this.gameScreen = gameScreen;
        this.hud = hud;
        this.buildMaster = buildMaster;
        this.scheduledTasks = new ArrayList<>();

    }

    public float getGameTime() {
        return runningGameTime;
    }

    /**
     * Gets given the time difference as well as the current running clock time. I then updates game time with this and
     * sets the actual program time every frame as well. It also does a check on the schedule to see if any tasks are
     * due.
     * @param deltaTime The time since the last update.
     * @param currentTime The current program time.
     */
    public void updateTime (float deltaTime, float currentTime) {
        updateRunningGameTime(deltaTime);
        exactRealTime = currentTime;
        schedulerCheck();
    }

    /**
     * Updates the game time and sends a request to the hud to update the on-screen timer.
     * @param deltaTime The time since the last update.
     */
    private void updateRunningGameTime (float deltaTime) {
        runningGameTime += deltaTime;
        hud.updateTime(runningGameTime);
    }

    /**
     * This is the method used to add a task to the schedule.
     * @param task This is the task object to be added to the tasks.
     */
    public void scheduleTask(Task task) {
        scheduledTasks.add(task);
    }


    /**
     * Checks if there are any outstanding tasks that need to be run and runs them if their allocated time to run has
     * passed.
     */
    private void schedulerCheck() {
        List<Task> completedTasks = new ArrayList<>();
        List<Task> midRunningTasks = new ArrayList<>(List.copyOf(scheduledTasks));
        for (Task task : midRunningTasks) {
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
        midRunningTasks.clear();
    }


    public void setTimer(float time) {
        runningGameTime = time;
    }

}
