package math;


public class Quaternion extends Vector4f
{

    /*-------------------------------------------------------------------
    in: 4 value for quaternion
    out: create a quaternion by the for values
    -------------------------------------------------------------------*/
    public Quaternion(double _x, double _y, double _z, double _w) {
        m_x = _x;
        m_y = _y;
        m_z = _z;
        m_w = _w;
    }

    /*-------------------------------------------------------------------
    in: 1 double &  1 vector
    out: create a quaternion by vector & double
    -------------------------------------------------------------------*/
    public Quaternion(Vector3f _v, double _w) {
        m_x = _v.m_x;
        m_y = _v.m_y;
        m_z = _v.m_z;
        m_w = _w;
    }

    /*-------------------------------------------------------------------
    in: double ptr
    out: create a quaternion by the 4 double pointed by ptr !!no check 4 ptr!=null
    -------------------------------------------------------------------*/
    public Quaternion(double _v[]) {
        m_x = _v[0];
        m_y = _v[1];
        m_z = _v[2];
        m_w = _v[3];
    }

    /*-------------------------------------------------------------------
    in:
    out:  create identity quat
    -------------------------------------------------------------------*/
    public Quaternion(Vector4f v) {
        m_x = v.m_x;
        m_y = v.m_y;
        m_z = v.m_z;
        m_w = v.m_w;
    }

    /*-------------------------------------------------------------------
    in:
    out:  create identity quat
    -------------------------------------------------------------------*/
    public Quaternion() {
        m_x = m_y = m_z = 0.0;
        m_w = 1.0;
    }

    /*-------------------------------------------------------------------
    //Compute equivalent quaternion from [angle,axis] representation
    in: angle in rad, and axis
    out:  create from euler representation
    -------------------------------------------------------------------*/
    public Quaternion(double angle, Vector3f axis) {
        double halfAngle = angle * 0.5;
        double s = Math.sin(halfAngle);
        m_x = axis.m_x * s;
        m_y = axis.m_y * s;
        m_z = axis.m_z * s;
        m_w = Math.cos(halfAngle);
    }

    //***************************************************
    public static void mulQ(Quaternion a, Quaternion b, Quaternion out)
    {
        double _w = a.m_w * b.m_w - a.m_x * b.m_x - a.m_y * b.m_y - a.m_z * b.m_z;
        double _x = a.m_w * b.m_x + a.m_x * b.m_w + a.m_y * b.m_z - a.m_z * b.m_y;
        double _y = a.m_w * b.m_y + a.m_y * b.m_w + a.m_z * b.m_x - a.m_x * b.m_z;
        double _z = a.m_w * b.m_z + a.m_z * b.m_w + a.m_x * b.m_y - a.m_y * b.m_x;

        out.setQ(_x, _y, _z, _w);

    }

    //***************************************************
    public Quaternion add(Quaternion q) {
        m_x += q.m_x;
        m_y += q.m_y;
        m_z += q.m_z;
        m_w += q.m_w;
        return this;
    }

    //***************************************************
    public Quaternion setQ(Quaternion q) {
        m_x = q.m_x;
        m_y = q.m_y;
        m_z = q.m_z;
        m_w = q.m_w;
        return this;
    }

    //***************************************************
    public Quaternion setQ(double _x, double _y, double _z, double _w) {
        m_x = _x;
        m_y = _y;
        m_z = _z;
        m_w = _w;
        return this;
    }

    //***************************************************
    public Quaternion sub(Quaternion q) {
        m_x -= q.m_x;
        m_y -= q.m_y;
        m_z -= q.m_z;
        m_w -= q.m_w;
        return this;
    }

    //***************************************************
    public Quaternion mulf(double f) {
        m_x *= f;
        m_y *= f;
        m_z *= f;
        m_w *= f;
        return this;
    }

    //***************************************************
    public double dot(Quaternion q)  //get_dot unit product
    {
        return (m_x * q.m_x + m_y * q.m_y + m_z * q.m_z + m_w * q.m_w);
    }

    //***************************************************
    public double dotUnit(Quaternion q)  //get_dot unit product
    {
        return (m_x * q.m_x + m_y * q.m_y + m_z * q.m_z + m_w * q.m_w);
    }

    /**
     * ************************************************
     * quaternion conjuge
     * !!! must be unitaire
     * *************************************************
     */
    public Quaternion conj() {
        m_x = -m_x;
        m_y = -m_y;
        m_z = -m_z; //w=w
        return this;
    }

    //***************************************************
    public Quaternion neg() {
        m_x = -m_x;
        m_y = -m_y;
        m_z = -m_z;
        m_w = -m_w;
        return this;
    }

    /*-------------------------------------------------------------------
    //normaliz this
    in:
    out
    -------------------------------------------------------------------*/
    public Quaternion qnormalize() {
        double l, c;
        l = m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w;
        if (l < 0.001)
            return setIdentity();
        return mulf(1.0 / l);
    }

