package com.jrmcore.client;

import com.jrmcore.JRMCore;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = JRMCore.MODID, value = net.minecraftforge.api.distmarker.Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeybindHandler {

    public static KeyMapping flightToggleKey;
    public static KeyMapping statsMenuKey;

    @SubscribeEvent
    public static void onRegisterKeys(RegisterKeyMappingsEvent event) {
        flightToggleKey = new KeyMapping("key.jrmcore.flight_toggle", GLFW.GLFW_KEY_F, "key.categories.jrmcore");
        statsMenuKey = new KeyMapping("key.jrmcore.open_stats", GLFW.GLFW_KEY_G, "key.categories.jrmcore");

        event.register(flightToggleKey);
        event.register(statsMenuKey);
    }
}
