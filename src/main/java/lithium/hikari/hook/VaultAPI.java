package lithium.hikari.hook;

import lithium.hikari.HikariTokens;
import lithium.hikari.data.H2UserData;
import lithium.hikari.data.MySQLUserData;
import lithium.hikari.manager.TokenManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Objects;

public class VaultAPI implements Economy {


    @Override
    public boolean isEnabled() {
        return HikariTokens.getInstance().getConfig().getBoolean("t.support.tokeneco-vault-dependency");
    }

    @Override
    public String getName() {
        return HikariTokens.getInstance().getDescription().getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String playerName) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (HikariTokens.getInstance().isMySQL()) {
            return HikariTokens.getUser().exists(player.getUniqueId());
        } else if (HikariTokens.getInstance().isH2()) {
            return HikariTokens.getH2user().exists(player.getUniqueId());
        }

        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        if (HikariTokens.getInstance().isMySQL()) {
            return HikariTokens.getUser().exists(player.getUniqueId());
        } else if (HikariTokens.getInstance().isH2()) {
            return HikariTokens.getH2user().exists(player.getUniqueId());
        }

        return false;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return false;
    }

    @Override
    public double getBalance(String playerName) {
        double amount = 0;

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (!player.isOnline()) {
            if (HikariTokens.getInstance().isMySQL()) {
                amount = MySQLUserData.getTokensInt(player.getUniqueId());
            } else if (HikariTokens.getInstance().isMySQL()) {
                amount = H2UserData.getTokensInt(player.getUniqueId());
            }
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            amount = tokens.getTokens();
        }

        return amount;
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        double amount = 0;

        if (!player.isOnline()) {
            if (HikariTokens.getInstance().isMySQL()) {
                amount = MySQLUserData.getTokensInt(player.getUniqueId());
            } else if (HikariTokens.getInstance().isMySQL()) {
                amount = H2UserData.getTokensInt(player.getUniqueId());
            }
        } else {
            TokenManager tokens = HikariTokens.getTokenManager(player);
            amount = tokens.getTokens();
        }

        return amount;
    }

    @Override
    public double getBalance(String playerName, String world) {
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return 0;
    }

    @Override
    public boolean has(String playerName, double amount) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        TokenManager tokens = HikariTokens.getTokenManager(player);
        return tokens.getTokens() >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        TokenManager tokens = HikariTokens.getTokenManager(player);
        return tokens.getTokens() >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        int i = (int) amount;

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        TokenManager tokens = HikariTokens.getTokenManager(player);


        if (tokens.getTokens() < amount) {
            return new EconomyResponse(0.0D, tokens.getTokens(), EconomyResponse.ResponseType.FAILURE, "Insufficient balance!");
        }

        tokens.removeTokens(i);

        return new EconomyResponse(amount, tokens.getTokens(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {

        int i = (int) amount;

        TokenManager tokens = HikariTokens.getTokenManager(player);


        if (tokens.getTokens() < amount) {
            return new EconomyResponse(0.0D, tokens.getTokens(), EconomyResponse.ResponseType.FAILURE, "Insufficient balance!");
        }

        tokens.removeTokens(i);

        return new EconomyResponse(amount, tokens.getTokens(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        int i = (int) amount;

        TokenManager tokens = HikariTokens.getTokenManager(player);
        tokens.addTokens(i);

        return new EconomyResponse(amount, tokens.getTokens(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        if (HikariTokens.getInstance().isMySQL()) {
            HikariTokens.getUser().createPlayer(Objects.requireNonNull(player.getPlayer()));
            HikariTokens.getTokenManager(player);
            HikariTokens.getBankManager(player);
        } else if (HikariTokens.getInstance().isH2()) {
            HikariTokens.getH2user().createPlayer(Objects.requireNonNull(player.getPlayer()));
            HikariTokens.getTokenManager(player);
            HikariTokens.getBankManager(player);
        }

        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return false;
    }
}
