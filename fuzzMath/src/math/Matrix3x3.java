package math;


final public class Matrix3x3
{
    /*
     matrix  structure
     l1=(U.m_x, V.m_x, N.m_x)
     l2=(U.m_y, V.m_y, N.m_y)
     l3=(U.m_z, v.m_z, N.m_z)
     */
    public double m11, m12, m13;
    public double m21, m22, m23;
    public double m31, m32, m33;

    public Matrix3x3(double _m11, double _m12, double _m13, double _m21, double _m22, double _m23, double _m31, double _m32, double _m33)
    {
        m11 = _m11;        m12 = _m12;        m13 = _m13;
        m21 = _m21;        m22 = _m22;        m23 = _m23;
        m31 = _m31;        m32 = _m32;        m33 = _m33;
    }

    public Matrix3x3(Matrix3x3 m)
    {
        m11 = m.m11;        m12 = m.m12;        m13 = m.m13;
        m21 = m.m21;        m22 = m.m22;        m23 = m.m23;
        m31 = m.m31;        m32 = m.m32;        m33 = m.m33;
    }

    public Matrix3x3(Matrix4x4 m)
    {
        m11 = m.m11;        m12 = m.m12;        m13 = m.m13;
        m21 = m.m21;        m22 = m.m22;        m23 = m.m23;
        m31 = m.m31;        m32 = m.m32;        m33 = m.m33;
    }

    public Matrix3x3(double[] a)
    {
        m11 = a[0];        m12 = a[1];        m13 = a[2];
        m21 = a[3];        m22 = a[4];        m23 = a[5];
        m31 = a[6];        m32 = a[7];        m33 = a[8];        
    }

    public Matrix3x3()
    {
    }

    public Matrix3x3(Quaternion q)
    {
        set_from_quaternion(q);
    }

    public Matrix3x3 set(double v)
    {
        m11 = m12 = m13 = m21 = m22 = m23 = m31 = m32 = m33 = v;
        return this;
    }

    public Matrix3x3 set(Vector3f v1, Vector3f v2, Vector3f v3)
    {
        m11 = v1.m_x;        m12 = v1.m_y;        m13 = v1.m_z;
        m21 = v2.m_x;        m22 = v2.m_y;        m23 = v2.m_z;
        m31 = v3.m_x;        m32 = v3.m_y;        m33 = v3.m_z;
        return this;
    }

    public Matrix3x3 set_identity()
    {
        m12 = m13 = m21 = m23 = m31 = m32 = 0.0;
        m11 = m22 = m33 = 1.0;
        return this;
    }

    public Matrix3x3 set_scale(Vector3f v)
    {
        m12 = m13 = m21 = m23 = m31 = m32 = 0.0;
        m11 = v.m_x;
        m22 = v.m_y;
        m33 = v.m_z;
        return this;
    }

    public Matrix3x3 set_rotate_x(double angle)
    {
        double cs =  Math.cos(angle);
        double sn =  Math.sin(angle);

        m11 = 1.0;
        m12 = 0.0;
        m13 = 0.0;
        m21 = 0.0;
        m22 = cs;
        m23 = -sn;
        m31 = 0.0;
        m32 = sn;
        m33 = cs;
        return this;
    }

    public Matrix3x3 set_rotate_y(double angle)
    {
        double cs = Math.cos(angle);
        double sn = Math.sin(angle);

        m11 = cs;
        m12 = 0.0;
        m13 = sn;
        m21 = 0.0;
        m22 = 1.0;
        m23 = 0.0;
        m31 = -sn;
        m32 = 0.0;
        m33 = cs;

        return this;
    }

    public Matrix3x3 set_rotate_z(double angle)
    {
        double cs = (double) Math.cos(angle);
        double sn = (double) Math.sin(angle);

        m11 = cs;
        m12 = -sn;
        m13 = 0.0;
        m21 = sn;
        m22 = cs;
        m23 = 0.0;
        m31 = 0.0;
        m32 = 0.0;
        m33 = 1.0;
        return this;
    }

    public Matrix3x3 set_from_quaternion(Quaternion q)
    {
        double s, xs, ys, zs, wx, wy, wz, xx, xy, xz, yy, yz, zz;
        double d;

        d = q.m_x * q.m_x + q.m_y * q.m_y + q.m_z * q.m_z + q.m_w * q.m_w;
        if (d == 0.0)
            s = 1.0;
        else
            s = 2.0 / d;

        xs = q.m_x * s;
        ys = q.m_y * s;
        zs = q.m_z * s;
        wx = q.m_w * xs;
        wy = q.m_w * ys;
        wz = q.m_w * zs;
        xx = q.m_x * xs;
        xy = q.m_x * ys;
        xz = q.m_x * zs;
        yy = q.m_y * ys;
        yz = q.m_y * zs;
        zz = q.m_z * zs;

        m11 = 1.0 - (yy + zz);
        m12 = xy - wz;
        m13 = xz + wy;
        m21 = xy + wz;
        m22 = 1.0 - (xx + zz);
        m23 = yz - wx;
        m31 = xz - wy;
        m32 = yz + wx;
        m33 = 1.0 - (xx + yy);

        return this;
    }

