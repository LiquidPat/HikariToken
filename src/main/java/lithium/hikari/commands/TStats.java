package lithium.hikari.commands;

import lithium.hikari.HikariTokens;
import lithium.hikari.data.H2UserData;
import lithium.hikari.data.UserData;
import lithium.hikari.utils.ColorUtils;
import lithium.hikari.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TStats implements CommandExecutor {

    private final HikariTokens te = HikariTokens.getPlugin(HikariTokens.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("tstats")) {
                if (player.hasPermission("te.stats") || player.hasPermission("te.player")) {
                    if (te.isMySQL()) {
                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKEN STATS &8&l>"));
                        player.sendMessage(Utils.applyFormat("&7Server Total: &e" + UserData.getServerTotalTokens()));
                        player.sendMessage(Utils.applyFormat("&7Dein Tokenstand: &e" + UserData.getTokensDouble(player.getUniqueId())));
                    } else if (te.isH2()) {
                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKEN STATS &8&l>"));
                        player.sendMessage(Utils.applyFormat("&7Server Total: &e" + H2UserData.getServerTotalTokens()));
                        player.sendMessage(Utils.applyFormat("&7Dein Tokenstand: &e" + H2UserData.getTokensDouble(player.getUniqueId())));
                    }
                } else {
                    player.sendMessage(Utils.applyFormat(Objects.requireNonNull(
                            HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION"))));
                }
            }
        }
        return false;
    }

}
