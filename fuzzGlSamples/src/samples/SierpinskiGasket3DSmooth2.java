package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;
import fuzzGl.geometry.Vertex2;

public class SierpinskiGasket3DSmooth2 extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    static double r=0.0;
    static int RECURSTION_DEPTH=4;

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
        c.glClearDepth(1.0);

        c.glEnable(Glenum.GL_CULL_FACE);
        c.glCullFace(Glenum.GL_BACK);
        
        c.glClear(Glenum.GL_COLOR_BUFFER_BIT);
        c.glClear(Glenum.GL_DEPTH_BUFFER_BIT);

        c.glViewport(0,0,m_width,m_height);

        c.glMatrixMode(Glenum.GL_PROJECTION);
        c.gluPerspective( 45.0, m_width/m_height, 0.1, 100.0 );

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
        c.gluLookAt(
                0.0,0.0,10.0,
                0.0,0.0,0.0,
                0.0,1.0,0.0);

        c.glPushMatrix();

        c.glRotated(r,0.0,1.0,0.0);
        c.glRotated(r,0.0,0.0,1.0);

        c.glScaled(6.0,6.0,6.0);

        tetrahedron(RECURSTION_DEPTH);
        c.glPopMatrix();

        //===================================================>
        c.glFlush();

        r+=0.2;
    }

    void tetrahedron( int m)
    {

        Vertex2 v1=new Vertex2();
        v1.posClip.set(0.0, 0.0, 1.0, 0.0);
        Vertex2 v2=new Vertex2();
        v2.posClip.set(0.0, 0.942809, -0.33333, 0.0);
        Vertex2 v3=new Vertex2();
        v3.posClip.set(-0.816497, -0.471405, -0.333333, 0.0);
        Vertex2 v4=new Vertex2();
        v4.posClip.set(0.816497, -0.471405, -0.333333, 0.0);

    /* Apply triangle subdivision to faces of tetrahedron */

        c.glColor3f(1.0,0.0,0.0);
        divide_triangle(v1, v2, v3, m);
        c.glColor3f(0.0,1.0,0.0);
        divide_triangle(v4, v3, v2, m);
        c.glColor3f(0.0,0.0,1.0);
        divide_triangle(v1, v4, v2, m);
        c.glColor3f(0.0,0.0,0.0);
        divide_triangle(v1, v3, v4, m);
    }

    void divide_triangle(Vertex2 a, Vertex2 b, Vertex2 c, int m)
    {

        /* triangle subdivision using vertex numbers
        righthand rule applied to create outward pointing faces */

        Vertex2 v1=new Vertex2();
        Vertex2 v2=new Vertex2();
        Vertex2 v3=new Vertex2();
        int j;
        if(m>0)
        {
            v1.setLerp(a,b,0.5);
            v2.setLerp(a,c,0.5);
            v3.setLerp(b,c,0.5);
            divide_triangle(a, v1, v2, m-1);
            divide_triangle(c, v2, v3, m-1);
            divide_triangle(b, v3, v1, m-1);
        }
        else
            draw(a,b,c); /* draw triangle at end of recursion */
    }

    void draw(Vertex2 vertex1, Vertex2 vertex2, Vertex2 vertex3)
    {
        c.glBegin (Glenum.GL_TRIANGLES);
            c.glColor3f (1.0, 0.0, 0.0);
            c.glVertex3d (vertex1.posClip.m_x,vertex1.posClip.m_y,vertex1.posClip.m_z);

            c.glColor3f (0.0, 1.0, 0.0);
            c.glVertex3d (vertex2.posClip.m_x,vertex2.posClip.m_y,vertex2.posClip.m_z);

            c.glColor3f (0.0, 0.0, 1.0);
            c.glVertex3d (vertex3.posClip.m_x,vertex3.posClip.m_y,vertex3.posClip.m_z);

        c.glEnd();
    }


    @Override
    public void deinit()
    {
    }
}
