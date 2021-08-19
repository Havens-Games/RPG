package net.whg.utils.math;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A mutable container for storing a hierarchical translation and rotation pair.
 */
public class Transform {
    private final List<Consumer<Transform>> listeners = new ArrayList<>();
    private final List<Transform> children = new ArrayList<>();
    private final Consumer<Transform> parentChangeListener = a -> callChangeListeners();
    private Quaternion rot = Quaternion.IDENTITY;
    private Vec3f pos = Vec3f.ZEROS;
    private Vec3f scale = Vec3f.ONES;
    private Mat4f mat = Mat4f.IDENTITY;
    private Transform parent;

    /**
     * Gets the parent of this transform. Used when calculation world position,
     * rotation, and scale.
     * 
     * @return The parent of this transform, or null if this transform has no
     *         parent.
     */
    public Transform getParent() {
        return parent;
    }

    /**
     * Sets the parent of this transform. Used when calculation world position,
     * rotation, and scale.
     * 
     * @param parent - The new parent.
     * @throws IllegalStateException If there is a circular dependency.
     */
    public void setParent(Transform parent) {
        if (this.parent != null) {
            this.parent.removeChild(this);
            this.parent.removeListener(parentChangeListener);
        }

        checkForCircularDependencies(parent);
        this.parent = parent;

        if (this.parent != null) {
            this.parent.addChild(this);
            this.parent.addListener(parentChangeListener);
        }

        callChangeListeners();
    }

    /**
     * Iterates up the hierarchy for the provided potential parent transform to
     * check if assigning the transform a new parent would create a circular
     * dependency.
     * 
     * @param newParent - The potential new parent transform.
     * @throws IllegalStateException If there is a circular dependency.
     */
    private void checkForCircularDependencies(Transform newParent) {
        var current = newParent;
        while (current != null) {
            if (current == this)
                throw new IllegalStateException("Transforms cannot have circular dependencies!");

            current = current.getParent();
        }
    }

    /**
     * An internal function that adds a new child to this transform's hierarchy.
     * 
     * @param child - The child to add.
     */
    private void addChild(Transform child) {
        children.add(child);
    }

    /**
     * An internal function that removes a child from this transform's hierarchy.
     * 
     * @param child - The child to remove.
     */
    private void removeChild(Transform child) {
        children.remove(child);
    }

    /**
     * Updates the matrix representing the combined position, scale, and rotation.
     * This function will also trigger all listeners attached to this transform.
     */
    private void updateMatrix() {
        mat = Mat4f.translationMatrix(pos) //
                .multiply(Mat4f.rotationMatrix(rot)) //
                .multiply(Mat4f.scalingMatrix(scale));

        callChangeListeners();
    }

    /**
     * Triggers all change listeners attached to this transform.
     */
    private void callChangeListeners() {
        for (var listener : listeners)
            listener.accept(this);
    }

    /**
     * Gets the local position of this transform.
     * 
     * @return The local position, regardless of the parent.
     */
    public Vec3f getLocalPosition() {
        return pos;
    }

    /**
     * Sets the local position of this transform.
     * 
     * @param pos - The local position.
     */
    public void setLocalPosition(Vec3f pos) {
        this.pos = pos;
        updateMatrix();
    }

    /**
     * Gets the local rotation of this transform.
     * 
     * @return The local rotation, regardless of the parent.
     */
    public Quaternion getLocalRotation() {
        return rot;
    }

    /**
     * Sets the local rotation of this transform.
     * 
     * @param rot - The local rotation.
     */
    public void setLocalRotation(Quaternion rot) {
        this.rot = rot;
        updateMatrix();
    }

    /**
     * Gets the local scale of this transform.
     * 
     * @return The local scale, regardless of the parent.
     */
    public Vec3f getLocalScale() {
        return scale;
    }

    /**
     * Sets the local scale of this transform.
     * 
     * @param scale - The local scale.
     */
    public void setLocalScale(Vec3f scale) {
        this.scale = scale;
        updateMatrix();
    }

    /**
     * Gets the world position of this transform, factoring the parent transform,
     * recursively.
     * 
     * @return The world position.
     */
    public Vec3f getWorldPosition() {
        return getWorldMatrix().multiplyPosition(pos);
    }

    /**
     * Gets the world rotation of this transform, factoring the parent transform,
     * recursively.
     * 
     * @return The world rotation.
     */
    public Quaternion getWorldRotation() {
        if (parent == null)
            return rot;

        return parent.getWorldRotation().multiply(rot);
    }

    /**
     * Gets the world scale of this transform, factoring the parent transform,
     * recursively.
     * 
     * @return The world scale.
     */
    public Vec3f getWorldScale() {
        return getWorldMatrix().multiplyDirection(scale);
    }

    /**
     * Gets the local transformation matrix of this transform, combining the
     * position, rotation, and scale.
     * 
     * @return The local matrix of this transform, regardless of the parent.
     */
    public Mat4f getLocalMatrix() {
        return mat;
    }

    /**
     * Gets the world transformation matrix of this transform, factoring the parent
     * transform, recursively.
     * 
     * @return The world matrix.
     */
    public Mat4f getWorldMatrix() {
        if (parent == null)
            return mat;

        return parent.getWorldMatrix().multiply(mat);
    }

    /**
     * Adds a new listener to this transform. The listener function is executed each
     * time this transform changes.
     * 
     * @param listener - The listener to add.
     */
    public void addListener(Consumer<Transform> listener) {
        listeners.add(listener);
    }

    /**
     * Removes an existing listener from this transform.
     * 
     * @param listener - The listener to remove.
     */
    public void removeListener(Consumer<Transform> listener) {
        listeners.remove(listener);
    }

    /**
     * Returns a list of all children for this transform.
     * 
     * @return A new list object containing all child transforms.
     */
    public List<Transform> getChildren() {
        return new ArrayList<>(children);
    }
}
