package qcy.qsky.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qcy.qsky.PetCooldown.CountdownManager;
import qcy.qsky.QskyClient;
import qcy.qsky.QskyGUI.RenderQskyGUI;

import static qcy.qsky.QskyClient.config;

@Mixin(InGameHud.class)
public class InGameHudMixin { // renderHotbarItem

    // Slot rendering logic for item cooldowns
    @Inject(method = "renderHotbarItem", at = @At("HEAD"), cancellable = true)
    private void onDrawSlot(DrawContext context, int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed, CallbackInfo ci) {

        if (CountdownManager.isCountingDown(stack) && RenderQskyGUI.petCooldownMod.modEnabled()) {
            long remainingTime = CountdownManager.getRemainingTime(stack);
            int minutes = (int) (remainingTime / (60*1000));
            int seconds = (int) ((remainingTime % (60*1000))/1000);
            String timeString = String.format("%d:%02d", minutes, seconds);


            if (timeString.length() >= 6) {
                timeString = timeString.substring(0,timeString.length()-3);
            }


            MatrixStack matrices = context.getMatrices();
            float scaleAmount = 0.6F;

            matrices.push();

            int textOffset = switch (timeString.length()) {
                case 3 -> 5;
                case 4 -> 6;
                case 5 -> 9;
                case 6 -> 14;
                case 7 -> 15;
                default -> 0;
            };

            matrices.translate(x,y,250.0F);

            matrices.scale(scaleAmount, scaleAmount, 1F);
            if (CountdownManager.isActive(stack)) {
                context.drawBorder(0,0,26,26, 0xE600FF00);
            }

            else {
                context.drawBorder(0,0,26,26, 0xE6FF0000);
            }
            context.drawText(MinecraftClient.getInstance().textRenderer, timeString, 9-textOffset, 10, 0xFFFFFFFF, true);
            matrices.pop();
        }
    }

}
