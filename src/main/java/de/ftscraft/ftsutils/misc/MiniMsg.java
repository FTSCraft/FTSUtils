package de.ftscraft.ftsutils.misc;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class MiniMsg {

    public static void msg(Player p, String miniMessage) {
        p.sendMessage(MiniMessage.miniMessage().deserialize(miniMessage));
    }

}
