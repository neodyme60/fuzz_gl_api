package fuzzGl.rasterizer;

import fuzzGl.BackBuffer;
import fuzzGl.geometry.Vertex2;

public class TriangleFlat
{
    private static double temp_f;
    public final static int FRACBITS = 16;
    
    //triangle interpolation
    private static int start;

    private static int right_x;
    private static int left_x;

    private static int temp_x;
    private static int y;
    private static int xs;
    private static int xe;

    //texture1
    private static int dx_left;
    private static int dx_right;

    //zbuffer
    private static double left_z;
    private static double right_z;
    private static double const_z;
    private static double z_left;
    private static double zs;
    private static double z;

    //temp variable for rendering
    private static int scanLengt;
    private static int offsetStart;
    private static int scanStart;
    private static int scanEnd;    
    private static  Vertex2 v4=null;

    private static ScanLine m_scanLine=new ScanLine();

    public static void flatFast(BackBuffer bb, Vertex2 v1,Vertex2 v2,Vertex2 v3, int color)
    {
        //
        //Y	sort with bubble sort
        //
        if (v1.posScreen.m_y > v2.posScreen.m_y) //swap 0 & 1
        {
            v4 = v1;            v1 = v2;            v2 = v4;
        }
        if ( v2.posScreen.m_y >  v3.posScreen.m_y) //swap 1 & 2
        {
            v4 = v2;            v2 = v3;            v3 = v4;
        }
        if ( v1.posScreen.m_y >  v2.posScreen.m_y) //swap 0 & 1
        {
            v4 = v1;            v1 = v2;            v2 = v4;
        }

        double coef;
        int height;

        left_x = (int) (v2.posScreen.m_x);
        int h = (int)((v3.posScreen.m_y +0x8000) - (v1.posScreen.m_y +0x8000)) >> FRACBITS;
        if (h == 0) return;

        //magic	coef
        coef = ((double) (((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS))) / (double) h;

        //poly incrment
        double tt = (double) ((int) v3.posScreen.m_x) - ((int) v1.posScreen.m_x);
        right_x = (int) v1.posScreen.m_x + (int) (coef * tt);

        if (right_x < left_x)
        {
            //for	scanline
            temp_x = right_x;
            right_x = left_x;
            left_x = temp_x;
        }

        start = bb.get_width() * ((int) (v1.posScreen.m_y + 0.5) >> FRACBITS);

        height =(int) ((v2.posScreen.m_y +0x8000)  -(v1.posScreen.m_y +0x8000)) >> FRACBITS;
        if (height > 0)
        {
            //start m_x for scanline
            xs = (int) v1.posScreen.m_x;
            xe = (int) v1.posScreen.m_x;

            //
            //les increments sur les Y  pour la 1ere partie du triangle
            //

            dx_left = (left_x - (int) v1.posScreen.m_x) / height;
            dx_right = (right_x - (int) v1.posScreen.m_x) / height;

            for (y = (int) v1.posScreen.m_y >> FRACBITS; y < (int) v2.posScreen.m_y >> FRACBITS; y++)
            {
                scanStart = xs >> FRACBITS;
                scanEnd = xe >> FRACBITS;

                if (y >= bb.get_height())
                    break;

                scanLengt = scanEnd - scanStart;
                offsetStart = start + scanStart;

                m_scanLine.m_color=color;
                m_scanLine.m_scanStart=offsetStart;
                m_scanLine.m_scanLenght=scanLengt;
                m_scanLine.m_colorBuffer=bb.get_raw_color_buffer();
                m_scanLine.m_zBuffer=bb.get_raw_depth_buffer();
                m_scanLine.drawFlat();

                //
                //update Y index
                //

                //scanline increment
                xs += dx_left;
                xe += dx_right;

                //new m_y screen start
                start += bb.get_width();
            }
        }

        height = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v2.posScreen.m_y >> FRACBITS);
        if (height > 0)
        {
            dx_right = ((int) v3.posScreen.m_x - right_x) / height;
            dx_left = ((int) v3.posScreen.m_x - left_x) / height;

            //start for m_x scanline
            xs = left_x;
            xe = right_x;

            //
            //les increments sur les Y
            //

            for (y = (int) v2.posScreen.m_y >> FRACBITS; y < (int) v3.posScreen.m_y >> FRACBITS; y++)
            {
                scanStart = xs >> FRACBITS;
                scanEnd = xe >> FRACBITS;

                if (y >= bb.get_height())
                    break;

                scanLengt = scanEnd - scanStart;
                offsetStart = start + scanStart;

                m_scanLine.m_color=color;
                m_scanLine.m_scanStart=offsetStart;
                m_scanLine.m_scanLenght=scanLengt;
                m_scanLine.m_colorBuffer=bb.get_raw_color_buffer();
                m_scanLine.m_zBuffer=bb.get_raw_depth_buffer();
                m_scanLine.drawFlat();

                //
                //update Y index
                //

                //scanline increment
                xs += dx_left;
                xe += dx_right;

                start += bb.get_width();
            }
        }
    }

