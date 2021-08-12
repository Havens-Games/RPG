package net.whg.utils.math;

/**
 * An immutable 3D point in space.
 */
public final class Vec3f {
    public final float x;
    public final float y;
    public final float z;

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Adds another vector to this vector.
     * 
     * @param other - The Vec3 to add.
     * @return A new vector with the coordinates added.
     */
    public Vec3f add(Vec3f other) {
        return new Vec3f(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Subtracts another vector from this vector.
     * 
     * @param other - The vector to subtract.
     * @return A new vector with the coordinates subtracted.
     */
    public Vec3f subtract(Vec3f other) {
        return new Vec3f(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Multiplies each coordinate in this vector with the corresponding coordinates
     * in another vector.
     * 
     * @param other - The vector to multiply against.
     * @return A new vector with the coordinates multiplied.
     */
    public Vec3f multiply(Vec3f other) {
        return new Vec3f(x * other.x, y * other.y, z * other.z);
    }

    /**
     * Checks if this vector lies within the given bounds, inclusive.
     * 
     * @param min - The minimum bounds position.
     * @param max - The maximum bounds position.
     * @return True if this position is within the indicated bounds. False
     *         otherwise.
     */
    public boolean isInBounds(Vec3f min, Vec3f max) {
        if (x < min.x || x > max.x)
            return false;

        if (y < min.y || y > max.y)
            return false;

        if (z < min.z || z > max.z)
            return false;

        return true;
    }
}
