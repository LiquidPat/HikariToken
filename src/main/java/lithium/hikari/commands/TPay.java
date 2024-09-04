package lithium.hikari.commands;

import lithium.hikari.HikariTokens;
import lithium.hikari.data.H2UserData;
import lithium.hikari.data.MySQLUserData;
import lithium.hikari.manager.TokenManager;
import lithium.hikari.utils.ColorUtils;
import lithium.hikari.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TPay implements CommandExecutor {


    private final HikariTokens te = HikariTokens.getPlugin(HikariTokens.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("pay")) {
                if(player.hasPermission("te.pay") ||  player.hasPermission("te.player")) {
                    if (args.length == 2) {
                        Player receiver = Bukkit.getPlayer(args[0]);
                        int amount1 = Integer.parseInt(args[1]);
                        double amount2 = Double.parseDouble(args[1]);

                        if (receiver != null) {
                            if (te.isMySQL()) {
                                if (!MySQLUserData.getIgnore(receiver.getUniqueId())) {
                                    TokenManager ptokens = HikariTokens.getTokenManager(player);
                                    TokenManager rtokens = HikariTokens.getTokenManager(receiver);
                                    if (!(rtokens.getTokens() >= HikariTokens.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                                        if (receiver != player) {
                                            if (amount1 >= HikariTokens.getConfigManager().getMinPay() || amount2 >= HikariTokens.getConfigManager().getMinPay()) {
                                                if (amount1 <= HikariTokens.getConfigManager().getMaxPay() || amount2 <= HikariTokens.getConfigManager().getMaxPay()) {
                                                    if (ptokens.getTokens() >= amount1) {
                                                        if (isInt(args[1])) {
                                                            rtokens.addTokens(amount1);
                                                            ptokens.removeTokens(amount1);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount1 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        } else if (isDouble(args[1])) {
                                                            rtokens.addTokens((int) amount2);
                                                            ptokens.removeTokens((int) amount2);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount2 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        }
                                                    } else if (ptokens.getTokens() >= amount2) {
                                                        if (isInt(args[1])) {
                                                            // UserData.addTokens(receiver.getUniqueId(), amount1);
                                                            rtokens.addTokens(amount1);
                                                            ptokens.removeTokens(amount1);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount1 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        } else if (isDouble(args[1])) {
                                                            rtokens.addTokens((int) amount2);
                                                            ptokens.removeTokens((int) amount2);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount2 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        }
                                                    } else {
                                                        player.sendMessage(ChatColor.RED + "Du hast genug Tokens!");
                                                    }
                                                } else {
                                                    int value = HikariTokens.getConfigManager().getMaxPay();
                                                    player.sendMessage(ColorUtils.translateColorCodes("&7Du kannst Maximal &c" + value + " &#00fb9aTokens &7senden."));
                                                }
                                            } else {
                                                int value = HikariTokens.getConfigManager().getMinPay();
                                                player.sendMessage(ColorUtils.translateColorCodes("&7Du musst minimal &c" + value + " &&#00fb9aTokens &7senden."));
                                            }
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Du kannst nicht Tokens an dich selbst senden!");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Dieser Spieler kann keine Tokens mehr aufnehmen.");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "Dieser Spieler akzeptiert keine Token sendung!");
                                }
                            } else if (te.isH2()) {
                                if (!H2UserData.getIgnore(receiver.getUniqueId())) {
                                    TokenManager ptokens = HikariTokens.getTokenManager(player);
                                    TokenManager rtokens = HikariTokens.getTokenManager(receiver);
                                    if (!(rtokens.getTokens() >= HikariTokens.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                                        if (receiver != player) {
                                            if (amount1 >= HikariTokens.getConfigManager().getMinPay() || amount2 >= HikariTokens.getConfigManager().getMinPay()) {
                                                if (amount1 <= HikariTokens.getConfigManager().getMaxPay() || amount2 <= HikariTokens.getConfigManager().getMaxPay()) {
                                                    if (ptokens.getTokens() >= amount1) {
                                                        if (isInt(args[1])) {
                                                            rtokens.addTokens(amount1);
                                                            ptokens.removeTokens(amount1);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount1 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        } else if (isDouble(args[1])) {
                                                            rtokens.addTokens((int) amount2);
                                                            ptokens.removeTokens((int) amount2);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        }
                                                    } else if (ptokens.getTokens() >= amount2) {
                                                        if (isInt(args[1])) {
                                                            // UserData.addTokens(receiver.getUniqueId(), amount1);
                                                            rtokens.addTokens(amount1);
                                                            ptokens.removeTokens(amount1);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount1 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        } else if (isDouble(args[1])) {
                                                            rtokens.addTokens((int) amount2);
                                                            ptokens.removeTokens((int) amount2);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount2 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        }
                                                    } else {
                                                        player.sendMessage(ChatColor.RED + "Du hast Genug Tokens!");
                                                    }
                                                } else {
                                                    int value = HikariTokens.getConfigManager().getMaxPay();
                                                    player.sendMessage(ColorUtils.translateColorCodes("&7Du kannst Maximal &c" + value + " &#00fb9aTokens &7senden."));
                                                }
                                            } else {
                                                int value = HikariTokens.getConfigManager().getMinPay();
                                                player.sendMessage(ColorUtils.translateColorCodes("&7Du musst minimal &c" + value + " &&#00fb9aTokens &7senden."));
                                            }
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Du kannst nicht Tokens an dich selbst senden!");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Dieser Spieler kann keine Tokens mehr aufnehmen.");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "Dieser Spieler akzeptiert keine Token sendung!");
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht Online.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Befehl: /pay <player> <amount>");
                    }
                } else {
                    player.sendMessage(Utils.applyFormat(Objects.requireNonNull(
                            HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                }
            }
        }
        return false;
    }

    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
