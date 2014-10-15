package fuzzGl.buffers;

public class ColorBuffer extends BufferIBase
{
    public ColorBuffer(int width, int height)
    {
        super(width, height);
    }

    public final void clear(int value)
    {
        int size = (m_width * m_height) - 1;
        int cleared = 1;
        int index = 1;
        m_buffer[0] = value;

        while (cleared < size)
        {
            System.arraycopy(m_buffer, 0, m_buffer, index, cleared);
            size -= cleared;
            index += cleared;
            cleared <<= 1;
        }
        System.arraycopy(m_buffer, 0, m_buffer, index, size);

    }
    
}
