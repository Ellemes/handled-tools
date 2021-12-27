package ninjaphenix.handled_tools.data.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockStateDefinitionProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.Models;
import ninjaphenix.handled_tools.data.content.ModItems;

public class BlockStateProvider extends FabricBlockStateDefinitionProvider {
    public BlockStateProvider(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(ModItems.OBSIDIAN_SWORD, Models.GENERATED);
        generator.register(ModItems.OBSIDIAN_SHOVEL, Models.GENERATED);
        generator.register(ModItems.OBSIDIAN_PICKAXE, Models.GENERATED);
        generator.register(ModItems.OBSIDIAN_AXE, Models.GENERATED);
        generator.register(ModItems.OBSIDIAN_HOE, Models.GENERATED);
    }
}