package org.lithereal.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.block.custom.FireCrucibleBlock;
import org.lithereal.block.custom.LitherCollectorBlock;
import org.lithereal.block.custom.InfusedLitheriteBlock;
import org.lithereal.block.custom.InfusementChamberBlock;
import org.lithereal.block.entity.InfusedLitheriteBlockEntity;
import org.lithereal.block.entity.LitherCollectorBlockEntity;
import org.lithereal.forge.block.ForgeBlocks;
import org.lithereal.forge.block.entity.ForgeBlockEntities;
import org.lithereal.forge.block.entity.ForgeFireCrucibleBlockEntity;
import org.lithereal.forge.block.entity.ForgeFreezingStationBlockEntity;
import org.lithereal.forge.block.entity.ForgeInfusementChamberBlockEntity;
import org.lithereal.forge.compat.CombatifyHooks;
import org.lithereal.forge.item.ForgeItems;
import org.lithereal.forge.item.custom.ForgeWarHammer;
import org.lithereal.forge.screen.ForgeFireCrucibleMenu;
import org.lithereal.forge.screen.ForgeFreezingStationMenu;
import org.lithereal.forge.screen.ForgeInfusementChamberMenu;
import org.lithereal.forge.screen.ForgeMenuTypes;
import org.lithereal.item.custom.Ability;
import org.lithereal.item.custom.WarHammer;
import org.lithereal.screen.LitherCollectorMenu;

import java.nio.file.Path;
import java.util.List;

public class LitherealExpectPlatformImpl {
    /**
     * This is our actual method to {@link LitherealExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static InfusedLitheriteBlock getInfusedLitheriteBlock() {
        return (InfusedLitheriteBlock) ForgeBlocks.INFUSED_LITHERITE_BLOCK.get();
    }

    public static BlockEntityType<InfusedLitheriteBlockEntity> getInfusedLitheriteBlockEntity() {
        return ForgeBlockEntities.INFUSED_LITHERITE_BLOCK.get();
    }

    public static BlockEntityType<ForgeFireCrucibleBlockEntity> getFireCrucibleBlockEntity() {
        return ForgeBlockEntities.FIRE_CRUCIBLE.get();
    }

    public static BlockEntityTicker<ForgeFireCrucibleBlockEntity> getFireCrucibleBlockEntityTicker() {
        return ForgeFireCrucibleBlockEntity::tick;
    }

    public static FireCrucibleBlock getFireCrucibleBlock() {
        return (FireCrucibleBlock) ForgeBlocks.FIRE_CRUCIBLE.get();
    }

    public static MenuType<ForgeFireCrucibleMenu> getFireCrucibleMenu() {
        return ForgeMenuTypes.FIRE_CRUCIBLE_MENU.get();
    }

    public static BlockEntityType<ForgeFreezingStationBlockEntity> getFreezingStationBlockEntity() {
        return ForgeBlockEntities.FREEZING_STATION.get();
    }

    public static BlockEntityTicker<ForgeFreezingStationBlockEntity> getFreezingStationBlockEntityTicker() {
        return ForgeFreezingStationBlockEntity::tick;
    }

    public static MenuType<ForgeFreezingStationMenu> getFreezingStationMenu() {
        return ForgeMenuTypes.FREEZING_STATION_MENU.get();
    }

    public static LitherCollectorBlock getFreezingStationBlock() {
        return (LitherCollectorBlock) ForgeBlocks.FREEZING_STATION.get();
    }

    public static BlockEntityType<ForgeInfusementChamberBlockEntity> getInfusementChamberBlockEntity() {
        return ForgeBlockEntities.INFUSEMENT_CHAMBER.get();
    }

    public static BlockEntityTicker<ForgeInfusementChamberBlockEntity> getInfusementChamberBlockEntityTicker() {
        return ForgeInfusementChamberBlockEntity::tick;
    }

    public static InfusementChamberBlock getInfusementChamberBlock() {
        return (InfusementChamberBlock) ForgeBlocks.INFUSEMENT_CHAMBER.get();
    }

    public static MenuType<ForgeInfusementChamberMenu> getInfusementChamberMenu() {
        return ForgeMenuTypes.INFUSEMENT_CHAMBER_MENU.get();
    }

    public static Item getLitheriteItem() {
        return ForgeItems.LITHERITE_CRYSTAL.get();
    }

    public static ResourceLocation getResourceLocation(ItemStack stack) {
        return ForgeRegistries.ITEMS.getKey(stack.getItem());
    }

    public static BlockEntityType<LitherCollectorBlockEntity> getLitherCollectorBlockEntity() {
        return null;
    }

    public static BlockEntityTicker<LitherCollectorBlockEntity> getLitherCollectorBlockEntityTicker() {
        return null;
    }

    public static LitherCollectorBlock getLitherCollectorBlock() {
        return null;
    }

    public static MenuType<LitherCollectorMenu> getLitherCollectorMenu() {
        return null;
    }

    public static Iterable<Potion> getRegisteredPotions() {
        return ForgeRegistries.POTIONS;
    }

    public static void applyKnockbackToNearbyEntities(Player player, LivingEntity target, float strength) {
        boolean combatify = ModList.get().isLoaded("combatify");
        double radius = !combatify ? 3.0 : CombatifyHooks.getCurrentAttackReach(player, 1);
        float x = Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
        float z = -Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
        if (!combatify)
            target.knockback(strength, x, z);
        else
            CombatifyHooks.knockback(target, strength, x, z);
        List<LivingEntity> nearbyEntities = player.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(radius));
        nearbyEntities.remove(target);

        int affectedEntities = 0;

        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (!nearbyEntity.isFallFlying() && Math.abs(nearbyEntity.getY() - target.getY()) < 0.1) {
                if (nearbyEntity instanceof Player player1 && player1.isBlocking()) {
                    if (combatify)
                        CombatifyHooks.disableShield(player, 5);
                    else if (!nearbyEntity.isCrouching()) {
                        player1.getCooldowns().addCooldown(player1.getUseItem().getItem(), 100);
                        player1.stopUsingItem();
                        player1.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + player1.level().random.nextFloat() * 0.4F);
                    }
                    player1.level().broadcastEntityEvent(player1, (byte)30);
                }
                if (!combatify)
                    nearbyEntity.knockback(strength, x, z);
                else
                    CombatifyHooks.knockback(nearbyEntity, strength, x, z);
                affectedEntities++;

                if (affectedEntities >= 4) break;
            }
        }
    }
    public static WarHammer createWarHammer(Tier tier, int damage, float speed, Item.Properties properties) {
        return new ForgeWarHammer(tier, damage, speed, properties);
    }

    public static Item createLongsword(Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateLongsword(tier, properties);
    }

    public static Item createKnife(Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateKnife(tier, properties);
    }

    public static Item createAbilityLongsword(Ability ability, Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateAbilityLongsword(ability, tier, properties);
    }

    public static Item createAbilityKnife(Ability ability, Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateAbilityKnife(ability, tier, properties);
    }

    public static Item createInfusedLongsword(Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateInfusedLongsword(tier, properties);
    }

    public static Item createInfusedKnife(Tier tier, Item.Properties properties) {
        return CombatifyHooks.generateInfusedKnife(tier, properties);
    }
}