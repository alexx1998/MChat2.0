package com.mordonia.mchat.commands;

import com.mordonia.mchat.util.Lang;
import com.mordonia.mcore.mchat.util.kingdomData.KingdomDataManager;
import com.mordonia.mcore.mchat.util.playerData.PlayerChatDataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class Toggles implements CommandExecutor {
    private PlayerChatDataManager playerChatDataManager;
    private KingdomDataManager kingdomDataManager;

    public Toggles(PlayerChatDataManager playerChatDataManager, KingdomDataManager kingdomDataManager) {
        this.playerChatDataManager = playerChatDataManager;
        this.kingdomDataManager = kingdomDataManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s,  @NotNull String[] args) {

        Player pl = (Player) sender;

        if(command.getName().equals("togglechat")){

            if (args.length == 0){
                pl.sendMessage(Lang.TITLE + ChatColor.DARK_RED + "You must specify which channel to toggle");
            return false;
            }
            String subcomand = args[0];
            String kColor = kingdomDataManager.dataMap.get(playerChatDataManager.get(pl).getKingdom()).getColor();

            switch (subcomand){
                default:
                    break;
                case("global"):
                    if(!playerChatDataManager.get(pl).isGlobal()){
                        playerChatDataManager.get(pl).setGlobal(true);
                        sender.sendMessage(Lang.TITLE + ChatColor.GOLD + "[Global] " + ChatColor.GREEN +" has been turned on");

                    }
                    else{
                        playerChatDataManager.get(pl).setGlobal(false);
                        sender.sendMessage(Lang.TITLE + ChatColor.GOLD + "[Global]" + ChatColor.RED +" has been turned off");
                    }

                    break;
                case("kingdom"):
                    if(!playerChatDataManager.get(pl).isKingdom_chat()){
                        playerChatDataManager.get(pl).setKingdom_chat(true);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE + kColor + "[Kingdom] " + ChatColor.GREEN  + "has been turned on"));
                    }
                    else{
                        playerChatDataManager.get(pl).setKingdom_chat(false);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.TITLE + kColor + "[Kingdom] " + ChatColor.RED  + "has been turned off"));                    }

                    break;
                case("market"):
                    if(!playerChatDataManager.get(pl).isMarket()){
                        sender.sendMessage(Lang.TITLE + ChatColor.DARK_BLUE + "[Market] " + ChatColor.GREEN +" has been turned on");
                        playerChatDataManager.get(pl).setMarket(true);
                    }
                    else{
                        playerChatDataManager.get(pl).setMarket(false);
                        sender.sendMessage(Lang.TITLE + ChatColor.DARK_BLUE + "[Market] " + ChatColor.RED +" has been turned off");
                    }

                    break;
                case("local"):
                    if(!playerChatDataManager.get(pl).isLocal()){
                        sender.sendMessage(Lang.TITLE + ChatColor.GRAY + "[Local] " + ChatColor.GREEN +" has been turned on");
                        playerChatDataManager.get(pl).setLocal(true);

                    }
                    else{
                        playerChatDataManager.get(pl).setLocal(false);
                        sender.sendMessage(Lang.TITLE + ChatColor.GRAY + "[Local] " + ChatColor.RED +" has been turned off");
                    }

                    break;


            }
        }


        return false;
    }


}