    /*-------------------------------------------------------------------
    in:
    out:  set this to identity
    -------------------------------------------------------------------*/
    public Quaternion setIdentity() {
        m_x = m_y = m_z = 0.0;
        m_w = 1.0;
        return this;
    }

    /*-------------------------------------------------------------------
    //square a quaternion
    in:
    out:
    -------------------------------------------------------------------*/
    public static Quaternion q = new Quaternion();

    Quaternion sq() {
        q.m_w = m_w * m_w;
        q.m_w -= q.dot(q);
        q.m_x = m_x * 2.0 * q.m_w;
        q.m_y = m_y * 2.0 * q.m_w;
        q.m_z = m_z * 2.0 * q.m_w;

        return (Quaternion) set(q);
    }

    //***************************************************
    public static Quaternion quatSlerp(double time, Quaternion a, Quaternion b, double spin) {
        double k1, k2;                    /* interpolation coefficions. */
        double angle;                  /* angle between A and B */
        double angleSpin;            /* angle between A and B plus spin. */
        double sin_a, cos_a;    /* sine, cosine of angle */
        int flipk2;                /* use negation of k2. */

//    cos_a = Qdotunit( a,b );
        cos_a = a.m_x * b.m_x + a.m_y * b.m_y + a.m_z * b.m_z + a.m_w * b.m_w;
        if (cos_a < 0.0) {
            cos_a = -cos_a;
            flipk2 = -1;
        } else
            flipk2 = 1;

        if ((1.0 - cos_a) < 0.0001f) {
            k1 = 1.0f - time;
            k2 = time;
        } else {                /* normal case */
            angle = Math.acos(cos_a);
            sin_a = Math.sin(angle);
            angleSpin = angle + spin * Math.PI;
            k1 = Math.sin(angle - time * angleSpin) / sin_a;
            k2 = Math.sin(time * angleSpin) / sin_a;
        }
        k2 *= flipk2;

        return new Quaternion((double)(k1 * a.m_x + k2 * b.m_x),(double)(k1 * a.m_y + k2 * b.m_y),(double)(k1 * a.m_z + k2 * b.m_z),(double)(k1 * a.m_w + k2 * b.m_w));
    }

    //***************************************************
    public static void quatSlerpLong(double time, Quaternion a, Quaternion b, Quaternion out, double spin) {
        double k1, k2;                    /* interpolation coefficions. */
        double angle;                    /* angle between A and B */
        double angleSpin;                /* angle between A and B plus spin. */
        double sin_a, cos_a;                    /* sine, cosine of angle */

//    cos_a = Qdotunit( a,b );
        cos_a = a.m_x * b.m_x + a.m_y * b.m_y + a.m_z * b.m_z + a.m_w * b.m_w;

        if (1.0f - Math.abs(cos_a) < 0.00001f) {
            k1 = 1.0f - time;
            k2 = time;
        } else {                /* normal case */
            angle = Math.acos(cos_a);
            sin_a = Math.sin(angle);
            angleSpin = angle + spin * Math.PI;
            k1 = Math.sin(angle - time * angleSpin) / sin_a;
            k2 = Math.sin(time * angleSpin) / sin_a;
        }
        out.m_x = (double) (k1 * a.m_x + k2 * b.m_x);
        out.m_y = (double) (k1 * a.m_y + k2 * b.m_y);
        out.m_z = (double) (k1 * a.m_z + k2 * b.m_z);
        out.m_w = (double) (k1 * a.m_w + k2 * b.m_w);
    }

    //***************************************************
    public static void fromMat3(Matrix3x3 mat, Quaternion out) {
        /*
        qt_frommat: convert rotation matrix to quaternion.
        */
        double tr, s;
        Quaternion q = new Quaternion();
        //    static int nxt[3] = {Y, Z, X};

        tr = mat.m11 + mat.m22 + mat.m33;
        if (tr > 0.0) {
            s = (double) Math.sqrt(tr + 1.0);
            q.m_w = s / 2.0;
            s = 0.5 / s;
            q.m_x = (mat.m23 - mat.m32) * s;
            q.m_y = (mat.m31 - mat.m13) * s;
            q.m_z = (mat.m12 - mat.m21) * s;
        } else {
            //i = 0;//X
            if (mat.m22 > mat.m11) {
                //i = 1;//Y;
                if (mat.m33 > mat.m22) {
                    //  i = 2;//Z;
                    // j = 0;//nxt[i];
                    // k = 1;//nxt[j];
                    s = (double) Math.sqrt((mat.m33 - (mat.m11 + mat.m22)) + 1.0);
                    q.m_z = s / 2.0;
                    if (s != 0.0) s = 0.5 / s;
                    q.m_w = (mat.m12 - mat.m21) * s;
                    q.m_x = (mat.m31 + mat.m13) * s;
                    q.m_y = (mat.m32 + mat.m23) * s;
                } else { //i=Y=1
                    //j = 2;//nxt[i];
                    //k = 0;//nxt[j];
                    s =  Math.sqrt((mat.m22 - (mat.m33 + mat.m11)) + 1.0);
                    q.m_y = s / 2.0;
                    if (s != 0.0) s = 0.5 / s;
                    q.m_w = (mat.m31 - mat.m13) * s;
                    q.m_z = (mat.m23 + mat.m31) * s;
                    q.m_x = (mat.m21 + mat.m12) * s;

                }
            } else {//i=0
                //j = 1;//nxt[i];
                //k = 2;//nxt[j];
                s =  Math.sqrt((mat.m11 - (mat.m22 + mat.m33)) + 1.0);
                q.m_x = s / 2.0;
                if (s != 0.0) s = 0.5 / s;
                q.m_w = (mat.m23 - mat.m32) * s;
                q.m_y = (mat.m12 + mat.m21) * s;
                q.m_z = (mat.m13 + mat.m31) * s;

            }
        }

        out.m_w = q.m_w;
        out.m_x = q.m_x;
        out.m_y = q.m_y;
        out.m_z = q.m_z;
    }

