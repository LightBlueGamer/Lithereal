package org.lithereal.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.lithereal.entity.CustomBoat;
import org.lithereal.entity.ModBoat;
import org.lithereal.entity.ModBoat.Type;
import org.lithereal.entity.ModChestBoat;

import java.util.List;
import java.util.function.Predicate;

public class ModBoatItem extends Item {
	private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
	private final Type type;
	private final boolean hasChest;

	public ModBoatItem(boolean hasChest, Type type, Properties properties) {
		super(properties);
		this.hasChest = hasChest;
		this.type = type;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		HitResult hitResult = getPlayerPOVHitResult(level, player, Fluid.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(itemStack);
		} else {
			Vec3 vec3 = player.getViewVector(1.0F);
			double d = 5.0;
			List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0)).inflate(1.0), ENTITY_PREDICATE);
			if (!list.isEmpty()) {
				Vec3 vec32 = player.getEyePosition();

				for (Entity entity : list) {
					AABB aABB = entity.getBoundingBox().inflate((double)entity.getPickRadius());
					if (aABB.contains(vec32)) {
						return InteractionResultHolder.pass(itemStack);
					}
				}
			}

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				CustomBoat boat = this.getBoat(level, hitResult, itemStack, player);
				boat.setVariant(this.type);
				boat.self().setYRot(player.getYRot());
				if (!level.noCollision(boat.self(), boat.self().getBoundingBox())) {
					return InteractionResultHolder.fail(itemStack);
				} else {
					if (!level.isClientSide) {
						level.addFreshEntity(boat.self());
						level.gameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getLocation());
						itemStack.consume(1, player);
					}

					player.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
				}
			} else {
				return InteractionResultHolder.pass(itemStack);
			}
		}
	}

	private CustomBoat getBoat(Level level, HitResult hitResult, ItemStack itemStack, Player player) {
		Vec3 vec3 = hitResult.getLocation();
		CustomBoat boat = this.hasChest ? new ModChestBoat(level, vec3.x, vec3.y, vec3.z) : new ModBoat(level, vec3.x, vec3.y, vec3.z);
		if (level instanceof ServerLevel serverLevel) {
			EntityType.createDefaultStackConfig(serverLevel, itemStack, player).accept(boat.self());
		}

		return boat;
	}
}
