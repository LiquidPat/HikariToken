package lithium.hikari.data;

import lithium.hikari.HikariTokens;
import lithium.hikari.manager.TokenManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.TreeMap;
import java.util.UUID;

public class UserData {


    private static final HikariTokens te = HikariTokens.getPlugin(HikariTokens.class);

    public static void setIgnore(UUID uuid, boolean ignore) {
        if (te.isMySQL())
            MySQLUserData.setIgnore(uuid, ignore);
        else if (te.isH2())
            H2UserData.setIgnore(uuid, ignore);
    }

    public static void setBank(UUID uuid, int amount) {
        if (te.isMySQL())
            MySQLUserData.setBank(uuid, amount);
        else if (te.isH2())
            H2UserData.setBank(uuid, amount);
    }

    public static void setBank(UUID uuid, double amount) {
        if (te.isMySQL())
            MySQLUserData.setBank(uuid, amount);
        else if (te.isH2())
            H2UserData.setBank(uuid, amount);
    }

    public static void addBank(UUID uuid, int amount) {
        if (te.isMySQL())
            MySQLUserData.addBank(uuid, amount);
        else if (te.isH2())
            H2UserData.addBank(uuid, amount);
    }

    public static void removeBank(UUID uuid, int amount) {
        if (te.isMySQL())
            MySQLUserData.removeBank(uuid, amount);
        else if (te.isH2())
            H2UserData.removeBank(uuid, amount);
    }

    public static void setTokens(UUID uuid, int amount) {
        if (te.isMySQL())
            MySQLUserData.setTokens(uuid, amount);
        else if (te.isH2())
            H2UserData.setTokens(uuid, amount);
    }

    public static void setTokens(UUID uuid, double amount) {
        if (te.isMySQL())
            MySQLUserData.setTokens(uuid, amount);
        else if (te.isH2())
            H2UserData.setTokens(uuid, amount);
    }

    public static void addTokens(UUID uuid, int amount) {
        if (te.isMySQL())
            MySQLUserData.addTokens(uuid, amount);
        else if (te.isH2())
            H2UserData.addTokens(uuid, amount);
    }

    public static void removeTokens(UUID uuid, int amount) {
        if (te.isMySQL())
            MySQLUserData.removeTokens(uuid, amount);
        else if (te.isH2())
            H2UserData.removeTokens(uuid, amount);
    }

    public static void addTokens(UUID uuid, double amount) {
        if (te.isMySQL())
            MySQLUserData.addTokens(uuid, amount);
        else if (te.isH2())
            H2UserData.addTokens(uuid, amount);
    }

    public static void removeTokens(UUID uuid, double amount) {
        if (te.isMySQL())
            MySQLUserData.removeTokens(uuid, amount);
        else if (te.isH2())
            H2UserData.removeTokens(uuid, amount);
    }

    public static boolean getIgnore(UUID uuid) {
        if (te.isMySQL())
            return MySQLUserData.getIgnore(uuid);
        else if (te.isH2())
            return H2UserData.getIgnore(uuid);
        return false;
    }

    public static double getBankDouble(UUID uuid) {
        if (te.isMySQL())
            return MySQLUserData.getBankDouble(uuid);
        else if (te.isH2())
            return H2UserData.getBankDouble(uuid);
        return -1;
    }

    public static int getBankInt(UUID uuid) {
        if (te.isMySQL())
            return MySQLUserData.getBankInt(uuid);
        else if (te.isH2())
            return H2UserData.getBankInt(uuid);
        return -1;
    }

    public static double getTokensDouble(UUID uuid) {
        if (te.isMySQL())
            return MySQLUserData.getTokensDouble(uuid);
        else if (te.isH2())
            return H2UserData.getTokensDouble(uuid);
        return -1;
    }

    public static int getTokensInt(UUID uuid) {
        if (te.isMySQL())
            return MySQLUserData.getTokensInt(uuid);
        else if (te.isH2())
            return H2UserData.getTokensInt(uuid);
        return -1;
    }

    public static double getTokensDoubleByName(String player) {
        if (te.isMySQL())
            return MySQLUserData.getTokensDoubleByName(player);
        else if (te.isH2())
            return H2UserData.getTokensDoubleByName(player);
        return -1;
    }

    public static TreeMap< String, Integer > getTop10() {
        if (te.isMySQL())
            return MySQLUserData.getTop10();
        else if (te.isH2())
            return H2UserData.getTop10();
        return null;
    }

    public static double getServerTotalTokens() {
        if (te.isMySQL())
            return MySQLUserData.getServerTotalTokens();
        else if (te.isH2())
            return H2UserData.getServerTotalTokens();
        return -1;
    }

    public static void createUserAccount(Player player) {
        if (te.isMySQL()) {
            HikariTokens.getUser().createPlayer(player);
        } else if (te.isH2()) {
            HikariTokens.getH2user().createPlayer(player);
        }
    }

    public static void updateTop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (Bukkit.getOnlinePlayers().size() >= 1) {
                        TokenManager tokens = HikariTokens.getTokenManager(players);

                        if (te.isMySQL()) {
                            MySQLUserData.setTokens(players.getUniqueId(), tokens.getTokens());
                        } else if (te.isH2()) {
                            H2UserData.setTokens(players.getUniqueId(), tokens.getTokens());
                        }

                        HikariTokens.getTokenMap().clear();
                        HikariTokens.getTokenManager(players);
                    }
                }

                Bukkit.getConsoleSender().sendMessage("[TokenStatUpdater] Updated Token-Stats!");
            }
        }.runTaskTimer(HikariTokens.getInstance(), 0, 20 * HikariTokens.getInstance().getConfig().getInt("t.plugin.update-stats"));

    }

}