    public double det()
    {
        return (m11 * (m22 * m33 - m32 * m23) - m12 * (m21 * m33 - m31 * m23) + m13 * (m21 * m32 - m31 * m22));
    }

    public Matrix3x3 inverse(Matrix3x3 mr)
    {
        double d = det();
        if (d != 0.0) {
            mr.m11 = (m22 * m33 - m32 * m23) * d;
            mr.m12 = ((-m12 * m33) + m32 * m13) * d;
            mr.m13 = (m12 * m23 - m22 * m13) * d;
            mr.m21 = ((-m21 * m33) + m31 * m23) * d;
            mr.m22 = (m11 * m33 - m31 * m13) * d;
            mr.m23 = ((-m11 * m23) + m21 * m13) * d;
            mr.m31 = (m21 * m32 - m31 * m22) * d;
            mr.m32 = ((-m11 * m32) + m31 * m12) * d;
            mr.m33 = (m11 * m22 - m21 * m12) * d;
        } else {
            // Determinant nul
            mr.m11 = 1.0;
            mr.m12 = 0.0;
            mr.m13 = 0.0;
            mr.m21 = 0.0;
            mr.m22 = 1.0;
            mr.m23 = 0.0;
            mr.m31 = 0.0;
            mr.m32 = 0.0;
            mr.m33 = 1.0;
        }
        return this;
    }

    public static void mulM3(Matrix3x3 A, Matrix3x3 B, Matrix3x3 out)
    {
        double mam11 = A.m11 * B.m11 + A.m12 * B.m21 + A.m13 * B.m31;
        double mam12 = A.m11 * B.m12 + A.m12 * B.m22 + A.m13 * B.m32;
        double mam13 = A.m11 * B.m13 + A.m12 * B.m23 + A.m13 * B.m33;
        double mam21 = A.m21 * B.m11 + A.m22 * B.m21 + A.m23 * B.m31;
        double mam22 = A.m21 * B.m12 + A.m22 * B.m22 + A.m23 * B.m32;
        double mam23 = A.m21 * B.m13 + A.m22 * B.m23 + A.m23 * B.m33;
        double mam31 = A.m31 * B.m11 + A.m32 * B.m21 + A.m13 * B.m31;
        double mam32 = A.m31 * B.m12 + A.m32 * B.m22 + A.m33 * B.m32;
        double mam33 = A.m31 * B.m13 + A.m32 * B.m23 + A.m33 * B.m33;

        out.m11 = mam11;
        out.m12 = mam12;
        out.m13 = mam13;
        out.m21 = mam21;
        out.m22 = mam22;
        out.m23 = mam23;
        out.m31 = mam31;
        out.m32 = mam32;
        out.m33 = mam33;
    }

    public static Matrix3x3 create_ortho_basis(Vector3f up, Point3f at, Point3f eye)
    {
        Matrix3x3 m=new Matrix3x3();
        
        Vector3f d=new Vector3f(at.m_x-eye.m_x, at.m_y-eye.m_y, at.m_z-eye.m_z);
        d.set_normalize();
        
        Vector3f u=new Vector3f(up);
        u.set_normalize();
        
        Vector3f c=d.get_cross(u);
        u=c.get_cross(d);

        d.m_x =-d.m_x;
        d.m_y =-d.m_y;
        d.m_z =-d.m_z;

        m.set(c, u, d);

        return m;
    }    

    public void mulV33Mat3(Vector3f in, Vector3f out)
    {
        double xx = in.m_x * m11 + in.m_y * m12 + in.m_z * m13;
        double yy = in.m_x * m21 + in.m_y * m22 + in.m_z * m23;
        double zz = in.m_x * m31 + in.m_y * m32 + in.m_z * m33;
        out.m_x = xx;
        out.m_y = yy;
        out.m_z = zz;
    }



    public Matrix3x3 setRandom()
    {
        //todo
//        Vector3f up=(Vector3f)getRandomUnitSpherePoint();
  //      Point3f center=new Point3f(0.0,0.0,0.0);
   //     Point3f at=getRandomUnitSpherePoint();
   //     return create_ortho_basis(up,center,at);
        return null;
    }
}

