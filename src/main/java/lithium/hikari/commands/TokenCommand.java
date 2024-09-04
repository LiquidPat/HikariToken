package lithium.hikari.commands;

import lithium.hikari.HikariTokens;
import lithium.hikari.data.UserData;
import lithium.hikari.manager.BankManager;
import lithium.hikari.manager.ConfigManager;
import lithium.hikari.manager.TokenManager;
import lithium.hikari.utils.ColorUtils;
import lithium.hikari.utils.Utils;
import lithium.hikari.utils.menu.menus.ExchangeMenu;
import lithium.hikari.utils.menu.menus.TokenMenu;
import lithium.hikari.utils.menu.menus.TopMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

import static lithium.hikari.utils.Utils.msgPlayer;

public class TokenCommand implements CommandExecutor {

        private final HikariTokens te = (HikariTokens)HikariTokens.getPlugin(HikariTokens.class);
        private final ConfigManager config = HikariTokens.getConfigManager();

        public TokenCommand(){

        }

        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                return true;
            } else {

                Player player = (Player) sender;

                TokenManager tokens = HikariTokens.getTokenManager(player);

                if (cmd.getName().equalsIgnoreCase("tokens")) {
                    if (args.length == 0) {
                        if (player.hasPermission("te.mainmenu")) {
                            new TokenMenu(HikariTokens.getMenuUtil(player)).open();
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                        }
                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("help")) {
                            msgPlayer(player, "&#00fb9a&l光TOKEN Commands&r",
                                    "",
                                    "&#00fb9a/tokens pay <user> <amount> &7- sende einem Spieler Tokens.",
                                    "&#00fb9a/tokens balance &7- zeigt deinen Tokenstand an.",
                                    "&#00fb9a/tokens bank &7- zeigt deinen Kontostand auf der Bank an.",
                                    "&#00fb9a/tokens exchange &7- Öffnet das austausch menu",
                                    "&#00fb9a/tokens top &7- Öffnet die Top-Liste",
                                    "&#00fb9a/tokens stats &7- Siehe die Server Statistik",
                                    "&#00fb9a/tokens toggle &7- Erlaube/Verbiete das erhalten von Tokens.",
                                    "&#00fb9a/tokens help &7- zeigt diese Nachricht.",
                                    "",
                                    "&#00fb9a&l光TOKENBank Befehle&r",
                                    "",
                                    "&#00fb9a/bank &7- zeigt dein Kontostand auf der Bank an.",
                                    "&#00fb9a/bank withdraw <amount> &7- hebt von deiner Bank etwas ab.",
                                    "&#00fb9a/bank deposit <amount> &7- zahlt was auf deine Bank ein.",
                                    "");
                        } else if (args[0].equalsIgnoreCase("top")) {
                            if (player.hasPermission("te.baltop") || player.hasPermission("te.player")) {
                                new TopMenu(HikariTokens.getMenuUtil(player)).open();
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                            }
                        } else if (args[0].equalsIgnoreCase("toggle")) {
                            if (player.hasPermission("te.toggle")) {
                                if (UserData.getIgnore(player.getUniqueId())) {
                                    UserData.setIgnore(player.getUniqueId(), false);
                                    player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKEN &7Spieler können dir wieder Tokens senden!"));
                                } else {
                                    UserData.setIgnore(player.getUniqueId(), true);
                                    player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKEN &7Spieler können dir jetzt keine Tokens mehr senden!"));
                                }
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                            }
                        } else if (args[0].equalsIgnoreCase("bank")) {
                            if (player.hasPermission("te.bank") && !player.hasPermission("te.player")) {
                                BankManager bank = HikariTokens.getBankManager(player);

                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariTokens.getConfigManager().getMessages().getString("m.BANK-BALANCE")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                            }
                        } else if (args[0].equalsIgnoreCase("balance")) {
                            if (player.hasPermission("te.balance") || player.hasPermission("te.player")) {

                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariTokens.getConfigManager().getMessages().getString("m.BALANCE")).replaceAll("%tokens%",
                                        String.valueOf(tokens.getTokens()).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()))));
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                            }
                        } else if (args[0].equalsIgnoreCase("exchange")) {
                            if (player.hasPermission("te.exchange") || player.hasPermission("te.player")) {
                                new ExchangeMenu(HikariTokens.getMenuUtil(player)).open();
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                            }
                        } else if (args[0].equalsIgnoreCase("stats")) {
                            if (player.hasPermission("te.stats") || player.hasPermission("te.player")) {
                                player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKEN STATS &8&l>"));
                                player.sendMessage(ColorUtils.translateColorCodes("&7Server Total: &e" + UserData.getServerTotalTokens()));
                                player.sendMessage(ColorUtils.translateColorCodes("&7Dein Tokenstand: &e" + UserData.getTokensInt(player.getUniqueId())));
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                            }
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Falscher Befehl! Nutze &c/tokens help"));
                        }
                    } else if (args.length == 3) {
                        if (args[0].equalsIgnoreCase("pay")) {
                            if (player.hasPermission("te.pay") || player.hasPermission("te.player")) {
                                Player receiver = Bukkit.getPlayer(args[1]);
                                int amount1 = Integer.parseInt(args[2]);
                                double amount2 = Double.parseDouble(args[2]);
                                if (receiver != null) {
                                    if (!UserData.getIgnore(receiver.getUniqueId())) {
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
                                                                receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                            } else if (isDouble(args[1])) {
                                                                rtokens.addTokens((int) amount2);
                                                                ptokens.removeTokens((int) amount2);
                                                                // send player & receiver confirmation message
                                                                player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                                receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                            }
                                                        } else {
                                                            player.sendMessage(ChatColor.RED + "Du hast nicht genug Tokens!");
                                                        }
                                                    } else {
                                                        int value = HikariTokens.getConfigManager().getMaxPay();
                                                        player.sendMessage(ColorUtils.translateColorCodes("&7Du kannst Maximal &c" + value + " &#00fb9aTokens &7senden!"));
                                                    }
                                                } else {
                                                    int value = HikariTokens.getConfigManager().getMinPay();
                                                    player.sendMessage(ColorUtils.translateColorCodes("&7Du musst minimal &c" + value + " &#00fb9aTokens &7senden!"));
                                                }
                                            } else {
                                                player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Du kannst nicht Tokens an dich selber senden!"));
                                            }
                                        } else {
                                            player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Dieser Spieler kann keine weiteren Token erhalten!"));
                                        }
                                    } else {
                                        player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Du kannst keine Tokens an diesen Spieler senden!"));
                                    }
                                } else {
                                    player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Dieser Spieler ist nicht Online!"));
                                }
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                            }
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Falscher Befehl! Nutze &c/tokens help"));
                        }
                    } else {
                        player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Falsches Format! Use &c/tokens help"));
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
