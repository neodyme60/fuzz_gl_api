package fuzzGl.rasterizer;

import fuzzGl.BackBuffer;
import fuzzGl.geometry.Vertex2;

public class Dot
{
    public static void drawPoint(BackBuffer bb, Vertex2 v, int color)
    {
        try
        {
            int bb_width=bb.get_width();
            int bb_height=bb.get_height();
            int[] bufColor=bb.get_raw_color_buffer();
            
            int xx=(int)(v.posScreen.m_x +0.5);
            int yy=(int)(v.posScreen.m_y +0.5);
            int index=xx+(yy*bb_width);
            bufColor[index]=color;
        }
        catch(Exception ex)
        {

        }
    }

   public static void drawPointZ(BackBuffer bb,Vertex2 v, int color)
    {
        try
        {
            int bb_width=bb.get_width();
            int bb_height=bb.get_height();
            int[] bufColor=bb.get_raw_color_buffer();
            double[] bufZ=bb.get_raw_depth_buffer();

            int xx=(int)(v.posScreen.m_x +0.5);
            int yy=(int)(v.posScreen.m_y +0.5);
            int index=xx+(yy*bb_width);
            bufColor[index]=color;

            if (v.posNormalizedDeviceCoordinates.m_z < bufZ[index])
            {
                bufZ[index] = v.posNormalizedDeviceCoordinates.m_z;
                bufColor[index] = color;
            }            
        }
        catch(Exception ex)
        {

        }
    }  
}
