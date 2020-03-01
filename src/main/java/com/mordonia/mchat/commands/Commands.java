package com.mordonia.mchat.commands;

import com.mordonia.mchat.util.Lang;
import com.mordonia.mcore.MySQLConnection;
import com.mordonia.mcore.mchat.util.kingdomData.KingdomData;
import com.mordonia.mcore.mchat.util.kingdomData.KingdomDataManager;
import com.mordonia.mcore.mchat.util.playerData.FileConfig;
import com.mordonia.mcore.mchat.util.playerData.PlayerChatDataManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


public class Commands implements CommandExecutor {
    private PlayerChatDataManager playerChatDataManager;
    private KingdomDataManager kingdomDataManager;
    private Player target;
    private MySQLConnection mysqlconnection;
    private FileConfig fileConfig;


    public Commands(PlayerChatDataManager playerChatDataManager, KingdomDataManager kingdomDataManager, MySQLConnection mysqlconnection, FileConfig fileConfig) {
        this.playerChatDataManager = playerChatDataManager;
        this.kingdomDataManager = kingdomDataManager;
        this.mysqlconnection = mysqlconnection;
        this.fileConfig = fileConfig;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length < 1){
            if(!player.hasPermission("mchat.staff")){
                player.sendMessage(Lang.MCHAT_HELP_USER);
                return false;
            }
            player.sendMessage(Lang.MCHAT_HELP_STAFF);
            return false;
        }
        String subcommand = args[0];

