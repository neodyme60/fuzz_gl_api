package fuzzGl.buffers;

public abstract class BufferIBase
{
    protected int m_width;
    protected int m_height;
    protected int[] m_buffer = null;

    protected BufferIBase(int width, int height)
    {
        m_width=width;
        m_height=height;
        m_buffer=new int[m_width*m_height];
    }
    
    public int[] getRawArray()
    {
        return m_buffer;
    }
}
