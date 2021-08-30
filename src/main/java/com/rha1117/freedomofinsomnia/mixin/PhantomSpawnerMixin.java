package com.rha1117.freedomofinsomnia.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.rha1117.freedomofinsomnia.CommandMixinInterface;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.gen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
	private PlayerEntity player;
	
	// This is just to access the playerEntity variable.
	// There probably is a better way to do this but idk what that is.
	/*
	 * Old declaration, but ordinal seems to be broken on Sponge right now so I need to use index (see the .class file's local variable table)
	 * What would happen is it would select all instances of that variable type, for no reason.
	 * @ModifyVariable(method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", at = @At("STORE"), ordinal = 0)
	*/
	@ModifyVariable(method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", at = @At("STORE"), index = 7)
	private PlayerEntity onLoop(PlayerEntity playerEntity) {
//		System.out.println("Insomnia spawning for player " + playerEntity.getName()); Debug message, ignore this line if reading code
		player = playerEntity;
		return playerEntity;
	}
	
	// Old declaration, but ordinal seems to be broken on Sponge right now (see above).
	//@ModifyVariable(method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", at = @At("STORE"), ordinal = 1)
	@ModifyVariable(method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", at = @At("STORE"), index = 11)
	private int onSpawn(int j) {
		if (((CommandMixinInterface) (ServerPlayerEntity) player).getInsomniaEnabled()) {
//			System.out.println("Insomnia spawning was NOT canceled."); Debug message, ignore this line if reading code
//			System.out.println("The value of time since sleeping is " + j); Debug message, ignore this line if reading code
			return j;
		} else {
//			System.out.println("Insomnia spawning was canceled."); Debug message, ignore this line if reading code
			return 1; // Returning 1 (one) prevents phantom spawns by causing a random chance to always fail and skip spawning for this player.
		}
	}
}