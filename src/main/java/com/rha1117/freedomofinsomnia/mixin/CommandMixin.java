package com.rha1117.freedomofinsomnia.mixin;

import net.minecraft.network.encryption.PlayerPublicKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.rha1117.freedomofinsomnia.CommandMixinInterface;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public abstract class CommandMixin extends PlayerEntity implements CommandMixinInterface {
	public CommandMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
		super(world, pos, yaw, gameProfile, publicKey);
	}
	
	private boolean insomniaDisabled;

	public void setInsomniaDisabled(boolean disabled) {
		insomniaDisabled = disabled;
	}
	
	public boolean getInsomniaDisabled() {
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