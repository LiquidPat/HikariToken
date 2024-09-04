package lithium.hikari.utils.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ReceiveTokensEvent extends Event {


    private final Player player;
    private int tokens;

    public ReceiveTokensEvent(Player player, int tokens) {
        this.player = player;
        this.tokens = tokens;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

    public void setTokens(int i) {
        this.tokens = i;
    }

    public int getTokens() {
        return tokens;
    }

    public Player getPlayer() {
        return player;
    }


}
