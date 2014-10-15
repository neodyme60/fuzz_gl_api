package math;

import java.util.Random;

final public class Matrix4x4
{
    /*
     matrix  structure: column major
     l1=(U.m_x, V.m_x, N.m_x, T.m_x)
     l2=(U.m_y, V.m_y, N.m_y, T.m_y)
     l3=(U.m_z, v.m_z, N.m_z, T.m_z)
     */
    public double m11, m12, m13, m14;
    public double m21, m22, m23, m24;
    public double m31, m32, m33, m34;
    public double m41, m42, m43, m44;

    public static double TO_RAD=Math.PI/180.0;

    public Matrix4x4(
            double _m11, double _m12, double _m13, double _m14, double _m21, double _m22, double _m23, double _m24,
            double _m31, double _m32, double _m33, double _m34, double _m41, double _m42, double _m43, double _m44)
    {
        m11 = _m11;        m12 = _m12;        m13 = _m13;        m14 = _m14;
        m21 = _m21;        m22 = _m22;        m23 = _m23;        m24 = _m24;
        m31 = _m31;        m32 = _m32;        m33 = _m33;        m34 = _m34;
        m41 = _m41;        m42 = _m42;        m43 = _m43;        m44 = _m44;
    }

    public Matrix4x4(Matrix4x4 m)
    {
        m11 = m.m11;        m12 = m.m12;        m13 = m.m13;        m14 = m.m14;
        m21 = m.m21;        m22 = m.m22;        m23 = m.m23;        m24 = m.m24;
        m31 = m.m31;        m32 = m.m32;        m33 = m.m33;        m34 = m.m34;
        m41 = m.m41;        m42 = m.m42;        m43 = m.m43;        m44 = m.m44;
    }

    public Matrix4x4(Vector4f v1, Vector4f v2, Vector4f v3, Vector4f v4)
    {
        m11 = v1.m_x;        m12 = v1.m_y;        m13 = v1.m_z;        m14 = v1.m_w;
        m21 = v2.m_x;        m22 = v2.m_y;        m23 = v2.m_z;        m24 = v2.m_w;
        m31 = v3.m_x;        m32 = v3.m_y;        m33 = v3.m_z;        m34 = v3.m_w;
        m41 = v4.m_x;        m42 = v4.m_y;        m43 = v4.m_z;        m44 = v4.m_w;
    }

    public Matrix4x4(Matrix3x3 m)
    {
        m11 = m.m11;        m12 = m.m12;        m13 = m.m13;
        m21 = m.m21;        m22 = m.m22;        m23 = m.m23;
        m31 = m.m31;        m32 = m.m32;        m33 = m.m33;
    }

    public Matrix4x4(double[] a)
    {
        m11 = a[0];        m12 = a[1];        m13 = a[2];   m14=a[3];
        m21 = a[4];        m22 = a[5];        m23 = a[6];   m24=a[7];
        m31 = a[8];        m32 = a[9];        m33 = a[10];  m34=a[11];
        m41 = a[12];       m42 = a[13];       m43 = a[14];  m44=a[15];
    }

    public Matrix4x4(Quaternion q)
    {
        this.set_from_quaternion(q);
    }

    public Matrix4x4()
    {
    }

    public void set(Matrix3x3 m)
    {
        m11 = m.m11;        m12 = m.m12;        m13 = m.m13;
        m21 = m.m21;        m22 = m.m22;        m23 = m.m23;
        m31 = m.m31;        m32 = m.m32;        m33 = m.m33;
    }

    public Matrix4x4 set_random()
    {
        Random r=new Random(new java.util.Date().getTime());
        m11 = r.nextFloat();        m12 = r.nextFloat();        m13 = r.nextFloat();        m14 = r.nextFloat();
        m21 = r.nextFloat();        m22 = r.nextFloat();        m23 = r.nextFloat();        m24 = r.nextFloat();
        m31 = r.nextFloat();        m32 = r.nextFloat();        m33 = r.nextFloat();        m34 = r.nextFloat();
        m41 = r.nextFloat();        m42 = r.nextFloat();        m43 = r.nextFloat();        m44 = r.nextFloat();
        return  this;
    }

