package lithium.hikari.listeners;

import lithium.hikari.HikariTokens;
import lithium.hikari.data.UserData;
import lithium.hikari.manager.BankManager;
import lithium.hikari.manager.TokenManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerEvents implements Listener {


    private final HikariTokens te = HikariTokens.getPlugin(HikariTokens.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        UserData.createUserAccount(player);
        HikariTokens.getTokenManager(player);
        HikariTokens.getBankManager(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (HikariTokens.getTokenMap().containsKey(player)) {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            BankManager bank = HikariTokens.getBankManager(player);

            UserData.setTokens(player.getUniqueId(), tokens.getTokens());
            UserData.setBank(player.getUniqueId(), bank.getBank());

            HikariTokens.getTokenMap().remove(e.getPlayer());
            HikariTokens.getBankMap().remove(e.getPlayer());
        }
    }
    @EventHandler
    public void onAdvance(PlayerAdvancementDoneEvent e) {
        Player player = e.getPlayer();

        TokenManager man = HikariTokens.getTokenManager(player);

        if (HikariTokens.getConfigManager().isInDisabledWorld(player)) {
            if (HikariTokens.getConfigManager().getValueEnabled("advancement-complete")) {
                if (!(man.getTokens() >= HikariTokens.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                    int tokens = HikariTokens.getConfigManager().getValue("advancement-complete");

                    man.addTokens(tokens);

                    if (HikariTokens.getConfigManager().isEventMessage()) {
                        player.sendMessage(HikariTokens.getConfigManager().getEventMessage("ADVANCEMENT", "&a+" + tokens).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onNetherEnter(PlayerTeleportEvent e) {
        Player player = e.getPlayer();

        TokenManager man = HikariTokens.getTokenManager(player);

        if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            String str = HikariTokens.getConfigManager().getConfig().getString("t.player.events.nether-portal.value");

            assert str != null;
            if (str.startsWith("-")) {
                str = str.replaceAll("-", "");

                int value = Integer.parseInt(str);

                man.removeTokens(value);

                if (HikariTokens.getConfigManager().isEventMessage()) {
                    player.sendMessage(HikariTokens.getConfigManager().getEventMessage("PORTAL-NETHER", "&c-" + value).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                }
            } else {
                int value = Integer.parseInt(str);
                if (!(man.getTokens() >= HikariTokens.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                    man.addTokens(value);

                    if (HikariTokens.getConfigManager().isEventMessage()) {
                        player.sendMessage(HikariTokens.getConfigManager().getEventMessage("PORTAL-NETHER", "&a+" + value).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                    }
                }
            }
        } else {

            if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {

                String str = HikariTokens.getConfigManager().getConfig().getString("t.player.events.end-portal.value");

                assert str != null;
                if (str.startsWith("-")) {
                    str = str.replaceAll("-", "");

                    int value = Integer.parseInt(str);

                    man.removeTokens(value);

                    if (HikariTokens.getConfigManager().isEventMessage()) {
                        player.sendMessage(HikariTokens.getConfigManager().getEventMessage("PORTAL-END", "&c-" + value).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                    }
                } else {
                    int value = Integer.parseInt(str);
                    if (!(man.getTokens() >= HikariTokens.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        man.addTokens(value);

                        if (HikariTokens.getConfigManager().isEventMessage()) {
                            player.sendMessage(HikariTokens.getConfigManager().getEventMessage("PORTAL-END", "&a+" + value).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                        }
                    }
                }
            }
        }
    }
}
