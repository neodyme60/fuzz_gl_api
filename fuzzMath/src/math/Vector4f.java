package math;

public class Vector4f
{
    public double m_x, m_y, m_z, m_w;

    public Vector4f()
    {
    }

    final public Vector4f set_lerp(Vector4f a, Vector4f b, double f)
    {
        m_x = Tools.get_lerp(a.m_x, b.m_x, f);
        m_y = Tools.get_lerp(a.m_y, b.m_y, f);
        m_z = Tools.get_lerp(a.m_z, b.m_z, f);
        m_w = Tools.get_lerp(a.m_w, b.m_w, f);
        return this;
    }

    final public Vector4f get_lerp(Vector4f a, Vector4f b, double f)
    {
        return new Vector4f(
                Tools.get_lerp(a.m_x, b.m_x, f),
                Tools.get_lerp(a.m_y, b.m_y, f),
                Tools.get_lerp(a.m_z, b.m_z, f),
                Tools.get_lerp(a.m_w, b.m_w, f));
    }

    public Vector4f(double _x, double _y, double _z, double _w)
    {
        m_x = _x;
        m_y = _y;
        m_z = _z;
        m_w = _w;
    }

    public Vector4f(Vector3f v, double f)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        m_w = f;
    }

    public Vector4f(Vector4f v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        m_w = v.m_w;
    }

    public Vector4f set_mul(Vector4f in, Matrix4x4 m)
    {
        m_x = in.m_x * m.m11 + in.m_y * m.m12 + in.m_z * m.m13 + in.m_w * m.m14;
        m_y = in.m_x * m.m21 + in.m_y * m.m22 + in.m_z * m.m23 + in.m_w * m.m24;
        m_z = in.m_x * m.m31 + in.m_y * m.m32 + in.m_z * m.m33 + in.m_w * m.m34;
        m_w = in.m_x * m.m41 + in.m_y * m.m42 + in.m_z * m.m43 + in.m_w * m.m44;
        return this;
    }

    final public Vector4f set_mul(double a)
    {
        m_x *= a;
        m_y *= a;
        m_z *= a;
        m_w *= a;
        return this;
    }

    final public Vector4f get_mul(double a)
    {
        return new Vector4f(
                m_x * a,
                m_y * a,
                m_z * a,
                m_w * a);
    }

    final public Vector4f set_normalize()
    {
        double l = 1.0 /  Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w);
        m_x *= l;
        m_y *= l;
        m_z *= l;
        m_w *= l;
        return this;
    }

    final public Vector4f set_add(Vector4f a)
    {
        m_x += a.m_x;
        m_y += a.m_y;
        m_z += a.m_z;
        m_w += a.m_w;
        return this;
    }

    final public Vector4f set_sub(Vector4f a)
    {
        m_x -= a.m_x;
        m_y -= a.m_y;
        m_z -= a.m_z;
        m_w -= a.m_w;
        return this;
    }

    final public Vector4f get_add(Vector4i a)
    {
        return new Vector4f(
                m_x + a.m_x,
                m_y + a.m_y,
                m_z + a.m_z,
                m_w + a.m_w);
    }

    final public Vector4f get_sub(Vector4i a)
    {
        return new Vector4f(
                m_x - a.m_x,
                m_y - a.m_y,
                m_z - a.m_z,
                m_w - a.m_w);
    }

    final public Vector4f set(Vector3f v, double f)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        m_w = f;
        return this;
    }

    final public Vector4f set(Vector4f v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        m_w = v.m_w;
        return this;
    }

    final public Vector4f set_zero()
    {
        m_x = m_y = m_z = m_w = 0.0f;
        return this;
    }

    final public Vector4f set(double x, double y, double z, double w)
    {
        m_x = x;
        m_y = y;
        m_z = z;
        m_w = w;
        return this;
    }
}

