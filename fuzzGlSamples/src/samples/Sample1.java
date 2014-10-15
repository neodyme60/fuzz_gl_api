package samples;

import fuzzGl.GlCore;
import fuzzGl.BackBuffer;
import fuzzGl.Glenum;

public class Sample1 extends SamplesBase
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

//        c.glViewport(m_width/4,m_height/4,m_width/2,m_height/2);
        c.glViewport(0,0,m_width,m_height);

        c.glMatrixMode(Glenum.GL_PROJECTION);
        c.gluPerspective( 45.0, m_width/m_height, 0.001, 100.0 );

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
        c.gluLookAt(
                0.0,3.0,10.0,
                0.0,-12.0,0.0,
                0.0,1.0,0.0);

/*

        c.glRotated(r,0.0,1.0,0.0);

        c.glPushMatrix();

        r+=0.02;

        double h=0.0;
        int rr=1;

        for(int m_x=-rr;m_x<=rr;m_x++)
        {
            for(int m_z=-rr;m_z<=rr;m_z++)
            {
                c.glPushMatrix();

                c.glTranslated(m_x*1.0,0.0,m_z*1.0);
                c.glScaled(0.4,0.4,0.4);

//                h=Math.sin(Math.sin(r)*0.06*m_x*m_z)*1.0;
                h=Math.sin(Math.sin(r+m_x)*0.06*m_x*m_z)*1.0;

                c.glBegin(fuzzGl.Glenum.GL_TRIANGLES);
                c.glColor3f(1.0,1.0,0.0);
                c.glVertex3d(-1.0,h,1.0);

                c.glColor3f(1.0,1.0,0.0);
                c.glVertex3d(1.0,h,1.0);

                c.glColor3f(1.0,1.0,0.0);
                c.glVertex3d(1.0,h,-1.0);
                c.glEnd();


                c.glBegin(fuzzGl.Glenum.GL_LINE_LOOP);

                    c.glColor3f(1.0,1.0,0.0);
                    c.glVertex3d(-1.0,h,-1.0);

                    c.glColor3f(1.0,1.0,0.0);
                    c.glVertex3d(-1.0,h,1.0);

                    c.glColor3f(1.0,1.0,0.0);
                    c.glVertex3d(1.0,h,1.0);

                    c.glColor3f(1.0,1.0,0.0);
                    c.glVertex3d(1.0,h,-1.0);
                c.glEnd();

                c.glPopMatrix();
            }
        }

        c.glPopMatrix();
*/
        //===================================================>

        c.glRotated(r,0.0,1.0,0.0);

        c.glPushMatrix();

        r+=0.02;

        double h=0.0;
        int rr=10;

        for(int x=-rr;x<=rr;x++)
        {
            for(int z=-rr;z<=rr;z++)
            {
                c.glPushMatrix();

                c.glTranslated(x*1.0,0.0,z*1.0);
                c.glScaled(0.4,0.4,0.4);

//                h=Math.sin(Math.sin(r)*0.06*m_x*m_z)*1.0;
                h=Math.sin(Math.sin(r+x)*0.06*x*z)*1.0;

                c.glPushMatrix();

                c.glTranslated(-0.2,0.0,0.0);

                c.glColor3f(Math.abs((float)x/(float)rr),Math.abs((float)h/5.0),Math.abs((float)z/(float)rr));
                                
                c.glBegin(Glenum.GL_TRIANGLES);
                    c.glColor3f(1.0,0.0,0.0);
                    c.glVertex3d(-1.0,h,-1.0);
                    c.glColor3f(0.0,1.0,0.0);
                    c.glVertex3d(-1.0,h,1.0);
                    c.glColor3f(0.0,0.0,1.0);
                    c.glVertex3d(1.0,h,1.0);
                c.glEnd();

                c.glPopMatrix();

                c.glTranslated(0.2,0.0,0.0);

                c.glBegin(Glenum.GL_TRIANGLES);

                c.glColor3f(Math.abs((float)x/(float)rr),Math.abs((float)h/5.0),Math.abs((float)z/(float)rr));
                    c.glColor3f(1.0,0.0,0.0);
                    c.glVertex3d(1.0,h,1.0);
                    c.glColor3f(0.0,1.0,0.0);
                    c.glVertex3d(1.0,h,-1.0);
                    c.glColor3f(0.0,0.0,1.0);
                    c.glVertex3d(-1.0,h,-1.0);
                c.glEnd();

                c.glPopMatrix();
            }
        }

        c.glPopMatrix();

        //===================================================>
        c.glFlush();

    }

    @Override
    public void deinit()
    {
    }
}
