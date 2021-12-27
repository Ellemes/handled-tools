package ninjaphenix.handled_tools.sort;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import ninjaphenix.handled_tools.items.ToolHead;

public class ToolHeadPouchScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public ToolHeadPouchScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(type, syncId);

        this.inventory = inventory;
        for (int i = 0; i < 5; i++) {
            this.addSlot(new FilteredSlot(inventory, i, 44 + i * 18, 20, stack -> stack.getItem() instanceof ToolHead));
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, y * 18 + 51));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 109));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasStack()) {
            ItemStack newStack = slot.getStack();
            originalStack = newStack.copy();
            if (index < inventory.size()) {
                if (!this.insertItem(newStack, inventory.size(), inventory.size() + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(newStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (newStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return originalStack;
    }
}
