package com.jrmcore.common.handlers;

import com.jrmcore.JRMCore;
import com.jrmcore.common.capability.JRMPlayerData;
import com.jrmcore.common.network.JRMCoreNetwork;
import com.jrmcore.common.network.SyncPlayerDataPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JRMCore.MODID)
public class CommonEventHandler {

    public static void register() {
        // This is optional right now since @Mod.EventBusSubscriber handles subscription
    }

    private static int syncDelay = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;

        player.getCapability(JRMPlayerData.INSTANCE).ifPresent(data -> {
            // Passive Ki regeneration
            if (player.tickCount % 20 == 0) {
                data.chargeKi(data.getConstitution() / 2);
            }

            // Periodic data sync (every 40 ticks)
            if (!player.level().isClientSide && player.tickCount % 40 == 0) {
                CompoundTag tag = new CompoundTag();
                data.saveNBTData(tag);
                JRMCoreNetwork.sendToClient(new SyncPlayerDataPacket(tag), (ServerPlayer) player);
            }
        });
    }
}
