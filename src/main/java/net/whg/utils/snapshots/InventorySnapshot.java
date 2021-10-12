package net.whg.utils.snapshots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * A snapshot of a player's inventory and stats that can be saved and applied at
 * a later point in time.
 */
public class InventorySnapshot {
    /**
     * Creates a snapshot of the player's current inventory and status.
     * 
     * @param player - The player.
     * @return The snapshot.
     */
    public static InventorySnapshot createSnapshot(Player player) {
        var snapshot = new InventorySnapshot();

        // Save game mode
        snapshot.gameMode = player.getGameMode();

        // Save attributes
        snapshot.maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        // Save stats
        snapshot.health = player.getHealth();
        snapshot.noDamageTicks = player.getNoDamageTicks();
        snapshot.fireTicks = player.getFireTicks();
        snapshot.remainingAir = player.getRemainingAir();

        // Save inventory
        var inventory = player.getInventory();
        snapshot.inventory = Arrays.copyOf(inventory.getContents(), 9 * 4);
        snapshot.armor = Arrays.copyOf(inventory.getArmorContents(), 4);
        snapshot.offhand = inventory.getItemInOffHand();

        // Save potion effects
        snapshot.potionEffects = new ArrayList<>(player.getActivePotionEffects());

        return snapshot;
    }

    /**
     * Creates a snapshot of the player's current inventory and status, then reset
     * all values to their default value in Minecraft.
     * 
     * @param player - The player.
     * @return The snapshot.
     */
    public static InventorySnapshot createSnapshotAndClear(Player player) {
        var snapshot = createSnapshot(player);

        // Reset game mode
        player.setGameMode(GameMode.ADVENTURE);

        // Reset attributes
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);

        // Reset values
        player.setHealth(20);
        player.setNoDamageTicks(0);
        player.setFireTicks(0);
        player.setRemainingAir(20);
        player.setArrowsStuck(0);

        // Clear potion effects
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        // Clear inventory
        var inventory = player.getInventory();
        inventory.clear();
        inventory.setArmorContents(new ItemStack[4]);
        inventory.setItemInOffHand(null);
        inventory.setHeldItemSlot(0);

        return snapshot;
    }

    /**
     * Applies a given snapshot to the provided player.
     * 
     * @param player   - The player to modify.
     * @param snapshot - The snapshot to apply to the player.
     */
    public static void apply(Player player, InventorySnapshot snapshot) {
        // Apply game mode
        player.setGameMode(snapshot.gameMode);

        // Apply Attributes
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(snapshot.maxHealth);

        // Apply Stats
        player.setHealth(snapshot.health);
        player.setNoDamageTicks(snapshot.noDamageTicks);
        player.setFireTicks(snapshot.fireTicks);
        player.setRemainingAir(snapshot.remainingAir);

        // Apply inventory
        var inventory = player.getInventory();
        inventory.setContents(snapshot.inventory);
        inventory.setArmorContents(snapshot.armor);
        inventory.setItemInOffHand(snapshot.offhand);

        // Apply Potion Effects
        for (var effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
        for (var effect : snapshot.potionEffects)
            player.addPotionEffect(effect);
    }

    // Game mode
    private GameMode gameMode;

    // Attributes
    private double maxHealth;

    // Stats
    private double health;
    private int noDamageTicks;
    private int fireTicks;
    private int remainingAir;

    // Inventory
    private ItemStack[] inventory;
    private ItemStack[] armor;
    private ItemStack offhand;

    // Potion Effects
    private List<PotionEffect> potionEffects;

    private InventorySnapshot() {
    }
}
