package net.whg.utils.snapshots;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.whg.utils.SafeArrayList;

/**
 * Represents a serializable snapshot of a player's inventory and stats that
 * stores modifiable data in order to reapply that data state back to the player
 * at a later point in time.
 */
@SerializableAs("Snapshot")
public class Snapshot implements ConfigurationSerializable {
    /**
     * Creates a new snapshot from a player's current state.
     * 
     * @param player   - The player to take a snapshot of.
     * @param provider - The snapshot item provider to use.
     * @return The new snapshot instance.
     */
    public static Snapshot fromPlayer(Player player, SnapshotItemProvider provider) {
        var snapshot = new Snapshot();

        for (var item : provider.getItems()) {
            item.capture(player);
            snapshot.items.add(item);
        }

        return snapshot;
    }

    /**
     * Creates a new snapshot instance using all default values as specified by the
     * snapshot item provider.
     * 
     * @param provider - The snapshot item provider to use.
     * @return A default snapshot instance.
     */
    public static Snapshot fromDefault(SnapshotItemProvider provider) {
        var snapshot = new Snapshot();

        for (var item : provider.getItems()) {
            snapshot.items.add(item);
        }

        return snapshot;
    }

    /*
     * Deserializes a Snapshot instance from a provided argument map.
     * 
     * @param args - The serialized map.
     * 
     * @return The new instance.
     */
    public static Snapshot deserialize(Map<String, Object> args) {
        var snapshot = new Snapshot();

        var length = args.size();
        for (var i = 0; i < length; i++) {
            var item = (SnapshotItem) args.get(String.valueOf(i));
            snapshot.items.add(item);
        }

        return snapshot;
    }

    private final SafeArrayList<SnapshotItem> items = new SafeArrayList<>();

    private Snapshot() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Map<String, Object> serialize() {
        var map = new HashMap<String, Object>();

        for (var i = 0; i < items.size(); i++) {
            map.put(String.valueOf(i), items.get(i));
        }

        return map;
    }

    /**
     * Applies this snapshot instance to a player and replaces the player's current
     * state.
     * 
     * @param player - The player to modify.
     */
    public void apply(Player player) {
        for (var item : items) {
            item.apply(player);
        }
    }
}
