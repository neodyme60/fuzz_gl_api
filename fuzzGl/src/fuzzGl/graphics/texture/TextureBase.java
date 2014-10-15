package fuzzGl.graphics.texture;

import fuzzGl.graphics.Color;

public abstract class TextureBase implements TextureInterface
{
    protected String name="no name";
    protected boolean isAnimated=false;
    protected boolean isLoaded=false;
    protected int width=0;
    protected int height=0;

    abstract public void copyDataFromArray(int[] source);


    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public boolean isAnimated()
    {
        return isAnimated;
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String _name)
    {
        name=_name;
    }

    public int[] getDataBuffer()
    {
        return null;
    }

    public Color getData(double x, double y,double z) { return new Color().setBlack();}
    public int getDatai(double x, double y, double z) {return 0;}
    public int getRedi(double x, double y,double z) {return 0;}
    public int getGreeni(double x, double y,double z) {return 0;}
    public int getBLuei(double x, double y,double z) {return 0;}
    public double getRedf(double x, double y,double z) {return 0;}
    public double getGreenf(double x, double y,double z) {return 0.0f; }
    public double getBLuef(double x, double y,double z) {return 0.0f; }

    public Color getData(int x, int y,int z){ return new Color().setBlack();}
    public int getDatai(int x, int y, int z) {return 0;}
    public int getRedi(int x, int y,int z) {return 0;}
    public int getGreeni(int x, int y,int z) {return 0;}
    public int getBLuei(int x, int y,int z) {return 0;}
    public double getRedf(int x, int y,int z) {return 0.0f; }
    public double getGreenf(int x, int y,int z) {return 0.0f; }
    public double getBLuef(int x, int y,int z) {return 0.0f; }
}
