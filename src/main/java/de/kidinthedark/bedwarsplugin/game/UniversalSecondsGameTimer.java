package de.kidinthedark.bedwarsplugin.game;

import de.kidinthedark.bedwarsplugin.BedwarsPlugin;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.block.Bed;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class UniversalSecondsGameTimer {

    public volatile static ScheduledTask task;

    public static void startTimer() {
        BedwarsPlugin.instance.getServer().getAsyncScheduler().runAtFixedRate(BedwarsPlugin.instance, new Consumer<ScheduledTask>() {
                    @Override
                    public void accept(ScheduledTask scheduledTask) {
                        task = scheduledTask;
                        BedwarsPlugin.instance.gameManager.tick();
                    }
                }, 1, 1, TimeUnit.SECONDS);
    }

    public static void stopTimer() {
        task.cancel();
    }

}
