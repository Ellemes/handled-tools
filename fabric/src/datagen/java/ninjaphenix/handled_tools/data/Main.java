package ninjaphenix.handled_tools.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import ninjaphenix.handled_tools.data.providers.BlockStateProvider;

public class Main implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(BlockStateProvider::new);
    }
}
