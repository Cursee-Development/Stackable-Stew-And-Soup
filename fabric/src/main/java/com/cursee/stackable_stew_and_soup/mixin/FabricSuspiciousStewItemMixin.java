package com.cursee.stackable_stew_and_soup.mixin;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(SuspiciousStewItem.class)
public class FabricSuspiciousStewItemMixin {

    @Inject(method = "finishUsingItem", at = @At("HEAD"), cancellable = true)
    public void finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {

        // only operate on the server
        if (level.isClientSide()) return;

        // consume item event
        if (livingEntity instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, itemStack);
        }

        // apply effects and set return item after using or stack empty
        if (itemStack.isEmpty()) {
            cir.setReturnValue(new ItemStack(Items.BOWL));
        }
        else {
            if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {

                livingEntity.eat(level, itemStack);

                FabricSuspiciousStewItemMixin.listPotionEffects(itemStack, livingEntity::addEffect);

                ItemStack itemstack = new ItemStack(Items.BOWL);

                if (!player.getInventory().add(itemstack)) {
                    player.drop(itemstack, false);
                }
            }

            cir.setReturnValue(itemStack);
        }
    }

    private static void listPotionEffects(ItemStack itemStack, Consumer<MobEffectInstance> mobEffectConsumer) {

        CompoundTag itemStackTag = itemStack.getTag();

        if (itemStackTag != null && itemStackTag.contains("Effects", 9)) {

            ListTag effects = itemStackTag.getList("Effects", 10);

            for(int effect = 0; effect < effects.size(); ++effect) {

                CompoundTag effectCompound = effects.getCompound(effect);

                int effectDuration;
                if (effectCompound.contains("EffectDuration", 99)) {
                    effectDuration = effectCompound.getInt("EffectDuration");
                }
                else {
                    effectDuration = 160;
                }

                MobEffect mobEffect = MobEffect.byId(effectCompound.getInt("EffectId"));
                if (mobEffect != null) {
                    mobEffectConsumer.accept(new MobEffectInstance(mobEffect, effectDuration));
                }
            }
        }
    }
}
