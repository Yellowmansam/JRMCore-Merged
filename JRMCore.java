package com.jrmcore;

import com.jrmcore.common.capability.JRMPlayerData;
import com.jrmcore.common.handlers.CommonEventHandler;
import com.jrmcore.common.network.JRMCoreNetwork;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(JRMCore.MODID)
public class JRMCore {
    public static final String MODID = "jrmcore";

    public JRMCore() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onCommonSetup);

        // Register capabilities
        JRMPlayerData.register();

        // Initialize network
        JRMCoreNetwork.register();

        // Register common event handler
        CommonEventHandler.register();
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        // Perform any needed setup work here
    }
}
