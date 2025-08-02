package com.jrmcore.common.network;

import com.jrmcore.common.capability.JRMPlayerData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncPlayerDataPacket {
    private final CompoundTag data;

    public SyncPlayerDataPacket(CompoundTag tag) {
        this.data = tag;
    }

    public static void encode(SyncPlayerDataPacket packet, FriendlyByteBuf buf) {
        buf.writeNbt(packet.data);
    }

    public static SyncPlayerDataPacket decode(FriendlyByteBuf buf) {
        return new SyncPlayerDataPacket(buf.readNbt());
    }

    public static void handle(SyncPlayerDataPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Player player = net.minecraft.client.Minecraft.getInstance().player;
                if (player != null) {
                    player.getCapability(JRMPlayerData.INSTANCE).ifPresent(data -> data.loadNBTData(packet.data));
                }
            });
        });
        context.get().setPacketHandled(true);
    }
}
