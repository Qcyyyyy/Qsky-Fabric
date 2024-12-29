package qcy.qsky;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import qcy.qsky.CommandCooldown.ChatEventListener;
import qcy.qsky.CommandCooldown.CommandCD;
import qcy.qsky.EntityInfoHud.EntityRenderHud;
import qcy.qsky.PetCooldown.CountdownManager;
import qcy.qsky.PetCooldown.RightClickListener;
import qcy.qsky.QskyGUI.RenderQskyGUI;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import static qcy.qsky.QskyGUI.RenderQskyGUI.*;

public class QskyClient implements ClientModInitializer {

	private static final int MILLISECONDS_PER_SECOND = 1000;
	private long lastUpdateTime = System.currentTimeMillis();
	public static ChatEventListener chatEvList = new ChatEventListener();
	public static String searchField = "";
	public static QskyConfig config = QskyConfig.load();
	public static CommandCD adrenalineCD = new CommandCD("");



	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new EntityRenderHud());
		RightClickListener.register();
		chatEvList.register();


		ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
			if (message.getString().startsWith("* ADRENALINE")) {
				MinecraftClient.getInstance().player.sendMessage(Text.literal("ADRENALINE triggered"));
				ChatEventListener.adrenRush.startCD();
			} else if (message.getString().startsWith("Fixer Felix:")) {
				ChatEventListener.fixerFelix.startCD();
			}
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("islandinfo")
					.then(ClientCommandManager.argument("horizontal", IntegerArgumentType.integer())
							.then(ClientCommandManager.argument("vertical", IntegerArgumentType.integer())
									.executes(context -> {
										int xValue = IntegerArgumentType.getInteger(context, "horizontal");
										int yValue = IntegerArgumentType.getInteger(context, "vertical");
										config.islandInfoX = xValue;
										config.islandInfoY = yValue;
										config.save();
										islandInformationMod.setX(xValue);
										islandInformationMod.setY(yValue);

										return 1;
									}))));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("entityhud")
					.then(ClientCommandManager.argument("horizontal", IntegerArgumentType.integer())
							.then(ClientCommandManager.argument("vertical", IntegerArgumentType.integer())
									.executes(context -> {
										int xValue = IntegerArgumentType.getInteger(context, "horizontal");
										int yValue = IntegerArgumentType.getInteger(context, "vertical");
										config.entityHudX = xValue;
										config.entityHudY = yValue;
										config.save();
										entityHudMod.setX(xValue);
										entityHudMod.setY(yValue);

										return 1;
									}))));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("commandcooldown")
					.then(ClientCommandManager.argument("horizontal", IntegerArgumentType.integer())
							.then(ClientCommandManager.argument("vertical", IntegerArgumentType.integer())
									.executes(context -> {
										int xValue = IntegerArgumentType.getInteger(context, "horizontal");
										int yValue = IntegerArgumentType.getInteger(context, "vertical");
										config.commandCooldownX = xValue;
										config.commandCooldownY = yValue;
										config.save();
										commandCooldownMod.setX(xValue);
										commandCooldownMod.setY(yValue);

										return 1;
									}))));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("qskysetrank")
					.then(ClientCommandManager.argument("Rank Number", IntegerArgumentType.integer())
									.executes(context -> {
										config.rank = IntegerArgumentType.getInteger(context, "Rank Number");
										config.save();
										CommandCD.rank = IntegerArgumentType.getInteger(context, "Rank Number");
										return 1;
									})));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("cs")
					.then(ClientCommandManager.argument("Search Field", StringArgumentType.greedyString())
							.executes(context -> {
								 searchField = StringArgumentType.getString(context, "Search Field");
								return 1;
							})));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("cs")
							.executes(context -> {
								searchField = "";
								return 1;
							}));
		});

        if (MinecraftClient.getInstance().player != null) {
			MinecraftClient.getInstance().player.getUuid();
		}

		QskyKeybinding.register();
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (QskyKeybinding.getQskyKeybinding().isPressed()) {
				MinecraftClient.getInstance().setScreen(new RenderQskyGUI(Text.literal("test")));
			}

			long currentTime = System.currentTimeMillis();
			long elapsedTime = currentTime - lastUpdateTime;

			if (elapsedTime >= MILLISECONDS_PER_SECOND/10) {
				lastUpdateTime = currentTime;
				CountdownManager.secondElapsed(elapsedTime);
				chatEvList.fixCommand.secondElapsed(elapsedTime);
				chatEvList.fixallCommand.secondElapsed(elapsedTime);
				chatEvList.healCommand.secondElapsed(elapsedTime);
				chatEvList.feedCommand.secondElapsed(elapsedTime);
				ChatEventListener.adrenRush.secondElapsed(elapsedTime);
				ChatEventListener.fixerFelix.secondElapsed(elapsedTime);
			}

		});
	}

}