    public Matrix4x4 set_transpose()
    {
        Matrix4x4 m = new Matrix4x4(this);
        m11 = m.m11;        m12 = m.m21;        m13 = m.m31;        m14 = m.m41;
        m21 = m.m12;        m22 = m.m22;        m23 = m.m32;        m24 = m.m42;
        m31 = m.m13;        m32 = m.m23;        m33 = m.m33;        m34 = m.m43;
        m41 = m.m14;        m42 = m.m24;        m43 = m.m34;        m44 = m.m44;
        return this;
    }

    public Matrix4x4 get_transpose()
    {
        Matrix4x4 m = new Matrix4x4();
        m.m11 = m11;        m.m12 = m21;        m.m13 = m31;        m.m14 = m41;
        m.m21 = m12;        m.m22 = m22;        m.m23 = m32;        m.m24 = m42;
        m.m31 = m13;        m.m32 = m23;        m.m33 = m33;        m.m34 = m43;
        m.m41 = m14;        m.m42 = m24;        m.m43 = m34;        m.m44 = m44;
        return m;
    }

    public Matrix4x4 set_from_quaternion(Quaternion q)
    {
        Matrix3x3 m=new Matrix3x3(q);
        set_identity();
        set(m);
        return this;
    }
    
    public Matrix4x4 set(double v)
    {
        m11 = m12 = m13 = m14 = m21 = m22 = m23 =m24 = m31 = m32 = m33 = m34 = m41 = m42 = m43 = m44 = v;
        return this;
    }

    public static Matrix4x4 create_perspective(double fov_y, double aspect, double zNear, double zFar)
    {
        Matrix4x4 m=new Matrix4x4();

        double f=1.0/Math.tan(Matrix4x4.TO_RAD*fov_y/2.0);

        m.m11 = f*aspect;
        m.m12 = 0.0;
        m.m13 = 0.0;
        m.m14 = 0.0;

        m.m21 = 0.0;
        m.m22 = f;
        m.m23 = 0.0;
        m.m24 = 0.0;

        m.m31 = 0.0;
        m.m32 = 0.0;
        m.m33 = (zNear+zFar)/(zNear-zFar);
        m.m34 = (2.0*zNear*zFar)/(zNear-zFar);

        m.m41 = 0.0;
        m.m42 = 0.0;
        m.m43 = -1.0;
        m.m44 = 0.0;

        return m;
    }

    public static Matrix4x4 create_orthonormal_2d(double left, double right, double bottom, double top)
    {
        return create_orthonormal(left, right, bottom, top, 0.0, 1.0);
    }

    public static Matrix4x4 create_orthonormal(double left, double right, double bottom, double top, double near, double far)
    {
        Matrix4x4 m=new Matrix4x4();

        double x = 2.0 / (right - left);
        double y = 2.0 / (top - bottom);
        double z = -2.0 / (far - near);
        double tx= (right+left)/(right-left);
        double ty= (top+bottom)/(top-bottom);
        double tz= (far+near)/(far-near);

        m.m11 = x;     m.m12 = 0.0;     m.m13 = 0.0;    m.m14 = tx;
        m.m21 = 0.0;   m.m22 = y;       m.m23 = 0.0;    m.m24 = ty;
        m.m31 = 0.0;   m.m32 = 0.0;     m.m33 = z;      m.m34 = tz;
        m.m41 = 0.0;   m.m42 = 0.0;     m.m43 = 0.0;    m.m44 = 1.0;

        return m;
    }

    public static Matrix4x4 create_frustum(double left, double right, double bottom, double top, double zNear, double zFar)
    {
        Matrix4x4 m=new Matrix4x4();

        double x = (zNear + zNear) / (right - left);
        double y = (zNear + zNear) / (top - bottom);
        double a = (right + left) / (right - left);
        double b = (top + bottom) / (top - bottom);
        double c = -(zFar + zNear) / (zFar - zNear);//<=>(zFar + zNear) / (zNear - zFar )
        double d = -(2 * zFar * zNear) / (zFar - zNear); //<=> (2 * zFar * zNear) / (zNear -zFar )

        m.m11 = x;     m.m12 = 0.0;     m.m13 = a;       m.m14 = 0.0;
        m.m21 = 0.0;   m.m22 = y;       m.m23 = b;       m.m24 = 0.0;
        m.m31 = 0.0;   m.m32 = 0.0;     m.m33 = c;       m.m34 = d;
        m.m41 = 0.0;   m.m42 = 0.0;     m.m43 = -1.0;    m.m44 = 0.0;
        return m;
    }

