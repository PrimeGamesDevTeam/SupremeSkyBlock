package net.primegames.skyblock.settings;

import lombok.NonNull;
import net.primegames.plugin.PrimePlugin;
import net.primegames.server.settings.ServerSettings;

public abstract class Settings extends ServerSettings {

    public Settings(@NonNull PrimePlugin plugin) {
        super(plugin);
    }

}
