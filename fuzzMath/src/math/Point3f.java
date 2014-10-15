package math;

public class Point3f
{
    public double m_x, m_y, m_z;

    public Point3f()
    {
        m_x = m_y = m_z = 0.0f;
    }

    public Point3f(double x, double y, double z)
    {
        m_x = x;
        m_y = y;
        m_z = z;
    }

    public Point3f set_mul(Vector4f in, Matrix4x4 m)
    {
        m_x = in.m_x * m.m11 + in.m_y * m.m12 + in.m_z * m.m13 + in.m_w * m.m14;
        m_y = in.m_x * m.m21 + in.m_y * m.m22 + in.m_z * m.m23 + in.m_w * m.m24;
        m_z = in.m_x * m.m31 + in.m_y * m.m32 + in.m_z * m.m33 + in.m_w * m.m34;
        double inv_w = 1.0/(in.m_x * m.m41 + in.m_y * m.m42 + in.m_z * m.m43 + in.m_w * m.m44);

        m_x*= inv_w;
        m_y*= inv_w;
        m_z*= inv_w;

        return this;
    }
}
