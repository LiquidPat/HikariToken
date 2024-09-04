package lithium.hikari.listeners;

import lithium.hikari.HikariTokens;
import lithium.hikari.manager.TokenManager;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Objects;

public class KillEvents implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent e) {

        if (e.getEntity() instanceof Player && e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();

            assert killer != null;
            TokenManager man = HikariTokens.getTokenManager(killer);

            if (HikariTokens.getConfigManager().isInDisabledWorld(killer)) {
                if (HikariTokens.getConfigManager().getValueEnabled("kill-players")) {
                    if (!(man.getTokens() >= HikariTokens.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        int tokens = HikariTokens.getConfigManager().getValue("kill-players");

                        man.addTokens(tokens);

                        if (HikariTokens.getConfigManager().isEventMessage()) {
                            killer.sendMessage(HikariTokens.getConfigManager().getEventMessage("PLAYER-KILL", "&a+" + tokens).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                        }
                    }
                }
            }
        } else if (e.getEntity() instanceof Mob && e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();

            assert killer != null;

            TokenManager man = HikariTokens.getTokenManager(killer);
            if (!HikariTokens.getConfigManager().isInDisabledWorld(killer)) {
                for (String entity : Objects.requireNonNull(HikariTokens.getConfigManager().getConfig().getConfigurationSection("t.player.events.kill-entities")).getKeys(false)) {
                    if (entity.contains(e.getEntity().getName().toUpperCase())) {

                        if (!(man.getTokens() >= HikariTokens.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                            int tokens = HikariTokens.getConfigManager().getConfig().getInt("t.player.events.kill-entities." + entity.toUpperCase() + ".value");

                            man.addTokens(tokens);

                            if (HikariTokens.getConfigManager().getConfig().getBoolean("t.player.events.enable-messages")) {
                                killer.sendMessage(HikariTokens.getConfigManager().getEventMessage("ENTITY-KILL", "&a+" + tokens).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())
                                        .replaceAll("%entity%", e.getEntity().getName()));
                            }
                        }
                    }
                }
            }
        } else if (e.getEntity() instanceof Player) {
            Player victim = (Player) e.getEntity();

            String str = HikariTokens.getConfigManager().getConfig().getString("t.player.events.player-death.value");
            TokenManager man = HikariTokens.getTokenManager(victim);
            if (HikariTokens.getConfigManager().isInDisabledWorld(victim)) {
                assert str != null;
                if (str.startsWith("-")) {
                    str = str.replaceAll("-", "");

                    int value = Integer.parseInt(str);

                    man.removeTokens(value);

                    if (HikariTokens.getConfigManager().isEventMessage()) {
                        victim.sendMessage(HikariTokens.getConfigManager().getEventMessage("PLAYER-DEATH", "&c-" + value).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                    }
                } else {
                    int value = Integer.parseInt(str);

                    if (!(man.getTokens() >= HikariTokens.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        man.addTokens(value);

                        if (HikariTokens.getConfigManager().isEventMessage()) {
                            victim.sendMessage(HikariTokens.getConfigManager().getEventMessage("PLAYER-DEATH", "&a+" + value).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                        }
                    }
                }
            }
        }
    }

}
