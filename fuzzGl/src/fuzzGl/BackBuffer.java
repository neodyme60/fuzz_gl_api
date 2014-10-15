package fuzzGl;

import fuzzGl.buffers.ColorBuffer;
import fuzzGl.buffers.DepthBuffer;

final public class BackBuffer
{
    private ColorBuffer m_colorBuffer=null;
    private DepthBuffer m_zBuffer=null;

    private int m_width=0;
    private int m_height=0;

    public BackBuffer(int width, int height)
    {
        m_width=width;
        m_height=height;
    }

    final public boolean hasZBuffer()
    {
        return m_zBuffer!=null;
    }

    final public boolean hasColorBuffer()
    {
        return m_colorBuffer!=null;
    }

    final public void add_depth_buffer()
    {
        m_zBuffer=new DepthBuffer(m_width,m_height);
    }

    final public int[] get_raw_color_buffer()
    {
        return m_colorBuffer.getRawArray();
    }
    
    final public double[] get_raw_depth_buffer()
    {
        return m_zBuffer.getRawArray();
    }

    final public void add_color_buffer()
    {
        m_colorBuffer=new ColorBuffer(m_width,m_height);
    }

    final public void clear_depth_buffer(double v)
    {
        if (m_zBuffer!=null)
            m_zBuffer.clear(v);
    }

    final public void clear_color_buffer(int c)
    {
        if (m_colorBuffer!=null)
            m_colorBuffer.clear(c);
    }

    final public int get_width() {
        return m_width;
    }

    final public int get_height() {
        return m_height;
    }
}