    public static Matrix4x4 create_look_at(double eyex, double eyey, double eyez, double centerx, double centery, double centerz, double upx, double upy, double upz)
    {
        Matrix4x4 O=new Matrix4x4();
        Matrix4x4 T=new Matrix4x4();

        O.set_identity();
        O.set(
                Matrix3x3.create_ortho_basis(
                        new Vector3f(upx, upy, upz),
                        new Point3f(centerx, centery, centerz),
                        new Point3f(eyex, eyey, eyez)
                ));

        //build T matrix
        T.set_translate(-eyex, -eyey, -eyez);

        //build view matrix
        return T.get_mul(O);
    }    

    public Matrix4x4 set_scale(double a, double b, double c)
    {
        m12 = m21 = m31 = m13 = m23 = m32 = m14 = m41 = m42 = m24 = m43 = m34 = 0.0;
        m11 = a;
        m22 = b;
        m33 = c;
        m44 = 1.0;
        return this;
    }

    public Matrix4x4 set_scale(Vector3f v)
    {
        m12 = m21 = m31 = m13 = m23 = m32 = m14 = m41 = m42 = m24 = m43 = m34 = 0.0;
        m11 = v.m_x;
        m22 = v.m_y;
        m33 = v.m_z;
        m44 = 1.0;
        return this;
    }

    public Matrix4x4 set_identity()
    {
        m12 = m21 = m31 = m13 = m23 = m32 = m14 = m41 = m42 = m24 = m43 = m34 = 0.0;
        m11 = m22 = m33 = m44 = 1.0;
        return this;
    }

    public Vector3f getTranslate()
    {
        return new Vector3f(m14, m24, m34);
    }

    public Matrix4x4 set_translate(Vector3f t)
    {
        m12 = m21 = m31 = m13 = m23 = m32 = m41 = m42 = m43 = 0.0;
        m11 = m22 = m33 = m44 = 1.0;
        m14 = t.m_x;
        m24 = t.m_y;
        m34 = t.m_z;
        return this;
    }

    public Matrix4x4 set_translate(double x, double y, double z)
    {
        m12 = m21 = m31 = m13 = m23 = m32 = m41 = m42 = m43 = 0.0;
        m11 = m22 = m33 = m44 = 1.0;
        m14 = x;
        m24 = y;
        m34 = z;
        return this;
    }

    public Matrix4x4 get_mul(Matrix4x4 m)
    {
        Matrix4x4 out=new Matrix4x4();
        
        double mam11 = this.m11 * m.m11 + this.m21 * m.m12 + this.m31 * m.m13 + this.m41 * m.m14;
        double mam12 = this.m12 * m.m11 + this.m22 * m.m12 + this.m32 * m.m13 + this.m42 * m.m14;
        double mam13 = this.m13 * m.m11 + this.m23 * m.m12 + this.m33 * m.m13 + this.m43 * m.m14;
        double mam14 = this.m14 * m.m11 + this.m24 * m.m12 + this.m34 * m.m13 + this.m44 * m.m14;

        double mam21 = this.m11 * m.m21 + this.m21 * m.m22 + this.m31 * m.m23 + this.m41 * m.m24;
        double mam22 = this.m12 * m.m21 + this.m22 * m.m22 + this.m32 * m.m23 + this.m42 * m.m24;
        double mam23 = this.m13 * m.m21 + this.m23 * m.m22 + this.m33 * m.m23 + this.m43 * m.m24;
        double mam24 = this.m14 * m.m21 + this.m24 * m.m22 + this.m34 * m.m23 + this.m44 * m.m24;

        double mam31 = this.m11 * m.m31 + this.m21 * m.m32 + this.m31 * m.m33 + this.m41 * m.m34;
        double mam32 = this.m12 * m.m31 + this.m22 * m.m32 + this.m32 * m.m33 + this.m42 * m.m34;
        double mam33 = this.m13 * m.m31 + this.m23 * m.m32 + this.m33 * m.m33 + this.m43 * m.m34;
        double mam34 = this.m14 * m.m31 + this.m24 * m.m32 + this.m34 * m.m33 + this.m44 * m.m34;

        double mam41 = this.m11 * m.m41 + this.m21 * m.m42 + this.m31 * m.m43 + this.m41 * m.m44;
        double mam42 = this.m12 * m.m41 + this.m22 * m.m42 + this.m32 * m.m43 + this.m42 * m.m44;
        double mam43 = this.m13 * m.m41 + this.m23 * m.m42 + this.m33 * m.m43 + this.m43 * m.m44;
        double mam44 = this.m14 * m.m41 + this.m24 * m.m42 + this.m34 * m.m43 + this.m44 * m.m44;

        out.m11 = mam11;        out.m12 = mam12;        out.m13 = mam13;        out.m14 = mam14;
        out.m21 = mam21;        out.m22 = mam22;        out.m23 = mam23;        out.m24 = mam24;
        out.m31 = mam31;        out.m32 = mam32;        out.m33 = mam33;        out.m34 = mam34;
        out.m41 = mam41;        out.m42 = mam42;        out.m43 = mam43;        out.m44 = mam44;
        return out;
    }

