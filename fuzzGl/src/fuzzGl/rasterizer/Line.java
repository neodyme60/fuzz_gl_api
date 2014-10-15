package fuzzGl.rasterizer;

import fuzzGl.BackBuffer;
import fuzzGl.geometry.Vertex2;

public class Line
{
    private final static int FRACBITS = 16;

    //
    // DDA Line Algorithm with Zbuffer
    // fixed-point arithmetic to assure we get set_sub-pixel accuracy.
    //
    public static void drawLineZ(BackBuffer bb, Vertex2 v1, Vertex2 v2, int color)
    {
        int length;
        int x;
        int y;
        int xx;
        int yy;
        int index;
        int xincrement;
        int yincrement;
        double zincrement;
        double z;
        int bb_width=bb.get_width();
        int bb_height=bb.get_height();
        int[] bufColor=bb.get_raw_color_buffer();
        double[] bufZ=bb.get_raw_depth_buffer();

        int xx1=(int) (v1.posScreen.m_x *(1<<FRACBITS));
        int yy1=(int) (v1.posScreen.m_y *(1<<FRACBITS));
        int xx2=(int) (v2.posScreen.m_x *(1<<FRACBITS));
        int yy2=(int) (v2.posScreen.m_y *(1<<FRACBITS));

        int zz1=(int) (v1.posNormalizedDeviceCoordinates.m_x *(1<<FRACBITS));
        int zz2=(int) (v2.posNormalizedDeviceCoordinates.m_y *(1<<FRACBITS));

        length = Math.abs(xx2 - xx1);

        if (Math.abs(yy2 - yy1) > length)
            length = Math.abs(yy2 - yy1);

        length=length>>FRACBITS;

        if (length==0)
        return;

        xincrement = (xx2 - xx1) / length;
        yincrement = (yy2 - yy1) / length;
        zincrement = (zz2 - zz1) / length;

        x = xx1;
        y = yy1;
        z = zz1;
        for (int i = 1; i <= length; ++i)
        {
            xx = x >> FRACBITS;
            yy = y >> FRACBITS;
            if (xx < bb_width & 0 <= xx & yy < bb_height & 0 < yy)
            {
                index = (bb_width * yy + xx);
                if (z < bufZ[index])
                {
                    bufZ[index] = z;            //update zbufer
                    bufColor[index] = color;
                }
            }
            x += xincrement ;
            y += yincrement;
            z += zincrement;
        }
    }

    //
    // DDA Line Algorithm
    // fixed-point arithmetic to assure we get set_sub-pixel accuracy.
    //
    public static void drawLine(BackBuffer bb, Vertex2 v1, Vertex2 v2, int color)
    {
        int length;
        int x;
        int y;
        int xx;
        int yy;
        int index;
        int xincrement;
        int yincrement;
        double z;
        int bb_width=bb.get_width();
        int bb_height=bb.get_height();
        
        int[] buf=bb.get_raw_color_buffer();


        int xx1=(int) (v1.posScreen.m_x *(1<<FRACBITS));
        int yy1=(int) (v1.posScreen.m_y *(1<<FRACBITS));
        int xx2=(int) (v2.posScreen.m_x *(1<<FRACBITS));
        int yy2=(int) (v2.posScreen.m_y *(1<<FRACBITS));

        length = Math.abs(xx2 - xx1);

        if (Math.abs(yy2 - yy1) > length)
            length = Math.abs(yy2 - yy1);

        length=length>>FRACBITS;

        if (length==0)
        return;

        xincrement = (xx2 - xx1) / length;
        yincrement = (yy2 - yy1) / length;

        x = xx1;
        y = yy1;
        for (int i = 1; i <= length; ++i)
        {
            xx = x >> FRACBITS;
            yy = y >> FRACBITS;
            if (xx < bb_width & 0 <= xx & yy < bb_height & 0 < yy)
            {
                index = (bb_width * yy + xx);
                buf[index] = color;
            }
            x += xincrement ;
            y += yincrement;
        }
    }
}
