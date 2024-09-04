package lithium.hikari.utils.api;

import lithium.hikari.*;
import lithium.hikari.data.*;
import lithium.hikari.manager.*;
import me.clip.placeholderapi.expansion.*;
import org.jetbrains.annotations.*;
import org.bukkit.*;

public class PlaceHolderAPI extends PlaceholderExpansion {


    private final HikariTokens plugin;

    public PlaceHolderAPI(HikariTokens plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "noscape";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "te";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if (params.equalsIgnoreCase(player.getName() + "_money")) {
            String text = null;

            if (player.isOnline()) {
                TokenManager man = HikariTokens.getTokenManager(player);
                text = String.valueOf(man.getTokens());
            } else {
                text = String.valueOf(UserData.getTokensInt(player.getUniqueId()));
            }

            return text;
        }

        if (params.equalsIgnoreCase(player.getName() + "_bank")) {
            String text = null;

            if (player.isOnline()) {
                BankManager bank = HikariTokens.getBankManager(player);
                text = String.valueOf(bank.getBank());
            } else {
                text = String.valueOf(UserData.getBankInt(player.getUniqueId()));
            }

            return text;
        }

        if (params.equalsIgnoreCase(player.getName() + "_money_formatted")) {
            String text = null;

            if (player.isOnline()) {
                TokenManager man = HikariTokens.getTokenManager(player);
                text = HikariTokens.getConfigManager().getTokenSymbol() + man.getTokens();
            } else {
                text = String.valueOf(UserData.getTokensInt(player.getUniqueId()));
            }

            return text;
        }

        if (params.equalsIgnoreCase(player.getName() + "_bank_formatted")) {
            String text = null;

            if (player.isOnline()) {
                BankManager bank = HikariTokens.getBankManager(player);
                text = HikariTokens.getConfigManager().getTokenSymbol() + bank.getBank();
            } else {
                text = String.valueOf(UserData.getTokensInt(player.getUniqueId()));
            }

            return text;
        }

        if (params.equalsIgnoreCase("player_bank")){
            String text;

            BankManager bank = HikariTokens.getBankManager(player);
            text = String.valueOf(bank.getBank());

            return text;
        }

        if (params.equalsIgnoreCase("player_money")){
            String text;

            TokenManager man = HikariTokens.getTokenManager(player);
            text = String.valueOf(man.getTokens());

            return text;
        }

        if (params.equalsIgnoreCase("player_money_formatted")){
            String text;

            TokenManager man = HikariTokens.getTokenManager(player);
            text = HikariTokens.getConfigManager().getTokenSymbol() + man.getTokens();

            return text;
        }

        return null;
    }

}
