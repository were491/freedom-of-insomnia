package com.rha1117.freedomofinsomnia.mixin;

import com.rha1117.freedomofinsomnia.CommandMixinInterface;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$FindTargetGoal")
public class PhantomTargetMixin {

    @ModifyVariable(method = "canStart()Z", at = @At("STORE"), index = 1)
    private List<PlayerEntity> modifyTargetList(List<PlayerEntity> value){

        value.removeIf(player -> ((CommandMixinInterface) player).freedom_of_insomnia$getInsomniaDisabled());

        return value;
    }

}