package net.whg.utils.math;

/**
 * An immutable 3D point in space.
 */
public final class Vec3f {
    public static final Vec3f ZEROS = new Vec3f(0, 0, 0);
    public static final Vec3f ONES = new Vec3f(1, 1, 1);

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
     * Multiplies each coordinate in this vector with the given scalar value.
     * 
     * @param scalar - The scalar to multiply against.
     * @return A new vector with the coordinates multiplied.
     */
    public Vec3f multiply(float scalar) {
        return new Vec3f(x * scalar, y * scalar, z * scalar);
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

    /**
     * Gets the dot product between this vector and another vector.
     * 
     * @param other - The other vector.
     * @return The dot product.
     */
    public float dot(Vec3f other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Gets the cross product between this vector and another vector.
     * 
     * @param other - The other vector.
     * @return The cross product.
     */
    public Vec3f cross(Vec3f other) {
        return new Vec3f(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }
}
