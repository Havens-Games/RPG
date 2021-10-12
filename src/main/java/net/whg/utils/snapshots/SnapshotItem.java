package net.whg.utils.snapshots;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

/**
 * Represents an element of a player that can be captured within an inventory
 * snapshot to be saved and reapplied to the player at a later point in time.
 */
public interface SnapshotItem extends ConfigurationSerializable {
    /**
     * Captures the current item value from the player and saves it.
     * 
     * @param player - The player to read from.
     */
    void capture(Player player);

    /**
     * Applies the currently saved value of this snapshot item to the player. If
     * there is currently no saved value attached to this snapshot item, then the
     * default value is written to the player.
     * 
     * @param player - The player to write to.
     */
    void apply(Player player);
}
