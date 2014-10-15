package fuzzGl.texture;

import fuzzGl.Glenum;

public class GlTextureMipMap
{
    private GlTexture[] m_textureMipMap=new GlTexture[7];
    public Glenum type=Glenum.GL_TEXTURE_2D; //default

    public void addTextureLevel(GlTexture d, int level)
    {
        m_textureMipMap[level]=d;
    }

    public GlTexture getTexturePixel(int level)
    {
        return m_textureMipMap[level];
    }        
}