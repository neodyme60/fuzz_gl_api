package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class SimpleMappedSquare extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    static double r=0.0;
    int texture[]=new int[1];

    @Override
    public void init(BackBuffer bb)
    {
        GlCore.setBackBuffer(bb);
        c= GlCore.getInstance();
        m_width=bb.get_width();
        m_height=bb.get_height();

        BufferedImage img=null;
        int[] pixel=null;
        int height=0;
        int width =0;
        try
        {
            img= ImageIO.read(new URL("File:textures/w6bkgnd.jpg"));
            height = img.getHeight(null);
            width = img.getWidth(null);
            pixel= new int[width * width];
            img.getRGB(0, 0, width, height, pixel, 0, width);
        }
        catch(Exception ex)
        {
        }

        c.glGenTextures(1, texture);
        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[0]);
        c.glTexImage2D(Glenum.GL_TEXTURE_2D, 0, 3, width, height, 0, Glenum.GL_RGB, Glenum.GL_UNSIGNED_BYTE, pixel);
    }    

    @Override
    public void update()
    {
        if (c==null)
        return;

        c.glClearColor(0.0,0.0,0.0,1.0);

        c.glEnable(Glenum.GL_TEXTURE_2D);
        c.glEnable(Glenum.GL_DEPTH_TEST);
        c.glShadeModel(Glenum.GL_SMOOTH);
        c.glClearDepth(1.0f);

        c.glClear(Glenum.GL_COLOR_BUFFER_BIT);
        c.glClear(Glenum.GL_DEPTH_BUFFER_BIT);

        c.glViewport(0,0,m_width,m_height);

        c.glMatrixMode(Glenum.GL_PROJECTION);
        c.gluPerspective( 45.0, m_width/m_height, 0.001, 100.0 );

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
        c.gluLookAt(
                0.0,0.0,2.2,
                0.0,0.0,0.0,
                0.0,1.0,0.0);


        //===================================================>
        r+=0.2;

        c.glRotated(r,0.0,0.0,1.0);
        c.glPushMatrix();

            c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[0]);

        double d=0xff0000;
/*
        c.glBegin(Glenum.GL_QUADS);

        c.glTexCoord2d(0.0,0.0);
        c.glVertex3d(-1.0,-1.0,0.0);

        c.glTexCoord2d(0.0,1.0*d);
        c.glVertex3d(-1.0,1.0,0.0);

        c.glTexCoord2d(1.0*d,1.0*d);
        c.glVertex3d(1.0,1.0,0.0);

        c.glTexCoord2d(1.0*d,0.0);
        c.glVertex3d(1.0,-1.0,0.0);
        
        c.glEnd();
*/

            c.glBegin(Glenum.GL_TRIANGLES);

                c.glColor3f(1.0,0.0,0.0);
                c.glTexCoord2d(0.0,0.0);
                c.glVertex3d(-1.0,-1.0,0.0);

                c.glColor3f(0.0,1.0,0.0);
                c.glTexCoord2d(0.0*d,1.0*d);
                c.glVertex3d(-1.0,1.0,0.0);

                c.glColor3f(0.0,0.0,1.0);
                c.glTexCoord2d(1.0*d,1.0*d);
                c.glVertex3d(1.0,1.0,0.0);

                c.glColor3f(0.0,0.0,1.0);
                c.glTexCoord2d(1.0*d,1.0*d);
                c.glVertex3d(1.0,1.0,0.0);

                c.glColor3f(1.0,1.0,1.0);
                c.glTexCoord2d(1.0*d,0.0*d);
                c.glVertex3d(1.0,-1.0,0.0);

                c.glColor3f(1.0,0.0,0.0);
                c.glTexCoord2d(0.0*d,0.0*d);
                c.glVertex3d(-1.0,-1.0,0.0);

        /*
        c.glColor3f(1.0,0.0,0.0);
        c.glTexCoord2d(0.0,0.0);
        c.glVertex3d(1.0,0.0,0.0);

        c.glColor3f(0.0,1.0,0.0);
        c.glTexCoord2d(0.0*d,1.0*d);
        c.glVertex3d(0.0,1.0,0.0);

        c.glColor3f(0.0,0.0,1.0);
        c.glTexCoord2d(1.0*d,1.0*d);
        c.glVertex3d(1.0,1.0,0.0);

        c.glColor3f(0.0,0.0,1.0);
        c.glTexCoord2d(1.0*d,1.0*d);
        c.glVertex3d(0.0,1.0,0.0);

        c.glColor3f(1.0,1.0,1.0);
        c.glTexCoord2d(1.0*d,0.0*d);
        c.glVertex3d(1.0,0.0,0.0);

        c.glColor3f(1.0,0.0,0.0);
        c.glTexCoord2d(0.0*d,0.0*d);
        c.glVertex3d(0.0,0.0,0.0);
*/
            c.glEnd();

        c.glPopMatrix();

        c.glFlush();
    }

    @Override
    public void deinit()
    {
    }
}
