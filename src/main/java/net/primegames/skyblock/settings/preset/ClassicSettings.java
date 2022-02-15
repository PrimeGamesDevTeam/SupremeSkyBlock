package net.primegames.skyblock.settings.preset;

import lombok.NonNull;
import net.primegames.components.vote.data.VoteSite;
import net.primegames.plugin.PrimePlugin;
import net.primegames.server.GameMode;
import net.primegames.server.GameServerSettings;
import net.primegames.server.GameServerStatus;
import net.primegames.skyblock.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ClassicSettings extends Settings {

    public ClassicSettings(@NonNull PrimePlugin plugin) {
        super(plugin);
    }

    @Override
    public @NonNull GameServerSettings getServerSettings() {
        return new GameServerSettings("SUPREME_SB", GameMode.SKYBLOCK, GameServerStatus.PRODUCTION, "primegames.net", 19285, "https://primegames.net/servericons/server/SkyBlock.png");
    }

    @Override
    public @NonNull String getLobbyWorldName() {
        return "world";
    }

    @Override
    public @NonNull Location getLobbySpawnLocation() {
        return new Location(getPlugin().getServer().getWorld("world"), -3, 98, -41, 33, -5.7f);
    }

    @Override
    public boolean onVoteClaim(Player player, VoteSite voteSite) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crazycrate give physical vote 1 " + player.getName());
        return true;
    }
}
