package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;

public class SimpleSmoothSquare extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    static double r=0.0;

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
        c.gluPerspective( 45.0, m_width/m_height, 0.001, 100.0 );

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
        c.gluLookAt(
                0.0,0.0,10.0,
                0.0,0.0,0.0,
                0.0,1.0,0.0);


        //===================================================>

        c.glRotated(r,0.0,0.0,1.0);

        c.glPushMatrix();

        r+=0.2;

//            c.glScaled(0.4,0.4,0.4);

            c.glBegin(Glenum.GL_TRIANGLES);
                c.glColor3f(1.0,0.0,0.0);
                c.glVertex3d(-1.0,-1.0,0.0);

                c.glColor3f(0.0,1.0,0.0);
                c.glVertex3d(-1.0,1.0,0.0);

                c.glColor3f(0.0,0.0,1.0);
                c.glVertex3d(1.0,1.0,0.0);

        c.glColor3f(0.0,0.0,1.0);
        c.glVertex3d(1.0,1.0,0.0);

        c.glColor3f(1.0,1.0,1.0);
        c.glVertex3d(1.0,-1.0,0.0);

        c.glColor3f(1.0,0.0,0.0);
        c.glVertex3d(-1.0,-1.0,0.0);
            c.glEnd();

        c.glPopMatrix();

        c.glFlush();
    }

    @Override
    public void deinit()
    {
    }
}
