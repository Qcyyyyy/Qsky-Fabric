package qcy.qsky;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import qcy.qsky.QskyGUI.RenderQskyGUI;

public class ModManager {

    private String modName;
    private boolean modEnabled;
    private boolean moveable;
    private int x;
    private int y;

    public ModManager(String modName, boolean defaultOn, boolean moveable, int x, int y) {
        this.modName = modName;
        this.modEnabled = defaultOn;
        this.moveable = moveable;
        this.x = x;
        this.y = y;
    }

    public boolean modEnabled() {
        return this.modEnabled;
    }

    public void setModEnabled(boolean newVal) {
        this.modEnabled = newVal;
    }

    public boolean isMoveable() {
        return this.moveable;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ButtonWidget getButton(int modNumber) {
        ButtonWidget modButton = ButtonWidget.builder(Text.of(this.modName), (btn) -> {
            String status = "(ON)";
            if (this.modEnabled()) {
                status = "(OFF)";
            }
            MinecraftClient.getInstance().player.sendMessage(Text.literal(modName + " " + status));
            this.setModEnabled(!modEnabled);
        }).dimensions(5, 5 + (25*modNumber), 120, 20).build();
        return modButton;
    }

}
