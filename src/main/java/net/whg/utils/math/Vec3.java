package net.whg.utils.math;

/**
 * An immutable 3D point in space, aligned to a grid.
 */
public final class Vec3 {
    public final int x;
    public final int y;
    public final int z;

    public Vec3(int x, int y, int z) {
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
    public Vec3 add(Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Subtracts another vector from this vector.
     * 
     * @param other - The vector to subtract.
     * @return A new vector with the coordinates subtracted.
     */
    public Vec3 subtract(Vec3 other) {
        return new Vec3(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Left bit shifts all bits for each coordinate in this vector.
     * 
     * @param n - The number of positions to bit shift.
     * @return A new vector with the coordinates bit shifted.
     */
    public Vec3 lBitShift(int n) {
        return new Vec3(x << n, y << n, z << n);
    }

    /**
     * Right bit shifts all bits for each coordinate in this vector.
     * 
     * @param n - The number of positions to bit shift.
     * @return A new vector with the coordinates bit shifted.
     */
    public Vec3 rBitShift(int n) {
        return new Vec3(x >> n, y >> n, z >> n);
    }

    /**
     * Multiplies each coordinate in this vector with the corresponding coordinates
     * in another vector.
     * 
     * @param other - The vector to multiply against.
     * @return A new vector with the coordinates multiplied.
     */
    public Vec3 multiply(Vec3 other) {
        return new Vec3(x * other.x, y * other.y, z * other.z);
    }

    /**
     * Checks if this vector lies within the given bounds, inclusive.
     * 
     * @param min - The minimum bounds position.
     * @param max - The maximum bounds position.
     * @return True if this position is within the indicated bounds. False
     *         otherwise.
     */
    public boolean isInBounds(Vec3 min, Vec3 max) {
        if (x < min.x || x > max.x)
            return false;

        if (y < min.y || y > max.y)
            return false;

        if (z < min.z || z > max.z)
            return false;

        return true;
    }

    /**
     * Returns a new Vec3f that with the same xyz components as this vector.
     * 
     * @return A Vec3f version of this vector.
     */
    public Vec3f asVec3f() {
        return new Vec3f(x, y, z);
    }
}
