package com.cursee.stackable_stew_and_soup.mixin;

import com.cursee.stackable_stew_and_soup.MixinConfigForge;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public class ForgeItemsMixin {

    // redirects are targeting class initialization, also known as the static initializer

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args= {"stringValue=rabbit_stew"}, ordinal = 0)), at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/BowlFoodItem;", ordinal = 0))
    private static BowlFoodItem redirect_$_rabbitStew(Item.Properties p_40682_) {
        return new BowlFoodItem((new Item.Properties()).stacksTo(Math.toIntExact(MixinConfigForge.STACKABLE_AMOUNT)).food(Foods.RABBIT_STEW));
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args= {"stringValue=mushroom_stew"}, ordinal = 0)), at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/BowlFoodItem;", ordinal = 0))
    private static BowlFoodItem redirect_$_mushroomStew(Item.Properties p_40682_) {
        return new BowlFoodItem((new Item.Properties()).stacksTo(Math.toIntExact(MixinConfigForge.STACKABLE_AMOUNT)).food(Foods.MUSHROOM_STEW));
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args= {"stringValue=beetroot_soup"}, ordinal = 0)), at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/BowlFoodItem;", ordinal = 0))
    private static BowlFoodItem redirect_$_beetrootSoup(Item.Properties p_40682_) {
        return new BowlFoodItem((new Item.Properties()).stacksTo(Math.toIntExact(MixinConfigForge.STACKABLE_AMOUNT)).food(Foods.BEETROOT_SOUP));
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args= {"stringValue=suspicious_stew"}, ordinal = 0)), at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/SuspiciousStewItem;", ordinal = 0))
    private static SuspiciousStewItem redirect_$_suspiciousStew(Item.Properties p_40682_) {
        return new SuspiciousStewItem((new Item.Properties()).stacksTo(Math.toIntExact(MixinConfigForge.STACKABLE_AMOUNT)).food(Foods.SUSPICIOUS_STEW));
    }
}
