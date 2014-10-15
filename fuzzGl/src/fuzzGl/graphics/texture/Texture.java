package fuzzGl.graphics.texture;

import java.awt.*;
import java.awt.image.PixelGrabber;

public class Texture extends TextureBase
{
    public static final  int MAT_TEXMAP = 0x01;
    public static final  int MAT_OPACMAP = 0x02;
    public static final  int MAT_BUMPMAP = 0x03;
    public static final  int MAT_REFLMAP = 0x04;
    public static final  int MAT_SPECULAR = 0x05;

    public int type1;

    public int width;
    public int height;
    public int[] pixel;
    String path = null;

    public Image img = null;

    //******************************************
    /*
    contructor from size
    */
    public Texture(int x, int y) {
        width = x;
        height = y;
        pixel = new int[x * y];
        type1 = -1;
    }   

    //******************************************
    public Texture() {
        width = -1;
        height = -1;
        pixel = null;
        type1 = -1;
    }

    public boolean init() {
        PixelGrabber pg;
        height = img.getHeight(null);
        width = img.getWidth(null);

        pixel = new int[height * width];
        pg = new PixelGrabber(img, 0, 0, width, height, pixel, 0, width);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        //
        //post processing texture is needed
        //

        switch (type1) {
            case 0x03:   //MAT_BUMPMAP
            {
                //rebuild the pixel area
                Texture bumpMap = new Texture(width, height);
                bumpMap.setName(this.getName());
                int va;
                int xb;
                int yb;
                for (int x = 0; x < width; x++)
                    for (int y = 0; y < height; y++) {
                        xb = (pixel[x + (y * width)] & 0xff) - (pixel[((x + 1) % width) + (y * width)] & 0xff);
                        yb = (pixel[x + (y * width)] & 0xff) - (pixel[x + (((y + 1) % height) * width)] & 0xff);
                        va = (((xb * 1) & 255) << 16) + ((yb * 1) & 255);
                        bumpMap.pixel[x + (y * width)] = va;
                    }
                pixel = bumpMap.pixel;
            }
            break;
            case -1:
            default:
                break;
        }
        return true;
    }


    public TextureInterface builFrom(TextureInterface i)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void copyDataFromArray(int[] source)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}