package com.rha1117.freedomofinsomnia.mixin;

import com.rha1117.freedomofinsomnia.CommandMixinInterface;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements CommandMixinInterface {
	@Unique
	private boolean insomniaDisabled;

	@Override
	public void freedom_of_insomnia$setInsomniaDisabled(boolean disabled) {
		insomniaDisabled = disabled;
	}

	@Override
	public boolean freedom_of_insomnia$getInsomniaDisabled() {
		return insomniaDisabled;
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
	public void writeCustomDataToNbt (NbtCompound tag, CallbackInfo info) {
		tag.putBoolean("insomniaDisabled", insomniaDisabled);
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
	public void readCustomDataFromNbt(NbtCompound tag, CallbackInfo info) {
		// Returns false if the tag doesn't already exist.
		insomniaDisabled = tag.getBoolean("insomniaDisabled");
	}
}