    public Vector4f get_mul(Vector4f in)
    {
        double xx = in.m_x * m11 + in.m_y * m12 + in.m_z * m13 + in.m_w * m14;
        double yy = in.m_x * m21 + in.m_y * m22 + in.m_z * m23 + in.m_w * m24;
        double zz = in.m_x * m31 + in.m_y * m32 + in.m_z * m33 + in.m_w * m34;
        double ww = in.m_x * m41 + in.m_y * m42 + in.m_z * m43 + in.m_w * m44;

        return new Vector4f(xx,yy,zz,ww);
    }

    public double get_det()
    {
        double res = 0.0f, det;
        // 28 muls total - totally inline-expanded and factored
        // Ugly (and nearly incomprehensible) but efficient
        double mr_3344_4334 = m33 * m44 - m43 * m34;
        double mr_3244_4234 = m32 * m44 - m42 * m34;
        double mr_3243_4233 = m32 * m43 - m42 * m33;
        double mr_3144_4134 = m31 * m44 - m41 * m34;
        double mr_3143_4133 = m31 * m43 - m41 * m33;
        double mr_3142_4132 = m31 * m42 - m41 * m32;

        //submat_4x4 (msub3, mr, 0, 0);
        //res += mr._11 * det_3x3 (msub3);
        det = m22 * mr_3344_4334 - m23 * mr_3244_4234 + m24 * mr_3243_4233;
        res += m11 * det;

        //submat_4x4 (msub3, mr, 0, 1);
        //res -= mr._12 * det_3x3 (msub3);
        det = m21 * mr_3344_4334 - m23 * mr_3144_4134 + m24 * mr_3143_4133;
        res -= m12 * det;

        //submat_4x4 (msub3, mr, 0, 2);
        //res += mr._13 * det_3x3 (msub3);
        det = m21 * mr_3244_4234 - m22 * mr_3144_4134 + m24 * mr_3142_4132;
        res += m13 * det;

        //submat_4x4 (msub3, mr, 0, 3);
        //res -= mr._14 * det_3x3 (msub3);
        det = m21 * mr_3243_4233 - m22 * mr_3143_4133 + m23 * mr_3142_4132;
        res -= m14 * det;
        return res;
    }

