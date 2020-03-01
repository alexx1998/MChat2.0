package com.mordonia.mchat.channels;

import com.mordonia.mchat.MChat;
import com.mordonia.mcore.mchat.util.channelData.ChatChannelDataManager;
import com.mordonia.mcore.mchat.util.kingdomData.KingdomDataManager;
import com.mordonia.mcore.mchat.util.playerData.PlayerChatDataManager;
import com.mordonia.mcore.ms.util.TicketDataManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Channels implements Listener {


    private ChatChannelDataManager chatChannelDataManager;
    private PlayerChatDataManager playerChatDataManager;
    private KingdomDataManager kingdomDataManager;
    private TicketDataManager ticketDataManager;
    private TextComponent firstname, lastname, channelName, msg, chatcolor;
    private static Boolean ticket;
    private Integer radius;
    private MChat plugin;
    private Boolean localChat;
    private ChannelManager channelManager;


    public Channels(ChatChannelDataManager chatChannelDataManager, PlayerChatDataManager playerChatDataManager, MChat plugin, KingdomDataManager kingdomDataManager, TicketDataManager ticketDataManager) {
        this.chatChannelDataManager = chatChannelDataManager;
        this.playerChatDataManager = playerChatDataManager;
        this.plugin = plugin;
        this.kingdomDataManager = kingdomDataManager;
        this.ticketDataManager = ticketDataManager;
        channelManager = new ChannelManager(playerChatDataManager, kingdomDataManager, chatChannelDataManager,ticketDataManager, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        String msg = e.getMessage();
        String msgSub = msg.substring(1);
        channelManager.runChat(p, msg, msgSub);
    }
}