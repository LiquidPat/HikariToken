package lithium.hikari.commands;

import lithium.hikari.HikariTokens;
import lithium.hikari.manager.ConfigManager;
import lithium.hikari.utils.Utils;
import lithium.hikari.utils.menu.menus.TokenShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TShop implements CommandExecutor {

    private final HikariTokens te = HikariTokens.getPlugin(HikariTokens.class);
    private final ConfigManager config = HikariTokens.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("tshop")) {
                if (player.hasPermission("te.shop") || player.hasPermission("te.player")) {
                    // /tbalance - giving the player their balance
                    new TokenShop(HikariTokens.getMenuUtil(player)).open();
                } else {
                    player.sendMessage(Utils.applyFormat(Objects.requireNonNull(
                            HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                }
            } else if (cmd.getName().equalsIgnoreCase("shop")) {
                if (player.hasPermission("te.shop") || player.hasPermission("te.player")) {
                    // /tbalance - giving the player their balance
                    new TokenShop(HikariTokens.getMenuUtil(player)).open();
                } else {
                    player.sendMessage(Utils.applyFormat(Objects.requireNonNull(
                            HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                }
            }
        }
        return false;
    }

}