    /**
     * ***************************************************************************
     * Routine:   submat_4x4
     * Input:     mr  - matrix (4x4) address
     * mb  - matrix (3x3) address
     * i,j - matrix coordinates
     * Output:    returns the 3x3 subset of 'mr' without column 'i' and row 'j'
     * ****************************************************************************
     */
    public void submat_4x4(Matrix3x3 mb, int i, int j)
    {
        // Unrolled - big, but very Tools (one indexed jump, one unconditional jump)
        switch (i * 4 + j) {
            // i == 0
            case 0:     // 0,0
                mb.m11 = m22;
                mb.m12 = m23;
                mb.m13 = m24;
                mb.m21 = m32;
                mb.m22 = m33;
                mb.m23 = m34;
                mb.m31 = m42;
                mb.m32 = m43;
                mb.m33 = m44;
                break;
            case 1:     // 0,1
                mb.m11 = m21;
                mb.m12 = m23;
                mb.m13 = m24;
                mb.m21 = m31;
                mb.m22 = m33;
                mb.m23 = m34;
                mb.m31 = m41;
                mb.m32 = m43;
                mb.m33 = m44;
                break;
            case 2:     // 0,2
                mb.m11 = m21;
                mb.m12 = m22;
                mb.m13 = m24;
                mb.m21 = m31;
                mb.m22 = m32;
                mb.m23 = m34;
                mb.m31 = m41;
                mb.m32 = m42;
                mb.m33 = m44;
                break;
            case 3:     // 0,3
                mb.m11 = m21;
                mb.m12 = m22;
                mb.m13 = m23;
                mb.m21 = m31;
                mb.m22 = m32;
                mb.m23 = m33;
                mb.m31 = m41;
                mb.m32 = m42;
                mb.m33 = m43;
                break;

            // i == 1
            case 4:     // 1,0
                mb.m11 = m12;
                mb.m12 = m13;
                mb.m13 = m14;
                mb.m21 = m32;
                mb.m22 = m33;
                mb.m23 = m34;
                mb.m31 = m42;
                mb.m32 = m43;
                mb.m33 = m44;
                break;
            case 5:     // 1,1
                mb.m11 = m11;
                mb.m12 = m13;
                mb.m13 = m14;
                mb.m21 = m31;
                mb.m22 = m33;
                mb.m23 = m34;
                mb.m31 = m41;
                mb.m32 = m43;
                mb.m33 = m44;
                break;
            case 6:     // 1,2
                mb.m11 = m11;
                mb.m12 = m12;
                mb.m13 = m14;
                mb.m21 = m31;
                mb.m22 = m32;
                mb.m23 = m34;
                mb.m31 = m41;
                mb.m32 = m42;
                mb.m33 = m44;
                break;
            case 7:     // 1,3
                mb.m11 = m11;
                mb.m12 = m12;
                mb.m13 = m13;
                mb.m21 = m31;
                mb.m22 = m32;
                mb.m23 = m33;
                mb.m31 = m41;
                mb.m32 = m42;
                mb.m33 = m43;
                break;

            // i == 2
            case 8:     // 2,0
                mb.m11 = m12;
                mb.m12 = m13;
                mb.m13 = m14;
                mb.m21 = m22;
                mb.m22 = m23;
                mb.m23 = m24;
                mb.m31 = m42;
                mb.m32 = m43;
                mb.m33 = m44;
                break;
            case 9:     // 2,1
                mb.m11 = m11;
                mb.m12 = m13;
                mb.m13 = m14;
                mb.m21 = m21;
                mb.m22 = m23;
                mb.m23 = m24;
                mb.m31 = m41;
                mb.m32 = m43;
                mb.m33 = m44;
                break;
            case 10:    // 2,2
                mb.m11 = m11;
                mb.m12 = m12;
                mb.m13 = m14;
                mb.m21 = m21;
                mb.m22 = m22;
                mb.m23 = m24;
                mb.m31 = m41;
                mb.m32 = m42;
                mb.m33 = m44;
                break;
            case 11:    // 2,3
                mb.m11 = m11;
                mb.m12 = m12;
                mb.m13 = m13;
                mb.m21 = m21;
                mb.m22 = m22;
                mb.m23 = m23;
                mb.m31 = m41;
                mb.m32 = m42;
                mb.m33 = m43;
                break;

            // i == 3
            case 12:    // 3,0
                mb.m11 = m12;
                mb.m12 = m13;
                mb.m13 = m14;
                mb.m21 = m22;
                mb.m22 = m23;
                mb.m23 = m24;
                mb.m31 = m32;
                mb.m32 = m33;
                mb.m33 = m34;
                break;
            case 13:    // 3,1
                mb.m11 = m11;
                mb.m12 = m13;
                mb.m13 = m14;
                mb.m21 = m21;
                mb.m22 = m23;
                mb.m23 = m24;
                mb.m31 = m31;
                mb.m32 = m33;
                mb.m33 = m34;
                break;
            case 14:    // 3,2
                mb.m11 = m11;
                mb.m12 = m12;
                mb.m13 = m14;
                mb.m21 = m21;
                mb.m22 = m22;
                mb.m23 = m24;
                mb.m31 = m31;
                mb.m32 = m32;
                mb.m33 = m34;
                break;
            case 15:    // 3,3
                mb.m11 = m11;
                mb.m12 = m12;
                mb.m13 = m13;
                mb.m21 = m21;
                mb.m22 = m22;
                mb.m23 = m23;
                mb.m31 = m31;
                mb.m32 = m32;
                mb.m33 = m33;
                break;
        }
    }


