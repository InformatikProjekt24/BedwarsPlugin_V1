package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;

public class UniversalSecondsGameTimer {

    public static int task;

    public static void startTimer() {
        task = BedwarsPlugin.instance.getServer().getScheduler().scheduleSyncRepeatingTask(BedwarsPlugin.instance, () -> BedwarsPlugin.instance.gameManager.tick(), 20, 20);
    }

    public static void stopTimer() {
        BedwarsPlugin.instance.getServer().getScheduler().cancelTask(task);
    }

}
