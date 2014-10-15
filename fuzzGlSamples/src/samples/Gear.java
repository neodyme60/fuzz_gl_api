package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;

//http://www.opengl.org/resources/code/samples/glut_examples/mesademos/gears.c
public class Gear extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    double view_rotx = 20.0, view_roty = 30.0, view_rotz = 0.0;
    double angle = 0.0;

    @Override
    public void init(BackBuffer bb)
    {
        GlCore.setBackBuffer(bb);
        c= GlCore.getInstance();
        m_width=bb.get_width();
        m_height=bb.get_height();

//        c.glLightfv(GL_LIGHT0, GL_POSITION, posClip);
        c.glEnable(Glenum.GL_CULL_FACE);
        c.glEnable(Glenum.GL_LIGHTING);
        c.glEnable(Glenum.GL_LIGHT0);
        c.glEnable(Glenum.GL_DEPTH_TEST);

        c.glShadeModel(Glenum.GL_FLAT);

    }

    @Override
    public void update()
    {
        if (c==null)
        return;

        c.glClearColor(0.0,0.0,0.0,1.0);

        c.glEnable(Glenum.GL_DEPTH_TEST);
        c.glClearDepth(100.0);

//        c.glEnable(Glenum.GL_CULL_FACE);
 //       c.glCullFace(Glenum.GL_BACK);

        c.glClear(Glenum.GL_COLOR_BUFFER_BIT);
        c.glClear(Glenum.GL_DEPTH_BUFFER_BIT);

        c.glViewport(0,0,m_width,m_height);

        c.glMatrixMode(Glenum.GL_PROJECTION);
        c.gluPerspective( 45.0, m_width/m_height, 0.1, 50.0 );
//        c.glFrustum(-1.0, 1.0, -m_height/m_width, m_height/m_width, 5.0, 60.0);

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
        c.glTranslated(0.0, 0.0, -20.0);

        c.glPushMatrix();
        c.glRotated(view_rotx, 1.0, 0.0, 0.0);
        c.glRotated(view_roty, 0.0, 1.0, 0.0);
        c.glRotated(view_rotz, 0.0, 0.0, 1.0);

        c.glColor4f(0.8, 0.1, 0.0, 1.0);
        c.glPushMatrix();
        c.glTranslated(-3.0, -2.0, 0.0);
        c.glRotated(angle, 0.0, 0.0, 1.0);
        gear(1.0, 4.0, 1.0, 20, 0.7);
        c.glPopMatrix();

        c.glColor4f(0.0, 0.8, 0.2, 1.0);
        c.glPushMatrix();
        c.glTranslated(3.1, -2.0, 0.0);
        c.glRotated(-2.0 * angle - 9.0, 0.0, 0.0, 1.0);
        gear(0.5, 2.0, 2.0, 10, 0.7);
        c.glPopMatrix();

        c.glColor4f(0.2, 0.2, 1.0, 1.0);
        c.glPushMatrix();
        c.glTranslated(-3.1, 4.2, 0.0);
        c.glRotated(-2.0 * angle - 25.0, 0.0, 0.0, 1.0);
        gear(1.3, 2.0, 0.5, 10, 0.7);
        c.glPopMatrix();

        c.glPopMatrix();

        c.glFlush();

        angle += 2.0;
        view_rotx+=0.2;
        view_roty+=0.3;
    }

    void gear(double inner_radius, double outer_radius, double width, int teeth, double tooth_depth)
    {
        int i;
        double r0, r1, r2;
        double angle, da;
        double u, v, len;

        r0 = inner_radius;
        r1 = outer_radius - tooth_depth / 2.0;
        r2 = outer_radius + tooth_depth / 2.0;

        da = 2.0 * Math.PI / teeth / 4.0;

        //glShadeModel(GL_FLAT);

        //glNormal3f(0.0, 0.0, 1.0);

        /* draw front face */
        c.glBegin(Glenum.GL_QUAD_STRIP);
        for (i = 0; i <= teeth; i++)
        {
            angle = i * 2.0 * Math.PI / teeth;
            c.glVertex3d(r0 * Math.cos(angle), r0 * Math.sin(angle), width * 0.5);
            c.glVertex3d(r1 * Math.cos(angle), r1 * Math.sin(angle), width * 0.5);
            c.glVertex3d(r0 * Math.cos(angle), r0 * Math.sin(angle), width * 0.5);
            c.glVertex3d(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), width * 0.5);
        }
        c.glEnd();

        /* draw front sides of teeth */
        c.glBegin(Glenum.GL_QUADS);
        da = 2.0 * Math.PI / teeth / 4.0;
        for (i = 0; i < teeth; i++)
        {
            angle = i * 2.0 * Math.PI / teeth;

            c.glVertex3d(r1 * Math.cos(angle), r1 * Math.sin(angle), width * 0.5);
            c.glVertex3d(r2 * Math.cos(angle + da), r2 * Math.sin(angle + da), width * 0.5);
            c.glVertex3d(r2 * Math.cos(angle + 2 * da), r2 * Math.sin(angle + 2 * da), width * 0.5);
            c.glVertex3d(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), width * 0.5);
        }
        c.glEnd();

        c.glNormal3d(0.0, 0.0, -1.0);

        /* draw back face */
        c.glBegin(Glenum.GL_QUAD_STRIP);
        for (i = 0; i <= teeth; i++)
        {
            angle = i * 2.0 * Math.PI / teeth;
            c.glVertex3d(r1 * Math.cos(angle), r1 * Math.sin(angle), -width * 0.5);
            c.glVertex3d(r0 * Math.cos(angle), r0 * Math.sin(angle), -width * 0.5);
            c.glVertex3d(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), -width * 0.5);
            c.glVertex3d(r0 * Math.cos(angle), r0 * Math.sin(angle), -width * 0.5);
        }
        c.glEnd();

        /* draw back sides of teeth */
        c.glBegin(Glenum.GL_QUADS);
        da = 2.0 * Math.PI / teeth / 4.0;
        for (i = 0; i < teeth; i++)
        {
            angle = i * 2.0 * Math.PI / teeth;

            c.glVertex3d(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), -width * 0.5);
            c.glVertex3d(r2 * Math.cos(angle + 2 * da), r2 * Math.sin(angle + 2 * da), -width * 0.5);
            c.glVertex3d(r2 * Math.cos(angle + da), r2 * Math.sin(angle + da), -width * 0.5);
            c.glVertex3d(r1 * Math.cos(angle), r1 * Math.sin(angle), -width * 0.5);
        }
        c.glEnd();

        /* draw outward faces of teeth */
        c.glBegin(Glenum.GL_QUAD_STRIP);
        for (i = 0; i < teeth; i++)
        {
            angle = i * 2.0 * Math.PI / teeth;

            c.glVertex3d(r1 * Math.cos(angle), r1 * Math.sin(angle), width * 0.5);
            c.glVertex3d(r1 * Math.cos(angle), r1 * Math.sin(angle), -width * 0.5);
            u = r2 * Math.cos(angle + da) - r1 * Math.cos(angle);
            v = r2 * Math.sin(angle + da) - r1 * Math.sin(angle);
            len = Math.sqrt(u * u + v * v);
            u /= len;
            v /= len;
            c.glNormal3d(v, -u, 0.0);
            c.glVertex3d(r2 * Math.cos(angle + da), r2 * Math.sin(angle + da), width * 0.5);
            c.glVertex3d(r2 * Math.cos(angle + da), r2 * Math.sin(angle + da), -width * 0.5);
            c.glNormal3d(Math.cos(angle), Math.sin(angle), 0.0);
            c.glVertex3d(r2 * Math.cos(angle + 2 * da), r2 * Math.sin(angle + 2 * da), width * 0.5);
            c.glVertex3d(r2 * Math.cos(angle + 2 * da), r2 * Math.sin(angle + 2 * da), -width * 0.5);
            u = r1 * Math.cos(angle + 3 * da) - r2 * Math.cos(angle + 2 * da);
            v = r1 * Math.sin(angle + 3 * da) - r2 * Math.sin(angle + 2 * da);
            c.glNormal3d(v, -u, 0.0);
            c.glVertex3d(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), width * 0.5);
            c.glVertex3d(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), -width * 0.5);
            c.glNormal3d(Math.cos(angle), Math.sin(angle), 0.0);
        }

        c.glVertex3d(r1 * Math.cos(0), r1 * Math.sin(0), width * 0.5);
        c.glVertex3d(r1 * Math.cos(0), r1 * Math.sin(0), -width * 0.5);

        c.glEnd();

//        c.glShadeModel(Glenum.GL_SMOOTH);

        /* draw inside radius cylinder */
        c.glBegin(Glenum.GL_QUAD_STRIP);
        for (i = 0; i <= teeth; i++)
        {
            angle = i * 2.0 * Math.PI / teeth;

            c.glNormal3d(-Math.cos(angle), -Math.sin(angle), 0.0);
            c.glVertex3d(r0 * Math.cos(angle), r0 * Math.sin(angle), -width * 0.5);
            c.glVertex3d(r0 * Math.cos(angle), r0 * Math.sin(angle), width * 0.5);
        }
        c.glEnd();
    }

    @Override
    public void deinit()
    {
    }
}
