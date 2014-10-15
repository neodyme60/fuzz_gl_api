package math;

public class Vector3i
{
    public int m_x, m_y, m_z;

    public Vector3i()
    {
    }

    public Vector3i(int x, int y, int z)
    {
        m_x = x;
        m_y = y;
        m_z = z;
    }

    public Vector3i(Point3i a, Point3i b)
    {
        m_x =a.m_x -b.m_x;
        m_y=a.m_y-b.m_y;
        m_z=a.m_z-b.m_z;
    }

    public Vector3i(Vector3i a, Vector3i b, int f)
    {
        m_x = a.m_x + (b.m_x - a.m_x) * f;
        m_y = a.m_y + (b.m_y - a.m_y) * f;
        m_z = a.m_z + (b.m_z - a.m_z) * f;
    }

    public Vector3i(Vector4i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
    }

    public Vector3i(Vector3i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
    }

    public Vector3i(Point3i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
    }

    final public double get_dot(Vector3i v)
    {
        return m_x * v.m_x + m_y * v.m_y + m_z * v.m_z;
    }

    final public Vector3i normalize()
    {
        double l = 1.0 / Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z);
        m_x *= l;
        m_y *= l;
        m_z *= l;
        return this;
    }

    final public Vector3i add(Vector3i a)
    {
        m_x += a.m_x;
        m_y += a.m_y;
        m_z += a.m_z;
        return this;
    }

    final public Vector3i set(Vector3i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        return this;
    }


    final public double get_norme()
    {
        return  Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z);
    }

    final public double get_norm_square()
    {
        return (m_x * m_x + m_y * m_y + m_z * m_z);
    }

    final public Vector3i set_div(double d)
    {
        double dd = 1.0 / d;
        m_x *= dd;
        m_y *= dd;
        m_z *= dd;
        return this;
    }


    final public Vector3i get_div(double d)
    {
        double dd = 1.0 / d;
        return new Vector3i((int)(m_x*dd), (int)(m_y*dd), (int)(m_z*dd));
    }

    final public Vector3i set_mul(double d)
    {
        m_x *= d;
        m_y *= d;
        m_z *= d;
        return this;
    }

    final public Vector3i get_mul(int d)
    {
        return new Vector3i(m_x*d, m_y *d, m_z*d);
    }


}

