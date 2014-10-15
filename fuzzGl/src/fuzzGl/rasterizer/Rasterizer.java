package fuzzGl.rasterizer;

import fuzzGl.BackBuffer;

public class Rasterizer
{
    private static BackBuffer m_backBuffer=null;
    private static Rasterizer instance=null;
 
    private Rasterizer()
    {
    }

    public Rasterizer getInstance()
    {
        if (instance==null)
            instance=new Rasterizer();
        return instance;
    }

    public static void init(BackBuffer bb)
    {
        m_backBuffer=bb;
    }

}
