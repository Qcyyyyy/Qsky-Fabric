package qcy.qsky.CommandCooldown;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import qcy.qsky.QskyClient;
import qcy.qsky.QskyConfig;

public class CommandCD {

    public static int rank = -1;
    private String command;
    private long timeLeft;

    public CommandCD(String command) {
        this.command = command;

    }

    public String getCommand() {
        return this.command;
    }

    public long getTimeLeft() {
        return this.timeLeft;
    }

    public void secondElapsed(long mseconds) {
        this.timeLeft -= mseconds;
    }

    public void startCD() {
        if (this.command.equals("fix")) {
            if (QskyClient.config.rank == 0) {
                this.timeLeft = 0;
            } else if (QskyClient.config.rank == 1) {
                this.timeLeft = 0;
            } else if (QskyClient.config.rank == 2) {
                this.timeLeft = 10*60*1000;
            } else if (QskyClient.config.rank == 3) {
                this.timeLeft = 2*60*1000;
            } else {
                MinecraftClient.getInstance().player.sendMessage(Text.of("[Qsky]: Rank not set! Use /qskysetrank #"), false);
            }
        } else if (this.command.equals("fix all")) {
            if (QskyClient.config.rank == 0) {
                this.timeLeft = 0;
            } else if (QskyClient.config.rank == 1) {
                this.timeLeft = 0;
            } else if (QskyClient.config.rank == 2) {
                this.timeLeft = 0;
            } else if (QskyClient.config.rank == 3) {
                this.timeLeft = 2*60*1000;
            } else {
                MinecraftClient.getInstance().player.sendMessage(Text.of("[Qsky]: Rank not set! Use /qskysetrank #"), false);
            }
        } else if (this.command.equals("feed")) {
            if (QskyClient.config.rank == 0) {
                this.timeLeft = 0;
            } else if (QskyClient.config.rank == 1) {
                this.timeLeft = 0;
            } else if (QskyClient.config.rank == 2) {
                this.timeLeft = 10*60*1000;
            } else if (QskyClient.config.rank == 3) {
                this.timeLeft = 5*60*1000;
            } else {
                MinecraftClient.getInstance().player.sendMessage(Text.of("[Qsky]: Rank not set! Use /qskysetrank #"), false);
            }
        } else if (this.command.equals("heal")) {
            if (QskyClient.config.rank == 0) {
                this.timeLeft = 0;
            } else if (QskyClient.config.rank == 1) {
                this.timeLeft = 0;
            } else if (QskyClient.config.rank == 2) {
                this.timeLeft = 10*60*1000;
            } else if (QskyClient.config.rank == 3) {
                this.timeLeft = 5*60*1000;
            } else {
                MinecraftClient.getInstance().player.sendMessage(Text.of("[Qsky]: Rank not set! Use /qskysetrank #"), false);
            }
        }
    }
}
