package net.primegames.skyblock;

import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.primegames.commands.BedrockPlayerCommandHandler;
import net.primegames.components.ComponentManager;
import net.primegames.components.store.StoreComponent;
import net.primegames.components.vote.VoteComponent;
import net.primegames.plugin.PrimePlugin;
import net.primegames.skyblock.listener.SkyBlockCommandListener;
import net.primegames.skyblock.listener.SkyBlockGroupListener;
import net.primegames.skyblock.settings.Settings;
import net.primegames.skyblock.settings.preset.ClassicSettings;
import org.bukkit.plugin.PluginManager;

import java.io.IOException;
import java.sql.SQLException;

public final class SkyBlock extends PrimePlugin {

    @Getter
    private static SkyBlock instance;
    @Getter
    private LuckPerms luckPerms;
    @Getter
    private Settings skyblockSettings;

    @Override
    protected void onInternalLoad() {
        instance = this;
        this.setServerSettings(skyblockSettings = new ClassicSettings(this));
    }

    @Override
    protected void onInternalEnable() {
        luckPerms = LuckPermsProvider.get();
    }

    @Override
    protected void onInternalDisable() {
    }

    @Override
    protected void registerListeners(PluginManager pluginManager) {
        pluginManager.registerEvents(new SkyBlockGroupListener(), this);
        pluginManager.registerEvents(new SkyBlockCommandListener(), this);
    }

    @Override
    protected void registerComponents(ComponentManager componentManager) {
        try {
            componentManager.register(new VoteComponent(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            componentManager.register(new StoreComponent(this));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
