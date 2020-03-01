package com.mordonia.mchat;

import com.mordonia.mchat.channels.Channels;
import com.mordonia.mchat.commands.Commands;
import com.mordonia.mchat.commands.Toggles;
import com.mordonia.mchat.listeners.ConnectionListeners;
import com.mordonia.mchat.listeners.DeathListeners;
import com.mordonia.mcore.MCore;
import com.mordonia.mcore.MySQLConnection;
import com.mordonia.mcore.mchat.util.channelData.ChatChannelDataManager;
import com.mordonia.mcore.mchat.util.kingdomData.KingdomDataManager;
import com.mordonia.mcore.mchat.util.playerData.FileConfig;
import com.mordonia.mcore.mchat.util.playerData.PlayerChatDataManager;
import com.mordonia.mcore.ms.util.TicketDataManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MChat extends JavaPlugin {
    private Chat chat = null;
    public MCore mcoreAPI = (MCore) Bukkit.getServer().getPluginManager().getPlugin("MCore");
    public static MChat mchat;

    @Override
    public void onEnable() {
        mchat = this;
        loadConfig();
        setupChat();
        registerDataEvents();
    }

    public void loadConfig(){
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    public MCore getMcoreAPI() {
        return mcoreAPI;
    }

    public MChat getInstance(){
        return mchat;
    }

    public void registerDataEvents(){
        MySQLConnection connection = mcoreAPI.mySQLConnection;
        PlayerChatDataManager playerChatDataManager = mcoreAPI.playerChatDataManager;
        KingdomDataManager kingdomDataManager = mcoreAPI.kingdomDataManager;
        ChatChannelDataManager chatChannelDataManager = mcoreAPI.chatChannelDataManager;
        TicketDataManager ticketDataManager = mcoreAPI.ticketDataManager;
        FileConfig fileConfig = new FileConfig();

        this.getServer().getPluginManager().registerEvents(new ConnectionListeners(connection,playerChatDataManager, kingdomDataManager), this);
        this.getCommand("togglechat").setExecutor(new Toggles(playerChatDataManager, kingdomDataManager));
        this.getCommand("mchat").setExecutor(new Commands(playerChatDataManager, kingdomDataManager, connection, fileConfig));
        this.getServer().getPluginManager().registerEvents(new Channels(chatChannelDataManager, playerChatDataManager, this, kingdomDataManager, ticketDataManager), this);
        this.getServer().getPluginManager().registerEvents(new DeathListeners(playerChatDataManager), this);

    }
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
    public Chat getChat(){
        return chat;
    }
}
