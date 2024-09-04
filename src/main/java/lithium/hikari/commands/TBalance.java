package lithium.hikari.commands;

import lithium.hikari.HikariTokens;
import lithium.hikari.manager.ConfigManager;
import lithium.hikari.manager.TokenManager;
import lithium.hikari.utils.ColorUtils;
import lithium.hikari.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TBalance implements CommandExecutor {


    private final HikariTokens te = HikariTokens.getPlugin(HikariTokens.class);
    private final ConfigManager config = HikariTokens.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            // if (player.hasPermission("te.balance") || player.hasPermission("te.player")) {

            TokenManager tokens = HikariTokens.getTokenManager(player);

            if (cmd.getName().equalsIgnoreCase("balance")) {
                // /tbalance - giving the player their balance
                if (args.length == 1) {
                    if (player.hasPermission("te.balance.other")) {
                        Player target = Bukkit.getPlayer(args[0]);

                        if (target != null) {
                            TokenManager tokensTarget = HikariTokens.getTokenManager(player);
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariTokens.getConfigManager().getMessages().getString("m.BALANCE")).replaceAll("%tokens%",
                                    String.valueOf(tokensTarget.getTokens()).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()))));
                        }
                    } else {
                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                    }
                } else {
                    if (player.hasPermission("te.balance") || player.hasPermission("te.player")) {
                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                HikariTokens.getConfigManager().getMessages().getString("m.BALANCE")).replaceAll("%tokens%",
                                String.valueOf(tokens.getTokens()).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()))));
                    } else {
                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                    }
                }
            }
        }
        return false;
    }

}
