package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class SkyBox extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    static double r=0.0;
    int texture[]=new int[6];
    double xrot=0;
    double yrot=0;
    double zrot=0;
    int []pixelData=null;

    static int BACK_ID=0;
    static int FRONT_ID=1;
    static int BOTTOM_ID=2;
    static int TOP_ID=3;
    static int LEFT_ID=4;
    static int RIGHT_ID=5;

    int[] loadBitmap(String filename)
    {
        BufferedImage img=null;
        int[] pixel=null;
        int height=0;
        int width =0;
        try
        {
            img= ImageIO.read(new URL(filename));
            height = img.getHeight(null);
            width = img.getWidth(null);
            pixel= new int[width * width];
            img.getRGB(0, 0, width, height, pixel, 0, width);
        }
        catch(Exception ex)
        {
        }
        return pixel;
    }

    @Override
    public void init(BackBuffer bb)
    {
        GlCore.setBackBuffer(bb);
        c= GlCore.getInstance();
        m_width=bb.get_width();
        m_height=bb.get_height();

        c.glGenTextures(6, texture);
 
        pixelData=loadBitmap("File:textures/skybox2/Back.bmp");
        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[BACK_ID]);
        c.glTexImage2D(Glenum.GL_TEXTURE_2D, 0, 3, 512, 512, 0, Glenum.GL_RGB, Glenum.GL_UNSIGNED_BYTE, pixelData);

        pixelData=loadBitmap("File:textures/skybox2/Front.bmp");
        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[FRONT_ID]);
        c.glTexImage2D(Glenum.GL_TEXTURE_2D, 0, 3, 512, 512, 0, Glenum.GL_RGB, Glenum.GL_UNSIGNED_BYTE, pixelData);
        
        pixelData=loadBitmap("File:textures/skybox2/Bottom.bmp");
        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[BOTTOM_ID]);
        c.glTexImage2D(Glenum.GL_TEXTURE_2D, 0, 3, 512, 512, 0, Glenum.GL_RGB, Glenum.GL_UNSIGNED_BYTE, pixelData);

        pixelData=loadBitmap("File:textures/skybox2/Top.bmp");
        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[TOP_ID]);
        c.glTexImage2D(Glenum.GL_TEXTURE_2D, 0, 3, 512, 512, 0, Glenum.GL_RGB, Glenum.GL_UNSIGNED_BYTE, pixelData);

        pixelData=loadBitmap("File:textures/skybox2/Left.bmp");
        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[LEFT_ID]);
        c.glTexImage2D(Glenum.GL_TEXTURE_2D, 0, 3, 512, 512, 0, Glenum.GL_RGB, Glenum.GL_UNSIGNED_BYTE, pixelData);

        pixelData=loadBitmap("File:textures/skybox2/Right.bmp");
        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[RIGHT_ID]);
        c.glTexImage2D(Glenum.GL_TEXTURE_2D, 0, 3, 512, 512, 0, Glenum.GL_RGB, Glenum.GL_UNSIGNED_BYTE, pixelData);


        c.glClearColor(0.0,0.0,0.0,1.0);

        c.glEnable(Glenum.GL_TEXTURE_2D);
        c.glEnable(Glenum.GL_DEPTH_TEST);
        c.glShadeModel(Glenum.GL_SMOOTH);
        c.glClearDepth(1.0f);

        c.glViewport(0,0,m_width,m_height);

        c.glMatrixMode(Glenum.GL_PROJECTION);
        c.gluPerspective( 45.0, m_width/m_height, 0.1, 1000.0 );

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
        c.gluLookAt(
                0.0,0.0,-40.0,
                0.0,-40.0,0.0,
                0.0,1.0,0.0);
    }

    void CreateSkyBox(float x, float y, float z, float width, float height, float length)
    {
    /*
          All this function does is create 6 squares and distance them according to the measurements
             provided by the user. Mess around with it if you want.
    */
        // Center the <b style="color:black;background-color:#a0ffff">skybox</b>
        x = x - width / 2;
        y = y - height / 2;
        z = z - length / 2;

        // Bind the BACK texture of the sky map to the BACK side of the cube
        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[BACK_ID]);
        c.glBegin(Glenum.GL_QUADS);
            c.glTexCoord2d(1.0, 0.0); c.glVertex3d(x + width, y,z);
            c.glTexCoord2d(1.0, 1.0); c.glVertex3d(x + width, y + height, z);
            c.glTexCoord2d(0.0, 1.0); c.glVertex3d(x,y + height, z);
            c.glTexCoord2d(0.0, 0.0); c.glVertex3d(x,y,z);
        c.glEnd();

        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[FRONT_ID]);
        c.glBegin(Glenum.GL_QUADS);
            c.glTexCoord2d(1.0, 0.0); c.glVertex3d(x,y,z + length);
            c.glTexCoord2d(1.0, 1.0); c.glVertex3d(x,y + height, z + length);
            c.glTexCoord2d(0.0, 1.0); c.glVertex3d(x + width, y + height, z + length);
            c.glTexCoord2d(0.0, 0.0); c.glVertex3d(x + width, y,z + length);
        c.glEnd();

        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[BOTTOM_ID]);
        c.glBegin(Glenum.GL_QUADS);
            c.glTexCoord2d(1.0, 0.0); c.glVertex3d(x,y,z);
            c.glTexCoord2d(1.0, 1.0); c.glVertex3d(x,y,z + length);
            c.glTexCoord2d(0.0, 1.0); c.glVertex3d(x + width, y,z + length);
            c.glTexCoord2d(0.0, 0.0); c.glVertex3d(x + width, y,z);
        c.glEnd();

        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[TOP_ID]);
        c.glBegin(Glenum.GL_QUADS);
