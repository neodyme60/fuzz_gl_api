package samples;

import fuzzGl.GlCore;
import fuzzGl.Glenum;
import fuzzGl.BackBuffer;
import fuzzGl.geometry.Vertex2;

public class SierpinskiGasket3DSmooth extends SamplesBase
{
    int m_width=0;
    int m_height=0;
    static double r=0.0;
    static int RECURSTION_DEPTH=3;

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
        c.glClearDepth(100.0);

        c.glEnable(Glenum.GL_CULL_FACE);
        c.glCullFace(Glenum.GL_BACK);
        
//        c.glShadeModel(Glenum.GL_FLAT);

        c.glClear(Glenum.GL_COLOR_BUFFER_BIT);
        c.glClear(Glenum.GL_DEPTH_BUFFER_BIT);


//        c.glViewport(m_width/4,m_height/4,m_width/2,m_height/2);
        c.glViewport(0,0,m_width,m_height);

        c.glMatrixMode(Glenum.GL_PROJECTION);
        c.gluPerspective( 45.0, m_width/m_height, 0.001, 100.0 );

        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();
        c.gluLookAt(
                0.0,0.0,5.0,
                0.0,0.0,0.0,
                0.0,1.0,0.0);

        c.glPushMatrix();

        c.glRotated(r,0.0,1.0,0.0);
        c.glRotated(r,0.0,0.0,1.0);

        c.glScaled(6.0,6.0,6.0);

        SierpinskiGasket3f(0.7,5);
        c.glPopMatrix();

        //===================================================>
        c.glFlush();

        r+=0.2;
    }

void SierpinskiGasket3f_A(Vertex2 _point0Arr, Vertex2 _point1Arr, Vertex2 _point2Arr, Vertex2 _point3Arr, int _count)
{
	if(_count > 0)
	{
		//reduce count
		_count -= 1;

		//new points array
		Vertex2 newPoint01Arr=new Vertex2();
		Vertex2 newPoint12Arr=new Vertex2();
		Vertex2 newPoint20Arr=new Vertex2();
		Vertex2 newPoint03Arr=new Vertex2();
		Vertex2 newPoint13Arr=new Vertex2();
		Vertex2 newPoint23Arr=new Vertex2();

		//fill points
        newPoint01Arr.setLerp(_point0Arr, _point1Arr,0.5);
        newPoint12Arr.setLerp(_point1Arr, _point2Arr,0.5);
        newPoint20Arr.setLerp(_point2Arr, _point0Arr,0.5);
        newPoint03Arr.setLerp(_point0Arr, _point3Arr,0.5);
        newPoint13Arr.setLerp(_point1Arr, _point3Arr,0.5);
        newPoint23Arr.setLerp(_point2Arr, _point3Arr,0.5);
        
		//  set_sub-triangle 1
		SierpinskiGasket3f_A(newPoint01Arr, _point1Arr, newPoint12Arr, newPoint13Arr, _count);

		//  set_sub-triangle 2
		SierpinskiGasket3f_A(newPoint12Arr, _point2Arr, newPoint20Arr, newPoint23Arr, _count);

		//  set_sub-triangle 3
		SierpinskiGasket3f_A(newPoint01Arr, _point0Arr, newPoint20Arr, newPoint03Arr, _count);

		//  set_sub-triangle 4
		SierpinskiGasket3f_A(newPoint03Arr, newPoint13Arr, newPoint23Arr, _point3Arr, _count);
	}
	else
	{
		//draw faces
		//side 0 (bottom)
		c.glColor4f(1, 0, 0, 1); //0
		c.glVertex3d(_point0Arr.posClip.m_x,_point0Arr.posClip.m_y,_point0Arr.posClip.m_z);
		c.glColor4f(0, 0, 1, 1); //2
		c.glVertex3d(_point2Arr.posClip.m_x,_point2Arr.posClip.m_y,_point2Arr.posClip.m_z);
        c.glColor4f(0, 1, 0, 1); //1
        c.glVertex3d(_point1Arr.posClip.m_x,_point1Arr.posClip.m_y,_point1Arr.posClip.m_z);
        
		//side 1
		c.glColor4f(1, 0, 0, 1); //0
		c.glVertex3d(_point0Arr.posClip.m_x,_point0Arr.posClip.m_y,_point0Arr.posClip.m_z);
		c.glColor4f(0, 1, 0, 1); //1
		c.glVertex3d(_point1Arr.posClip.m_x,_point1Arr.posClip.m_y,_point1Arr.posClip.m_z);
		c.glColor4f(0.7, 0.7, 0.7, 1); //3
		c.glVertex3d(_point3Arr.posClip.m_x,_point3Arr.posClip.m_y,_point3Arr.posClip.m_z);

		//side 2
		c.glColor4f(0, 1, 0, 1); //1
		c.glVertex3d(_point1Arr.posClip.m_x,_point1Arr.posClip.m_y,_point1Arr.posClip.m_z);
		c.glColor4f(0, 0, 1, 1); //2
		c.glVertex3d(_point2Arr.posClip.m_x,_point2Arr.posClip.m_y,_point2Arr.posClip.m_z);
		c.glColor4f(0.7, 0.7, 0.7, 1); //3
		c.glVertex3d(_point3Arr.posClip.m_x,_point3Arr.posClip.m_y,_point3Arr.posClip.m_z);

		//side 3
		c.glColor4f(0, 0, 1, 1); //2
		c.glVertex3d(_point2Arr.posClip.m_x,_point2Arr.posClip.m_y,_point2Arr.posClip.m_z);
		c.glColor4f(1, 0, 0, 1); //0
		c.glVertex3d(_point0Arr.posClip.m_x,_point0Arr.posClip.m_y,_point0Arr.posClip.m_z);
		c.glColor4f(0.7, 0.7, 0.7, 1); //3
		c.glVertex3d(_point3Arr.posClip.m_x,_point3Arr.posClip.m_y,_point3Arr.posClip.m_z);
	}
}
    

    void SierpinskiGasket3f(double _rad, int _count)
    {
        Vertex2 points0Arr=new Vertex2(); points0Arr.posClip.set(_rad, 0, 0, 0);
        Vertex2 points1Arr=new Vertex2(); points1Arr.posClip.set(_rad * (-0.5f), _rad * (0.866025404f), 0, 0);
        Vertex2 points2Arr=new Vertex2(); points2Arr.posClip.set(_rad * (-0.5f), _rad * (-0.866025404f), 0, 0);
        Vertex2 points3Arr=new Vertex2(); points3Arr.posClip.set(0, 0, _rad, 0);
        c.glBegin(Glenum.GL_TRIANGLES);
        SierpinskiGasket3f_A(points0Arr, points1Arr, points2Arr, points3Arr, _count);
        c.glEnd();
    }
    

    @Override
    public void deinit()
    {
    }
}
