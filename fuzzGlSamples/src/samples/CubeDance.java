package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;
import fuzzGl.geometry.Vertex2;

public class CubeDance extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    static double r=0.0;

    Vertex2 []arrayVertex=null;
    int []arrayFace=null;

    @Override
    public void init(BackBuffer bb)
    {
        GlCore.setBackBuffer(bb);
        c= GlCore.getInstance();
        m_width=bb.get_width();
        m_height=bb.get_height();
    }

    public void drawCube()
    {
        /*
        c.glBegin(Glenum.GL_TRIANGLES);
            c.glColor3f(0.0f,1.0f,0.0f);			// Set The Color To Green
            c.glVertex3d( 1.0f, 1.0f,-1.0f);			// Top Right Of The Quad (Top)
            c.glVertex3d(-1.0f, 1.0f,-1.0f);			// Top Left Of The Quad (Top)
            c.glVertex3d(-1.0f, 1.0f, 1.0f);			// Bottom Left Of The Quad (Top)
            c.glVertex3d( 1.0f, 1.0f, 1.0f);			// Bottom Right Of The Quad (Top)

            c.glColor3f(1.0f,0.5f,0.0f);			// Set The Color To Orange
            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Top Right Of The Quad (Bottom)
            c.glVertex3d(-1.0f,-1.0f, 1.0f);			// Top Left Of The Quad (Bottom)
            c.glVertex3d(-1.0f,-1.0f,-1.0f);			// Bottom Left Of The Quad (Bottom)
            c.glVertex3d( 1.0f,-1.0f,-1.0f);			// Bottom Right Of The Quad (Bottom)

            c.glColor3f(1.0f,0.0f,0.0f);			// Set The Color To Red
            c.glVertex3d( 1.0f, 1.0f, 1.0f);			// Top Right Of The Quad (Front)
            c.glVertex3d(-1.0f, 1.0f, 1.0f);			// Top Left Of The Quad (Front)
            c.glVertex3d(-1.0f,-1.0f, 1.0f);			// Bottom Left Of The Quad (Front)
            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Bottom Right Of The Quad (Front)

            c.glColor3f(1.0f,1.0f,0.0f);			// Set The Color To Yellow
            c.glVertex3d( 1.0f,-1.0f,-1.0f);			// Bottom Left Of The Quad (Back)
            c.glVertex3d(-1.0f,-1.0f,-1.0f);			// Bottom Right Of The Quad (Back)
            c.glVertex3d(-1.0f, 1.0f,-1.0f);			// Top Right Of The Quad (Back)
            c.glVertex3d( 1.0f, 1.0f,-1.0f);			// Top Left Of The Quad (Back)

            c.glColor3f(0.0f,0.0f,1.0f);			// Set The Color To Blue
            c.glVertex3d(-1.0f, 1.0f, 1.0f);			// Top Right Of The Quad (Left)
            c.glVertex3d(-1.0f, 1.0f,-1.0f);			// Top Left Of The Quad (Left)
            c.glVertex3d(-1.0f,-1.0f,-1.0f);			// Bottom Left Of The Quad (Left)
            c.glVertex3d(-1.0f,-1.0f, 1.0f);			// Bottom Right Of The Quad (Left)

            c.glColor3f(1.0f,0.0f,1.0f);			// Set The Color To Violet
            c.glVertex3d( 1.0f, 1.0f,-1.0f);			// Top Right Of The Quad (Right)
            c.glVertex3d( 1.0f, 1.0f, 1.0f);			// Top Left Of The Quad (Right)
            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Bottom Left Of The Quad (Right)
            c.glVertex3d( 1.0f,-1.0f,-1.0f);			// Bottom Right Of The Quad (Right)
        c.glEnd();
        */
        c.glBegin(Glenum.GL_TRIANGLES);
            c.glColor3f(0.0f,1.0f,0.0f);			// Set The Color To Green
            c.glVertex3d( 1.0f, 1.0f,-1.0f);			// Top Right Of The Quad (Top)
            c.glVertex3d(-1.0f, 1.0f,-1.0f);			// Top Left Of The Quad (Top)
            c.glVertex3d(-1.0f, 1.0f, 1.0f);			// Bottom Left Of The Quad (Top)

            c.glVertex3d(-1.0f, 1.0f, 1.0f);			// Bottom Left Of The Quad (Top)
            c.glVertex3d( 1.0f, 1.0f, 1.0f);			// Bottom Right Of The Quad (Top)
            c.glVertex3d( 1.0f, 1.0f,-1.0f);			// Top Right Of The Quad (Top)


            c.glColor3f(1.0f,0.5f,0.0f);			// Set The Color To Orange
            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Top Right Of The Quad (Bottom)
            c.glVertex3d(-1.0f,-1.0f, 1.0f);			// Top Left Of The Quad (Bottom)
            c.glVertex3d(-1.0f,-1.0f,-1.0f);			// Bottom Left Of The Quad (Bottom)

            c.glVertex3d(-1.0f,-1.0f,-1.0f);			// Bottom Left Of The Quad (Bottom)
            c.glVertex3d( 1.0f,-1.0f,-1.0f);			// Bottom Right Of The Quad (Bottom)
            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Top Right Of The Quad (Bottom)

            c.glColor3f(1.0f,0.0f,0.0f);			// Set The Color To Red
            c.glVertex3d( 1.0f, 1.0f, 1.0f);			// Top Right Of The Quad (Front)
            c.glVertex3d(-1.0f, 1.0f, 1.0f);			// Top Left Of The Quad (Front)
            c.glVertex3d(-1.0f,-1.0f, 1.0f);			// Bottom Left Of The Quad (Front)

            c.glVertex3d(-1.0f,-1.0f, 1.0f);			// Bottom Left Of The Quad (Front)
            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Bottom Right Of The Quad (Front)
            c.glVertex3d( 1.0f, 1.0f, 1.0f);			// Top Right Of The Quad (Front)

            c.glColor3f(1.0f,1.0f,0.0f);			// Set The Color To Yellow
            c.glVertex3d( 1.0f,-1.0f,-1.0f);			// Bottom Left Of The Quad (Back)
            c.glVertex3d(-1.0f,-1.0f,-1.0f);			// Bottom Right Of The Quad (Back)
            c.glVertex3d(-1.0f, 1.0f,-1.0f);			// Top Right Of The Quad (Back)

            c.glVertex3d(-1.0f, 1.0f,-1.0f);			// Top Right Of The Quad (Back)
            c.glVertex3d( 1.0f, 1.0f,-1.0f);			// Top Left Of The Quad (Back)
            c.glVertex3d( 1.0f,-1.0f,-1.0f);			// Bottom Left Of The Quad (Back)

            c.glColor3f(0.0f,0.0f,1.0f);			// Set The Color To Blue
            c.glVertex3d(-1.0f, 1.0f, 1.0f);			// Top Right Of The Quad (Left)
            c.glVertex3d(-1.0f, 1.0f,-1.0f);			// Top Left Of The Quad (Left)
            c.glVertex3d(-1.0f,-1.0f,-1.0f);			// Bottom Left Of The Quad (Left)

            c.glVertex3d(-1.0f,-1.0f,-1.0f);			// Bottom Left Of The Quad (Left)
            c.glVertex3d(-1.0f,-1.0f, 1.0f);			// Bottom Right Of The Quad (Left)
            c.glVertex3d(-1.0f, 1.0f, 1.0f);			// Top Right Of The Quad (Left)

            c.glColor3f(1.0f,0.0f,1.0f);			// Set The Color To Violet
            c.glVertex3d( 1.0f, 1.0f,-1.0f);			// Top Right Of The Quad (Right)
            c.glVertex3d( 1.0f, 1.0f, 1.0f);			// Top Left Of The Quad (Right)
            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Bottom Left Of The Quad (Right)

            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Bottom Left Of The Quad (Right)
            c.glVertex3d( 1.0f,-1.0f,-1.0f);			// Bottom Right Of The Quad (Right)
            c.glVertex3d( 1.0f, 1.0f,-1.0f);			// Top Right Of The Quad (Right)
        c.glEnd();
        
    }


    @Override
    public void update()
    {
        if (c==null)
        return;

        c.glClearColor(0.0,0.0,0.0,1.0);

        c.glEnable(Glenum.GL_DEPTH_TEST);
        c.glClearDepth(2.0);

        c.glClear(Glenum.GL_COLOR_BUFFER_BIT);
        c.glClear(Glenum.GL_DEPTH_BUFFER_BIT);

        c.glViewport(0,0,m_width,m_height);

        c.glMatrixMode(Glenum.GL_PROJECTION);
        c.gluPerspective( 45.0, m_width/m_height, 0.001, 100.0 );

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
        c.gluLookAt(
                0.0,0.0,16,
                0.0,0.0,0.0,
                0.0,1.0,0.0);

        //===================================================>

        c.glRotated(r,0.0,0.0,1.0);
        c.glRotated(r*2,1.0,0.0,1.0);

        c.glPushMatrix();

        r+=0.2;
        int rr=3;

        for(int x=-rr;x<rr;x++)
        {
            for(int y=-rr;y<rr;y++)
            {
                for(int z=-rr;z<rr;z++)
                {

                    c.glPushMatrix();
                    c.glTranslated(x,y,z);
                    c.glScaled(0.2,0.2,0.2);
                    drawCube();
                    c.glPopMatrix();
                }
            }
        }


        c.glPopMatrix();

        c.glFlush();
    }

    @Override
    public void deinit()
    {
    }
}
