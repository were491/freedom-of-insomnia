package com.rha1117.freedomofinsomnia.mixin;

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
	public CommandMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}
	
	private boolean insomniaEnabled = true;
	
	public void setInsomniaEnabled(boolean enabled) {
		insomniaEnabled = enabled;
	}
	
	public boolean getInsomniaEnabled() {
		return insomniaEnabled;
	}
	
	@Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
	public void writeCustomDataToNbt (NbtCompound tag, CallbackInfo info) {
		tag.putBoolean("insomniaEnabled", insomniaEnabled);
	}
	
	@Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
	public void readCustomDataFromNbt(NbtCompound tag, CallbackInfo info) {
		insomniaEnabled = tag.getBoolean("insomniaEnabled");
	}
}