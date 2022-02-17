package net.primegames.skyblock.group;

import lombok.Getter;
import lombok.NonNull;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeEqualityPredicate;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.util.Tristate;
import net.primegames.groups.GroupTier;
import net.primegames.skyblock.SkyBlock;
import net.primegames.utils.LoggerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public enum SkyBlockGroup {

    OWNER("owner", GroupTier.OWNER, "&l&c{faction_rank}&3{faction}&f〚&9Owner&f〛&3{player}&e: &b{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&l&9Owner&f]&3{player}"),
    CHIEF("chief", GroupTier.CHIEF, "&l&c{faction_rank}&3{faction}&f〚&aChief&f〛&5{player}&6: &a{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&aChief&f]&5{player}"),
    MANAGER("manager", GroupTier.LOS, "&l&c{faction_rank}&3{faction}&f〚&aLOS&f〛&a{player}&6: &a{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&aLOS&f]&d{player}"),
    ADMIN("admin", GroupTier.ADMIN, "&l&c{faction_rank}&3{faction}&f〚&3Admin&f〛{player}&7: &4{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&3Admin&f]&9{player}"),
    MODERATOR("moderator", GroupTier.MOD, "&l&c{faction_rank}&3{faction}&f〚&3MOD&f〛{player}&7: &4{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&3MOD&a&f]{player}"),
    HELPER("helper", GroupTier.TRAINEE, "&l&c{faction_rank}&3{faction}&f〚&eTrainee&f〛{player}&7: &e{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&eTrainee&f]&e{player}"),
    DEFAULT("default", GroupTier.TIER_0, "&c{faction_rank}&3{faction}&f〚&7Mortal&f〛{player}&7: {message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&7Mortal&f]{player}"),
    VOTER("voter", GroupTier.TIER_1, "&c{faction_rank}&3{faction}&f〚&aVoter&f〛{player}&7: &f{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&aVoter&f]&7{player}"),
    IRON("iron", GroupTier.TIER_2, "&c{faction_rank}&3{faction}&f〚&cAres&f〛{player}&7: &c{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&cAres&f]&c{player}"),
    GOLD("gold", GroupTier.TIER_3, "&c{faction_rank}&3{faction}&f〚&3Iris&f〛{player}&7: &3{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&3Iris&f]&d{player}"),
    DIAMOND("diamond", GroupTier.TIER_4, "&c{faction_rank}&3{faction}&f〚&6Poseidon&f〛{player}&7: &l&6{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&6Poseidon&f]&4{player}"),
    BUILDER("builder", GroupTier.TIER_5, "&l&c{faction_rank}&3{faction}&f〚&6Builder&f〛{player}&7: &6{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&6Builder&f]&6{player}"),
    EMERALD("emerald", GroupTier.TIER_6, "&l&c{faction_rank}&3{faction}&f〚&5Hades&f〛{player}&7: &5{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&5Hades&f]&4{player}"),
    OBSIDIAN("obsidian", GroupTier.TIER_8, "&l&c{faction_rank}&3{faction}&f〚&bZeus&f〛{player}&7: &b{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&bZeus&f]&6{player}"),
    SUPREME("supreme", GroupTier.TIER_9, "&l&c{faction_rank}&3{faction}&f〚&aTitan&f〛{player}&7: &a{message}", "•&c{faction_rank}&3{faction}{faction_ln}&f[&a&lTitan&r&f]&a{player}&r");

    @Getter
    private final String name;
    @Getter
    private final GroupTier tier;
    @Getter
    @NonNull
    private final Group group;

    SkyBlockGroup(String name, GroupTier groupTier, String chatFormat, String displayName) {
        this.tier = groupTier;
        this.name = name;
        Group group = SkyBlock.getInstance().getLuckPerms().getGroupManager().getGroup(name);
        if (group != null) {
            this.group = group;
        } else {
            throw new IllegalArgumentException("Group named: " + name + " does not exist in LuckPerms!");
        }
    }

    public static List<@NonNull SkyBlockGroup> fromTiers(List<GroupTier> tiers) {
        List<@NonNull SkyBlockGroup> groups = new ArrayList<>();
        SkyBlockGroup[] factionsGroups = SkyBlockGroup.values();
        for (SkyBlockGroup factionsGroup : factionsGroups) {
            for (GroupTier tier : tiers) {
                if (factionsGroup.getTier().equals(tier)) {
                    groups.add(factionsGroup);
                }
            }
        }
        return groups;
    }

    public static SkyBlockGroup fromTier(GroupTier groupTier) {
        SkyBlockGroup[] groups = SkyBlockGroup.values();
        for (SkyBlockGroup group : groups) {
            if (group.getTier().equals(groupTier)) {
                return group;
            }
        }
        return null;
    }

    public static SkyBlockGroup fromTierId(int id) {
        SkyBlockGroup[] groups = SkyBlockGroup.values();
        for (SkyBlockGroup group : groups) {
            if (group.getTier().getId() == id) {
                return group;
            }
        }
        return null;
    }

    public static SkyBlockGroup getHighestPriority(List<SkyBlockGroup> groups) {
        SkyBlockGroup highestPriority = SkyBlockGroup.DEFAULT;
        for (SkyBlockGroup factionsGroup : groups) {
            if (highestPriority.getTier().getPriority() < factionsGroup.getTier().getPriority()) {
                highestPriority = factionsGroup;
            }
        }
        return highestPriority;
    }

    public static void addGroupsFromTiers(@NonNull Player player, List<@NonNull GroupTier> tiers) {
        List<SkyBlockGroup> factionsGroups = fromTiers(tiers);
        LoggerUtils.info(ChatColor.YELLOW + "Loading factions groups for " + player.getName());
        LuckPerms luckPerms = SkyBlock.getInstance().getLuckPerms();
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user != null) {
            for (SkyBlockGroup factionsGroup : factionsGroups) {
                InheritanceNode node = InheritanceNode.builder(factionsGroup.getGroup().getName()).value(true).build();
                if (!user.getNodes().contains(node)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getUniqueId() + " parent add " + factionsGroup.getGroup().getName());
                }
            }
        } else {
            LoggerUtils.error("Failed to get LuckPerms User for " + player.getName());
        }
    }


    @NonNull
    public static SkyBlockGroup getGroupFor(Player player){
        try {
            LuckPerms luckPerms = SkyBlock.getInstance().getLuckPerms();
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                for (SkyBlockGroup factionsGroup : values()) {
                    if (!factionsGroup.equals(SkyBlockGroup.DEFAULT) && user.getPrimaryGroup().equals(factionsGroup.getName())) {
                        return factionsGroup;
                    }
                }
            }
            return SkyBlockGroup.DEFAULT;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
