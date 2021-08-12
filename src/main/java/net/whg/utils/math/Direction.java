package net.whg.utils.math;

/**
 * Stores a immutable direction vector that can be converted to a yaw and pitch
 * and used in a location variable. The yaw and pitch are stored in degrees to
 * confirm to Bukkit standards.
 */
public class Direction {
    public final float yaw;
    public final float pitch;
    public final Vec3f vec;

    /**
     * Creates a new Direction object from a given yaw and pitch. The directional
     * vector is automatically calculated.
     * 
     * @param yaw   - The yaw value in degrees.
     * @param pitch - The pitch value in degrees.
     */
    public Direction(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;

        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);

        double csPitch = Math.cos(pitchRad);
        double snPitch = Math.sin(pitchRad);
        double csYaw = Math.cos(yawRad);
        double snYaw = Math.sin(yawRad);

        float x = (float) (-snYaw * csPitch);
        float y = (float) snPitch;
        float z = (float) (-csYaw * csPitch);

        vec = new Vec3f(x, y, z);
    }

    /**
     * Creates a new Direction object from a given direction vector. The yaw and
     * pitch are automatically calculated.
     * 
     * @param vec - The direction vector.
     */
    public Direction(Vec3f vec) {
        this.vec = vec;

        double tau = 2 * Math.PI;
        double theta = Math.atan2(-vec.x, vec.z);
        yaw = (float) Math.toDegrees((theta + tau) % tau);

        double xz = Math.sqrt(vec.x * vec.x + vec.z * vec.z);
        pitch = (float) Math.toDegrees(Math.atan(-vec.y / xz));
    }
}
