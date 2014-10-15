package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;
import fuzzGl.geometry.Vertex2;
import math.Vector3f;

public class PQTorusKnot extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    static double r=0.0;

    Vertex2 []arrayVertex=null;
    int []arrayFace=null;

          static double angle=0;
    @Override
    public void init(BackBuffer bb)
    {
        GlCore.setBackBuffer(bb);
        c= GlCore.getInstance();
        m_width=bb.get_width();
        m_height=bb.get_height();

        angle+=0.1;


//        bz(8,60,3,20,2);
        generate_torusknot(
            256,
             16,
             (float)(1.5f + Math.sin(angle) / 2),
             0.2f,
             12,
             angle * 30,
             0.5f,
             4,
             64,
             2,
             5);
        
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


// Torus knot generation
// written by Jari Komppa aka Sol / Trauma
// Based on:
// http://www.blackpawn.com/texts/pqtorus/default.html

    void  generate_torusknot(int aSteps,           // in: Number of steps in the torus knot
                                      int aFacets,          // in: Number of facets
                                      double aScale,         // in: Scale of the knot
                                      double aThickness,     // in: Thickness of the knot
                                      double aClumps,        // in: Number of clumps in the knot
                                      double aClumpOffset,   // in: Offset of the clump (in 0..2pi)
                                      double aClumpScale,    // in: Scale of a clump
                                      double aUScale,        // in: U coordinate scale
                                      double aVScale,        // in: V coordinate scale
                                      double aP,             // in: P parameter of the knot
                                      double aQ)             // in: Q parameter of the knot
    {
        int i, j;

        aThickness *= aScale;

        arrayVertex=new Vertex2[((aSteps + 1) * (aFacets + 1) + 1)];
        arrayFace=new int[((aSteps + 1) * aFacets) * 3*2];

        for (j = 0; j < aFacets; j++)
        {
            for (i = 0; i < aSteps + 1; i++)
            {
//                idx[i * 2 + 0 + j * (aSteps + 1) * 2] = ((j + 1) + i * (aFacets + 1));
//                idx[i * 2 + 1 + j * (aSteps + 1) * 2] = (j + i * (aFacets + 1));
//                int index=i * 2 + 0 + j * (aSteps + 1) * 2;
                int index=(aFacets*aSteps)+i;
                arrayFace[index*3+0]=index;
                arrayFace[index*3+1]=index+1;
                arrayFace[index*3+2]=index+aSteps;
            }
        }

        for (i = 0; i < aSteps; i++)
        {
            Vector3f centerpoint=new Vector3f();
            double Pp = aP * i * Math.PI*2.0 / aSteps;
            double Qp = aQ * i * Math.PI*2.0 / aSteps;
            double r = (.5 * (2 + Math.sin(Qp))) * aScale;
            centerpoint.m_x = r * Math.cos(Pp);
            centerpoint.m_y = r * Math.cos(Qp);
            centerpoint.m_z = r * Math.sin(Pp);

            Vector3f nextpoint=new Vector3f();
            Pp = aP * (i + 1) * Math.PI*2.0 / aSteps;
            Qp = aQ * (i + 1) * Math.PI*2.0 / aSteps;
            r = (.5 * (2 + Math.sin(Qp))) * aScale;
            nextpoint.m_x = r * Math.cos(Pp);
            nextpoint.m_y = r * Math.cos(Qp);
            nextpoint.m_z = r * Math.sin(Pp);

            Vector3f T=new Vector3f(nextpoint.m_x - centerpoint.m_x,nextpoint.m_y - centerpoint.m_y,nextpoint.m_z - centerpoint.m_z);
            Vector3f N=new Vector3f(nextpoint.m_x + centerpoint.m_x,nextpoint.m_y + centerpoint.m_y,nextpoint.m_z + centerpoint.m_z);
            Vector3f B=new Vector3f(T.m_y *N.m_z - T.m_z *N.m_y,T.m_z *N.m_x - T.m_x *N.m_z,T.m_x *N.m_y - T.m_y *N.m_x);

            N.m_x = B.m_y *T.m_z - B.m_z *T.m_y;
            N.m_y = B.m_z *T.m_x - B.m_x *T.m_z;
            N.m_z = B.m_x *T.m_y - B.m_y *T.m_x;

            B.set_normalize();
            N.set_normalize();

            for (j = 0; j < aFacets; j++)
            {
                double pointx = Math.sin(j * Math.PI*2.0 / aFacets) * aThickness * (((float)Math.sin(aClumpOffset + aClumps * i * Math.PI*2.0 / aSteps) * aClumpScale) + 1);
                double pointy = Math.cos(j * Math.PI*2.0 / aFacets) * aThickness * (((float)Math.cos(aClumpOffset + aClumps * i * Math.PI*2.0 / aSteps) * aClumpScale) + 1);

                Vertex2 vv=new Vertex2();
                arrayVertex[i * (aFacets + 1) * 3 + j * 3 ]=vv;

                vv.posClip.m_x = N.m_x * pointx + B.m_x * pointy + centerpoint.m_x;
                vv.posClip.m_y = N.m_y * pointx + B.m_y * pointy + centerpoint.m_y;
                vv.posClip.m_z = N.m_z * pointx + B.m_z * pointy + centerpoint.m_z;

                vv.normal.set(vv.posClip.m_x -centerpoint.m_x, vv.posClip.m_y -centerpoint.m_y, vv.posClip.m_z -centerpoint.m_z);
                vv.normal.set_normalize();

                vv.texturePos.m_x = (j / aFacets) * aUScale;
                vv.texturePos.m_y = (i / aSteps) * aVScale;
            }

            // create duplicate vertex for sideways wrapping
            // otherwise identical to first vertex in the 'ring' except for the U coordinate
            Vertex2 vv=new Vertex2();
            arrayVertex[i * (aFacets + 1) * 3 + j * 3 ]=vv;
            vv.set(arrayVertex[i * (aFacets + 1) * 3 + 0]);
        }

        // create duplicate ring of vertices for longways wrapping
        // otherwise identical to first 'ring' in the knot except for the V coordinate
        for (j = 0; j < aFacets; j++)
        {
            Vertex2 vv=new Vertex2();
            arrayVertex[aSteps * (aFacets + 1) * 3 + j * 3 ]=vv;
            vv.set(arrayVertex[j * 3 + 0]);
        }

        // finally, there's one vertex that needs to be duplicated due to both U and V coordinate.
        Vertex2 vv=new Vertex2();
        arrayVertex[aSteps * (aFacets + 1) * 3 + aFacets * 3]=vv;
        vv.set(arrayVertex[0]);
        vv.texturePos.m_x =aUScale;
        vv.texturePos.m_y =aVScale;
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
