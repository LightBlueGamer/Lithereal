package org.litecraft.lithereal.item;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.block.ModBlocks;

@Mod.EventBusSubscriber(modid = Lithereal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            Lithereal.MOD_ID);

    public static RegistryObject<CreativeModeTab> LITHEREAL_TAB = CREATIVE_MODE_TABS.register("lithereal_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.LITHERITE_CRYSTAL.get()))
                    .title(Component.translatable("creativemodetab.lithereal_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.LITHERITE_BLOCK.get());
                        output.accept(ModItems.LITHERITE_CRYSTAL.get());
                        output.accept(ModItems.LITHERITE_SWORD.get());
                        output.accept(ModItems.LITHERITE_SHOVEL.get());
                        output.accept(ModItems.LITHERITE_PICKAXE.get());
                        output.accept(ModItems.LITHERITE_AXE.get());
                        output.accept(ModItems.LITHERITE_HOE.get());
                        output.accept(ModItems.LITHERITE_HAMMER.get());
                        output.accept(ModItems.LITHERITE_HELMET.get());
                        output.accept(ModItems.LITHERITE_CHESTPLATE.get());
                        output.accept(ModItems.LITHERITE_LEGGINGS.get());
                        output.accept(ModItems.LITHERITE_BOOTS.get());
                        output.accept(ModItems.LITHERITE_WRENCH.get());

                        output.accept(ModBlocks.HEATED_LITHERITE_BLOCK.get());
                        output.accept(ModItems.HEATED_LITHERITE_CRYSTAL.get());
                        output.accept(ModItems.HEATED_LITHERITE_SWORD.get());
                        output.accept(ModItems.HEATED_LITHERITE_SHOVEL.get());
                        output.accept(ModItems.HEATED_LITHERITE_PICKAXE.get());
                        output.accept(ModItems.HEATED_LITHERITE_AXE.get());
                        output.accept(ModItems.HEATED_LITHERITE_HOE.get());
                        output.accept(ModItems.HEATED_LITHERITE_HAMMER.get());
                        output.accept(ModItems.HEATED_LITHERITE_HELMET.get());
                        output.accept(ModItems.HEATED_LITHERITE_CHESTPLATE.get());
                        output.accept(ModItems.HEATED_LITHERITE_LEGGINGS.get());
                        output.accept(ModItems.HEATED_LITHERITE_BOOTS.get());

                        output.accept(ModBlocks.COOLED_LITHERITE_BLOCK.get());
                        output.accept(ModItems.COOLED_LITHERITE_CRYSTAL.get());
                        output.accept(ModItems.COOLED_LITHERITE_SWORD.get());
                        output.accept(ModItems.COOLED_LITHERITE_SHOVEL.get());
                        output.accept(ModItems.COOLED_LITHERITE_PICKAXE.get());
                        output.accept(ModItems.COOLED_LITHERITE_AXE.get());
                        output.accept(ModItems.COOLED_LITHERITE_HOE.get());
                        output.accept(ModItems.COOLED_LITHERITE_HAMMER.get());
                        output.accept(ModItems.COOLED_LITHERITE_HELMET.get());
                        output.accept(ModItems.COOLED_LITHERITE_CHESTPLATE.get());
                        output.accept(ModItems.COOLED_LITHERITE_LEGGINGS.get());
                        output.accept(ModItems.COOLED_LITHERITE_BOOTS.get());

                        output.accept(ModBlocks.WITHERING_LITHERITE_BLOCK.get());
                        output.accept(ModItems.WITHERING_LITHERITE_CRYSTAL.get());
                        output.accept(ModItems.WITHERING_LITHERITE_SWORD.get());
                        output.accept(ModItems.WITHERING_LITHERITE_SHOVEL.get());
                        output.accept(ModItems.WITHERING_LITHERITE_PICKAXE.get());
                        output.accept(ModItems.WITHERING_LITHERITE_AXE.get());
                        output.accept(ModItems.WITHERING_LITHERITE_HOE.get());
                        output.accept(ModItems.WITHERING_LITHERITE_HAMMER.get());
                        output.accept(ModItems.WITHERING_LITHERITE_HELMET.get());
                        output.accept(ModItems.WITHERING_LITHERITE_CHESTPLATE.get());
                        output.accept(ModItems.WITHERING_LITHERITE_LEGGINGS.get());
                        output.accept(ModItems.WITHERING_LITHERITE_BOOTS.get());

                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModBlocks.INFUSED_LITHERITE_BLOCK.get().asItem(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_CRYSTAL.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_SWORD.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_SHOVEL.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_PICKAXE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_AXE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_HOE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_HAMMER.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_HELMET.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_CHESTPLATE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_LEGGINGS.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                        displayParameters.holders().lookup(Registries.POTION).ifPresent((p_270032_) -> generatePotionEffectTypes(output, p_270032_, ModItems.INFUSED_LITHERITE_BOOTS.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));

                        output.accept(ModBlocks.LITHERITE_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_LITHERITE_ORE.get());

//                        output.accept(ModBlocks.INFUSEMENT_CHAMBER_CASING.get());
//                        output.accept(ModBlocks.INFUSEMENT_CHAMBER_CONTROLLER.get());
//                        output.accept(ModBlocks.INFUSEMENT_CHAMBER_CORE.get());
                        output.accept(ModBlocks.INFUSEMENT_CHAMBER.get());

                        output.accept(ModBlocks.FREEZING_STATION.get());
                        output.accept(ModBlocks.FIRE_CRUCIBLE.get());
                    })
                    .build());
    private static void generatePotionEffectTypes(CreativeModeTab.Output p_270129_, HolderLookup<Potion> p_270334_, Item p_270968_, CreativeModeTab.TabVisibility p_270778_) {
        p_270334_.listElements().filter((p_270012_) -> !p_270012_.is(Potions.EMPTY_ID)).map(
                (p_269986_) -> PotionUtils.setPotion(new ItemStack(p_270968_), p_269986_.value()))
                .forEach((p_270000_) -> p_270129_.accept(p_270000_, p_270778_));
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}