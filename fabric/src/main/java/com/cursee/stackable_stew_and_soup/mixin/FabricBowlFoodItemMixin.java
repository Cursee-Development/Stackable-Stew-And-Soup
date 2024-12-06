package com.cursee.stackable_stew_and_soup.mixin;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowlFoodItem.class)
public class FabricBowlFoodItemMixin {

    @Inject(method = "finishUsingItem", at = @At("HEAD"), cancellable = true)
    public void finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {

        // only operate on the server
        if (level.isClientSide()) return;

        // consume item event
        if (livingEntity instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, itemStack);
        }

        // set return item if stack is empty
        if (itemStack.isEmpty()) {
            cir.setReturnValue(new ItemStack(Items.BOWL));
            return;
        }

        // eat item and drop new bowl stack
        if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {

            livingEntity.eat(level, itemStack);

            ItemStack itemstack = new ItemStack(Items.BOWL);

            if (!player.getInventory().add(itemstack)) {
                player.drop(itemstack, false);
            }
        }

        cir.setReturnValue(itemStack);
    }
}
