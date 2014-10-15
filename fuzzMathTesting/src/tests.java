import fuzzGl.GlCore;
import fuzzGl.Glenum;
import math.Matrix4x4;
import math.Vector4f;
import org.junit.*;

import static org.junit.Assert.*;

public class tests
{
    GlCore c;

    @Before
    public void setUp()
    {
        System.out.println("@Before - setUp");
        c = GlCore.getInstance();
    }

    @Test
    public void pushPop()
    {
        System.out.println("@Test - pushPop");
        
        //one object by default
        assertEquals(c.getStackSize(),1);

        //1 push
        c.glPushMatrix();
        assertEquals(c.getStackSize(),2);
        
        //1 pop
        c.glPopMatrix();
        assertEquals(c.getStackSize(),1);

        //2 push
        c.glPushMatrix();
        c.glPushMatrix();
        assertEquals(c.getStackSize(),3);

        //2 pop
        c.glPopMatrix();
        c.glPopMatrix();
        assertEquals(c.getStackSize(),1);
    }

    @Test
    public void matrixIdentity()
    {
        System.out.println("@Test - matrixIdentity");
        
        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();

        Matrix4x4 m=c.getCurrent();
        assertEquals(1.0,m.m11,0.1);
        assertEquals(1.0,m.m22,0.1);
        assertEquals(1.0,m.m33,0.1);
        assertEquals(1.0,m.m44,0.1);
    }

    @Test
    public void rotX()
    {
        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();

        System.out.println("@Test - rotx");

        c.glRotated(90,1.0,0.0,0.0);

        Matrix4x4 m=c.getCurrent();

        Vector4f v=new Vector4f(0.0,0.0,1.0,1.0);

        Vector4f vv=m.get_mul(v);

        System.out.println(vv.m_x +" "+vv.m_y +" "+vv.m_z +" "+vv.w);
        assertEquals(0.0,vv.m_x,0.1);
        assertEquals(-1.0,vv.m_y,0.1);
        assertEquals(0.0,vv.m_z,0.1);
        assertEquals(1.0,vv.w,0.1);
    }

    @Test
    public void rotY()
    {
        c.glMatrixMode(Glenum.GL_MODELVIEW);
        c.glLoadIdentity();

        System.out.println("@Test - roty");

        c.glRotated(90,0.0,1.0,0.0);

        Matrix4x4 m=c.getCurrent();

        Vector4f v=new Vector4f(0.0,0.0,1.0,0.0);

        Vector4f vv=m.get_mul(v);

        assertEquals(1.0,vv.m_x,0.1);
        assertEquals(0.0,vv.m_y,0.1);
        assertEquals(0.0,vv.m_z,0.1);
        assertEquals(0.0,vv.w,0.1);
    }

    @Test
     public void rotZ()
     {
         c.glMatrixMode(Glenum.GL_MODELVIEW);
         c.glLoadIdentity();

         System.out.println("@Test - rotz");

         c.glRotated(90,1.0,0.0,0.0);

         Matrix4x4 m=c.getCurrent();

         Vector4f v=new Vector4f(0.0,0.0,1.0,0.0);

         Vector4f vv=m.get_mul(v);

         assertEquals(0.0,vv.m_x,0.1);
         assertEquals(-1.0,vv.m_y,0.1);
         assertEquals(0.0,vv.m_z,0.1);
         assertEquals(0.0,vv.w,0.1);
     }

}
