package fuzzGl.buffers;

public class DepthBuffer extends BufferRBase
{
    public DepthBuffer(int width, int height)
    {
        super(width, height);
    }

    public final void clear(double v)
    {
        //java.util.util.Arrays.fill();

        double value = Double.MAX_VALUE;
        int size = (m_width * m_height) - 1;
        int cleared = 1;
        int index = 1;
        m_buffer[0] = v;

        while (cleared < size)
        {
            System.arraycopy(m_buffer, 0, m_buffer, index, cleared);
            size -= cleared;
            index += cleared;
            cleared <<= 1;
        }
        System.arraycopy(m_buffer, 0, m_buffer, index, size);
    }

    public double[] getRawArray()
    {
        return m_buffer;
    }    
}
