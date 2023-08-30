package com.rha1117.freedomofinsomnia.mixin;

import com.rha1117.freedomofinsomnia.CommandMixinInterface;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
	@Inject(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isSpectator()Z"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	public void spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir, Random random, int i, Iterator<ServerPlayerEntity> iterator, ServerPlayerEntity serverPlayerEntity) {
		if (((CommandMixinInterface) serverPlayerEntity).freedom_of_insomnia$getInsomniaDisabled()) {
			cir.setReturnValue(i);
		}
	}
}
