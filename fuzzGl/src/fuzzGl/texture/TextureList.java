package fuzzGl.texture;

import java.util.*;

public class TextureList
{
    private HashMap<Integer,GlTextureMipMap> l=new HashMap<Integer,GlTextureMipMap>();

    public GlTextureMipMap getTextureById(int id)
    {
        if (!l.containsKey(id))
            return null;
                
        return (GlTextureMipMap)l.get(id);
    }

    public void allocates(int n, int[]r )
    {
        for(int i=0;i<n;i++)
        {
            int id=0;
            while(l.containsKey(id))
            {
                id++;
            }
            l.put(id,new GlTextureMipMap());
            r[i]=id;
        }
    }
}
