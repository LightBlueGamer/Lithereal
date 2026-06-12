package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class ModUntintedParticleLeavesBlock extends LeavesBlock {
	public static final MapCodec<ModUntintedParticleLeavesBlock> CODEC = RecordCodecBuilder.mapCodec(
		i -> i.group(
				ExtraCodecs.floatRange(0.0F, 1.0F).fieldOf("leaf_particle_chance").forGetter(e -> e.leafParticleChance),
				ParticleTypes.CODEC.fieldOf("leaf_particle").forGetter(e -> e.leafParticle.get()),
				propertiesCodec()
			)
			.apply(i, ModUntintedParticleLeavesBlock::new)
	);
	protected final Supplier<ParticleOptions> leafParticle;

	private ModUntintedParticleLeavesBlock(final float leafParticleChance, final ParticleOptions leafParticle, final BlockBehaviour.Properties properties) {
		this(leafParticleChance, () -> leafParticle, properties);
	}

	public ModUntintedParticleLeavesBlock(final float leafParticleChance, final Supplier<ParticleOptions> leafParticle, final BlockBehaviour.Properties properties) {
		super(leafParticleChance, properties);
		this.leafParticle = leafParticle;
	}

	@Override
	protected void spawnFallingLeavesParticle(final Level level, final BlockPos pos, final RandomSource random) {
		ParticleUtils.spawnParticleBelow(level, pos, random, this.leafParticle.get());
	}

	@Override
	public MapCodec<ModUntintedParticleLeavesBlock> codec() {
		return CODEC;
	}
}