/*
            c.glTexCoord2d(0.0, 0.0); c.glVertex3d(m_x + width, m_y + height, m_z);
            c.glTexCoord2d(1.0, 0.0); c.glVertex3d(m_x + width, m_y + height, m_z + length);
            c.glTexCoord2d(1.0, 1.0); c.glVertex3d(m_x,m_y + height,m_z + length);
            c.glTexCoord2d(0.0, 1.0); c.glVertex3d(m_x,m_y + height,m_z);
*/
        c.glTexCoord2d(0.0, 1.0); c.glVertex3d(x + width, y + height, z);
        c.glTexCoord2d(0.0, 0.0); c.glVertex3d(x + width, y + height, z + length);
        c.glTexCoord2d(1.0, 0.0); c.glVertex3d(x,y + height,z + length);
        c.glTexCoord2d(1.0, 1.0); c.glVertex3d(x,y + height,z);
        c.glEnd();

        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[LEFT_ID]);
        c.glBegin(Glenum.GL_QUADS);
            c.glTexCoord2d(1.0, 1.0); c.glVertex3d(x,y + height,z);
            c.glTexCoord2d(0.0, 1.0); c.glVertex3d(x,y + height,z + length);
            c.glTexCoord2d(0.0, 0.0); c.glVertex3d(x,y,z + length);
            c.glTexCoord2d(1.0, 0.0); c.glVertex3d(x,y,z);
        c.glEnd();

        c.glBindTexture(Glenum.GL_TEXTURE_2D, texture[RIGHT_ID]);
        c.glBegin(Glenum.GL_QUADS);
            c.glTexCoord2d(0.0, 0.0); c.glVertex3d(x + width, y,z);
            c.glTexCoord2d(1.0, 0.0); c.glVertex3d(x + width, y,z + length);
            c.glTexCoord2d(1.0, 1.0); c.glVertex3d(x + width, y + height,z + length);
            c.glTexCoord2d(0.0, 1.0); c.glVertex3d(x + width, y + height,z);
        c.glEnd();
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

//        c.glRotated(xrot,1.0f,0.0f,0.0f);						// Rotate On The X Axis
        c.glRotated(yrot,0.0f,1.0f,0.0f);						// Rotate On The Y Axis
//        c.glRotated(zrot,0.0f,0.0f,1.0f);						// Rotate On The Z Axis        c.glPushMatrix();

        CreateSkyBox(0,0,0,500,500,500);
/*
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
*/
        c.glPopMatrix();
        
//        xrot+=0.3f;								// X Axis Rotation
        yrot+=0.2f;								// Y Axis Rotation
 //       zrot+=0.4f;								// Z Axis Rotation

        c.glFlush();
    }

    @Override
    public void deinit()
    {
    }
}
