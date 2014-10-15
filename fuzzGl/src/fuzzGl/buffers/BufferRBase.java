package fuzzGl.buffers;

public abstract class BufferRBase
{
    protected int m_width;
    protected int m_height;
    protected double[] m_buffer = null;

    protected BufferRBase(int width, int height)

    {
        m_width=width;
        m_height=height;
        m_buffer=new double[m_width*m_height];
    }    

}