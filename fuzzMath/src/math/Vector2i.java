package math;

public class Vector2i
{
    public int m_x, m_y;

    public Vector2i()
    {
        m_x = m_y = 0;
    }

    public Vector2i(Point2i a, Point2i b)
    {
        m_x =a.m_x -b.m_x;
        m_y=a.m_y-b.m_y;
    }

    public Vector2i(int x, int y)
    {
        m_x = x;
        m_y = y;
    }

    public Vector2i(Point2i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
    }

    public Vector2i(Vector3i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
    }

    public Vector2i(Vector4i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
    }
    
    public Vector2i set_add(Vector2i v)
    {
        m_x += v.m_x;
        m_y += v.m_y;
        return this;
    }

    public Vector2i set(Vector2i v)
    {
        m_x = v.m_x;
        m_y = v.m_y;
        return this;
    }

    public Vector2i set(int x,int y)
    {
        m_x = x;
        m_y = y;
        return this;
    }

}
