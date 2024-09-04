package lithium.hikari.commands;

import lithium.hikari.HikariTokens;
import lithium.hikari.manager.ConfigManager;
import lithium.hikari.manager.BankManager;
import lithium.hikari.manager.TokenManager;
import lithium.hikari.utils.ColorUtils;
import lithium.hikari.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Objects;

public class TBank implements CommandExecutor {
    public TBank() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {
            Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("bank")) {
                if (!player.hasPermission("te.bank") && !player.hasPermission("te.player")) {
                    player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.PERMISSION"))).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                } else {
                    BankManager bank;
                    if (args.length == 0) {
                        bank = HikariTokens.getBankManager(player);
                        player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.BANK-BALANCE"))).replaceAll("%tokens%", String.valueOf(bank.getBank())).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                    } else {
                        String bank_help;
                        Iterator var9;
                        if (args.length == 1) {
                            if (args[0].equalsIgnoreCase("help")) {
                                var9 = HikariTokens.getConfigManager().getMessages().getStringList("m.BANK-HELP").iterator();

                                while(var9.hasNext()) {
                                    bank_help = (String)var9.next();
                                    player.sendMessage(Utils.applyFormat(bank_help).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                                }
                            } else {
                                var9 = HikariTokens.getConfigManager().getMessages().getStringList("m.BANK-HELP").iterator();

                                while(var9.hasNext()) {
                                    bank_help = (String)var9.next();
                                    player.sendMessage(Utils.applyFormat(bank_help).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                                }
                            }
                        } else if (args.length == 2) {
                            bank = HikariTokens.getBankManager(player);
                            TokenManager tokens = HikariTokens.getTokenManager(player);
                            int amount;
                            if (args[0].equalsIgnoreCase("withdraw")) {
                                amount = Integer.parseInt(args[1]);
                                if (amount <= HikariTokens.getConfigManager().getMaxWithdraw()) {
                                    if (amount >= HikariTokens.getConfigManager().getMinWithdraw()) {
                                        if (bank.getBank() >= amount) {
                                            bank.removeBank(amount);
                                            tokens.addTokens(amount);
                                            player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.BANK-WITHDRAW"))).replaceAll("%amount%", String.valueOf(amount)).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                                        } else {
                                            player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.NOT-ENOUGH-TO-WITHDRAW"))).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                                        }
                                    } else {
                                        player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.MIN-WITHDRAW"))).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                                    }
                                } else {
                                    player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.MAX-WITHDRAW"))).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                                }
                            }

                            if (args[0].equalsIgnoreCase("deposit")) {
                                amount = Integer.parseInt(args[1]);
                                if (amount <= HikariTokens.getConfigManager().getMaxDeposit()) {
                                    if (amount >= HikariTokens.getConfigManager().getMinDeposit()) {
                                        if (tokens.getTokens() >= amount) {
                                            tokens.removeTokens(amount);
                                            bank.addBank(amount);
                                            player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.BANK-DEPOSIT"))).replaceAll("%amount%", String.valueOf(amount)).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                                        } else {
                                            player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.NOT-ENOUGH-TO-DEPOSIT"))).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                                        }
                                    } else {
                                        player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.MIN-DEPOSIT"))).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                                    }
                                } else {
                                    player.sendMessage(Utils.applyFormat(((String)Objects.requireNonNull(HikariTokens.getConfigManager().getMessages().getString("m.MAX-DEPOSIT"))).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix())));
                                }
                            }
                        } else {
                            var9 = HikariTokens.getConfigManager().getMessages().getStringList("m.BANK-HELP").iterator();

                            while(var9.hasNext()) {
                                bank_help = (String)var9.next();
                                player.sendMessage(Utils.applyFormat(bank_help).replaceAll("%PREFIX%", HikariTokens.getConfigManager().getPrefix()));
                            }
                        }
                    }
                }
            }

            return false;
        }
    }
}