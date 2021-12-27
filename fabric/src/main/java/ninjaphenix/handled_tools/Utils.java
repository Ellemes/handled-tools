package ninjaphenix.handled_tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Utils {
    public static final String MOD_ID = "handled_tools";

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static boolean damageObsidianTool(Item tool, int damageModifier, ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient() && state.getHardness(world, pos) != 0) {
            int damage = 1 + damageModifier;
            MiningToolItem toolItem = tool instanceof MiningToolItem item ? item : null;
            if (toolItem != null && state.isIn(toolItem.effectiveBlocks) || toolItem == null && tool.getMiningSpeedMultiplier(stack, state) != 1) {
                float hardness = state.getHardness(world, pos);
                if (hardness < 0) {

                } else if (hardness >= 50) {
                    damage = MathHelper.floor( 20 * (hardness / 50));
                } else {
                    damage = MathHelper.floor(MathHelper.lerp(hardness / 50, 1, 20));
                }
            }
            stack.damage(damage, miner, (entity) -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }
}
