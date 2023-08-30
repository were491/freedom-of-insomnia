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

public class InsomniaCommand implements ModInitializer {
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			LiteralCommandNode<ServerCommandSource> insomniaNode = CommandManager
					.literal("insomnia")
				        .build();

			LiteralCommandNode<ServerCommandSource> enableNode = CommandManager
					.literal("enable")
				        .executes(this::enableInsomnia)
				        .build();

			LiteralCommandNode<ServerCommandSource> disableNode = CommandManager
					.literal("disable")
				        .executes(this::disableInsomnia)
				        .build();

			LiteralCommandNode<ServerCommandSource> statusNode = CommandManager
					.literal("status")
				        .executes(this::displayStatus)
				        .build();

			dispatcher.getRoot().addChild(insomniaNode);
			insomniaNode.addChild(enableNode);
			insomniaNode.addChild(disableNode);
			insomniaNode.addChild(statusNode);
		});

		// Register an event to persist insomnia status past death.
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) ->
				((CommandMixinInterface) newPlayer).freedom_of_insomnia$setInsomniaDisabled( ((CommandMixinInterface) oldPlayer).freedom_of_insomnia$getInsomniaDisabled() ));
	}

	private int displayStatus(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		if (((CommandMixinInterface) context.getSource().getPlayerOrThrow()).freedom_of_insomnia$getInsomniaDisabled()) {
			context.getSource().sendFeedback(() -> Text.literal("Insomnia is currently disabled.").formatted(Formatting.RED), false);
		} else {
			context.getSource().sendFeedback(() -> Text.literal("Insomnia is currently enabled.").formatted(Formatting.GREEN), false);
		}
		return 0;
	}

	private int enableInsomnia(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		((CommandMixinInterface) context.getSource().getPlayerOrThrow()).freedom_of_insomnia$setInsomniaDisabled(false);
		context.getSource().sendFeedback(() -> Text.literal("Insomnia is now enabled.").formatted(Formatting.GREEN), false);
		return 0;
	}

	private int disableInsomnia(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		((CommandMixinInterface) context.getSource().getPlayerOrThrow()).freedom_of_insomnia$setInsomniaDisabled(true);
		context.getSource().sendFeedback(() -> Text.literal("Insomnia is now disabled.").formatted(Formatting.RED), false);
		return 0;
	}
}
