package lithium.hikari.commands;

import lithium.hikari.HikariTokens;
import lithium.hikari.manager.ConfigManager;
import lithium.hikari.utils.ColorUtils;
import lithium.hikari.utils.Utils;
import lithium.hikari.utils.menu.menus.TopMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TBalTop implements CommandExecutor {


    private final HikariTokens te = HikariTokens.getPlugin(HikariTokens.class);
    private final ConfigManager config = HikariTokens.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("baltop")) {
                if (player.hasPermission("te.baltop") || player.hasPermission("te.player")) {
                    new TopMenu(HikariTokens.getMenuUtil(player)).open();
                } else {
                    player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                            HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION"))));
                }
            }
        }
        return false;
    }

}