    public static void flatFastZ(BackBuffer bb, Vertex2 v1,Vertex2 v2,Vertex2 v3, int color, boolean isDepthBufferWritable)
    {
        Vertex2 v4=null;

        //
        //Y	sort with bubble sort
        //
        if (v1.posScreen.m_y > v2.posScreen.m_y) //swap 0 & 1
        {
            v4 = v1;            v1 = v2;            v2 = v4;
        }
        if ( v2.posScreen.m_y >  v3.posScreen.m_y) //swap 1 & 2
        {
            v4 = v2;            v2 = v3;            v3 = v4;
        }
        if ( v1.posScreen.m_y >  v2.posScreen.m_y) //swap 0 & 1
        {
            v4 = v1;            v1 = v2;            v2 = v4;
        }

        double coef;
        int height;

        left_x = (int) (v2.posScreen.m_x);
        long h = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS);
        if (h == 0) return;

        //magic	coef
        coef = ((double) (((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS))) / (double) h;

        //poly incrment
        double tt = (double) ((int) v3.posScreen.m_x) - ((int) v1.posScreen.m_x);
        right_x = (int) v1.posScreen.m_x + (int) (coef * tt);

        //zbuffer	au point 4 & 2
        left_z = v2.posNormalizedDeviceCoordinates.m_z;
        tt = v3.posNormalizedDeviceCoordinates.m_z - v1.posNormalizedDeviceCoordinates.m_z;
        right_z = v1.posNormalizedDeviceCoordinates.m_z + (coef * tt);

        if (right_x < left_x)
        {
            //for	scanline
            temp_x = right_x;
            right_x = left_x;
            left_x = temp_x;

            //for	zbuffer
            temp_f = right_z;
            right_z = left_z;
            left_z = temp_f;
        }

        //
        //les increment constant pour tout le triangle sur les X
        //

        //zbuffer
        const_z = ((right_z - left_z) / (double) (right_x - left_x)) * 65536.0;

        start = bb.get_width() * ((int) (v1.posScreen.m_y + 0.5) >> FRACBITS);

        height = ((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS);
        if (height > 0)
        {
            //start m_x for scanline
            xs = (int) v1.posScreen.m_x;
            xe = (int) v1.posScreen.m_x;

            //
            //les increments sur les Y pour la 1ere partie du triangle
            //

            dx_left = (left_x - (int) v1.posScreen.m_x) / height;
            dx_right = (right_x - (int) v1.posScreen.m_x) / height;

            //zbuffer
            z_left = (left_z - v1.posNormalizedDeviceCoordinates.m_z) / (double)height;
            zs = v1.posNormalizedDeviceCoordinates.m_z;

            for (y = (int) v1.posScreen.m_y >> FRACBITS; y < (int) v2.posScreen.m_y >> FRACBITS; y++)
            {
                scanStart = xs >> FRACBITS;
                scanEnd = xe >> FRACBITS;

                if (y >= bb.get_height())
                    break;

                m_scanLine.m_zStart=zs;
                m_scanLine.m_zIncr=const_z;
                m_scanLine.m_color=color;
                m_scanLine.m_scanStart=start + scanStart;
                m_scanLine.m_scanLenght=scanEnd - scanStart;
                m_scanLine.m_colorBuffer=bb.get_raw_color_buffer();
                m_scanLine.m_zBuffer=bb.get_raw_depth_buffer();
                if (isDepthBufferWritable)
                    m_scanLine.drawFlatZUpdate();
                else
                    m_scanLine.drawFlatZNoUpdate();

                //
                //update Y index
                //

                //scanline increment
                xs += dx_left;
                xe += dx_right;

                zs += z_left;

                //new m_y screen start
                start += bb.get_width();
            }
        }

        height = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v2.posScreen.m_y >> FRACBITS);
        if (height > 0) {
            dx_right = ((int) v3.posScreen.m_x - right_x) / height;
            dx_left = ((int) v3.posScreen.m_x - left_x) / height;

            //start for m_x scanline
            xs = left_x;
            xe = right_x;

            //
            //les increments sur les Y
            //

            //zbuffer
            zs = left_z;
            z_left = (v3.posNormalizedDeviceCoordinates.m_z - left_z) / (double)height;

            for (y = (int) v2.posScreen.m_y >> FRACBITS; y < (int) v3.posScreen.m_y >> FRACBITS; y++)
            {
                scanStart = xs >> FRACBITS;
                scanEnd = xe >> FRACBITS;

                if (y >= bb.get_height())
                    break;

                m_scanLine.m_zStart=zs;
                m_scanLine.m_zIncr=const_z;
                m_scanLine.m_color=color;
                m_scanLine.m_scanStart=start + scanStart;
                m_scanLine.m_scanLenght=scanEnd - scanStart;
                m_scanLine.m_colorBuffer=bb.get_raw_color_buffer();
                m_scanLine.m_zBuffer=bb.get_raw_depth_buffer();
                if (isDepthBufferWritable)
                    m_scanLine.drawFlatZUpdate();
                else
                    m_scanLine.drawFlatZNoUpdate();

                //
                //update Y index
                //

                //scanline increment
                xs += dx_left;
                xe += dx_right;

                zs += z_left;

                start += bb.get_width();
            }
        }
    }
}
