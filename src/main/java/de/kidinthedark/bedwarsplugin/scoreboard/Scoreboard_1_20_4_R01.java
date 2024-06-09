package de.kidinthedark.bedwarsplugin.scoreboard;

import net.kyori.adventure.text.Component;
import net.minecraft.network.PacketBundlePacker;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.BundlePacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Scoreboard_1_20_4_R01 {

    public void sendScoreboard(Player p) {
        Scoreboard board = new Scoreboard();
        Objective obj = board.addObjective("MainScore", ObjectiveCriteria.DUMMY, Component.text("test"),
                ObjectiveCriteria.RenderType.INTEGER);

        ArrayList<PacketPlayOutScoreboardScore> scores = new ArrayList<>();

        int i = lines.size();
        for (String line : lines) {
            PacketPlayOutScoreboardScore score = new PacketPlayOutScoreboardScore(ScoreboardServer.Action.a,
                    "MainScore", resolvePlaceholder(line, p), i);
            scores.add(score);
            i--;
        }

        Packet<PacketListener> createPacket = new PacketBundlePacker()
        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

        sendPacket(p, removePacket);
        sendPacket(p, createPacket);
        sendPacket(p, display);

        for (PacketPlayOutScoreboardScore score : scores) {
            sendPacket(p, score);
        }

        p.setPlayerListHeaderFooter(resolvePlaceholder(tabHeader, p), resolvePlaceholder(tabFooter, p));

    }

    public void sendPacket(Player p, Packet<?> packet) {
        ((CraftPlayer) p).getHandle().b.sendPacket(packet);
    }

}
