package lithium.hikari.utils.api;

import lithium.hikari.HikariTokens;
import lithium.hikari.data.UserData;
import lithium.hikari.manager.TokenManager;
import org.bukkit.OfflinePlayer;

public class TokenAPI {



    private final HikariTokens te = HikariTokens.getPlugin(HikariTokens.class);

    public int getTokensInt(OfflinePlayer player) {
        if (!player.isOnline()) {
            UserData.getTokensInt(player.getUniqueId());
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            return tokens.getTokens();
        }
        return 0;
    }

    public double getTokensDouble(OfflinePlayer player) {
        if (!player.isOnline()) {
            UserData.getTokensDouble(player.getUniqueId());
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            return tokens.getTokens();
        }

        return 0.0;
    }

    public void setTokens(OfflinePlayer player, int amount) {

        if (!player.isOnline()) {
            UserData.setTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            tokens.setTokens(amount);
        }
    }

    public void setTokens(OfflinePlayer player, double amount) {
        if (!player.isOnline()) {
            UserData.setTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            tokens.setTokens((int) amount);
        }
    }

    public void addTokens(OfflinePlayer player, int amount) {

        if (!player.isOnline()) {
            UserData.addTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            tokens.addTokens(amount);
        }
    }

    public void addTokens(OfflinePlayer player, double amount) {

        if (!player.isOnline()) {
            UserData.addTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            tokens.addTokens((int) amount);
        }
    }

    public void removeTokens(OfflinePlayer player, int amount) {

        if (!player.isOnline()) {
            UserData.removeTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            tokens.removeTokens(amount);
        }
    }

    public void removeTokens(OfflinePlayer player, double amount) {

        if (!player.isOnline()) {
            UserData.removeTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            tokens.removeTokens((int) amount);
        }
    }

    public void resetTokens(OfflinePlayer player) {

        if (!player.isOnline()) {
            UserData.setTokens(player.getUniqueId(), HikariTokens.getConfigManager().getDefaultTokens());
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            tokens.setTokens(HikariTokens.getConfigManager().getDefaultTokens());
        }
    }


}
