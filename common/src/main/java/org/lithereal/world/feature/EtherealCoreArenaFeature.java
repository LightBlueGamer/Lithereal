package org.lithereal.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.lithereal.Lithereal;

import java.util.Optional;

public class EtherealCoreArenaFeature extends Feature<NoneFeatureConfiguration> {

    public EtherealCoreArenaFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        ServerLevelAccessor level = ctx.level();
        RandomSource randomSource = ctx.random();
        BlockPos pos = ctx.origin();

        return placeTemplate(level, randomSource, pos, Rotation.NONE, Lithereal.id("ethereal_core_arena"));
    }

    private boolean placeTemplate(ServerLevelAccessor level, RandomSource randomSource, BlockPos centerPos, Rotation rotation, ResourceLocation id) {
        Optional<StructureTemplate> templateOptional = level.getLevel().getStructureManager().get(id);
        if (templateOptional.isEmpty()) {
            return false;
        }

        StructureTemplate template = templateOptional.get();
        BlockPos cornerPos = centerPos.offset(-template.getSize().getX() / 2, 0, -template.getSize().getZ() / 2);
        StructurePlaceSettings structurePlaceSettings = new StructurePlaceSettings();
        structurePlaceSettings.setRotation(rotation);
        template.placeInWorld(level, cornerPos, centerPos, structurePlaceSettings, randomSource, 2);
        return true;
    }
}