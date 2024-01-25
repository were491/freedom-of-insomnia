package com.rha1117.freedomofinsomnia;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class FreedomOfInsomnia implements ModInitializer {
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			LiteralCommandNode<ServerCommandSource> insomniaNode = CommandManager
					.literal("insomnia")
						.executes(this::displayStatus)
				        .build();

			LiteralCommandNode<ServerCommandSource> toggleNode = CommandManager
					.literal("toggle")
				        .executes(this::toggleInsomnia)
				        .build();

			dispatcher.getRoot().addChild(insomniaNode);
			insomniaNode.addChild(toggleNode);
		});

		// Register an event to persist insomnia status past death.
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> ((CommandMixinInterface) newPlayer).freedom_of_insomnia$setInsomniaDisabled(((CommandMixinInterface) oldPlayer).freedom_of_insomnia$getInsomniaDisabled()));
	}

	private int displayStatus(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		boolean insomniaDisabled = ((CommandMixinInterface) context.getSource().getPlayerOrThrow()).freedom_of_insomnia$getInsomniaDisabled();

		context.getSource().sendFeedback(() -> Text.literal("Insomnia is currently ").append(insomniaDisabled ? Text.literal("disabled").formatted(Formatting.RED) : Text.literal("enabled").formatted(Formatting.GREEN)).append("."), false);

		return 0;
	}

	private int toggleInsomnia(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		CommandMixinInterface player = (CommandMixinInterface) context.getSource().getPlayerOrThrow();

		player.freedom_of_insomnia$setInsomniaDisabled(!player.freedom_of_insomnia$getInsomniaDisabled());
		context.getSource().sendFeedback(() -> Text.literal("Insomnia is now ").append(player.freedom_of_insomnia$getInsomniaDisabled() ? Text.literal("disabled").formatted(Formatting.RED) : Text.literal("enabled").formatted(Formatting.GREEN)).append(Text.literal(".")), false);

		return 0;
	}
}
