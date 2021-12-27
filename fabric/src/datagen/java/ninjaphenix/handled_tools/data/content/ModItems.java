package ninjaphenix.handled_tools.data.content;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import ninjaphenix.handled_tools.Utils;

public class ModItems {
    public static final Item OBSIDIAN_SWORD = item("obsidian_sword");
    public static final Item OBSIDIAN_SHOVEL = item("obsidian_shovel");
    public static final Item OBSIDIAN_PICKAXE = item("obsidian_pickaxe");
    public static final Item OBSIDIAN_AXE = item("obsidian_axe");
    public static final Item OBSIDIAN_HOE = item("obsidian_hoe");

    private static Item item(String path) {
        return Registry.ITEM.get(Utils.id(path));
    }
}
