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
	@ModifyVariable(method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", at = @At("STORE"), index = 7)
	private PlayerEntity onLoop(PlayerEntity playerEntity) {
		player = playerEntity;
		return playerEntity;
	}
	
	@ModifyVariable(method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", at = @At("STORE"), index = 11)
	private int onSpawn(int j) {
		if (((CommandMixinInterface) (ServerPlayerEntity) player).getInsomniaDisabled()) {
			// Returning 1 (one) prevents phantom spawns by causing a random chance to always fail and skip spawning for this player.
			return 1;
		} else {
			return j;
		}
	}
}