        switch (subcommand){
            default:
                break;
            case("set"):
                if(!player.hasPermission("mchat.set")){
                    player.sendMessage(Lang.NO_PERMISSION);
                    break;
                }
                if(args.length < 2){
                    player.sendMessage(Lang.MCHAT_HELP_STAFF);
                    break;
                }
                String subcommand2 = args[1];
                switch (subcommand2){
                    default:
                        break;
                    case("name"):
                        if(!player.hasPermission("mchat.set.name")){
                            player.sendMessage(Lang.NO_PERMISSION);
                            break;
                        }
                        if(args.length < 5){
                            player.sendMessage(Lang.MCHAT_HELP_STAFF);
                            break;
                        }
                        target = Bukkit.getPlayer(args[2]);
                        if(target == null){
                            player.sendMessage(Lang.PLAYER_NOT_FOUND);
                            break;
                        }
                        String firstname = args[3];
                        String lastname = args[4];
                        playerChatDataManager.get(target).setFirstname(firstname);
                        playerChatDataManager.get(target).setLastname(lastname);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE + " &2Name set successfully to: " + firstname + " " + lastname));
                        break;
                    case("firstname"):
                        if(!player.hasPermission("mchat.set.name")){
                            player.sendMessage(Lang.NO_PERMISSION);
                            break;
                        }
                        if(args.length < 4){
                            player.sendMessage(Lang.MCHAT_HELP_STAFF);
                            break;
                        }
                        target = Bukkit.getPlayer(args[2]);
                        if(target == null){
                            player.sendMessage(Lang.PLAYER_NOT_FOUND);
                            break;
                        }
                        playerChatDataManager.get(target).setFirstname(args[3]);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE + " &Firstname set successfully to: " + args[3]));
                        break;
                    case("lastname"):
                        if(!player.hasPermission("mchat.set.name")){
                            player.sendMessage(Lang.NO_PERMISSION);
                            break;
                        }
                        if(args.length < 4){
                            player.sendMessage(Lang.MCHAT_HELP_STAFF);
                            break;
                        }
                        target = Bukkit.getPlayer(args[2]);
                        if(target == null){
                            player.sendMessage(Lang.PLAYER_NOT_FOUND);
                            break;
                        }
                        playerChatDataManager.get(target).setLastname(args[3]);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE + " &Lastname set successfully to: " + args[3]));
                        break;
                    case("kingdom"):
                        if(!player.hasPermission("mchat.set.kingdom")){
                            player.sendMessage(Lang.NO_PERMISSION);
                            break;
                        }
                        if(args.length < 4){
                            player.sendMessage(Lang.MCHAT_HELP_STAFF);
                            break;
                        }
                        target = Bukkit.getPlayer(args[2]);
                        if(target == null){
                            player.sendMessage(Lang.PLAYER_NOT_FOUND);
                            break;
                        }
                        String kingdom = args[3];
                        if(!kingdomDataManager.dataMap.containsKey(kingdom)){
                            player.sendMessage(Lang.INVALID_KINGDOM);
                            break;
                        }
                        playerChatDataManager.get(target).setKingdom(args[3]);
                        player.sendMessage(Lang.KINGDOM_SET);
                        break;
                    case("rank1"):
                        if(!player.hasPermission("mchat.set.rank")){
                            player.sendMessage(Lang.NO_PERMISSION);
                            break;
                        }
                        if(args.length < 4){
                            player.sendMessage(Lang.MCHAT_HELP_STAFF);
                            break;
                        }
                        target = Bukkit.getPlayer(args[2]);
                        if(target == null){
                            player.sendMessage(Lang.PLAYER_NOT_FOUND);
                            break;
                        }
                        playerChatDataManager.get(target).setRank1(args[3]);
                        sender.sendMessage(ChatColor.GREEN + "Rank was set successfully!");
                        break;
                    case("rank2"):
                        if(!player.hasPermission("mchat.set.rank")){
                            player.sendMessage(Lang.NO_PERMISSION);
                            break;
                        }
                        if(args.length < 4){
                            player.sendMessage(Lang.MCHAT_HELP_STAFF);
                            break;
                        }
                        target = Bukkit.getPlayer(args[2]);
                        if(target == null){
                            player.sendMessage(Lang.PLAYER_NOT_FOUND);
                            break;
                        }
                        playerChatDataManager.get(target).setRank2(args[3]);
                        sender.sendMessage(ChatColor.GREEN + "Rank was set successfully!");
                        break;
                }
                break;
            case("name"):
                if(!playerChatDataManager.get(player).getFirstname().equalsIgnoreCase("Unnamed")){
                    player.sendMessage(Lang.TITLE + ChatColor.DARK_RED + " Your name is already set!");
                    break;
                }
                if(args.length < 2){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE + " &4Invalid usage!&r \n /mchat name [name]"));
                    break;
                }
                playerChatDataManager.get(player).setFirstname(args[1]);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE + " &2You set your name successfully!"));
                break;
            case("create"):
                if(!player.hasPermission("mchat.create.kingdom")){
                    player.sendMessage(Lang.NO_PERMISSION);
                    break;
                }
                if(!args[1].equalsIgnoreCase("kingdom")){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE  + "&4Invalid usage! \n /mchat create kingdom [name] [color]"));
                    break;
                }
                if(args.length < 4){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE  + "&4Invalid usage! \n /mchat create kingdom [name] [color]"));
                    break;
                }
                if(kingdomDataManager.dataMap.containsKey(args[3])){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE + " &4Kingdom already exists!"));
                    break;
                }
                kingdomDataManager.dataMap.put(args[2], new KingdomData(args[2], args[3]));
                try{
                    PreparedStatement preparedStatement1 = mysqlconnection.getConnection().prepareStatement("INSERT INTO kingdom_data(name, color) VALUES(?, ?)");
                    preparedStatement1.setString(1, args[2]);
                    preparedStatement1.setString(2, args[3]);
                    preparedStatement1.executeUpdate();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case("delete"):
                if(!player.hasPermission("mchat.delete.kingdom")){
                    player.sendMessage(Lang.NO_PERMISSION);
                    break;
                }
                if(args.length < 2){
                    player.sendMessage(ChatColor.DARK_RED + "/mchat delete kingdom [name]");
                    break;
                }
                if(!args[1].equalsIgnoreCase("kingdom")){
                    player.sendMessage(ChatColor.DARK_RED + "/mchat delete kingdom [name]");
                    break;
                }
                if(!kingdomDataManager.dataMap.containsKey(args[2])){
                    player.sendMessage(ChatColor.DARK_RED + "The kingdom you are trying to delete does not exist");
                }
                else{
                    try{
                        PreparedStatement preparedStatement2 = mysqlconnection.getConnection().prepareStatement("DELETE * FROM kingdom_data WHERE name=?");
                        preparedStatement2.setString(1, args[2]);
                        preparedStatement2.executeUpdate();
                    }
                    catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            case("channels"):
                sender.sendMessage(Lang.TITLE + ChatColor.GOLD + "Channel list:");
                sender.sendMessage(ChatColor.GOLD + "                    ");
                sender.sendMessage(ChatColor.GOLD + "[Global] " + ChatColor.RESET + "Prefix: @ " + ChatColor.GOLD + " ➢ Used to talk to the whole server");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "[RP] " + ChatColor.RESET + "Prefix: ^ " + ChatColor.GOLD + " ➢ Used to RolePlay so that everyone can see it");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "[LocalRP] " + ChatColor.RESET + "Prefix: ) " + ChatColor.GOLD + " ➢ Used to Roleplay, only appears to players in a 25 block radius");
                sender.sendMessage(ChatColor.DARK_BLUE + "[Market] " + ChatColor.RESET + "Prefix: $ " + ChatColor.GOLD + " ➢ Used to trade/buy items with others!");
                sender.sendMessage(ChatColor.AQUA + "[Request] " + ChatColor.RESET + "Prefix: ! " + ChatColor.GOLD + " ➢ Used to request various things like entry to a town you don't belong to or joining a town");
                sender.sendMessage(ChatColor.GRAY + "[Local] " + ChatColor.RESET + "Prefix: none " + ChatColor.GOLD + " ➢ The default chat! Players in a 50 block radius can see it!");
                sender.sendMessage(ChatColor.DARK_GRAY + "[Whisper] " + ChatColor.RESET + "Prefix: < " + ChatColor.GOLD + " ➢ Only players in a 5 block radius can see these messages");
                sender.sendMessage(ChatColor.GOLD + "[Kingdom] " + ChatColor.RESET + "Prefix: + " + ChatColor.GOLD + " ➢ Speak to the players in the same kingdom as you!");
                break;
            case("random"):
                if(!player.hasPermission("mchat.random")){
                    player.sendMessage(Lang.NO_PERMISSION);
                    break;
                }
                if(args.length < 2){
                    player.sendMessage(Lang.MCHAT_HELP_STAFF);
                    break;
                }
                Player target = Bukkit.getPlayerExact(args[1]);
                if(target == null){
                    player.sendMessage(Lang.PLAYER_NOT_FOUND);
                    break;
                }
                List<String> lnl = fileConfig.getLastNamesCfg().getStringList("lastnames.names");
                int rand = new Random().nextInt(lnl.size());
                String lastName = lnl.get(rand);
                playerChatDataManager.get(target).setLastname(lastName);
                break;
            case("chatcolor"):
                TextComponent colorMessage = new TextComponent("Your color has been set!");
                if(args.length < 2){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE + "&4 Invalid usage!&f \n /mchat chatcolor [color]"));
                    break;
                }
                String color = args[1];
                switch (color){
                    default: break;
                    case("white"):
                        playerChatDataManager.get(player).setChatcolor("&f");
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);
                        break;
                    case("red"):
                        if(!player.hasPermission("mchat.admin")){
                            player.sendMessage(Lang.NO_PERMISSION);
                            break;
                        }
                        playerChatDataManager.get(player).setChatcolor("&c");
                        colorMessage.setColor(net.md_5.bungee.api.ChatColor.RED);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);
                        break;
                    case("gold"):
                        if(!player.hasPermission("mchat.owner")){
                            player.sendMessage(Lang.NO_PERMISSION);
                            break;
                        }
                        playerChatDataManager.get(player).setChatcolor("&6");
                        colorMessage.setColor(net.md_5.bungee.api.ChatColor.GOLD);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);
                        break;
                    case("darkaqua"):
                        if(!player.hasPermission("mchat.colors.darkaqua")){
                            player.sendMessage(Lang.CHAT_COLOR_DONATION);
                            break;
                        }
                        playerChatDataManager.get(player).setChatcolor("&3");
                        colorMessage.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);
                        break;
                    case("darkpurple"):
                        if(!player.hasPermission("mchat.colors.darkpurple")){
                            player.sendMessage(Lang.CHAT_COLOR_DONATION);
                            break;
                        }
                        playerChatDataManager.get(player).setChatcolor("&5");
                        colorMessage.setColor(net.md_5.bungee.api.ChatColor.DARK_PURPLE);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);
                        break;
                    case("darkgreen"):
                        if(!player.hasPermission("mchat.colors.darkgreen")){
                            player.sendMessage(Lang.CHAT_COLOR_DONATION);
                            break;
                        }
                        playerChatDataManager.get(player).setChatcolor("&2");
                        colorMessage.setColor(net.md_5.bungee.api.ChatColor.DARK_GREEN);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);                        break;
                    case("blue"):
                        if(!player.hasPermission("mchat.colors.blue")){
                            player.sendMessage(Lang.CHAT_COLOR_DONATION);
                            break;
                        }
                        playerChatDataManager.get(player).setChatcolor("&9");
                        colorMessage.setColor(net.md_5.bungee.api.ChatColor.BLUE);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);
                        break;
                    case("yellow"):
                        if(!player.hasPermission("mchat.colors.yellow")){
                            player.sendMessage(Lang.CHAT_COLOR_DONATION);
                            break;
                        }
                        playerChatDataManager.get(player).setChatcolor("&e");
                        colorMessage.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);
                        break;
                    case("green"):
                        if(!player.hasPermission("mchat.colors.green")){
                            player.sendMessage(Lang.CHAT_COLOR_DONATION);
                            break;
                        }
                        playerChatDataManager.get(player).setChatcolor("&a");
                        colorMessage.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);
                        break;
                    case("lightpurple"):
                        if(!player.hasPermission("mchat.colors.lightpurple")){
                            player.sendMessage(Lang.CHAT_COLOR_DONATION);
                            break;
                        }
                        playerChatDataManager.get(player).setChatcolor("&d");
                        colorMessage.setColor(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, colorMessage);
                        break;
                }

        }

        return false;
    }
}
