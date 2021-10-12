package net.whg.utils.snapshots;

import java.util.ArrayList;
import java.util.List;

import net.whg.utils.snapshots.types.Inventory;

/**
 * A factory object that generates a list of snapshot items that can be used to
 * take a full snapshot of a player.
 */
public class SnapshotItemProvider {
    /**
     * Gets a list of new snapshot item instances that should be applied in a
     * snapshot.
     * 
     * @return A list of new snapshot item instances of different types.
     */
    public List<SnapshotItem> getItems() {
        var list = new ArrayList<SnapshotItem>();

        list.add(new Inventory());

        return list;
    }
}
