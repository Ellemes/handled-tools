package ninjaphenix.handled_tools;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;
import ninjaphenix.handled_tools.client.ToolHeadPouchScreen;
import ninjaphenix.handled_tools.items.ObsidianAxe;
import ninjaphenix.handled_tools.items.ObsidianHoe;
import ninjaphenix.handled_tools.items.ObsidianPickaxe;
import ninjaphenix.handled_tools.items.ObsidianShovel;
import ninjaphenix.handled_tools.items.ObsidianSword;
import ninjaphenix.handled_tools.items.ToolHeadPouch;
import ninjaphenix.handled_tools.sort.ToolHeadPouchScreenHandler;

public class Main implements ModInitializer {
    public static final Main INSTANCE = new Main();
    private ScreenHandlerType<ToolHeadPouchScreenHandler> toolHeadPouchScreenHandlerType;

    private Main() {

    }

    @Override
    public void onInitialize() {
        this.registerObsidianTools();
        this.registerToolHeadPouch();
    }

    private void registerToolHeadPouch() {
        Registry.register(Registry.ITEM, Utils.id("tool_head_pouch"), new ToolHeadPouch(new Item.Settings().group(ItemGroup.TOOLS)));
        toolHeadPouchScreenHandlerType = ScreenHandlerRegistry.registerSimple(Utils.id("tool_head_pouch"), (syncId, inventory) -> {
            return new ToolHeadPouchScreenHandler(toolHeadPouchScreenHandlerType, syncId, inventory, new SimpleInventory(5));
        });
        if (FabricLoaderImpl.INSTANCE.getEnvironmentType() == EnvType.CLIENT) {
            ScreenRegistry.register(toolHeadPouchScreenHandlerType, ToolHeadPouchScreen::new);
        }
    }

    private void registerObsidianTools() {
        ToolMaterial obsidianToolMaterial = new ToolMaterial() {
            @Override
            public int getDurability() {
                return ToolMaterials.DIAMOND.getDurability();
            }

            @Override
            public float getMiningSpeedMultiplier() {
                return ToolMaterials.STONE.getMiningSpeedMultiplier();
            }

            @Override
            public float getAttackDamage() {
                return ToolMaterials.STONE.getMiningSpeedMultiplier();
            }

            @Override
            public int getMiningLevel() {
                return ToolMaterials.DIAMOND.getMiningLevel();
            }

            @Override
            public int getEnchantability() {
                return ToolMaterials.STONE.getEnchantability();
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.ofItems(Items.OBSIDIAN, Items.CRYING_OBSIDIAN);
            }
        };
        Registry.register(Registry.ITEM, Utils.id("obsidian_sword"), new ObsidianSword(obsidianToolMaterial, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT)));
        Registry.register(Registry.ITEM, Utils.id("obsidian_shovel"), new ObsidianShovel(obsidianToolMaterial, 1.5f, -3, new Item.Settings().group(ItemGroup.TOOLS)));
        Registry.register(Registry.ITEM, Utils.id("obsidian_pickaxe"), new ObsidianPickaxe(obsidianToolMaterial, 1, -2, new Item.Settings().group(ItemGroup.TOOLS)));
        Registry.register(Registry.ITEM, Utils.id("obsidian_axe"), new ObsidianAxe(obsidianToolMaterial, 7, -3.2f, new Item.Settings().group(ItemGroup.TOOLS)));
        Registry.register(Registry.ITEM, Utils.id("obsidian_hoe"), new ObsidianHoe(obsidianToolMaterial, -1, -2, new Item.Settings().group(ItemGroup.TOOLS)));
    }

    public ScreenHandlerType<ToolHeadPouchScreenHandler> getToolHeadPouchScreenHandlerType() {
        return toolHeadPouchScreenHandlerType;
    }
}
