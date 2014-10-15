package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;

public class Nehe05 extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    double rtri=0;
    double rquad=0;
    
    @Override
    public void init(BackBuffer bb)
    {
        GlCore.setBackBuffer(bb);
        c= GlCore.getInstance();
        m_width=bb.get_width();
        m_height=bb.get_height();
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
        c.gluPerspective( 45.0, m_width/m_height, 0.1, 100.0 );

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
/*
        c.gluLookAt(
                0.0,0.0,0.0,
                0.0,0.0,0.0,
                0.0,1.0,0.0);
*/
        c.glPushMatrix();
        
        c.glTranslated(-1.5f,0.0f,-6.0f);
        c.glRotated(rtri,0.0f,1.0f,0.0f);
        
        c.glBegin(Glenum.GL_TRIANGLES);
            c.glColor3f(1.0f,0.0f,0.0f);			// Red
            c.glVertex3d( 0.0f, 1.0f, 0.0f);			// Top Of Triangle (Front)
            c.glColor3f(0.0f,1.0f,0.0f);			// Green
            c.glVertex3d(-1.0f,-1.0f, 1.0f);			// Left Of Triangle (Front)
            c.glColor3f(0.0f,0.0f,1.0f);			// Blue
            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Right Of Triangle (Front)

            c.glColor3f(1.0f,0.0f,0.0f);			// Red
            c.glVertex3d( 0.0f, 1.0f, 0.0f);			// Top Of Triangle (Right)
            c.glColor3f(0.0f,0.0f,1.0f);			// Blue
            c.glVertex3d( 1.0f,-1.0f, 1.0f);			// Left Of Triangle (Right)
            c.glColor3f(0.0f,1.0f,0.0f);			// Green
            c.glVertex3d( 1.0f,-1.0f, -1.0f);			// Right Of Triangle (Right)

            c.glColor3f(1.0f,0.0f,0.0f);			// Red
            c.glVertex3d( 0.0f, 1.0f, 0.0f);			// Top Of Triangle (Back)
            c.glColor3f(0.0f,1.0f,0.0f);			// Green
            c.glVertex3d( 1.0f,-1.0f, -1.0f);			// Left Of Triangle (Back)
            c.glColor3f(0.0f,0.0f,1.0f);			// Blue
            c.glVertex3d(-1.0f,-1.0f, -1.0f);			// Right Of Triangle (Back)

            c.glColor3f(1.0f,0.0f,0.0f);			// Red
            c.glVertex3d( 0.0f, 1.0f, 0.0f);			// Top Of Triangle (Left)
            c.glColor3f(0.0f,0.0f,1.0f);			// Blue
            c.glVertex3d(-1.0f,-1.0f,-1.0f);			// Left Of Triangle (Left)
            c.glColor3f(0.0f,1.0f,0.0f);			// Green
            c.glVertex3d(-1.0f,-1.0f, 1.0f);			// Right Of Triangle (Left)
        c.glEnd();						// Done Drawing The Pyramid

        c.glPopMatrix();
        c.glPushMatrix();

        c.glTranslated(1.5f,0.0f,-7.0f);				// Move Right And Into The Screen
        c.glRotated(rquad,1.0f,1.0f,1.0f);			// Rotate The Cube On X, Y & Z

        c.glBegin(Glenum.GL_QUADS);

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
        c.glEnd();						// Done Drawing The Quad

        rtri+=0.2f;						// Increase The Rotation Variable For The Triangle
        rquad-=0.15f;						// Decrease The Rotation Variable For The Quad

        c.glPopMatrix();
        
        c.glFlush();
    }

    @Override
    public void deinit()
    {
    }
}
