package net.whg.utils.math;

/**
 * An immutable class representing a 3D rotation and direction in space.
 */
public class Quaternion {
    public static final Quaternion IDENTITY = new Quaternion(0, 0, 0, 1);

    /**
     * Creates a new quaternion from the given euler angles.
     * 
     * @param yaw   - The yaw, in degrees.
     * @param pitch - The pitch, in degrees.
     * @param roll  - The roll, in degrees.
     * @return A new quaternion for the given euler angles.
     */
    public static Quaternion euler(float yaw, float pitch, float roll) {
        var xR = Math.toRadians(-pitch) * 0.5;
        var yR = Math.toRadians(-yaw + 180) * 0.5;
        var zR = Math.toRadians(-roll) * 0.5;

        var s = new Vec3f((float) Math.sin(xR), (float) Math.sin(yR), (float) Math.sin(zR));
        var c = new Vec3f((float) Math.cos(xR), (float) Math.cos(yR), (float) Math.cos(zR));

        float x = s.x * c.y * c.z - s.y * s.z * c.x;
        float y = s.y * c.x * c.z + s.x * s.z * c.y;
        float z = s.z * c.x * c.y - s.x * s.y * c.z;
        float w = c.x * c.y * c.z + s.y * s.z * s.x;
        return new Quaternion(x, y, z, w).normalize();
    }

    public final float x;
    public final float y;
    public final float z;
    public final float w;

    /**
     * Creates a new Quaternion object.
     * 
     * @param x - The x component.
     * @param y - The y component.
     * @param z - The z component.
     * @param w - The w component.
     */
    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Multiplies this quaternion with another quaternion.
     * 
     * @param other - The quaternion to multiply against.
     * @return A new quaternion containing the result.
     */
    public Quaternion multiply(Quaternion other) {
        return new Quaternion( //
                w * other.x + x * other.w + y * other.z - z * other.y,
                w * other.y + y * other.w + z * other.x - x * other.z,
                w * other.z + z * other.w + x * other.y - y * other.x,
                w * other.w - x * other.x - y * other.y - z * other.z);
    }

    /**
     * Multiplies this quaternion against a point and returns the point rotated
     * around 0,0,0.
     * 
     * @param point - The point or vector to rotate.
     * @return The rotated vector.
     */
    public Vec3f multiply(Vec3f point) {
        var u = new Vec3f(x, y, z);
        var t = u.cross(point).multiply(2f);
        return point.add(t.multiply(w)).add(u.cross(t));
    }

    /**
     * Converts this quaternion into a direction object.
     * 
     * @return This direction represented as a direction.
     */
    public Direction toDirection() {
        return new Direction(getYaw(), getPitch());
    }

    /**
     * Gets the yaw value of this Quaternion.
     * 
     * @return The yaw, in degrees.
     */
    public float getYaw() {
        double sinyCosp = 2 * (w * z + x * y);
        double cosyCopy = 1 - 2 * (y * y + z * z);
        return (float) Math.toDegrees(Math.atan2(sinyCosp, cosyCopy));
    }

    /**
     * Gets the pitch value of this Quaternion.
     * 
     * @return The pitch, in degrees.
     */
    public float getPitch() {
        double sinp = 2 * (w * y - z * x);

        if (Math.abs(sinp) >= 1)
            return (float) Math.toDegrees(Math.copySign(Math.PI / 2, sinp));
        else
            return (float) Math.toDegrees(Math.asin(sinp));
    }

    /**
     * Normalizes this quaternion.
     * 
     * @return A new quaternion that contains the result.
     */
    public Quaternion normalize() {
        var mag = (float) Math.sqrt(x * x + y * y + z * z + w * w);
        return new Quaternion(x / mag, y / mag, z / mag, w / mag);
    }

    /**
     * Returns the dot product between this quaternion another another quaternion.
     * 
     * @param other - The other quaternion.
     * @return The dot product.
     */
    public float dot(Quaternion other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }
}
