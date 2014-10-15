package math;

public class Vector3f {
    public double m_x, m_y, m_z;

    public Vector3f() {
    }

    public static Vector3f create_from_two_points(Point3f a, Point3f b) {
        return new Vector3f(a.m_x - b.m_x, a.m_y - b.m_y, a.m_z - b.m_z);
    }

    public Vector3f(double x, double y, double z) {
        m_x = x;
        m_y = y;
        m_z = z;
    }

    public Vector3f(Vector4f v) {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
    }

    public Vector3f(Vector3f v) {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
    }

    public Vector3f(Point3f v) {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
    }

    final public double get_dot(Vector3f v) {
        return m_x * v.m_x + m_y * v.m_y + m_z * v.m_z;
    }

    final public Vector3f get_cross(Vector3f c) {
        return new Vector3f(m_y * c.m_z - m_z * c.m_y, m_z * c.m_x - m_x * c.m_z, m_x * c.m_y - m_y * c.m_x);
    }

    final public Vector3f set_normalize() {
        double l = 1.0 / Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z);
        m_x *= l;
        m_y *= l;
        m_z *= l;
        return this;
    }

    final public Vector3f set_add(Vector3f a) {
        m_x += a.m_x;
        m_y += a.m_y;
        m_z += a.m_z;
        return this;
    }

    final public Vector3f get_sub(Vector3f a) {
        return new Vector3f(m_x - a.m_x, m_y - a.m_y, m_z - a.m_z);
    }

    final public Vector3f set_lerp(Vector3f a, Vector3f b, double f) {
        m_x = Tools.get_lerp(a.m_x, b.m_x, f);
        m_y = Tools.get_lerp(a.m_y, b.m_y, f);
        m_z = Tools.get_lerp(a.m_z, b.m_z, f);
        return this;
    }

    final public Vector3f set(Vector3f v) {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        return this;
    }

    final public Vector3f set_zero() {
        m_x = m_y = m_z = 0.0;
        return this;
    }

    final public Vector3f set(double x, double y, double z) {
        m_x = x;
        m_y = y;
        m_z = z;
        return this;
    }

    final public double get_norme() {
        return Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z);
    }

    final public double get_norm_square() {
        return (m_x * m_x + m_y * m_y + m_z * m_z);
    }

    final public Vector3f set_div(double d) {
        double dd = 1.0f / d;
        m_x *= dd;
        m_y *= dd;
        m_z *= dd;
        return this;
    }

    final public Vector3f set_mul(double d) {
        m_x *= d;
        m_y *= d;
        m_z *= d;
        return this;
    }

    public Vector3f set_mul(Vector3f in, Matrix3x3 m)
    {
        m_x = in.m_x * m.m11 + in.m_y * m.m12 + in.m_z * m.m13;
        m_y = in.m_x * m.m21 + in.m_y * m.m22 + in.m_z * m.m23;
        m_z = in.m_x * m.m31 + in.m_y * m.m32 + in.m_z * m.m33;
        return this;
    }
}

