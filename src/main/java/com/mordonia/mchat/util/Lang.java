package com.mordonia.mchat.util;

import org.bukkit.ChatColor;


public class Lang {
    public static String TITLE = ChatColor.translateAlternateColorCodes('&', "&9[&6MChat&9]&r");
    public static String NO_PERMISSION = ChatColor.translateAlternateColorCodes('&', TITLE + " &4You do not have enough permission to perform this command!");
    public static String MCHAT_HELP_STAFF = ChatColor.translateAlternateColorCodes('&', TITLE + " &6Command List: \n &f/mchat set name [player] [firstname] [lastname] \n /mchat set firstname [player] [firstname] \n /mchat set lastname [player] [lastname] \n /mchat set kingdom player [kingdom] \n /mchat set rank1 [rank] \n /mchat set rank2 [rank]");
    public static String MCHAT_HELP_USER = ChatColor.translateAlternateColorCodes('&', TITLE + " &6 Command List: \n &f/mchat name \n /mchat chatcolor color" );
    public static String PLAYER_NOT_FOUND = ChatColor.translateAlternateColorCodes('&', TITLE + " &4Player was not found!");
    public static String INVALID_KINGDOM = ChatColor.translateAlternateColorCodes('&', TITLE + " &4Invalid Kingdom!");
    public static String KINGDOM_SET = ChatColor.translateAlternateColorCodes('&', TITLE + " &aKingdom set successfully!");
    public static String CHAT_COLOR_DONATION = ChatColor.translateAlternateColorCodes('&', Lang.TITLE + " &6You do not own that chatcolor! You can purchase it via the donation store:&r \n https://www.mordoniamc.com/donationshop#chat-colors-1499703");











    public Lang(){}
}
