package com.rha1117.freedomofinsomnia;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class InsomniaCommand implements ModInitializer {
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			LiteralCommandNode<ServerCommandSource> insomniaNode = CommandManager
					.literal("insomnia")
				        .build();
			
			LiteralCommandNode<ServerCommandSource> enableNode = CommandManager
					.literal("enable")
				        .executes(context -> enableInsomnia(context))
				        .build();
			
			LiteralCommandNode<ServerCommandSource> disableNode = CommandManager
					.literal("disable")
				        .executes(context -> disableInsomnia(context))
				        .build();
			
			LiteralCommandNode<ServerCommandSource> statusNode = CommandManager
					.literal("status")
				        .executes(context -> displayStatus(context))
				        .build();
			
			dispatcher.getRoot().addChild(insomniaNode);
			insomniaNode.addChild(enableNode);
			insomniaNode.addChild(disableNode);
			insomniaNode.addChild(statusNode);
		});
		
	}

	private int displayStatus(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		if (((CommandMixinInterface) context.getSource().getPlayer()).getInsomniaDisabled()) {
			context.getSource().sendFeedback(new LiteralText("Insomnia is currently disabled.").formatted(Formatting.RED), false);
			return 0;
		} else {
			context.getSource().sendFeedback(new LiteralText("Insomnia is currently enabled.").formatted(Formatting.GREEN), false);
			return 1;
		}
	}
	
	private int enableInsomnia(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		((CommandMixinInterface) context.getSource().getPlayer()).setInsomniaDisabled(false);
		context.getSource().sendFeedback(new LiteralText("Insomnia is now enabled.").formatted(Formatting.GREEN), false);
		return 1;
	}
	
	private int disableInsomnia(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		((CommandMixinInterface) context.getSource().getPlayer()).setInsomniaDisabled(true);
		context.getSource().sendFeedback(new LiteralText("Insomnia is now disabled.").formatted(Formatting.RED), false);
		return 1;
	}
}
