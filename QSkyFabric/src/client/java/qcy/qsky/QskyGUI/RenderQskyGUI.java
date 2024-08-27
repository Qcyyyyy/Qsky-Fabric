package qcy.qsky.QskyGUI;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import qcy.qsky.ModManager;

@Environment(EnvType.CLIENT)
public class RenderQskyGUI extends Screen {

    public static ModManager entityHudMod = new ModManager("Entity Hud Mod", true, true, 0, 0);
    ButtonWidget entityHudButton = entityHudMod.getButton(0);

    public static ModManager petCooldownMod = new ModManager("Pet Cooldown Mod", true, false, 0, 0);
    ButtonWidget petCooldownButton = petCooldownMod.getButton(1);

    public static ModManager islandInformationMod = new ModManager("Island Info Mod", true, true, 0, 0);
    ButtonWidget IslandInformationButton = islandInformationMod.getButton(2);

    public static ModManager commandCooldownMod = new ModManager("Command Cooldown Mod", true, true, 0, 0);
    ButtonWidget commandCooldownButton = commandCooldownMod.getButton(3);

    public RenderQskyGUI(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        this.addDrawableChild(entityHudButton);
        this.addDrawableChild(petCooldownButton);
        this.addDrawableChild(IslandInformationButton);
        this.addDrawableChild(commandCooldownButton);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

}
