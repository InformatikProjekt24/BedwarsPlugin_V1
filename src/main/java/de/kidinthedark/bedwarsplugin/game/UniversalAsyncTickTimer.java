package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;

public class UniversalAsyncTickTimer {

    public static int task;

    @SuppressWarnings("deprecation")
    public static void startTimer() {
        BedwarsPlugin.instance.getLogger().info("[UniversalAsyncTickTimer] Starting timer...");
        task = BedwarsPlugin.instance.getServer().getScheduler().scheduleAsyncRepeatingTask(BedwarsPlugin.instance, () -> BedwarsPlugin.instance.gameManager.asyncTick(), 1, 1);
    }

    public static void stopTimer() {
        BedwarsPlugin.instance.getLogger().info("[UniversalAsyncTickTimer] Stopping timer...");
        BedwarsPlugin.instance.getServer().getScheduler().cancelTask(task);
    }

}
