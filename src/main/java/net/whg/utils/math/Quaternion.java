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

    /**
     * Creates a new quaternion from a direction vector.
     * 
     * @param forward - The forward direction.
     * @return A quaternion that looks in the given direction.
     */
    public static Quaternion lookDirection(Vec3f forward) {
        var dot = Vec3f.FORWARD.dot(forward);

        if (Math.abs(dot - (-1.0f)) < 0.000001f) {
            return new Quaternion(0, 1, 0, (float) Math.PI);
        }
        if (Math.abs(dot - (1.0f)) < 0.000001f) {
            return new Quaternion(0, 0, 0, 1);
        }

        var rotAngle = (float) Math.acos(dot);
        var rotAxis = Vec3f.FORWARD.cross(forward).normalized();
        return fromAxisAngle(rotAxis, (float) Math.toDegrees(rotAngle));
    }

    /**
     * Creates a new quaternion from a rotation around a given axis.
     * 
     * @param axis  - The axis of rotation.
     * @param angle - The angle, in degrees, to rotate around the axis.
     * @return A new quaternion.
     */
    public static Quaternion fromAxisAngle(Vec3f axis, float angle) {
        float sinHalf = (float) Math.sin(Math.toRadians(angle / 2));
        float cosHalf = (float) Math.cos(Math.toRadians(angle / 2));

        return new Quaternion(axis.x * sinHalf, axis.y * sinHalf, axis.z * sinHalf, cosHalf);
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
     * Gets the pitch value of this Quaternion.
     * 
     * @return The pitch, in degrees.
     */
    public float getPitch() {
        var forward = multiply(Vec3f.FORWARD);
        return (float) Math.toDegrees(Math.asin(-forward.y));
    }

    /**
     * Gets the yaw value of this Quaternion.
     * 
     * @return The yaw, in degrees.
     */
    public float getYaw() {
        var forward = multiply(Vec3f.FORWARD);
        return (float) Math.toDegrees(Math.atan2(forward.x, forward.z));
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
