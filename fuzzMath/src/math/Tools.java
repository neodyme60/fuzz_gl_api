package math;

public class Tools
{

    public Point3f getRandomUnitSpherePoint()
    {
        double t=0;
        double z=0;

        double x=Math.sqrt(1-z*z)*Math.cos(t);
        double y=Math.sqrt(1-z*z)*Math.sin(t);

        return new Point3f(x,y,z);
    }

  /** Fast approximation of 1.0 / sqrt(m_x).
   * See <a href="http://www.beyond3d.com/content/articles/8/">http://www.beyond3d.com/content/articles/8/</a>
   * @param x Positive value to estimate inverse of square root of
   * @return Approximately 1.0 / sqrt(m_x)
   **/
  public static double invSqrt(double x)
  {
    double xhalf = 0.5 * x;
    long i = Double.doubleToRawLongBits(x);
    i = 0x5FE6EB50C7B537AAL - (i>>1);
    x = Double.longBitsToDouble(i);
    x = x * (1.5 - xhalf*x*x); 
    return x;
  }

   public static double get_lerp(double a, double b, double t)
   {
    return a + (b - a) * t;
   }

    public double ln(double val)
    {
        final double x = (Double.doubleToLongBits(val) >> 32);
        return (x - 1072632447) / 1512775;
    }
    
    public static double exp(double val)
    {
        final long tmp = (long) (1512775 * val + (1072693248 - 60801));
        return Double.longBitsToDouble(tmp << 32);
    }

    //Tools square root for intel cpu
    //http://martin.ankerl.com/2009/01/05/approximation-of-sqrtx-in-java/
    //
    public static double sqrt(final double a)
    {
        final long x = Double.doubleToLongBits(a) >> 32;
        double y = Double.longBitsToDouble((x + 1072632448) << 31);

        // repeat the following line for more precision
        //m_y = (m_y + a / m_y) * 0.5;
        return y;
    }

    //http://martin.ankerl.com/2007/10/04/optimized-pow-approximation-for-java-and-c-c/
    public static double pow(final double a, final double b)
    {
        final int x = (int) (Double.doubleToLongBits(a) >> 32);
        final int y = (int) (b * (x - 1072632447) + 1072632447);
        return Double.longBitsToDouble(((long) y) << 32);
    }

    public static int log2(int x)
    {
        return(int)(Math.log(x)/Math.log(2));
    }
}
