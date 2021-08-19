package net.whg.utils.math;

/**
 * An immutable object representing a 4D vector.
 */
public class Vec4f {
    public static final Vec4f ZEROS = new Vec4f(0, 0, 0, 0);
    public static final Vec4f ONES = new Vec4f(1, 1, 1, 1);

    public final float x;
    public final float y;
    public final float z;
    public final float w;

    /**
     * Creates a new Vec4f with the given components.
     * 
     * @param x - The x component.
     * @param y - The y component.
     * @param z - The z component.
     * @param w - The w component.
     */
    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
}
