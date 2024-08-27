package qcy.qsky.PetCooldown;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class CountdownManager {
    private static Map<String, Long> itemTimers = new HashMap<>();
    private static Map<String, Long> activeTimers = new HashMap<>();

    public static void startTimer(String itemKey, long mseconds, long activeMseconds) {
        itemTimers.put(itemKey, mseconds);
        activeTimers.put(itemKey, activeMseconds);
    }

    public static void secondElapsed(long mseconds) {
        itemTimers.replaceAll((key, value) -> value > 0 ? value - mseconds : 0);
        activeTimers.replaceAll((key, value) -> value > 0 ? value - mseconds : 0);
    }

    public static long getRemainingTime(ItemStack stack) {
        if (itemTimers.get(stack.getName().getString()) != null) {
            return itemTimers.get(stack.getName().getString());
        }
        return 0;
    }

    public static boolean isActive(ItemStack stack) {
        return activeTimers.get(stack.getName().getString()) > 0;
    }

    public static boolean isCountingDown(ItemStack stack) {
        if (stack.getName() == null) {
            return false;
        }
        return itemTimers.containsKey(stack.getName().getString())
                && itemTimers.get(stack.getName().getString()) > 0;
    }
}