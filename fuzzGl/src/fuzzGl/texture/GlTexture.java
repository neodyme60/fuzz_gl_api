package fuzzGl.texture;

import math.Tools;

public class GlTexture
{
    private int m_widthLog2=0;
    private int m_heightLog2=0;
    private int[] m_pixel=null;

    final public int getWidthLog2()
    {
        return m_widthLog2;
    }

    public int getHeightLog2()
    {
        return m_heightLog2;
    }
    
    final public int getWidth()
    {
        return 1<<m_widthLog2;
    }

    final public int getHeight()
    {
        return 1<<m_heightLog2;
    }

    public GlTexture(int width, int height, int[] pixel)
    {
        m_widthLog2= Tools.log2(width);
        m_heightLog2= Tools.log2(height);
        m_pixel=pixel;
    }

    final public int[] getData()
    {
        return m_pixel;
    }

    final public void setData(int[] pixel)
    {
        m_pixel=pixel;
    }
}