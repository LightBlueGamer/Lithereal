package org.lithereal.world.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

public class WaterReplaceProcessor extends StructureProcessor {
	public static final MapCodec<WaterReplaceProcessor> CODEC = MapCodec.unit(() -> WaterReplaceProcessor.INSTANCE);
	public static final WaterReplaceProcessor INSTANCE = new WaterReplaceProcessor();

	private WaterReplaceProcessor() {
	}

	@Override
	public StructureTemplate.StructureBlockInfo processBlock(
		LevelReader levelReader,
		BlockPos blockPos,
		BlockPos blockPos2,
		StructureTemplate.StructureBlockInfo structureBlockInfo,
		StructureTemplate.StructureBlockInfo structureBlockInfo2,
		StructurePlaceSettings structurePlaceSettings
	) {
		if (!structureBlockInfo2.state().is(Blocks.LIGHT_BLUE_SHULKER_BOX)) {
			return structureBlockInfo2;
		} else {

			return new StructureTemplate.StructureBlockInfo(structureBlockInfo2.pos(), Blocks.WATER.defaultBlockState(), structureBlockInfo2.nbt());
		}
	}

	@Override
	protected @NotNull StructureProcessorType<?> getType() {
		return StructureProcessorType.BLACKSTONE_REPLACE;
	}
}