    /*-------------------------------------------------------------------
        inverse( original_matrix, inverse_matrix )

         calculate the inverse of a 4x4 matrix

          -1
          A  = ___1__ adjoint A
              get_det A

    in:
    out:
    -------------------------------------------------------------------*/
    public int inverse(Matrix4x4 mr)
    {
        double mdet = get_det();
        Matrix3x3 mtemp = new Matrix3x3();

//    if (math.abs (mdet) < 0.0005f)
//        return 0;

        mdet = 1.0 / mdet;

        // row column labeling reversed for out since we transpose rows & columns
        submat_4x4(mtemp, 0, 0);
        mr.m11 = mtemp.det() * mdet;

        submat_4x4(mtemp, 0, 1);
        mr.m21 = -mtemp.det() * mdet;

        submat_4x4(mtemp, 0, 2);
        mr.m31 = mtemp.det() * mdet;

        submat_4x4(mtemp, 0, 3);
        mr.m41 = -mtemp.det() * mdet;


        submat_4x4(mtemp, 1, 0);
        mr.m12 = -mtemp.det() * mdet;

        submat_4x4(mtemp, 1, 1);
        mr.m22 = mtemp.det() * mdet;

        submat_4x4(mtemp, 1, 2);
        mr.m32 = -mtemp.det() * mdet;

        submat_4x4(mtemp, 1, 3);
        mr.m42 = mtemp.det() * mdet;


        submat_4x4(mtemp, 2, 0);
        mr.m13 = mtemp.det() * mdet;

        submat_4x4(mtemp, 2, 1);
        mr.m23 = -mtemp.det() * mdet;

        submat_4x4(mtemp, 2, 2);
        mr.m33 = mtemp.det() * mdet;

        submat_4x4(mtemp, 2, 3);
        mr.m43 = -mtemp.det() * mdet;


        submat_4x4(mtemp, 3, 0);
        mr.m14 = -mtemp.det() * mdet;

        submat_4x4(mtemp, 3, 1);
        mr.m24 = mtemp.det() * mdet;

        submat_4x4(mtemp, 3, 2);
        mr.m34 = -mtemp.det() * mdet;

        submat_4x4(mtemp, 3, 3);
        mr.m44 = mtemp.det() * mdet;

        return 1;

    }

    public void set_rotate_x(double angle)
    {
        Matrix3x3 m=new Matrix3x3();
        m.set_rotate_x(angle);
        set_identity();
        set(m);
    }

    public void set_rotate_y(double angle)
    {
        Matrix3x3 m=new Matrix3x3();
        m.set_rotate_y(angle);
        set_identity();
        set(m);
    }

    public void set_rotate_z(double angle)
    {
        Matrix3x3 m=new Matrix3x3();
        m.set_rotate_z(angle);
        set_identity();
        set(m);
    }


    public Matrix4x4 set_rotation(Vector3f rotation)
	{
		double cr = Math.cos( rotation.m_x *TO_RAD );
		double sr = Math.sin( rotation.m_x *TO_RAD );
		double cp = Math.cos( rotation.m_y *TO_RAD );
		double sp = Math.sin( rotation.m_y *TO_RAD );
		double cy = Math.cos( rotation.m_z *TO_RAD );
		double sy = Math.sin( rotation.m_z *TO_RAD );

		m11 = ( cp*cy );
		m12 = ( cp*sy );
		m13 = ( -sp );

		double srsp = sr*sp;
		double crsp = cr*sp;

		m21 = ( srsp*cy-cr*sy );
		m22 = ( srsp*sy+cr*cy );
		m23 = ( sr*cp );

		m31 = ( crsp*cy+sr*sy );
		m32 = ( crsp*sy-sr*cy );
		m33 = ( cr*cp );

        return this;
	}

    //http://www.gamedev.net/reference/articles/article1691.asp#Q38
    public void set_rotate(double angle, double x, double y, double z)
    {
        double cs = Math.cos(angle*TO_RAD);
        double sn = Math.sin(angle*TO_RAD);
        double omcs = 1.0 - cs;
        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;
        double xym = x * y * omcs;
        double xzm = x * z * omcs;
        double yzm = y * z * omcs;
        double xsin = x * sn;
        double ysin = y * sn;
        double zsin = z * sn;

        m11 = x2 * omcs + cs;
        m12 = xym - zsin;
        m13 = xzm + ysin;
        m21 = xym + zsin;
        m22 = y2 * omcs + cs;
        m23 = yzm - xsin;
        m31 = xzm - ysin;
        m32 = yzm + xsin;
        m33 = z2 * omcs + cs;
        m41 = m42 = m43 = 0.0;
        m44 = 1.0;
    }
}

