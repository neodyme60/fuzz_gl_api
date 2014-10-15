package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Nehe07 extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    static double r=0.0;
    int texture[]=new int[1];
    double xrot=0;
    double yrot=0;
    double zrot=0;

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
            img= ImageIO.read(new URL("File:textures/Crate.bmp"));
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

        c.glClearColor(0.0,0.0,0.0,1.0);

        c.glEnable(Glenum.GL_TEXTURE_2D);
        c.glEnable(Glenum.GL_DEPTH_TEST);
        c.glShadeModel(Glenum.GL_SMOOTH);
        c.glClearDepth(1.0f);

        c.glViewport(0,0,m_width,m_height);

        c.glMatrixMode(Glenum.GL_PROJECTION);
        c.gluPerspective( 45.0, m_width/m_height, 0.1, 100.0 );

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
        c.gluLookAt(
                0.0,0.0,-4.0,
                0.0,0.0,0.0,
                0.0,1.0,0.0);
    }

    @Override
    public void update()
    {
        if (c==null)
        return;

        c.glClear(Glenum.GL_COLOR_BUFFER_BIT);
        c.glClear(Glenum.GL_DEPTH_BUFFER_BIT);
        
        c.glMatrixMode(Glenum.GL_MODELVIEW);

        c.glPushMatrix();
        //===================================================>
        r+=0.2;

        c.glRotated(xrot,1.0f,0.0f,0.0f);						// Rotate On The X Axis
        c.glRotated(yrot,0.0f,1.0f,0.0f);						// Rotate On The Y Axis
        c.glRotated(zrot,0.0f,0.0f,1.0f);						// Rotate On The Z Axis        c.glPushMatrix();

        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[0]);

        c.glBegin(Glenum.GL_QUADS);

		// Front Face
		c.glTexCoord2d(0.0, 0.0); c.glVertex3d(-1.0, -1.0,  1.0);	// Bottom Left Of The Texture and Quad
		c.glTexCoord2d(1.0, 0.0); c.glVertex3d( 1.0, -1.0,  1.0);	// Bottom Right Of The Texture and Quad
		c.glTexCoord2d(1.0, 1.0); c.glVertex3d( 1.0,  1.0,  1.0);	// Top Right Of The Texture and Quad
		c.glTexCoord2d(0.0, 1.0); c.glVertex3d(-1.0,  1.0,  1.0);	// Top Left Of The Texture and Quad
		// Back Face
		c.glTexCoord2d(1.0, 0.0); c.glVertex3d(-1.0, -1.0, -1.0);	// Bottom Right Of The Texture and Quad
		c.glTexCoord2d(1.0, 1.0); c.glVertex3d(-1.0,  1.0, -1.0);	// Top Right Of The Texture and Quad
		c.glTexCoord2d(0.0, 1.0); c.glVertex3d( 1.0,  1.0, -1.0);	// Top Left Of The Texture and Quad
		c.glTexCoord2d(0.0, 0.0); c.glVertex3d( 1.0, -1.0, -1.0);	// Bottom Left Of The Texture and Quad
		// Top Face
		c.glTexCoord2d(0.0, 1.0); c.glVertex3d(-1.0,  1.0, -1.0);	// Top Left Of The Texture and Quad
		c.glTexCoord2d(0.0, 0.0); c.glVertex3d(-1.0,  1.0,  1.0);	// Bottom Left Of The Texture and Quad
		c.glTexCoord2d(1.0, 0.0); c.glVertex3d( 1.0,  1.0,  1.0);	// Bottom Right Of The Texture and Quad
		c.glTexCoord2d(1.0, 1.0); c.glVertex3d( 1.0,  1.0, -1.0);	// Top Right Of The Texture and Quad
		// Bottom Face
		c.glTexCoord2d(1.0, 1.0); c.glVertex3d(-1.0, -1.0, -1.0);	// Top Right Of The Texture and Quad
		c.glTexCoord2d(0.0, 1.0); c.glVertex3d( 1.0, -1.0, -1.0);	// Top Left Of The Texture and Quad
		c.glTexCoord2d(0.0, 0.0); c.glVertex3d( 1.0, -1.0,  1.0);	// Bottom Left Of The Texture and Quad
		c.glTexCoord2d(1.0, 0.0); c.glVertex3d(-1.0, -1.0,  1.0);	// Bottom Right Of The Texture and Quad
		// Right face
		c.glTexCoord2d(1.0, 0.0); c.glVertex3d( 1.0, -1.0, -1.0);	// Bottom Right Of The Texture and Quad
		c.glTexCoord2d(1.0, 1.0); c.glVertex3d( 1.0,  1.0, -1.0);	// Top Right Of The Texture and Quad
		c.glTexCoord2d(0.0, 1.0); c.glVertex3d( 1.0,  1.0,  1.0);	// Top Left Of The Texture and Quad
		c.glTexCoord2d(0.0, 0.0); c.glVertex3d( 1.0, -1.0,  1.0);	// Bottom Left Of The Texture and Quad
		// Left Face
		c.glTexCoord2d(0.0, 0.0); c.glVertex3d(-1.0, -1.0, -1.0);	// Bottom Left Of The Texture and Quad
		c.glTexCoord2d(1.0, 0.0); c.glVertex3d(-1.0, -1.0,  1.0);	// Bottom Right Of The Texture and Quad
		c.glTexCoord2d(1.0, 1.0); c.glVertex3d(-1.0,  1.0,  1.0);	// Top Right Of The Texture and Quad
		c.glTexCoord2d(0.0, 1.0); c.glVertex3d(-1.0,  1.0, -1.0);	// Top Left Of The Texture and Quad
        c.glEnd();

        c.glPopMatrix();
        
        xrot+=0.3f;								// X Axis Rotation
        yrot+=0.2f;								// Y Axis Rotation
        zrot+=0.4f;								// Z Axis Rotation

        c.glFlush();
    }

    @Override
    public void deinit()
    {
    }
}
