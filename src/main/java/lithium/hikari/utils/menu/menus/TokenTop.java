package lithium.hikari.utils.menu.menus;

import lithium.hikari.HikariTokens;
import lithium.hikari.data.H2UserData;
import lithium.hikari.data.UserData;
import lithium.hikari.utils.Utils;
import lithium.hikari.utils.menu.Menu;
import lithium.hikari.utils.menu.MenuUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TokenTop extends Menu {


    private final HikariTokens te = HikariTokens.getPlugin(HikariTokens.class);

    public TokenTop(MenuUtil menuUtil) {
        super(menuUtil);
    }

    @Override
    public String getMenuName() {
        return Utils.applyFormat(HikariTokens.getConfigManager().getTitleTop());
    }

    @Override
    public int getSlots() {
        return HikariTokens.getConfigManager().getSlotsTop();
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {

        // add all entries.
        if (te.isMySQL()) {
            for (String top : UserData.getTop10().keySet()) {
                inventory.addItem(getSkull(top, top.indexOf(top)));
            }
        } else if (te.isH2()) {
            for (String top : H2UserData.getTop10().keySet()) {
                inventory.addItem(getSkull(top, top.indexOf(top)));
            }
        }

        // fill glass
        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta glass_meta = glass.getItemMeta();
        assert glass_meta != null;
        glass_meta.setDisplayName(Utils.applyFormat("&8*"));
        glass.setItemMeta(glass_meta);

        // loop through empty slots and set glass
        for(int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
    }

    public ItemStack getSkull(String name, int slot) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));

        for (String lore : HikariTokens.getConfigManager().getTokenTop().getStringList("gui.items.top_players.lore")) {
            nameItem(name, skull, Utils.applyFormat(Utils.applyFormat(HikariTokens.getConfigManager().getTokenTop().getString("gui.items.top_players.displayname"))), lore);
        }

        String displayname = HikariTokens.getConfigManager().getTokenTop().getString("gui.items.top_players.displayname");

        assert displayname != null;
        if (te.isMySQL()) {
            displayname = displayname.replaceAll("%tokens%", String.valueOf(UserData.getTokensDoubleByName(name)));
        } else if (te.isH2()) {
            displayname = displayname.replaceAll("%tokens%", String.valueOf(H2UserData.getTokensDoubleByName(name)));
        }

        displayname = displayname.replaceAll("%top_player%", name);
        displayname = displayname.replaceAll("%number%", String.valueOf(slot - 1).replace("-", ""));

        meta.setDisplayName(Utils.applyFormat(displayname));

        skull.setItemMeta(meta);

        return skull;
    }

    private ItemStack nameItem(final String player, final ItemStack item, final String name, final String... lore) {
        final ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;

        if (te.isMySQL()) {
            item_meta.setDisplayName(name.replaceAll("%tokens%", String.valueOf(UserData.getTokensDoubleByName(player))));
        } else if (te.isH2()) {
            item_meta.setDisplayName(name.replaceAll("%tokens%", String.valueOf(H2UserData.getTokensDoubleByName(player))));
        }
        /* Setting the lore but translating the color codes as well. */
        item_meta.setLore(Arrays.stream(lore).map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList()));

        item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item_meta.addItemFlags(ItemFlag.HIDE_DESTROYS);

        item.setItemMeta(item_meta);
        return item;
    }

}
