package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;
import fuzzGl.geometry.Vertex2;

public class TrefoilKnot extends SamplesBase
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

        bz(8,60,3,20,2);
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
                0.0,0.0,50.2,
                0.0,0.0,0.0,
                0.0,1.0,0.0);


        //===================================================>

        c.glRotated(r,0.0,0.0,1.0);
        c.glRotated(r*2,1.0,0.0,1.0);

        c.glPushMatrix();

        r+=0.2;

//            c.glScaled(0.4,0.4,0.4);

            c.glBegin(Glenum.GL_TRIANGLES);

                for(int i=0;i<(arrayFace.length/3);i++)
                {
                    c.glColor3f(1.0,0.0,0.0);
                    c.glVertex3d(arrayVertex[arrayFace[i*3+0]].posClip.m_x,arrayVertex[arrayFace[i*3+0]].posClip.m_y,arrayVertex[arrayFace[i*3+0]].posClip.m_z);

                    c.glColor3f(0.0,1.0,0.0);
                    c.glVertex3d(arrayVertex[arrayFace[i*3+1]].posClip.m_x,arrayVertex[arrayFace[i*3+1]].posClip.m_y,arrayVertex[arrayFace[i*3+1]].posClip.m_z);

                    c.glColor3f(0.0,0.0,1.0);
                    c.glVertex3d(arrayVertex[arrayFace[i*3+2]].posClip.m_x,arrayVertex[arrayFace[i*3+2]].posClip.m_y,arrayVertex[arrayFace[i*3+2]].posClip.m_z);
                }
            c.glEnd();

        c.glPopMatrix();

        c.glFlush();
    }

    void bz(int nbSpanLargeur, int nbSpanLongeur, double paramDouble1, double radius, double paramDouble3)
    {
        int i = nbSpanLargeur * nbSpanLongeur;
        arrayFace=new int[nbSpanLargeur * nbSpanLongeur * 2*3];
        arrayVertex=new Vertex2[nbSpanLargeur * nbSpanLongeur];
        for(int j=0;j<arrayVertex.length;j++)
            arrayVertex[j]=new Vertex2();

        int j = 0;
        double d1 = 0.0D;
        int k = 0;


        while (k < nbSpanLongeur)
        {
            double d2 = radius * Math.cos(2.0 * d1) + paramDouble1 * Math.sin(d1);
            double d3 = radius * Math.sin(2.0 * d1) + paramDouble1 * Math.cos(d1);
            double d4 = radius * Math.cos(3.0 * d1);
            
            double d5 = -2.0D * radius * Math.sin(2.0D * d1) + paramDouble1 * Math.cos(d1);
            double d6 = 2.0D * radius * Math.cos(2.0D * d1) - paramDouble1 * Math.sin(d1);
            double d7 = -3.0D * radius * Math.sin(3.0D * d1);
            double d8 = Math.sqrt(d5 * d5 + d7 * d7);
            double d9 = Math.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
            double d10 = 0.0D;
            int i8 = 0;
            while (i8 < nbSpanLargeur)
            {
                arrayVertex[j].posClip.m_x = d2 - paramDouble3 * (Math.cos(d10) * d7 - Math.sin(d10) * d5 * d6 / d9) / d8;
                arrayVertex[j].posClip.m_y = d3 - paramDouble3 * Math.sin(d10) * d8 / d9;
                arrayVertex[j].posClip.m_z = d4 + paramDouble3 * (Math.cos(d10) * d5 + Math.sin(d10) * d6 * d7 / d9) / d8;
                ++j;
                ++i8;
                d10 += Math.PI*2.0 / nbSpanLargeur;
            }
            ++k;
            d1 += Math.PI*2.0 / nbSpanLongeur;
        }
        int l = 0;
        for (int i1 = 0; i1 < nbSpanLongeur; ++i1)
        {
            int i2 = i1 * nbSpanLargeur;
            int i3 = i2 + nbSpanLargeur;
            i3 %= i;
            int i4 = 0;
             double d4 = (arrayVertex[i2].posClip.m_x - arrayVertex[i3].posClip.m_x) * (arrayVertex[i2].posClip.m_x - arrayVertex[i3].posClip.m_x) +
                    (arrayVertex[i2].posClip.m_y - arrayVertex[i3].posClip.m_y) * (arrayVertex[i2].posClip.m_y - arrayVertex[i3].posClip.m_y) +
                    (arrayVertex[i2].posClip.m_z - arrayVertex[i3].posClip.m_z) * (arrayVertex[i2].posClip.m_z - arrayVertex[i3].posClip.m_z);
            int i7;
            for (int i5 = 1; i5 < nbSpanLargeur; ++i5)
            {
                i7 = i5 + i2 + nbSpanLargeur;
                if (i1 == nbSpanLongeur - 1)
                  i7 = i5;
                double d6 = (arrayVertex[i2].posClip.m_x - arrayVertex[i7].posClip.m_x) * (arrayVertex[i2].posClip.m_x - arrayVertex[i7].posClip.m_x) +
                        (arrayVertex[i2].posClip.m_y - arrayVertex[i7].posClip.m_y) * (arrayVertex[i2].posClip.m_y - arrayVertex[i7].posClip.m_y) +
                        (arrayVertex[i2].posClip.m_z - arrayVertex[i7].posClip.m_z) * (arrayVertex[i2].posClip.m_z - arrayVertex[i7].posClip.m_z);
                if (d6 >= d4)
                  continue;
                d4 = d6;
                i4 = i5;
            }
            for (int i6 = 0; i6 < nbSpanLargeur; ++i6)
            {
                i7 = (nbSpanLargeur + i6 + i4) % nbSpanLargeur;
                arrayFace[l*3+0] = ((i2 + i6) % i);
                i7 = (i6 + 1) % nbSpanLargeur;
                arrayFace[l*3+1] = ((i2 + i7) % i);
                i7 = (i6 + i4 + 1) % nbSpanLargeur;
                arrayFace[l*3+2] = ((i2 + i7 + nbSpanLargeur) % i);
                ++l;
                i7 = (nbSpanLargeur + i6 + i4) % nbSpanLargeur;
                arrayFace[l*3+0] = ((i2 + i6) % i);
                i7 = (i6 + i4 + 1) % nbSpanLargeur;
                arrayFace[l*3+1] = ((i2 + i7 + nbSpanLargeur) % i);
                i7 = (i6 + i4) % nbSpanLargeur;
                arrayFace[l*3+2] = ((i2 + i7 + nbSpanLargeur) % i);
                ++l;
            }
        }
//        parammesh_c.buildFaceNormal();
//        parammesh_c.buildVertexNormal();
    }

    @Override
    public void deinit()
    {
    }
}
