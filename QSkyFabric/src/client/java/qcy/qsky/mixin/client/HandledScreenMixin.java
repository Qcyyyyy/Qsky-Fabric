package qcy.qsky.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qcy.qsky.PetCooldown.CountdownManager;
import qcy.qsky.QskyClient;
import qcy.qsky.QskyGUI.RenderQskyGUI;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;

import static qcy.qsky.QskyClient.config;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {

    // Slot rendering logic for item cooldowns
    @Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
    private void onDrawSlot(DrawContext context, Slot slot, CallbackInfo ci) {
        ItemStack stack = slot.getStack();
        MatrixStack matrices = context.getMatrices();

        if (!QskyClient.searchField.isEmpty() && stack.getName().getString().toLowerCase().contains(QskyClient.searchField.toLowerCase())) {
            matrices.push();
            matrices.translate(slot.x,slot.y,252.0F);
            context.drawBorder(0,0,16,16, 0xE6007EFF);
            matrices.pop();
        }

        if (CountdownManager.isCountingDown(stack) && RenderQskyGUI.petCooldownMod.modEnabled()) {
            long remainingTime = CountdownManager.getRemainingTime(stack);
            int minutes = (int) (remainingTime / (60*1000));
            int seconds = (int) ((remainingTime % (60*1000))/1000);
            String timeString = String.format("%d:%02d", minutes, seconds);
            float scaleAmount = 0.6F;

            if (timeString.length() >= 6) {
                timeString = timeString.substring(0,timeString.length()-3);
            }

            matrices.push();

            matrices.translate(slot.x,slot.y,251.0F);

            matrices.scale(scaleAmount, scaleAmount, 1F);
            if (CountdownManager.isActive(stack)) {
                context.drawBorder(0,0,26,26, 0xE600FF00);
            }

            else {
                context.drawBorder(0,0,26,26, 0xE6FF0000);
            }

            int textOffset = switch (timeString.length()) {
                case 3 -> 5;
                case 4 -> 6;
                case 5 -> 9;
                case 6 -> 14;
                case 7 -> 15;
                default -> 0;
            };

            //context.fill(slot.x, slot.y, slot.x+18, slot.y+18, 0xFFFFFF);
            context.drawText(MinecraftClient.getInstance().textRenderer, timeString, 9-textOffset, 10, 0xFFFFFFFF, true);

            matrices.pop();
        }
    }

}
