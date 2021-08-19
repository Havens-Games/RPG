package net.whg.utils.math;

/**
 * An immutable object representing a 4x4 matrix.
 */
public class Mat4f {
    public static final Mat4f IDENTITY = new Mat4f();

    /**
     * Creates a new scaling matrix.
     * 
     * @param scale - The scale along each axis.
     * @return The new scaling matrix.
     */
    public static Mat4f scalingMatrix(Vec3f scale) {
        return new Mat4f( //
                scale.x, 0f, 0f, 0f, //
                0f, scale.y, 0f, 0f, //
                0f, 0f, scale.z, 0f, //
                0f, 0f, 0f, 1f);
    }

    /**
     * Creates a new rotation matrix from a quaternion rotation. The quaternion is
     * assumed to be normalized.
     * 
     * @param rot - The rotation quaternion for this matrix.
     * @return The new rotation matrix.
     */
    public static Mat4f rotationMatrix(Quaternion rot) {
        float x = rot.x * 2f;
        float y = rot.y * 2f;
        float z = rot.z * 2f;
        float xx = rot.x * x;
        float yy = rot.y * y;
        float zz = rot.z * z;
        float xy = rot.x * y;
        float xz = rot.x * z;
        float yz = rot.y * z;
        float wx = rot.w * x;
        float wy = rot.w * y;
        float wz = rot.w * z;

        return new Mat4f( //
                1f - (yy + zz), xy + wz, xz - wy, 0f, //
                xy - wz, 1f - (xx + zz), yz + wx, 0f, //
                xz + wy, yz - wx, 1f - (xx + yy), 0f, //
                0f, 0f, 0f, 1f);
    }

    /**
     * Creates a new translation matrix.
     * 
     * @param offset - The translation offset for this matrix.
     * @return A new translation matrix.
     */
    public static Mat4f translationMatrix(Vec3f offset) {
        return new Mat4f( //
                1f, 0f, 0f, offset.x, //
                0f, 1f, 0f, offset.y, //
                0f, 0f, 1f, offset.z, //
                0f, 0f, 0f, 1f);
    }

    private final float m00;
    private final float m01;
    private final float m02;
    private final float m03;
    private final float m10;
    private final float m11;
    private final float m12;
    private final float m13;
    private final float m20;
    private final float m21;
    private final float m22;
    private final float m23;
    private final float m30;
    private final float m31;
    private final float m32;
    private final float m33;

    /**
     * Creates a new identity matrix.
     */
    public Mat4f() {
        m00 = 1;
        m01 = 0;
        m02 = 0;
        m03 = 0;
        m10 = 0;
        m11 = 1;
        m12 = 0;
        m13 = 0;
        m20 = 0;
        m21 = 0;
        m22 = 1;
        m23 = 0;
        m30 = 0;
        m31 = 0;
        m32 = 0;
        m33 = 1;
    }

    /**
     * Creates a new matrix from the given components.
     */
    public Mat4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20,
            float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    /**
     * Creates a 4x4 matrix from a float array with a length of 16. Values are
     * sorted by columns then by rows.
     * 
     * @param values - The values to place in the matrix.
     * @throws IllegalArgumentException If the float array has a length other than
     *                                  16.
     */
    public Mat4f(float[] values) {
        if (values.length != 16)
            throw new IllegalArgumentException("Values array must have a length of 16!");

        m00 = values[0];
        m01 = values[1];
        m02 = values[2];
        m03 = values[3];
        m10 = values[4];
        m11 = values[5];
        m12 = values[6];
        m13 = values[7];
        m20 = values[8];
        m21 = values[9];
        m22 = values[10];
        m23 = values[11];
        m30 = values[12];
        m31 = values[13];
        m32 = values[14];
        m33 = values[15];
    }

    /**
     * Converts this 4x4 matrix into a float array. Values are sorted by columns
     * then by rows.
     * 
     * @return The float array.
     */
    public float[] asFloatArray() {
        return new float[] { //
                m00, m01, m02, m03, //
                m10, m11, m12, m13, //
                m20, m21, m22, m23, //
                m30, m31, m32, m33 };
    }

    /**
     * Multiplies this matrix against a given 4D vector and returns the result.
     * 
     * @param vec - The vector to multiply against.
     * @return A new vector containing the result.
     */
    public Vec4f multiply(Vec4f vec) {
        return new Vec4f( //
                m00 * vec.x + m01 * vec.y + m02 * vec.z + m03 * vec.w, //
                m10 * vec.x + m11 * vec.y + m12 * vec.z + m13 * vec.w, //
                m20 * vec.x + m21 * vec.y + m22 * vec.z + m23 * vec.w, //
                m30 * vec.x + m31 * vec.y + m32 * vec.z + m33 * vec.w);
    }

    /**
     * Multiplies this matrix against a given position.
     * 
     * @param pos - The position vector.
     * @return A new position vector containing the result.
     */
    public Vec3f multiplyPosition(Vec3f pos) {
        return new Vec3f( //
                m00 * pos.x + m01 * pos.y + m02 * pos.z + m03, //
                m10 * pos.x + m11 * pos.y + m12 * pos.z + m13, //
                m20 * pos.x + m21 * pos.y + m22 * pos.z + m23);
    }

    /**
     * Multiplies this matrix against a given direction.
     * 
     * @param dir - The direction vector.
     * @return A new direction vector containing the result.
     */
    public Vec3f multiplyDirection(Vec3f dir) {
        return new Vec3f( //
                m00 * dir.x + m01 * dir.y + m02 * dir.z, //
                m10 * dir.x + m11 * dir.y + m12 * dir.z, //
                m20 * dir.x + m21 * dir.y + m22 * dir.z);
    }

    /**
     * Multiplies this matrix against another matrix and returns the result.
     * 
     * @param mat - The matrix to multiply against.
     * @return A new matrix containing the result.
     */
    public Mat4f multiply(Mat4f other) {
        return new Mat4f( //
                m00 * other.m00 + m01 * other.m10 + m02 * other.m20 + m03 * other.m30,
                m00 * other.m01 + m01 * other.m11 + m02 * other.m21 + m03 * other.m31,
                m00 * other.m02 + m01 * other.m12 + m02 * other.m22 + m03 * other.m32,
                m00 * other.m03 + m01 * other.m13 + m02 * other.m23 + m03 * other.m33,

                m10 * other.m00 + m11 * other.m10 + m12 * other.m20 + m13 * other.m30,
                m10 * other.m01 + m11 * other.m11 + m12 * other.m21 + m13 * other.m31,
                m10 * other.m02 + m11 * other.m12 + m12 * other.m22 + m13 * other.m32,
                m10 * other.m03 + m11 * other.m13 + m12 * other.m23 + m13 * other.m33,

                m20 * other.m00 + m21 * other.m10 + m22 * other.m20 + m23 * other.m30,
                m20 * other.m01 + m21 * other.m11 + m22 * other.m21 + m23 * other.m31,
                m20 * other.m02 + m21 * other.m12 + m22 * other.m22 + m23 * other.m32,
                m20 * other.m03 + m21 * other.m13 + m22 * other.m23 + m23 * other.m33,

                m30 * other.m00 + m31 * other.m10 + m32 * other.m20 + m33 * other.m30,
                m30 * other.m01 + m31 * other.m11 + m32 * other.m21 + m33 * other.m31,
                m30 * other.m02 + m31 * other.m12 + m32 * other.m22 + m33 * other.m32,
                m30 * other.m03 + m31 * other.m13 + m32 * other.m23 + m33 * other.m33);
    }
}
