package org.lithereal.data.datagen;

import com.mojang.math.Quadrant;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.Potion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.lithereal.block.FireCrucibleBlock;
import org.lithereal.block.InfusementChamberBlock;
import org.lithereal.block.PureEtherealCrystalBlock;
import org.lithereal.block.entity.FireCrucibleBlockEntity;

import java.util.function.Supplier;

import static net.minecraft.client.data.models.BlockModelGenerators.ROTATION_HORIZONTAL_FACING;
import static net.minecraft.client.data.models.BlockModelGenerators.ROTATION_TORCH;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_180;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_270;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_90;
import static net.minecraft.client.data.models.BlockModelGenerators.condition;
import static net.minecraft.client.data.models.BlockModelGenerators.createEmptyOrFullDispatch;
import static net.minecraft.client.data.models.BlockModelGenerators.createRotatedVariants;
import static net.minecraft.client.data.models.BlockModelGenerators.createSimpleBlock;
import static net.minecraft.client.data.models.BlockModelGenerators.or;
import static net.minecraft.client.data.models.BlockModelGenerators.plainModel;
import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;
import static net.minecraft.client.data.models.BlockModelGenerators.variants;
import static net.minecraft.client.data.models.ItemModelGenerators.BLANK_LAYER;
import static net.minecraft.client.data.models.ItemModelGenerators.createFlatModelDispatch;
import static net.minecraft.client.data.models.model.ModelTemplates.create;
import static net.minecraft.client.data.models.model.ModelTemplates.createItem;

