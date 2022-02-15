package net.primegames.skyblock.listener;

import net.primegames.event.player.CorePlayerLoadedEvent;
import net.primegames.skyblock.group.SkyBlockGroup;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkyBlockGroupListener implements Listener {

    @EventHandler
    public void onGroupLoad(CorePlayerLoadedEvent event) {
        SkyBlockGroup.addGroupsFromTiers(event.getPlayer(), event.getPlayerData().getGroupTiers());
    }
}
