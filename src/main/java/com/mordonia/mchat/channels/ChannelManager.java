package com.mordonia.mchat.channels;

import com.mordonia.mchat.MChat;
import com.mordonia.mcore.mchat.util.channelData.ChatChannelDataManager;
import com.mordonia.mcore.mchat.util.kingdomData.KingdomDataManager;
import com.mordonia.mcore.mchat.util.playerData.PlayerChatDataManager;
import com.mordonia.mcore.ms.util.TicketDataManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ChannelManager {
    private PlayerChatDataManager playerChatDataManager;
    private KingdomDataManager kingdomDataManager;
    public ChatChannelDataManager chatChannelDataManager;
    public TicketDataManager ticketDataManager;
    private MChat mChat;


    public ChannelManager(PlayerChatDataManager playerChatDataManager, KingdomDataManager kingdomDataManager, ChatChannelDataManager chatChannelDataManager, TicketDataManager ticketDataManager, MChat mChat) {
        this.chatChannelDataManager = chatChannelDataManager;
        this.kingdomDataManager = kingdomDataManager;
        this.playerChatDataManager = playerChatDataManager;
        this.ticketDataManager = ticketDataManager;
        this.mChat = mChat;
    }

    public void runChat(Player p, String msg, String msgSub) {
        TextComponent channelName = new TextComponent("[Local] ");
        channelName.setColor(ChatColor.GRAY);
        TextComponent message = new TextComponent(msg);
        String chatColor = playerChatDataManager.get(p).getChatcolor();
        switch(chatColor){
            default: break;
            case("&f"):
                message.setColor(ChatColor.WHITE);
                break;
            case("&3"):
                message.setColor(ChatColor.DARK_AQUA);
                break;
            case("&5"):
                message.setColor(ChatColor.DARK_PURPLE);
                break;
            case("&2"):
                message.setColor(ChatColor.DARK_GREEN);
                break;
            case("&9"):
                message.setColor(ChatColor.BLUE);
                break;
            case("&e"):
                message.setColor(ChatColor.YELLOW);
                break;
            case("&a"):
                message.setColor(ChatColor.GREEN);
                break;
            case("&d"):
                message.setColor(ChatColor.LIGHT_PURPLE);
                break;
            case("&c"):
                message.setColor(ChatColor.RED);
                break;
            case("&6"):
                message.setColor(ChatColor.GOLD);
                break;
        }
        TextComponent firstname = new TextComponent(ChatColor.translateAlternateColorCodes('&', playerChatDataManager.get(p).getFirstname() + " "));
        TextComponent lastname = new TextComponent(ChatColor.translateAlternateColorCodes('&', playerChatDataManager.get(p).getLastname() + ": "));
        World world = p.getWorld();
        String group = mChat.getChat().getPrimaryGroup(p);
        String groupPrefix = mChat.getChat().getGroupPrefix(world, group);
        TextComponent grPrefix = new TextComponent(ChatColor.translateAlternateColorCodes('&', groupPrefix + " &r"));
        String k = playerChatDataManager.get(p).getKingdom();
        String kColor = kingdomDataManager.dataMap.get(k).getColor();
        String r1 = playerChatDataManager.get(p).getRank1();
        String r2 = playerChatDataManager.get(p).getRank2();

        firstname.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', kColor + k + " || " + r1 + " || " + r2)).create()));
        lastname.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', kColor + k + " || " + r1 + " || " + r2)).create()));

        if(msg.startsWith("@")){
            message.setText(message.getText().substring(1));
            onGlobal(p, firstname, lastname, message, grPrefix);
            return;
        }
        if(msg.startsWith("$")){
            message.setText(message.getText().substring(1));
            onMarket(p, firstname, lastname, message, grPrefix);
            return;
        }
        if(msg.startsWith("!")){
            message.setText(message.getText().substring(1));
            onRequest(p, firstname, lastname, message, grPrefix);
            return;
        }
        if(msg.startsWith("^")){
            message.setText(message.getText().substring(1));
            onRp(p, firstname, lastname, message, grPrefix);
            return;
        }
        if(msg.startsWith("-")){
            message.setText(message.getText().substring(1));
            onAdmin(p, firstname, lastname, message, grPrefix);
            return;
        }
        if(msg.startsWith("?")){
            message.setText(message.getText().substring(1));
            onHelper(p, firstname, lastname, message, grPrefix);
            return;
        }
        if(msg.startsWith("#")){
            message.setText(message.getText().substring(1));
            onStaff(p, firstname, lastname, message, grPrefix);
            return;
        }
        if(msg.startsWith("<")){
            message.setText(message.getText().substring(1));
            onWhisper(p, firstname, lastname, message, grPrefix);
            return;
        }
        if(msg.startsWith(")")){
            message.setText(message.getText().substring(1));
            onLrp(p, firstname, lastname, message, grPrefix);
            return;
        }
        if(msg.startsWith("+")){
            message.setText(message.getText().substring(1));
            onKingdomChat(p, firstname, lastname, message, grPrefix);
            return;
        }
        else{
            onLocal(p, firstname, lastname, message, grPrefix);
        }

    }

    public void onLocal(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix) {
        if (playerChatDataManager.get(p).isTicket()) return;
            if (!playerChatDataManager.get(p).isLocal()) return;
            TextComponent channelName = new TextComponent("[Local] ");
            channelName.setColor(ChatColor.GRAY);
            p.getWorld().getPlayers().stream().filter(rec -> rec.getLocation().distance(p.getLocation()) <= 25).forEach(rec -> {
                if (!playerChatDataManager.get(rec).isTicket()) {
                    if (playerChatDataManager.get(rec).isLocal()) {
                        rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
                    }
                }
            });

        }
    public void onGlobal(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).isTicket()) return;
        if(!playerChatDataManager.get(p).isGlobal()) return;
        TextComponent channelName = new TextComponent("[Global] ");
        channelName.setColor(ChatColor.GOLD);
        for(Player rec: Bukkit.getOnlinePlayers()){
            if(!playerChatDataManager.get(rec).isTicket()) {
                if(playerChatDataManager.get(rec).isGlobal()) {
                    rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
                }
            }
        }
    }
    public void onMarket(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).isTicket()) return;
        if(!playerChatDataManager.get(p).isLocal()) return;
        TextComponent channelName = new TextComponent("[Market] ");
        channelName.setColor(ChatColor.DARK_BLUE);
        for(Player rec: Bukkit.getOnlinePlayers()){
            if(!playerChatDataManager.get(rec).isTicket()) {
                if(playerChatDataManager.get(rec).isMarket()) {
                    rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
                }
            }
        }
    }
    public void onRequest(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).isTicket()) return;
        TextComponent channelName = new TextComponent("[Request] ");
        channelName.setColor(ChatColor.BLUE);
        for(Player rec: Bukkit.getOnlinePlayers()){
            if(!playerChatDataManager.get(rec).isTicket()) {
                rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
            }
        }
    }
    public void onRp(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).isTicket()) return;
        TextComponent channelName = new TextComponent("[RP] ");
        channelName.setColor(ChatColor.LIGHT_PURPLE);
        for(Player rec: Bukkit.getOnlinePlayers()){
            if(!playerChatDataManager.get(rec).isTicket()) {
                rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
            }
        }
    }
    public void onAdmin(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).isTicket()) return;
        TextComponent channelName = new TextComponent("[A] ");
        channelName.setColor(ChatColor.RED);
        for(Player rec: Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("mchat.admin")) {
                if (rec.hasPermission("mchat.admin")) {
                    if (!playerChatDataManager.get(rec).isTicket()) {
                        rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
                    }
                }
            }
        }
    }
    public void onHelper(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).isTicket()) return;
        TextComponent channelName = new TextComponent("[H] ");
        channelName.setColor(ChatColor.GREEN);
        for(Player rec: Bukkit.getOnlinePlayers()){
            if(p.hasPermission("mchat.helper")){
                if(rec.hasPermission("mchat.helper")){
                    if(!playerChatDataManager.get(rec).isTicket()) {
                        rec.spigot().sendMessage(channelName,groupPrefix, firstname, lastname, message);
                    }                }
            }
        }
    }
    public void onStaff(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).isTicket()) return;
        if(!playerChatDataManager.get(p).isStaff()) return;
        TextComponent channelName = new TextComponent("[Staff] ");
        channelName.setColor(ChatColor.DARK_AQUA);
        for(Player rec: Bukkit.getOnlinePlayers()){
            if(p.hasPermission("mchat.staff")){
                if(rec.hasPermission("mchat.staff")){
                    if(!playerChatDataManager.get(rec).isTicket()) {
                        if(playerChatDataManager.get(rec).isStaff()) {
                            rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
                        }
                    }
                }
            }
        }
    }
    public void onWhisper(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).isTicket()) return;
        TextComponent channelName = new TextComponent("[Whisper] ");
        channelName.setColor(ChatColor.DARK_GRAY);
        p.getWorld().getPlayers().stream().filter(rec -> rec.getLocation().distance(p.getLocation()) <= 5).forEach(rec -> {
            if(!playerChatDataManager.get(rec).isTicket()) {
                rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
            }
        });

    }
    public void onLrp(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).isTicket()) return;
        TextComponent channelName = new TextComponent("[LRP] ");
        channelName.setColor(ChatColor.LIGHT_PURPLE);
        p.getWorld().getPlayers().stream().filter(rec -> rec.getLocation().distance(p.getLocation()) <= 30).forEach(rec -> {
            if(!playerChatDataManager.get(rec).isTicket()) {
                rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
            }        });

    }

    public void onKingdomChat(Player p, TextComponent firstname, TextComponent lastname, TextComponent message, TextComponent groupPrefix){
        if(playerChatDataManager.get(p).getKingdom().equals("Nomad")) return;
        if(playerChatDataManager.get(p).isTicket()) return;
        if(!playerChatDataManager.get(p).isKingdom_chat()) return;
        String kingdom = playerChatDataManager.get(p).getKingdom();
        String kColor = kingdomDataManager.dataMap.get(kingdom).getColor();
        TextComponent channelName = new TextComponent(ChatColor.translateAlternateColorCodes('&', kColor + "[Kingdom]&f "));
        for(Player rec : Bukkit.getOnlinePlayers()){
            String recKingdom = playerChatDataManager.get(rec).getKingdom();
            if(recKingdom.equals(kingdom)){
                if(!playerChatDataManager.get(rec).isTicket()) {
                    if(playerChatDataManager.get(rec).isKingdom_chat()) {
                        rec.spigot().sendMessage(channelName, groupPrefix, firstname, lastname, message);
                    }
                }
            }

        }


    }
    }

