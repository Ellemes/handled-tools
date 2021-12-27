package ninjaphenix.handled_tools.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import ninjaphenix.handled_tools.Main;
import ninjaphenix.handled_tools.sort.ToolHeadPouchScreenHandler;

public class ToolHeadPouch extends Item {
    public ToolHeadPouch(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            user.openHandledScreen(new NamedScreenHandlerFactory() {
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    return new ToolHeadPouchScreenHandler(Main.INSTANCE.getToolHeadPouchScreenHandlerType(), syncId, inv, new SimpleInventory(5));
                }

                @Override
                public Text getDisplayName() {
                    return ToolHeadPouch.this.getName();
                }
            });
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
