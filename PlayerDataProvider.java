package com.jrmcore.common.capability;

import com.jrmcore.JRMCore;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerDataProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
    public static final ResourceLocation ID = new ResourceLocation(JRMCore.MODID, "player_data");

    private final JRMPlayerData backend = new JRMPlayerData();
    private final LazyOptional<JRMPlayerData> optional = LazyOptional.of(() -> backend);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == JRMPlayerData.INSTANCE ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        backend.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.loadNBTData(nbt);
    }
}
