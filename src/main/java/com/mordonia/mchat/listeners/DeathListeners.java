package com.mordonia.mchat.listeners;

import com.mordonia.mcore.mchat.util.playerData.PlayerChatDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListeners implements Listener {
    private PlayerChatDataManager playerChatDataManager;

    public DeathListeners(PlayerChatDataManager playerChatDataManager){
        this.playerChatDataManager = playerChatDataManager;
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        String firstname = playerChatDataManager.get(player).getFirstname();
        String lastname = playerChatDataManager.get(player).getLastname();
        String deathMessage = event.getDeathMessage().replaceAll(player.getDisplayName(), firstname + " " + lastname);
        event.setDeathMessage(deathMessage);
    }
}
