package fuzzGl.rasterizer;

import fuzzGl.graphics.Color;
import fuzzGl.texture.GlTexture;
import math.Vector2i;

final public class ScanLine
{
    public int[] m_colorBuffer=null;
    public GlTexture m_texture1=null;
    public double[] m_zBuffer=null;
    public int m_scanStart=0;
    public int m_scanLenght=0;
    
    public int m_color=0;
    public double m_colorRStart=0;
    public double m_colorGStart=0;
    public double m_colorBStart=0;

    public Vector2i m_UV=new Vector2i(0,0);
    public Vector2i m_UVDX=new Vector2i(0,0);

    public double m_colorRIncr=0;
    public double m_colorGIncr=0;
    public double m_colorBIncr=0;
    
    public double m_zIncr=0;
    public double m_zStart=0;

    //
    //flat
    //
    final void drawFlat()
    {
        while ((m_scanLenght--) > 0)
        {
            m_colorBuffer[m_scanStart] = m_color;

            //scanline increment
            m_scanStart++;
        }
    }

    final void drawFlatZUpdate()
    {
        while ((m_scanLenght--) > 0)
        {
            if (m_zStart < m_zBuffer[m_scanStart])    //need to update pixel ?
            {
                //update zbuffer
                m_zBuffer[m_scanStart] = m_zStart;
                m_colorBuffer[m_scanStart] = m_color;
            }

            //scanline increment
            m_scanStart++;

            //zbuffer increment
            m_zStart += m_zIncr;
        }

    }

    final void drawFlatZNoUpdate()
    {
        while ((m_scanLenght--) > 0)
        {
            if (m_zStart < m_zBuffer[m_scanStart])    //need to update pixel ?
            {
                m_colorBuffer[m_scanStart] = m_color;
            }

            //scanline increment
            m_scanStart++;

            //zbuffer increment
            m_zStart += m_zIncr;
        }
    }

    //
    //gouraud
    //
    final void drawGouraud()
    {
        while ((m_scanLenght--) > 0)
        {
            //update zbuffer
            m_zBuffer[m_scanStart] = m_zStart;
            m_colorBuffer[m_scanStart] = m_color;

            //scanline increment
            m_scanStart++;
        }
    }

    final void drawGouraudZUpdate()
    {
        while ((m_scanLenght--) > 0)
        {
            if (m_zStart < m_zBuffer[m_scanStart])    //need to update pixel ?
            {
                //update zbuffer
                m_zBuffer[m_scanStart] = m_zStart;
                m_colorBuffer[m_scanStart] = Color.color4fToInt(m_colorRStart, m_colorGStart, m_colorBStart, 1.0);;
            }

            //scanline increment
            m_scanStart++;

            //color increment
            m_colorRStart+=m_colorRIncr;
            m_colorGStart+=m_colorGIncr;
            m_colorBStart+=m_colorBIncr;

            //zbuffer increment
            m_zStart += m_zIncr;
        }
    }
    
    final void drawGouraudZNoUpdate()
    {
        while ((m_scanLenght--) > 0)
        {
            if (m_zStart < m_zBuffer[m_scanStart])    //need to update pixel ?
            {
                m_colorBuffer[m_scanStart] = Color.color4fToInt(m_colorRStart, m_colorGStart, m_colorBStart, 1.0);;
            }

            //scanline increment
            m_scanStart++;

            //color increment
            m_colorRStart+=m_colorRIncr;
            m_colorGStart+=m_colorGIncr;
            m_colorBStart+=m_colorBIncr;            

            //zbuffer increment
            m_zStart += m_zIncr;
        }
    }

    //
    //mapping
    //
    final void drawMapping()
    {
        while ((m_scanLenght--) > 0)
        {
            int mappingColor = m_texture1.getData() [(((m_UV.m_x >> 16)) & (m_texture1.getWidth()-1)) + ((((m_UV.m_y >> 16)) & (m_texture1.getHeight()-1)) << m_texture1.getWidthLog2())];
            m_colorBuffer[m_scanStart] = mappingColor;

            //scanline increment
            m_scanStart++;

            //color increment
             m_UV.set_add(m_UVDX);

        }
    }

    final void drawMappingZUpdate()
    {
        while ((m_scanLenght--) > 0)
        {
            boolean g=false;
            try
            {
                g=m_zStart < m_zBuffer[m_scanStart];
            }
            catch(Exception ex)
            {

            }
            if (g)    //need to update pixel ?
            {
                //update zbuffer
                m_zBuffer[m_scanStart] = m_zStart;
                int mappingColor = m_texture1.getData() [(((m_UV.m_x >> 16)) & (m_texture1.getWidth()-1)) + ((((m_UV.m_y >> 16)) & (m_texture1.getHeight()-1)) << m_texture1.getWidthLog2())];
                m_colorBuffer[m_scanStart] = mappingColor;
            }

            //scanline increment
            m_scanStart++;

            //color increment
            m_UV.set_add(m_UVDX);

            //zbuffer increment
            m_zStart += m_zIncr;
        }
    }

    final void drawMappingZNoUpdate()
    {
        while ((m_scanLenght--) > 0)
        {
            if (m_zStart < m_zBuffer[m_scanStart])    //need to update pixel ?
            {
                int mappingColor = m_texture1.getData() [(((m_UV.m_x >> 16)) & (m_texture1.getWidth()-1)) + ((((m_UV.m_y >> 16)) & (m_texture1.getHeight()-1)) << m_texture1.getWidthLog2())];
                m_colorBuffer[m_scanStart] = mappingColor;
            }

            //scanline increment
            m_scanStart++;

            //color increment
             m_UV.set_add(m_UVDX);

            //zbuffer increment
            m_zStart += m_zIncr;
        }
    }    
}
