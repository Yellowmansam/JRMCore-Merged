package com.jrmcore.common.capability;

import com.jrmcore.JRMCore;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JRMCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityAttacher {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(PlayerDataProvider.ID, new PlayerDataProvider());
        }
    }

    // Copy capability data on player clone (death/respawn, dimension change, etc.)
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player oldP = event.getOriginal();
        Player newP = event.getEntity();

        oldP.getCapability(JRMPlayerData.INSTANCE).ifPresent(oldData -> {
            newP.getCapability(JRMPlayerData.INSTANCE).ifPresent(newData -> {
                CompoundTag tmp = new CompoundTag();
                oldData.saveNBTData(tmp);
                newData.loadNBTData(tmp);
            });
        });
    }
}
