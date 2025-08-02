package com.jrmcore.client.gui;

import com.jrmcore.common.capability.JRMPlayerData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class StatScreen extends Screen {
    private final Player player;
    private int cost;

    public StatScreen(Player player) {
        super(Component.literal("Stat Upgrade"));
        this.player = player;
    }

    @Override
    protected void init() {
        this.cost = 10;

        addRenderableWidget(Button.builder(Component.literal("Strength +"), btn -> upgrade("strength"))
            .pos(width / 2 - 80, height / 2 - 20).size(100, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Constitution +"), btn -> upgrade("constitution"))
            .pos(width / 2 - 80, height / 2 + 10).size(100, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Close"), btn -> onClose())
            .pos(width / 2 - 80, height / 2 + 40).size(100, 20).build());
    }

    private void upgrade(String stat) {
        player.getCapability(JRMPlayerData.INSTANCE).ifPresent(data -> {
            if (data.getTP() >= cost) {
                if ("strength".equals(stat)) data.increaseStrength();
                if ("constitution".equals(stat)) data.increaseConstitution();
                data.addTP(-cost);
            }
        });
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);

        player.getCapability(JRMPlayerData.INSTANCE).ifPresent(data -> {
            graphics.drawString(this.font, "TP: " + data.getTP(), width / 2 - 80, height / 2 - 60, 0xFFFFFF);
            graphics.drawString(this.font, "STR: " + data.getStrength(), width / 2 - 80, height / 2 - 50, 0xFFFFFF);
            graphics.drawString(this.font, "CON: " + data.getConstitution(), width / 2 - 80, height / 2 - 40, 0xFFFFFF);
        });
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
