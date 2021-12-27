package ninjaphenix.handled_tools.sort;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

public class FilteredSlot extends Slot {
    private final Predicate<ItemStack> canInsertStack;

    public FilteredSlot(Inventory inventory, int index, int x, int y, Predicate<ItemStack> canInsertStack) {
        super(inventory, index, x, y);
        this.canInsertStack = canInsertStack;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return canInsertStack.test(stack);
    }
}
