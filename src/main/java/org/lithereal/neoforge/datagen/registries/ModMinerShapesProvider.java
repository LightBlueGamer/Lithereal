package org.lithereal.neoforge.datagen.registries;

//? neoforge {
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import org.lithereal.core.component.miner_shape.MinerShape;
import org.lithereal.core.component.miner_shape.MinerShapes;
import org.lithereal.core.component.miner_shape.block_pos_processor.AxisSelectedBlockPosProcessor;
import org.lithereal.core.component.miner_shape.block_pos_processor.DirectBlockPosProcessor;
import org.lithereal.core.component.miner_shape.block_pos_processor.OriginProcessor;
import org.lithereal.core.component.miner_shape.condition.*;
import org.lithereal.core.component.miner_shape.condition.Constant;
import org.lithereal.core.component.miner_shape.value_provider.*;
import org.lithereal.registry.ModRegistries;

import java.util.List;
import java.util.Optional;

import static org.lithereal.item.ability.IAbility.snakeCaseToName;

public class ModMinerShapesProvider implements RegistryEntryProvider<MinerShape> {
    @Override
    public ResourceKey<Registry<MinerShape>> key() {
        return ModRegistries.MINER_SHAPE_REGISTRY;
    }

    @Override
    public void run(BootstrapContext<MinerShape> context) {
        AxisSelectedBlockPosProcessor straight = new AxisSelectedBlockPosProcessor(new DirectBlockPosProcessor(OriginProcessor.TARGET, OriginProcessor.CENTER, OriginProcessor.CENTER),
                new DirectBlockPosProcessor(OriginProcessor.CENTER, OriginProcessor.TARGET, OriginProcessor.CENTER),
                new DirectBlockPosProcessor(OriginProcessor.CENTER, OriginProcessor.CENTER, OriginProcessor.TARGET));
        ValueProvider radiusSqr = new MathFunction(Variable.RADIUS, Optional.of(Variable.RADIUS), MathFunction.Operation.MULTIPLY);
        ValidityCondition matchingXAxis = new Compare(new BlockPosValue(OriginProcessor.TARGET, Direction.Axis.X), new BlockPosValue(OriginProcessor.CENTER, Direction.Axis.X), Compare.Comparison.EQUAL);
        ValidityCondition matchingYAxis = new Compare(new BlockPosValue(OriginProcessor.TARGET, Direction.Axis.Y), new BlockPosValue(OriginProcessor.CENTER, Direction.Axis.Y), Compare.Comparison.EQUAL);
        ValidityCondition matchingZAxis = new Compare(new BlockPosValue(OriginProcessor.TARGET, Direction.Axis.Z), new BlockPosValue(OriginProcessor.CENTER, Direction.Axis.Z), Compare.Comparison.EQUAL);
        context.register(MinerShapes.RECTANGULAR_PRISM,
                new MinerShape(generate(MinerShapes.RECTANGULAR_PRISM),
                        new Constant(true),
                        true));
        context.register(MinerShapes.CYLINDER,
                new MinerShape(generate(MinerShapes.CYLINDER),
                    new Compare(new Distance(OriginProcessor.TARGET, straight, Distance.DistanceType.DIST_SQR),
                            radiusSqr,
                            Compare.Comparison.LESS_THAN_OR_EQUAL),
                    true));
        context.register(MinerShapes.SPHERE,
                new MinerShape(generate(MinerShapes.SPHERE),
                        new Compare(new Distance(OriginProcessor.TARGET, OriginProcessor.CENTER, Distance.DistanceType.DIST_SQR),
                                radiusSqr,
                                Compare.Comparison.LESS_THAN_OR_EQUAL),
                        false));
        context.register(MinerShapes.OCTAHEDRON,
                new MinerShape(generate(MinerShapes.OCTAHEDRON),
                        new Compare(new Distance(OriginProcessor.TARGET, OriginProcessor.CENTER, Distance.DistanceType.DIST_MANHATTAN),
                                Variable.RADIUS,
                                Compare.Comparison.LESS_THAN_OR_EQUAL),
                        false));
        context.register(MinerShapes.DIAMOND_PRISM,
                new MinerShape(generate(MinerShapes.DIAMOND_PRISM),
                        new Compare(new Distance(OriginProcessor.TARGET, straight, Distance.DistanceType.DIST_MANHATTAN),
                                Variable.RADIUS,
                                Compare.Comparison.LESS_THAN_OR_EQUAL),
                        true));
        context.register(MinerShapes.CROSS,
                new MinerShape(generate(MinerShapes.CROSS),
                        new AxisSelectedCondition(new AnyOf(List.of(matchingYAxis, matchingZAxis)),
                                new AnyOf(List.of(matchingXAxis, matchingZAxis)),
                                new AnyOf(List.of(matchingXAxis, matchingYAxis))),
                        true));
        context.register(MinerShapes.TRICROSS,
                new MinerShape(generate(MinerShapes.TRICROSS),
                        new AnyOf(List.of(new AllOf(matchingXAxis, matchingYAxis), new AllOf(matchingXAxis, matchingZAxis), new AllOf(matchingYAxis, matchingZAxis))),
                        true));
        context.register(MinerShapes.STRAIGHT,
                new MinerShape(generate(MinerShapes.STRAIGHT),
                        new AxisSelectedCondition(new AllOf(List.of(matchingYAxis, matchingZAxis)),
                                new AllOf(List.of(matchingXAxis, matchingZAxis)),
                                new AllOf(List.of(matchingXAxis, matchingYAxis))),
                        true));
    }

    public Component generate(ResourceKey<MinerShape> minerShapeKey) {
        return Component.translatableWithFallback("miner_shape." + minerShapeKey.identifier().getNamespace() + "." + minerShapeKey.identifier().getPath(), snakeCaseToName(minerShapeKey.identifier().getPath()));
    }
}
//?}