package com.jrmcore.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

public class JRMPlayerData {
    public static final Capability<JRMPlayerData> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    private int tp = 0;
    private int maxKi = 100;
    private int currentKi = 100;
    private int strength = 5;
    private int constitution = 5;
    private int alignment = 0;

    public int getTP() { return tp; }
    public void addTP(int amount) { tp += amount; }

    public int getKi() { return currentKi; }
    public int getMaxKi() { return maxKi; }
    public void chargeKi(int amount) {
        currentKi = Math.min(currentKi + amount, maxKi);
    }
    public void drainKi(int amount) {
        currentKi = Math.max(currentKi - amount, 0);
    }

    public int getStrength() { return strength; }
    public void increaseStrength() { strength++; }

    public int getConstitution() { return constitution; }
    public void increaseConstitution() { constitution++; }

    public int getAlignment() { return alignment; }
    public void adjustAlignment(int delta) { alignment += delta; }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("TP", tp);
        nbt.putInt("MaxKi", maxKi);
        nbt.putInt("CurrentKi", currentKi);
        nbt.putInt("Strength", strength);
        nbt.putInt("Constitution", constitution);
        nbt.putInt("Alignment", alignment);
    }

    public void loadNBTData(CompoundTag nbt) {
        tp = nbt.getInt("TP");
        maxKi = nbt.getInt("MaxKi");
        currentKi = nbt.getInt("CurrentKi");
        strength = nbt.getInt("Strength");
        constitution = nbt.getInt("Constitution");
        alignment = nbt.getInt("Alignment");
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(JRMPlayerData.class);
    }

    public static LazyOptional<JRMPlayerData> get(Player player) {
        return player.getCapability(INSTANCE);
    }
}
