package com.jrmcore.common.network;

import com.jrmcore.JRMCore;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.SimpleChannel;

import java.util.function.Supplier;

public class JRMCoreNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(JRMCore.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.registerMessage(id(), SyncPlayerDataPacket.class,
            SyncPlayerDataPacket::encode,
            SyncPlayerDataPacket::decode,
            SyncPlayerDataPacket::handle);
    }

    public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
        INSTANCE.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
