package net.whg.utils.snapshots.types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import net.whg.utils.snapshots.SnapshotItem;

/**
 * A snapshot item that captures all of the item stacks, armor, and left-hand
 * item within the player's inventory.
 */
@SerializableAs("Snapshot_Inventory")
public class Inventory implements SnapshotItem {
    /*
     * Deserializes a Snapshot_Inventory instance from a provided argument map.
     * 
     * @param args - The serialized map.
     * 
     * @return The new instance.
     */
    public static Inventory deserialize(Map<String, Object> args) {
        var inventory = new Inventory();

        for (var i = 0; i < inventory.items.length; i++)
            inventory.items[i] = (ItemStack) args.get("Item" + i);

        for (var i = 0; i < inventory.armor.length; i++)
            inventory.armor[i] = (ItemStack) args.get("Armor" + i);

        inventory.offHand = (ItemStack) args.get("Offhand");

        return inventory;
    }

    private ItemStack[] items = new ItemStack[9 * 4];
    private ItemStack[] armor = new ItemStack[4];
    private ItemStack offHand;

    @Override
    public @NotNull Map<String, Object> serialize() {
        var map = new HashMap<String, Object>();

        for (var i = 0; i < items.length; i++)
            map.put("Item" + i, items[i]);

        for (var i = 0; i < armor.length; i++)
            map.put("Armor" + i, armor[i]);

        map.put("Offhand", offHand);

        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void capture(Player player) {
        var inventory = player.getInventory();

        items = Arrays.copyOf(inventory.getContents(), items.length);
        armor = Arrays.copyOf(inventory.getArmorContents(), armor.length);
        offHand = inventory.getItemInOffHand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(Player player) {
        var inventory = player.getInventory();

        inventory.setContents(items);
        inventory.setArmorContents(armor);
        inventory.setItemInOffHand(offHand);
    }
}
