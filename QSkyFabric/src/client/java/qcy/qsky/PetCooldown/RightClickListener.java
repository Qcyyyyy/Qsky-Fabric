package qcy.qsky.PetCooldown;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public class RightClickListener {

    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (world.isClient) {
                String itemName = player.getStackInHand(hand).getName().getString();
                if (!(CountdownManager.getRemainingTime(player.getStackInHand(hand)) > 0)) {
                    if (itemName.contains("Loot Llama Pet")) {
                        CountdownManager.startTimer(itemName, 24 * 60 * 60 * 1000, 0);
                    } else if (itemName.contains("Battle Pig Pet")) {
                        CountdownManager.startTimer(itemName, 5 * 60 * 1000, 60 * 1000);
                    } else if (itemName.contains("Miner Matt Pet")) {
                        CountdownManager.startTimer(itemName, 10 * 60 * 1000, 0);
                    } else if (itemName.contains("Chaos Cow Pet")) {
                        CountdownManager.startTimer(itemName, 5 * 60 * 1000, 60 * 1000);
                    } else if (itemName.contains("Blacksmith Brandon Pet")) {
                        CountdownManager.startTimer(itemName, 20 * 60 * 1000, 0);
                    } else if (itemName.contains("Fisherman Fred Pet")) {
                        CountdownManager.startTimer(itemName, 10 * 60 * 1000, 0);
                    } else if (itemName.contains("Alchemist Alex Pet")) {
                        CountdownManager.startTimer(itemName, 2 * 60 * 60 * 1000, 0);
                    } else if (itemName.contains("Blood Sheep Pet")) {
                        CountdownManager.startTimer(itemName, 5 * 60 * 1000, 60 * 1000);
                    } else if (itemName.contains("Merchant Pet")) {
                        CountdownManager.startTimer(itemName, 15 * 60 * 1000, 0);
                    } else if (itemName.contains("Dire Wolf Pet")) {
                        CountdownManager.startTimer(itemName, 15 * 60 * 1000, 0);
                    }
                }

                return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand).getItem().getDefaultStack());
            }
            return new TypedActionResult<ItemStack>(ActionResult.FAIL, player.getStackInHand(hand).getItem().getDefaultStack());
        });
    }

}
