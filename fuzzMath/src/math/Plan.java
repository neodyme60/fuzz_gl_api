package math;

final public class Plan
{
    //normal & distance( origine du repere projete perpendiculairement sur le plan)
    public Vector3f m_normale;
    public double m_distance;

    public Plan()
    {
        m_normale =new Vector3f(0.0, 1.0, 0.0);
        m_distance = 0.0;
    }    

    public Vector3f mirroring_vector(Vector3f p)
    {
        //http://www.gamedev.net/community/forums/topic.asp?topic_id=411626
        //http://knol.google.com/k/mirroring-a-point-on-a-3d-plane
        
        Vector3f n=new Vector3f(m_normale).set_normalize();
        double d=(n.get_dot(p));
        return new Vector3f(p.m_x -2.0f*n.m_x *d, p.m_y -2.0f*n.m_y *d, p.m_z -2.0f*n.m_z *d);
    }

    public Plan swap_normal()
    {
        m_normale.m_x = -m_normale.m_x;
        m_normale.m_y = -m_normale.m_y;
        m_normale.m_z = -m_normale.m_z;
        return this;
    }

    public Vector3f get_normal()
    {
        return m_normale;
    }

    public Plan set_normal(Vector3f _v)
    {
        m_normale.m_x = _v.m_x;
        m_normale.m_y = _v.m_y;
        m_normale.m_z = _v.m_z;
        return this;
    }

    public Plan set_distance(double distance)
    {
        m_distance = distance;
        return this;
    }

    public double get_distance_from_point(Point3f p)
    {
        return get_normal().get_dot(new Vector3f(p));
    }

    public Plan setFrom3Point(Point3f v1, Point3f v2, Point3f v3)
    {
        Vector3f a=Vector3f.create_from_two_points(v1, v2).set_normalize();
        Vector3f b=Vector3f.create_from_two_points(v3, v2).set_normalize();
        m_normale = (a.get_cross(b)).set_normalize();
        m_distance = -get_distance_from_point(v1);
        return this;
    }
}