    //***************************************************
    //convert quaternion to angle axis
    public void qToAngleAxis(double angle, Vector3f axis)
    {
        double length2 = m_x * m_x + m_y * m_y + m_z * m_z;
        double ll = 1.0 / Math.sqrt(length2);

        if (length2 > 0.0f) {
            axis = new Vector3f(m_x * ll, m_y * ll, m_z * ll);
            angle = (double) (2.0 * Math.acos(m_w));
        } else {
            // angle is 0 (mod 2*pi), so any axis will do
            axis.m_x = 1.0;
            axis.m_y = 0.0;
            axis.m_z = 0.0;
            angle = 0.0;
        }
    }

    //***************************************************
    public static void quatExp(Quaternion q, Quaternion qOut) /* Calculate quaternion`s exponent. */ {
        double d, d1;
        d =  (Math.sqrt(q.m_x * q.m_x + q.m_y * q.m_y + q.m_z * q.m_z));
        if (d > 0.0)
            d1 = Math.sin(d) / d;
        else
            d1 = 1.0;
        qOut.m_w = (double) Math.cos(d);
        qOut.m_x = q.m_x * d1;
        qOut.m_y = q.m_y * d1;
        qOut.m_z = q.m_z * d1;
    }

    //***************************************************
    public static void quatLog(Quaternion q, Quaternion qOut)    /* Calculate quaternion`s logarithm. */ {
        double d;
        d =  Math.sqrt(q.m_x * q.m_x + q.m_y * q.m_y + q.m_z * q.m_z);
        if (q.m_w != 0.0)
            d = Math.atan(d / q.m_w);
        else
            d = Math.PI * 0.5;
        qOut.m_w = 0.0;
        qOut.m_x = q.m_x * d;
        qOut.m_y = q.m_y * d;
        qOut.m_z = q.m_z * d;
    }

    //***************************************************
    // qinv:  Form multiplicative inverse of q
    public static void quatInv(Quaternion q, Quaternion qq) {
        double l;
        l = (q.m_x * q.m_x + q.m_y * q.m_y + q.m_z * q.m_z + q.m_w * q.m_w);
        if (l != 0.0)
            l = 1.0 / l;
        else
            l = 1.0;
        if (l == 0.0)
            l = 1.0;
        qq.m_x = -q.m_x * l;
        qq.m_y = -q.m_y * l;
        qq.m_z = -q.m_z * l;
        qq.m_w = q.m_w * l;
    }

    //***************************************************
    // Calculate logarithm of the relative rotation from p to q
    public static void quatLndif(Quaternion qa, Quaternion qb, Quaternion qOut) {
        Quaternion inv = new Quaternion();
        Quaternion dif = new Quaternion();
        double d, d1;
        double s;

        Quaternion.quatInv(qa, inv);            /* inv = -p; */
        Quaternion.mulQ(inv, q, dif);        /* dif = -p*q */

        d = (double) Math.sqrt(dif.m_x * dif.m_x + dif.m_y * dif.m_y + dif.m_z * dif.m_z);
        s = qa.m_x * qb.m_x + qa.m_y * qb.m_y + qa.m_z * qb.m_z + qa.m_w * qb.m_w;
        if (s != 0.0)
            d1 = Math.atan(d / s);
        else
            d1 =  Math.PI * 0.5;
        if (d != 0.0)
            d1 /= d;
        qOut.m_w = 0.0;
        qOut.m_x = dif.m_x * d1;
        qOut.m_y = dif.m_y * d1;
        qOut.m_z = dif.m_z * d1;
    }
    //***************************************************
    //***************************************************
    //***************************************************

}