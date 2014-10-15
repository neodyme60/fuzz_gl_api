package math;

public class Vector4i
{
    public int m_x, m_y, m_z, m_w;

    public Vector4i()
    {
    }

    public Vector4i(int x, int y, int z, int w)
    {
        m_x = x;
        m_y = y;
        m_z = z;
        m_w = w;
    }

    public Vector4i(Vector3i v, int f)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        m_w = f;
    }

    public Vector4i(Vector4i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        m_w = v.m_w;
    }

    final public Vector4i set_mul(int a)
    {
        m_x *= a;
        m_y *= a;
        m_z *= a;
        m_w *= a;
        return this;
    }

    final public Vector4i get_mul(int a)
    {
        return new Vector4i(m_x * a, m_y * a, m_z * a, m_w * a);
    }

    final public Vector4i get_lerp(Vector4i a, Vector4i b, int f)
    {
        return new Vector4i(
                (int)Tools.get_lerp(a.m_x, b.m_x, f),
                (int)Tools.get_lerp(a.m_y, b.m_y, f),
                (int)Tools.get_lerp(a.m_z, b.m_z, f),
                (int)Tools.get_lerp(a.m_w, b.m_w, f));
    }

    final public Vector4i set_lerp(Vector4i a, Vector4i b, int f)
    {
        m_x = (int)Tools.get_lerp(a.m_x, b.m_x, f);
        m_y = (int)Tools.get_lerp(a.m_y, b.m_y, f);
        m_z = (int)Tools.get_lerp(a.m_z, b.m_z, f);
        m_w = (int)Tools.get_lerp(a.m_w, b.m_w, f);
        return this;
    }

    final public Vector4i set_normalize()
    {
        double l = 1.0 /  Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w);
        m_x *= l;
        m_y *= l;
        m_z *= l;
        m_w *= l;
        return this;
    }

    final public Vector4i set_add(Vector4i a)
    {
        m_x += a.m_x;
        m_y += a.m_y;
        m_z += a.m_z;
        m_w += a.m_w;
        return this;
    }

    final public Vector4i set_sub(Vector4i a)
    {
        m_x -= a.m_x;
        m_y -= a.m_y;
        m_z -= a.m_z;
        m_w -= a.m_w;
        return this;
    }

    final public Vector4i get_add(Vector4i a)
    {
        return new Vector4i(m_x + a.m_x, m_y + a.m_y, m_z + a.m_z, m_w + a.m_w);
    }

    final public Vector4i get_sub(Vector4i a)
    {
        return new Vector4i(m_x - a.m_x, m_y - a.m_y, m_z - a.m_z, m_w - a.m_w);
    }

    final public Vector4i set(Vector3i v, int f)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        m_w = f;
        return this;
    }

    final public Vector4i set(Vector4i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        m_w = v.m_w;
        return this;
    }

    final public Vector4i set(int x, int y, int z, int w)
    {
        m_x = x;
        m_y = y;
        m_z = z;
        m_w = w;
        return this;
    }

    final public Vector4i set_zero()
    {
        m_x = m_y = m_z = m_w = 0;
        return this;
    }
}

