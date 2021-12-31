package ninjaphenix.handled_tools.items.obsidian_tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ninjaphenix.handled_tools.Utils;

public final class ObsidianShovel extends ShovelItem {
    public ObsidianShovel(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        return Utils.damageObsidianTool(this, 0, stack, world, state, pos, miner);
    }
}
