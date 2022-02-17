package net.primegames.skyblock.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class SkyBlockCommandListener implements Listener {

    //check if the player is bedrock and if yes remove db from name
    @EventHandler
    public void onCommand(ServerCommandEvent event) {
        String command = event.getCommand();
        if (command.contains("rs give") || command.contains("rosestacker give")){
            event.setCommand(command.replace("[BD]", ""));
        }
    }
}
