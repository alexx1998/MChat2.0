package com.mordonia.mchat.listeners;

import com.mordonia.mchat.MChat;
import com.mordonia.mchat.util.NameTag;
import com.mordonia.mchat.util.TeamAction;
import com.mordonia.mcore.MySQLConnection;
import com.mordonia.mcore.mchat.util.kingdomData.KingdomDataManager;
import com.mordonia.mcore.mchat.util.playerData.PlayerChatDataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConnectionListeners implements Listener {
    private NameTag nameTag;
    private MChat mchat;
    private PlayerChatDataManager playerChatDataManager;
    private KingdomDataManager kingdomDataManager;
    private MySQLConnection mySQLConnection;

    public ConnectionListeners(MySQLConnection mySQLConnection, PlayerChatDataManager playerChatDataManager, KingdomDataManager kingdomDataManager) {
        this.mySQLConnection = mySQLConnection;
        this.playerChatDataManager = playerChatDataManager;
        this.kingdomDataManager = kingdomDataManager;
    }


    @EventHandler
    public void joinListener(PlayerJoinEvent event){
        Player p = event.getPlayer();
        String firstname = playerChatDataManager.get(event.getPlayer()).getFirstname();
        String lastname = playerChatDataManager.get(event.getPlayer()).getLastname();
        String kingdom = playerChatDataManager.get(p).getKingdom();
        String color = kingdomDataManager.dataMap.get(kingdom).getColor();
        String namelist = ChatColor.translateAlternateColorCodes('&', color + firstname + "[" + p.getDisplayName() +  "]" + lastname);
        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', color + firstname + " " + lastname + ChatColor.DARK_GREEN +" has joined!"));
        p.setPlayerListName(namelist);
        p.setPlayerListHeader(ChatColor.GOLD + "Welcome to Mordonia!");
        p.setPlayerListFooter("www.mordoniatm.com");
        NameTag.changePlayerName(p, color + firstname + "&r[" , "]" + color +  lastname, TeamAction.CREATE);
    }
        @EventHandler
        public void leaveListiner (PlayerQuitEvent event){
            Player p = event.getPlayer();
            String firstname = playerChatDataManager.get(event.getPlayer()).getFirstname();
            String lastname = playerChatDataManager.get(event.getPlayer()).getLastname();
            String color = kingdomDataManager.dataMap.get(playerChatDataManager.get(p).getKingdom()).getColor();

            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', color + firstname + " " + lastname + ChatColor.DARK_RED +" has left!"));


            try {
                PreparedStatement preparedStatement1 = mySQLConnection.getConnection().prepareStatement("SELECT * FROM player WHERE uuid=?");
                preparedStatement1.setString(1, event.getPlayer().getUniqueId().toString());
                ResultSet rs = preparedStatement1.executeQuery();
                if (rs.next()) {
                    String Rfirstname = rs.getString("firstname");
                    String Rlastname = rs.getString("lastname");
                    String RChatColor = rs.getString("chatcolor");
                    String RKingdom = rs.getString("kingdom");
                    String rank1 = rs.getString("rank1");
                    String rank2 = rs.getString("rank2");

                    if (Rfirstname != playerChatDataManager.get(p).getFirstname()) {
                        PreparedStatement pr1 = mySQLConnection.getConnection().prepareStatement("UPDATE player SET firstname=? WHERE uuid=?");
                        pr1.setString(1, playerChatDataManager.get(p).getFirstname());
                        pr1.setString(2, p.getUniqueId().toString());
                        pr1.executeUpdate();
                    }
                    if (Rlastname != playerChatDataManager.get(p).getLastname()) {
                        PreparedStatement pr1 = mySQLConnection.getConnection().prepareStatement("UPDATE player SET lastname=? WHERE uuid=?");
                        pr1.setString(1, playerChatDataManager.get(p).getLastname());
                        pr1.setString(2, p.getUniqueId().toString());
                        pr1.executeUpdate();
                    }
                    if (RChatColor != playerChatDataManager.get(p).getChatcolor()) {
                        PreparedStatement pr1 = mySQLConnection.getConnection().prepareStatement("UPDATE player SET chatcolor=? WHERE uuid=?");
                        pr1.setString(1, playerChatDataManager.get(p).getChatcolor());
                        pr1.setString(2, p.getUniqueId().toString());
                        pr1.executeUpdate();
                    }
                    if (RKingdom != playerChatDataManager.get(p).getKingdom()) {
                        PreparedStatement pr1 = mySQLConnection.getConnection().prepareStatement("UPDATE player SET kingdom=? WHERE uuid=?");
                        pr1.setString(1, playerChatDataManager.get(p).getKingdom());
                        pr1.setString(2, p.getUniqueId().toString());
                        pr1.executeUpdate();
                    }
                    if(rank1 != playerChatDataManager.get(p).getRank1()){
                        PreparedStatement pr1 = mySQLConnection.getConnection().prepareStatement("UPDATE player SET rank1=? WHERE uuid=?");
                        pr1.setString(1, playerChatDataManager.get(p).getRank1());
                        pr1.setString(2, p.getUniqueId().toString());
                        pr1.executeUpdate();
                    }
                    if(rank1 != playerChatDataManager.get(p).getRank2()){
                        PreparedStatement pr1 = mySQLConnection.getConnection().prepareStatement("UPDATE player SET rank2=? WHERE uuid=?");
                        pr1.setString(1, playerChatDataManager.get(p).getRank2());
                        pr1.setString(2, p.getUniqueId().toString());
                        pr1.executeUpdate();
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            playerChatDataManager.remove(event.getPlayer());
        }

    }

