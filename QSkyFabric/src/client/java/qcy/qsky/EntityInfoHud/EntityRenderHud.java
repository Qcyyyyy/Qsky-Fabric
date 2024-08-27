package qcy.qsky.EntityInfoHud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.scoreboard.*;
import net.minecraft.text.Text;
import qcy.qsky.CommandCooldown.CommandCD;
import qcy.qsky.QskyGUI.RenderQskyGUI;
import qcy.qsky.QskyClient;

import java.util.*;
import java.util.stream.Collectors;

import static qcy.qsky.QskyClient.*;
import static qcy.qsky.QskyGUI.RenderQskyGUI.*;

public class EntityRenderHud implements HudRenderCallback {

    private double percent;
    private double diff = 0;
    private long lastUpdateTime;
    private long timeNeeded = -1;
    private int lastXP = 0;
    private int xph = -1;
    private int xpGained = 0;
    private long timeLapsed = 0;
    private long lastXPHTime = System.currentTimeMillis();

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && RenderQskyGUI.entityHudMod.modEnabled()) {
            int screenWidth = drawContext.getScaledWindowWidth();
            int screenHeight = drawContext.getScaledWindowHeight();
            String hudText = client.worldRenderer.getEntitiesDebugString().split(" ")[1].replace(",", "");
            int textWidth = client.textRenderer.getWidth(hudText);
            drawContext.drawText(client.textRenderer, hudText, (screenWidth/2 - textWidth/2) + config.entityHudX, (screenHeight/2 + 10) + config.entityHudY, 0xFFFFFFFF, true);
        }

        if (client.player != null && RenderQskyGUI.commandCooldownMod.modEnabled()) {

            ArrayList<String> cdList = new ArrayList<String>();

            int screenHeight = drawContext.getScaledWindowHeight();
            int numToRender = 0;

            int x = (2) + config.commandCooldownX;
            int y = ((screenHeight/2)) + 10 + config.commandCooldownY;

            long fixCD = QskyClient.chatEvList.fixCommand.getTimeLeft();
            long fixAllCD = QskyClient.chatEvList.fixallCommand.getTimeLeft();
            long healCD = QskyClient.chatEvList.healCommand.getTimeLeft();
            long feedCD = QskyClient.chatEvList.feedCommand.getTimeLeft();
            HashMap<String, Integer> colors = new HashMap<String, Integer>();

            String cdAdder;

            if (fixCD > 0) {
                cdAdder = "/fix: " + String.format("%d:%02d", fixCD / (60 * 1000), (fixCD % (60 * 1000))/1000);
                cdList.add(cdAdder);
                colors.put(cdAdder, 0x007EFF);
            }

            if (fixAllCD > 0) {
                cdAdder = "/fix all: " + String.format("%d:%02d", fixAllCD / (60 * 1000), (fixAllCD % (60 * 1000))/1000);
                cdList.add(cdAdder);
                colors.put(cdAdder, 0x007EFF);
            }

            if (healCD > 0) {
                cdAdder = "/heal: " + String.format("%d:%02d", healCD / (60 * 1000), (healCD % (60 * 1000))/1000);
                cdList.add(cdAdder);
                colors.put(cdAdder, 0x50FF00);
            }

            if (feedCD > 0) {
                cdAdder = "/feed: " + String.format("%d:%02d", feedCD / (60 * 1000), (feedCD % (60 * 1000))/1000);
                cdList.add(cdAdder);
                colors.put(cdAdder, 0xFF1493);
            }
            if (!cdList.isEmpty()) {
                drawContext.fill(x, y, x + 75, y + 10 + cdList.size()*10, 0x80000000);
            }
            int yInc = 0;
            for (String cd: cdList) {
                drawContext.drawText(client.textRenderer, cd, x + 5, y + 5 + yInc, colors.get(cd), true);
                yInc += 10;
            }

        }

        boolean onIsland = client.world.getRegistryKey().getValue().toString().contains("minecraft:skyblock_world");

        if (client.player != null && islandInformationMod.modEnabled() && onIsland) {
            int screenHeight = drawContext.getScaledWindowHeight();
            Scoreboard scoreboard = client.world.getScoreboard();
            ScoreboardObjective objective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);

            if (objective != null) {
                String hudText = "";

                List<ScoreboardEntry> scs = scoreboard.getScoreboardEntries(objective).stream().filter(score -> !score.hidden()).toList();


                double newPercent = -1;
                for (int i = 0; i < scs.size(); i++) {
                    Team team = scoreboard.getScoreHolderTeam(scs.get(i).owner());
                    Text text = scs.get(i).name();
                    Text text2 = Team.decorateName(team, text);
                    if (text2.getString().contains("%")) {
                        newPercent = Double.parseDouble(text2.getString().split(" ")[0].replace("%", ""));
                    }
                }

                if (System.currentTimeMillis() - lastXPHTime > 3000) {

                    xpGained = client.player.totalExperience - lastXP;
                    xph = (int) (((xpGained)/((System.currentTimeMillis() - lastXPHTime)/1000))*3600);
                    lastXP = client.player.totalExperience;

                    lastXPHTime = System.currentTimeMillis();
                }

                if (newPercent != percent) {
                    long currentTime = System.currentTimeMillis();
                    diff = newPercent - percent;
                    percent = newPercent;

                    double remaining = 100-newPercent;
                    double instancesRem = remaining/diff;
                    timeLapsed = currentTime - lastUpdateTime;
                    timeNeeded = (long) instancesRem*timeLapsed;
                    lastUpdateTime = currentTime;
                }

                int x = (2) + config.islandInfoX;
                int y = (screenHeight/2-50) + config.islandInfoY;

                drawContext.fill(x, y, x + 140, y + 50, 0x80000000);

                drawContext.drawText(client.textRenderer,"Qsky Island Info", x + 5, y + 5, 0xFF1493, true);
                drawContext.drawText(client.textRenderer,"ETA LVL: " + formatTime(timeNeeded), x + 5, y + 15, 0xFFFFFF, true);
                drawContext.drawText(client.textRenderer,"Player XP: " + client.player.totalExperience, x + 5, y + 25, 0xFFFFFF, true);
                drawContext.drawText(client.textRenderer,"Player XP/h: " + xph, x + 5, y + 35, 0xFFFFFF, true);

            }
        }

    }

    public static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        StringBuilder timeString = new StringBuilder();
        if (days > 0) {
            timeString.append(days).append("d ");
        }
        if (hours > 0 || days > 0) {
            timeString.append(hours).append("h ");
        }
        if ((minutes > 0 || hours > 0) && days < 1) {
            timeString.append(minutes).append("m ");
        }
        if (days < 1) {
            timeString.append(seconds).append("s");
        }

        // Remove trailing comma and space if they exist
        if (timeString.length() > 2 && timeString.substring(timeString.length() - 2).equals(", ")) {
            timeString.setLength(timeString.length() - 2);
        }

        return timeString.toString();
    }


}
