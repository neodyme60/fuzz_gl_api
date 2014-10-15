package math;

public class Vector2f
{
    public double m_x, m_y;

    public Vector2f()
    {
        m_x = m_y = 0;
    }

    public Vector2f(Point2f a, Point2f b)
    {
        m_x =a.m_x -b.m_x;
        m_y =a.m_y -b.m_y;
    }

    public Vector2f(double x, double y)
    {
        m_x = x;
        m_y = y;
    }

    public Vector2f(Point2f v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
    }

    public Vector2f(Vector3f v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
    }

    public Vector2f(Vector4f v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
    }
    
}