public class CommonModelCreators {
    public static final ModelTemplate TWO_LAYERED_HANDHELD_ITEM = createItem("handheld",TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate TWO_LAYERED_SPEAR_IN_HAND = createItem("spear_in_hand", "_in_hand", TextureSlot.LAYER0, TextureSlot.LAYER1);
    public static final ModelTemplate HANDHELD_WAR_HAMMER_ITEM = createItem("lithereal:handheld_war_hammer", TextureSlot.LAYER0);
    public static final ModelTemplate BRUSH = createItem("brush", TextureSlot.LAYER0);
    public static final ModelTemplate BRUSH_BRUSHING_0 = createItem("brush_brushing_0", TextureSlot.LAYER0);
    public static final ModelTemplate BRUSH_BRUSHING_1 = createItem("brush_brushing_1", TextureSlot.LAYER0);
    public static final ModelTemplate BRUSH_BRUSHING_2 = createItem("brush_brushing_2", TextureSlot.LAYER0);
    public static final ModelTemplate GLASS_ENCLOSED_TORCH = create("lithereal:template_glass_enclosed_torch", TextureSlot.TORCH);
    public static final ModelTemplate GLASS_ENCLOSED_WALL_TORCH = create("lithereal:template_glass_enclosed_torch_wall", TextureSlot.TORCH);
    public static void blockWithItem(Supplier<? extends Block> block, BlockModelGenerators blockModels) {
        blockModels.createTrivialCube(block.get());
        blockModels.registerSimpleItemModel(block.get(), ModelLocationUtils.getModelLocation(block.get()));
    }

    public static void existingBlockWithItem(Supplier<? extends Block> block, BlockModelGenerators blockModels) {
        blockModels.createNonTemplateModelBlock(block.get());
        blockModels.registerSimpleItemModel(block.get(), ModelLocationUtils.getModelLocation(block.get()));
    }

    public static void blockWithTintedItem(Supplier<? extends Block> block, ItemTintSource tintSource, BlockModelGenerators blockModels) {
        blockModels.createNonTemplateModelBlock(block.get());
        blockModels.registerSimpleTintedItemModel(block.get(), ModelLocationUtils.getModelLocation(block.get()), tintSource);
    }

    public static void nyliumBlock(Block nylium, Block topDonor, Block netherrack, BlockModelGenerators blockModels) {
        TextureMapping mapping = (new TextureMapping()).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(netherrack)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(topDonor)).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(nylium, "_side"));
        blockModels.blockStateOutput.accept(createSimpleBlock(nylium, plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(nylium, mapping, blockModels.modelOutput))));
        blockModels.registerSimpleItemModel(nylium, ModelLocationUtils.getModelLocation(nylium));
    }

    public static void fireCrucibleBlock(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiVariant litModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_lit"));
        MultiVariant blueLitModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_blue_lit"));
        MultiVariant litFireModel = variants(plainModel(ModelLocationUtils.getModelLocation(block, "_lit_fire0")),
                plainModel(ModelLocationUtils.getModelLocation(block, "_lit_fire1")));
        MultiVariant litBlueFireModel = variants(plainModel(ModelLocationUtils.getModelLocation(block, "_lit_blue_fire0")),
                plainModel(ModelLocationUtils.getModelLocation(block, "_lit_blue_fire1")));
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.UNLIT), model)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.LIT), litModel)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.BLUE_LIT), blueLitModel)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.LIT), litFireModel)
                .with(new ConditionBuilder().term(FireCrucibleBlock.HEAT_STATE, FireCrucibleBlockEntity.HeatState.BLUE_LIT), litBlueFireModel));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public static void electricCrucibleBlock(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiVariant onModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_on"));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(FireCrucibleBlock.HEAT_STATE)
                .select(FireCrucibleBlockEntity.HeatState.UNLIT, model)
                .select(FireCrucibleBlockEntity.HeatState.LIT, onModel)
                .select(FireCrucibleBlockEntity.HeatState.BLUE_LIT, onModel)));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public static void infusementChamberBlock(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(ModelLocationUtils.getModelLocation(block));
        MultiVariant filledModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_filled"));
        MultiVariant overlayModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_overlay"));
        MultiVariant filledOverlayModel = plainVariant(ModelLocationUtils.getModelLocation(block, "_filled_overlay"));
        plainVariant(TexturedModel.ORIENTABLE_ONLY_TOP.updateTexture(textureMapping -> {
            textureMapping.put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_empty"));
            textureMapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_rest"));
            textureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_vent"));
        }).createWithSuffix(block, "_inventory", blockModels.modelOutput));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, InfusementChamberBlock.PRIMARY_FILLED, InfusementChamberBlock.SECONDARY_FILLED)
                .generate((direction, primaryFilled, secondaryFilled) -> {
                    MultiVariant usedModel = model;
                    if (primaryFilled && secondaryFilled) usedModel = filledOverlayModel;
                    else if (primaryFilled) usedModel = overlayModel;
                    else if (secondaryFilled) usedModel = filledModel;
                    return switch (direction) {
                        case NORTH -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R180));
                        case WEST -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R90));
                        case EAST -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R270));
                        default -> usedModel;
                    };
                })));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block, "_inventory"));
    }

    public static void corneredBlock(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(TexturedModel.CUBE.create(block, blockModels.modelOutput));
        MultiVariant cornerModel = plainVariant(TexturedModel.COLUMN.updateTexture(textureMapping -> {
            textureMapping.put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_corner"));
            textureMapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block));
        }).createWithSuffix(block, "_corner", blockModels.modelOutput));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(PureEtherealCrystalBlock.CORNER, BlockStateProperties.HORIZONTAL_FACING)
                .generate((isCorner, direction) -> {
                    MultiVariant usedModel = isCorner ? cornerModel : model;
                    return switch (direction) {
                        case NORTH -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R180));
                        case WEST -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R90));
                        case EAST -> usedModel.with(VariantMutator.Y_ROT.withValue(Quadrant.R270));
                        default -> usedModel;
                    };
                })));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public static void existingBlockWithFacing(Block block, BlockModelGenerators blockModels) {
        MultiVariant model = plainVariant(ModelLocationUtils.getModelLocation(block));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.FACING)
                .select(Direction.UP, model)
                .select(Direction.DOWN, model.with(VariantMutator.X_ROT.withValue(Quadrant.R180)))
                .select(Direction.EAST, model.with(VariantMutator.X_ROT.withValue(Quadrant.R90).then(VariantMutator.Y_ROT.withValue(Quadrant.R90))))
                .select(Direction.WEST, model.with(VariantMutator.X_ROT.withValue(Quadrant.R270).then(VariantMutator.Y_ROT.withValue(Quadrant.R90))))
                .select(Direction.NORTH, model.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                .select(Direction.SOUTH, model.with(VariantMutator.X_ROT.withValue(Quadrant.R270)))));
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public static void riftBlock(Block rift, BlockModelGenerators blockModels) {
        Identifier verticalModel = ModelLocationUtils.getModelLocation(rift, "_vertical");
        MultiVariant riftHorizontal = plainVariant(ModelLocationUtils.getModelLocation(rift, "_horizontal"));
        MultiVariant riftVertical = plainVariant(verticalModel);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(rift).with(PropertyDispatch.initial(BlockStateProperties.AXIS)
                .select(Direction.Axis.X, riftVertical.with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                .select(Direction.Axis.Y, riftHorizontal)
                .select(Direction.Axis.Z, riftVertical)));
        blockModels.registerSimpleItemModel(rift, verticalModel);
    }

    public static void riftLikePortalBlock(Block portal, Block rift, BlockModelGenerators blockModels) {
        Identifier horizontalModel = ModelLocationUtils.getModelLocation(rift, "_horizontal");
        MultiVariant riftHorizontal = plainVariant(horizontalModel);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(portal, riftHorizontal));
        blockModels.registerSimpleItemModel(portal, horizontalModel);
    }

    public static void logBlock(Block log, Block wood, BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.woodProvider(log).log(log).wood(wood);
    }

    public static void createFarmland(Block farmland, Block dirt, BlockModelGenerators blockModels) {
        TextureMapping dryTextures = (new TextureMapping()).put(TextureSlot.DIRT, TextureMapping.getBlockTexture(dirt)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(farmland));
        TextureMapping moistTextures = (new TextureMapping()).put(TextureSlot.DIRT, TextureMapping.getBlockTexture(dirt)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(farmland, "_moist"));
        MultiVariant dryModel = plainVariant(ModelTemplates.FARMLAND.create(farmland, dryTextures, blockModels.modelOutput));
        MultiVariant moistModel = plainVariant(ModelTemplates.FARMLAND.create(ModelLocationUtils.getModelLocation(farmland, "_moist"), moistTextures, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(farmland).with(createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, moistModel, dryModel)));
        blockModels.registerSimpleItemModel(farmland, ModelLocationUtils.getModelLocation(farmland));
    }

    public static void createFire(Block fire, BlockModelGenerators blockModels) {
        ConditionBuilder noSides = condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false).term(BlockStateProperties.UP, false);
        MultiVariant floorFireModels = blockModels.createFloorFireModels(fire);
        MultiVariant sideFireModels = blockModels.createSideFireModels(fire);
        MultiVariant topFireModels = blockModels.createTopFireModels(fire);
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(fire).with(noSides, floorFireModels).with(or(condition().term(BlockStateProperties.NORTH, true), noSides), sideFireModels).with(or(condition().term(BlockStateProperties.EAST, true), noSides), sideFireModels.with(Y_ROT_90)).with(or(condition().term(BlockStateProperties.SOUTH, true), noSides), sideFireModels.with(Y_ROT_180)).with(or(condition().term(BlockStateProperties.WEST, true), noSides), sideFireModels.with(Y_ROT_270)).with(condition().term(BlockStateProperties.UP, true), topFireModels));
    }

    public static void createVault(Block vault, BlockModelGenerators blockModels, boolean isOminous) {
        TextureMapping inactiveTextures = TextureMapping.vault(vault, "_front_off", "_side_off", "_top", "_bottom");
        TextureMapping activeTextures = TextureMapping.vault(vault, "_front_on", "_side_on", "_top", "_bottom");
        TextureMapping unlockingTextures = TextureMapping.vault(vault, "_front_ejecting", "_side_on", "_top", "_bottom");
        TextureMapping ejectingRewardTextures = TextureMapping.vault(vault, "_front_ejecting", "_side_on", "_top_ejecting", "_bottom");
        Identifier inactiveModel = ModelTemplates.VAULT.create(vault, inactiveTextures, blockModels.modelOutput);
        MultiVariant inactive = plainVariant(inactiveModel);
        MultiVariant active = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_active", activeTextures, blockModels.modelOutput));
        MultiVariant unlocking = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_unlocking", unlockingTextures, blockModels.modelOutput));
        MultiVariant ejectingReward = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_ejecting_reward", ejectingRewardTextures, blockModels.modelOutput));
        blockModels.registerSimpleItemModel(vault, inactiveModel);
        if (isOminous) {
            TextureMapping inactiveTexturesOminous = TextureMapping.vault(vault, "_front_off_ominous", "_side_off_ominous", "_top_ominous", "_bottom_ominous");
            TextureMapping activeTexturesOminous = TextureMapping.vault(vault, "_front_on_ominous", "_side_on_ominous", "_top_ominous", "_bottom_ominous");
            TextureMapping unlockingTexturesOminous = TextureMapping.vault(vault, "_front_ejecting_ominous", "_side_on_ominous", "_top_ominous", "_bottom_ominous");
            TextureMapping ejectingRewardTexturesOminous = TextureMapping.vault(vault, "_front_ejecting_ominous", "_side_on_ominous", "_top_ejecting_ominous", "_bottom_ominous");
            MultiVariant inactiveOminous = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_ominous", inactiveTexturesOminous, blockModels.modelOutput));
            MultiVariant activeOminous = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_active_ominous", activeTexturesOminous, blockModels.modelOutput));
            MultiVariant unlockingOminous = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_unlocking_ominous", unlockingTexturesOminous, blockModels.modelOutput));
            MultiVariant ejectingRewardOminous = plainVariant(ModelTemplates.VAULT.createWithSuffix(vault, "_ejecting_reward_ominous", ejectingRewardTexturesOminous, blockModels.modelOutput));
            blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(vault).with(PropertyDispatch.initial(VaultBlock.STATE, VaultBlock.OMINOUS).generate((state, ominous) -> {
                MultiVariant variant;
                switch (state) {
                    case INACTIVE -> variant = ominous ? inactiveOminous : inactive;
                    case ACTIVE -> variant = ominous ? activeOminous : active;
                    case UNLOCKING -> variant = ominous ? unlockingOminous : unlocking;
                    case EJECTING -> variant = ominous ? ejectingRewardOminous : ejectingReward;
                    default -> throw new MatchException(null, null);
                }

                return variant;
            })).with(ROTATION_HORIZONTAL_FACING));
        }
        else {
            blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(vault).with(PropertyDispatch.initial(VaultBlock.STATE).generate((state) -> {
                MultiVariant variant;
                switch (state) {
                    case INACTIVE -> variant = inactive;
                    case ACTIVE -> variant = active;
                    case UNLOCKING -> variant = unlocking;
                    case EJECTING -> variant = ejectingReward;
                    default -> throw new MatchException(null, null);
                }

                return variant;
            })).with(ROTATION_HORIZONTAL_FACING));
        }
    }

    public static void grassLikeBlock(Supplier<? extends Block> grass, Supplier<? extends Block> dirt, ItemTintSource tintSource, BlockModelGenerators blockModels) {
        Material bottomTexture = TextureMapping.getBlockTexture(dirt.get());
        TextureMapping snowyMapping = (new TextureMapping()).put(TextureSlot.BOTTOM, bottomTexture).copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE).put(TextureSlot.TOP, TextureMapping.getBlockTexture(grass.get(), "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(grass.get(), "_snow"));
        MultiVariant snowyGrass = plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(grass.get(), "_snow", snowyMapping, blockModels.modelOutput));
        Identifier plainGrassModel = ModelLocationUtils.getModelLocation(grass.get());
        blockModels.createGrassLikeBlock(grass.get(), createRotatedVariants(plainModel(plainGrassModel)), snowyGrass);
        blockModels.registerSimpleTintedItemModel(grass.get(), plainGrassModel, tintSource);
    }

    public static void createGlassEnclosedTorch(Block ground, Block wall, BlockModelGenerators blockModels) {
        TextureMapping textures = TextureMapping.torch(ground);
        blockModels.blockStateOutput.accept(createSimpleBlock(ground, plainVariant(GLASS_ENCLOSED_TORCH.create(ground, textures, blockModels.modelOutput))));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(wall, plainVariant(GLASS_ENCLOSED_WALL_TORCH.create(wall, textures, blockModels.modelOutput))).with(ROTATION_TORCH));
        blockModels.registerSimpleFlatItemModel(ground.asItem());
    }

    public static void itemForBlockModel(Block block, BlockModelGenerators blockModels) {
        blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public static void basicItem(Item item, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    public static void basicPotionItemNoOverlay(Item item, ItemModelGenerators itemModels) {
        generateItemWithTint(item, new Potion(), itemModels);
    }

    public static void generateItemWithTint(Item item, ItemTintSource overlayTint, ItemModelGenerators itemModels) {
        Identifier model = itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model, overlayTint));
    }

    public static void basicPotionItem(Item item, ItemModelGenerators itemModels) {
        itemModels.generateItemWithTintedOverlay(item, new Potion());
    }

    public static void handheldPotionItem(Item item, ItemModelGenerators itemModels) {
        generateHandheldItemWithTintedOverlay(item, new Potion(), itemModels);
    }

    public static void generateHandheldItemWithTintedOverlay(Item item, ItemTintSource overlayTint, ItemModelGenerators itemModels) {
        generateHandheldItemWithTintedOverlay(item, "_overlay", overlayTint, itemModels);
    }

    public static void generateHandheldItemWithTintedOverlay(Item item, String overlaySuffix, ItemTintSource overlayTint, ItemModelGenerators itemModels) {
        Identifier model = generateLayeredHandheldItem(item, TextureMapping.getItemTexture(item), TextureMapping.getItemTexture(item, overlaySuffix), itemModels);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model, BLANK_LAYER, overlayTint));
    }

    public static Identifier generateLayeredHandheldItem(Item target, Material layer0, Material layer1, ItemModelGenerators itemModels) {
        return TWO_LAYERED_HANDHELD_ITEM.create(target, TextureMapping.layered(layer0, layer1), itemModels.modelOutput);
    }

    public static void handheldItem(Item item, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    public static void brushItem(Item item, ItemModelGenerators itemModels) {
        itemModels.createFlatItemModel(item, BRUSH);
        createFlatItemModelWithNameSuffix(item, "_brushing_0", BRUSH_BRUSHING_0, itemModels);
        createFlatItemModelWithNameSuffix(item, "_brushing_1", BRUSH_BRUSHING_1, itemModels);
        createFlatItemModelWithNameSuffix(item, "_brushing_2", BRUSH_BRUSHING_2, itemModels);
        itemModels.generateBrush(item);
    }

    public static Identifier createFlatItemModelWithNameSuffix(Item item, String suffix, ModelTemplate template, ItemModelGenerators itemModels) {
        return template.create(ModelLocationUtils.getModelLocation(item, suffix), TextureMapping.layer0(item), itemModels.modelOutput);
    }

    public static void bowItem(Item bowItem, ItemModelGenerators itemModels) {
        itemModels.createFlatItemModel(bowItem, ModelTemplates.BOW);
        itemModels.generateBow(bowItem);
    }

    public static void spearItem(Item spearItem, ItemModelGenerators itemModels) {
        itemModels.generateSpear(spearItem);
    }

    public static void spearPotionItem(Item spearItem, ItemModelGenerators itemModels) {
        generateSpearItemWithTintedOverlay(spearItem, new Potion(), itemModels);
    }

    public static void generateSpearItemWithTintedOverlay(Item item, ItemTintSource overlayTint, ItemModelGenerators itemModels) {
        generateSpearItemWithTintedOverlay(item, "_overlay", overlayTint, itemModels);
    }

    public static void generateSpearItemWithTintedOverlay(Item item, String overlaySuffix, ItemTintSource overlayTint, ItemModelGenerators itemModels) {
        ItemModel.Unbaked flatModel = ItemModelUtils.tintedModel(ModelTemplates.TWO_LAYERED_ITEM.create(item, TextureMapping.layered(TextureMapping.getItemTexture(item), TextureMapping.getItemTexture(item, overlaySuffix)), itemModels.modelOutput), BLANK_LAYER, overlayTint);
        ItemModel.Unbaked inHandModel = ItemModelUtils.tintedModel(TWO_LAYERED_SPEAR_IN_HAND.create(item, TextureMapping.layered(TextureMapping.getItemTexture(item, "_in_hand"), TextureMapping.getItemTexture(item, "_in_hand" + overlaySuffix)), itemModels.modelOutput), BLANK_LAYER, overlayTint);
        itemModels.itemModelOutput.accept(item, createFlatModelDispatch(flatModel, inHandModel), new ClientItem.Properties(true, false, 1.95F));
    }
}
