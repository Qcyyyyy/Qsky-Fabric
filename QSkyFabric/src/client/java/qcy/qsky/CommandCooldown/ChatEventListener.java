package qcy.qsky.CommandCooldown;

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatEventListener {

    public CommandCD fixCommand = new CommandCD("fix");
    public CommandCD fixallCommand = new CommandCD("fix all");
    public CommandCD healCommand = new CommandCD("heal");
    public CommandCD feedCommand = new CommandCD("feed");

    public void register() {
        ClientSendMessageEvents.ALLOW_COMMAND.register((message) -> {
            onChatMessage(message);
            return true;
        });
    }

    private void onChatMessage(String message) {
        // Your code here, executed when a chat message is sent
        if (message.equals(fixCommand.getCommand()) && fixCommand.getTimeLeft() <= 0) {
            fixCommand.startCD();
        } else if (message.equals(fixallCommand.getCommand()) && fixallCommand.getTimeLeft() <= 0) {
            fixallCommand.startCD();
        } else if (message.equals(healCommand.getCommand())&& healCommand.getTimeLeft() <= 0) {
            healCommand.startCD();
        } else if ((message.equals(feedCommand.getCommand()) || message.equals("eat")) && feedCommand.getTimeLeft() <= 0) {
            feedCommand.startCD();
        }

    }
}
