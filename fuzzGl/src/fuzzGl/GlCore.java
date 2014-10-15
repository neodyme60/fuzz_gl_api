package fuzzGl;

import fuzzGl.geometry.Vertex2;
import fuzzGl.rasterizer.*;
import fuzzGl.texture.GlTexture;
import fuzzGl.texture.TextureList;
import math.Matrix4x4;
import math.Vector3f;
import math.Vector4f;


import java.util.*;

import static fuzzGl.graphics.Color.color4fToInt;

public class GlCore
{
    class blockRenderType
    {
        public int indexVertexStart=0;
        public int count=0;
        public int idTexture=-1;
        Glenum blockType;
        double glDepthFar=1.0;
        double glDepthNear=0.0;
    }

    //texture list
    TextureList textureList=new TextureList();

    //glbegin/glend vertex list
    static int MAXVERTEX=50000;
    private int vertexListCurrentIndex=0;
    private ArrayList<Vertex2> vertexList= new ArrayList<Vertex2>();
    private ArrayList<blockRenderType> blockRendertypeList= new ArrayList<blockRenderType>();

    private static BackBuffer m_backBuffer=null;

    public boolean isBegin=false;

    public Glenum currentError=Glenum.GL_NO_ERROR;

    private StackMatrixBase matrixMODELVIEW=new StackMatrixBase();
    private StackMatrixBase matrixPROJECTION=new StackMatrixBase();
    private Stack<Matrix4x4> matrixCurrentStack;

    //current render bloc
    private blockRenderType b=null;

    //shading model GL_SMOOTH or GL_FLAT
    private Glenum shadeModel=Glenum.GL_SMOOTH;

    //glClearColor
    private int clearColor=0;
    
    //glClearAccum
    private int clearAcum=0;

    //glClearStencil
    private int clearStencil=0;

    //glClearDepth
    private double clearDepth=1.0;

    //final matrix
    private Matrix4x4 worldToScreen=new Matrix4x4().set_identity();

    //stats
    private int polyRenderedCount=0;
    private int polyTotal=0;
    private int polyBackfaceCount=0;

    //glViewPort
    private double viewPortX=0;
    private double viewPortY=0;
    private double viewPortWidth=1;
    private double viewPortHeight=1;

    //gl error
    private Glenum glError=Glenum.GL_NO_ERROR;

    //glEnable section
    private boolean textue2dEnable=false;
    private boolean depthTestEnable=false;
    private boolean lightEnable=false;
    private boolean cullFace=false;
    private boolean enableLight0=false;
    private boolean enableLight1=false;
    private boolean enableLight2=false;
    private boolean enableLight3=false;
    private boolean enableLight4=false;
    private boolean enableLight5=false;
    private boolean enableLight6=false;
    private boolean enableLight7=false;
    private boolean enableFog=false;
    private boolean enableColorMaterial=false;

    private boolean isDepthBufferWritable=true;

    private Glenum cullFaceType=Glenum.GL_BACK;

    //glDepthRange
    private double glDepthRangeNear=0.0;
    private double glDepthRangeFar=1.0;

    //glNormal
    private Vector3f currentNormal=new Vector3f(0.0,0.0,1.0);

    //glTexCoord
    private Vector4f currentTextureCood=new Vector4f(0.0,0.0,0.0,0.0);

    private int currentTextureId=-1;

    //glColor
    private Vector4f currentColor=new Vector4f(0.0,0.0,0.0,1.0);

	private static GlCore m_core=null;
	
	public static GlCore getInstance()
	{
		if (m_core==null)
			m_core=new GlCore();
		return m_core;
	}

    private void initStacks()
    {
        matrixMODELVIEW.clear();
        matrixPROJECTION.clear();

        matrixMODELVIEW.push(new Matrix4x4().set_identity());
        matrixPROJECTION.push(new Matrix4x4().set_identity());
    }


    //Specifies whether the depth buffer is enabled for writing. If flag is GL_FALSE, depth buffer writing is disabled. Otherwise, it is enabled. Initially, depth buffer writing is enabled.
    public void glDepthMask(boolean flag)
    {
        if (isBegin)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }
        isDepthBufferWritable=flag;
    }
    
    private GlCore()
    {
        //default current stack
        matrixCurrentStack=matrixMODELVIEW;

        initStacks();

        //alloc vertex array
        for(int i=0;i<MAXVERTEX;i++)
            vertexList.add(new Vertex2());
    }

    private void updateWorldToScreenMatrix()
    {
        worldToScreen=matrixMODELVIEW.peek().get_mul(matrixPROJECTION.peek());
    }

    public static void setBackBuffer(BackBuffer bb)
    {
        m_backBuffer=bb;
    }

    public int getStackSize()
    {
        return matrixCurrentStack.size();
    }

    void glGetBooleanv(Glenum pname, boolean[] params)
    {
        //todo
    }

    void glGetDoublev(Glenum pname, double[] params)
    {
        //todo
    }

    void glGetFloatv(Glenum pname, float[] params)
    {
        //todo
    }

    void glGetIntegerv(Glenum pname,int []	params)
    {
        //todo
    }

    public void glDepthRange(double nearVal, double farVal)
    {
        if (isBegin)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }
        glDepthRangeNear=Math.min(Math.max(nearVal,0.0),1.0);
        glDepthRangeFar=Math.min(Math.max(farVal,0.0),1.0);
    }

    public void glCullFace(Glenum e)
    {
        if (isBegin)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }
        switch(e)
        {
            case GL_BACK:
            case GL_FRONT:
            case GL_FRONT_AND_BACK:
                cullFaceType=e;
                break;
            default:
                glError=Glenum.GL_INVALID_ENUM;
        }        
    }

    //
    //texture section
    //
    public void glGenTextures(int n, int[] textures)
    {
        if (isBegin)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }
        
        if (n<0)
        {
            glError=Glenum.GL_INVALID_VALUE;
            return;
        }
        textureList.allocates(n,textures);
    }

    //set current currentTexture
    public void glBindTexture(Glenum target, int idTexture)
    {
        if (isBegin)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }

        if (idTexture==-1)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }

        currentTextureId=idTexture;

        if (textureList.getTextureById(currentTextureId).type!=target)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }

        switch(target)
        {
            case GL_TEXTURE_1D:
                break;
            case GL_TEXTURE_2D:
                break;
//            case GL_TEXTURE_3D:
//                break;
            default:
                glError=Glenum.GL_INVALID_ENUM;
        }
    }

    //http://neogamedev.chable.net/index.php?PageID=2033
    /*
    * float MipmapLevel(float2 uv, float2 textureSize)
{
float2 dx = ddx(uv * textureSize.m_x);
float2 dy = ddy(uv * textureSize.m_y);
float d = max( get_dot(dx, dx), get_dot(dy, dy) );

return log2( sqrt(d) );
} 
    * */
    public void glTexImage2D(Glenum  target, int level,int internalFormat,int width,int height,int border,Glenum format,Glenum type,int[] data)
    {
        if (isBegin)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }

        if (currentTextureId==-1)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }
        textureList.getTextureById(currentTextureId).addTextureLevel(new GlTexture(width,height,data),0);
    }

    void glTexImage1D(Glenum target,int level,int internalFormat,int width,int border,Glenum format,Glenum type,int[]data)
    {
        
    }

    void glDeleteTextures(int n, int[]	textures)
    {
        
    }

    //
    //
    //

    /*
        GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT, GL_ACCUM_BUFFER_BIT, and GL_STENCIL_BUFFER_BIT    
    */
    public void glClear(Glenum c)
    {
        switch(c)
        {
            case GL_COLOR_BUFFER_BIT:
                m_backBuffer.clear_color_buffer(clearColor);
                break;
            case GL_DEPTH_BUFFER_BIT:
                m_backBuffer.clear_depth_buffer(clearDepth);
                break;
            case GL_ACCUM_BUFFER_BIT:
                //todo
                break;
        }

        //todo
//        if ((c & fuzzGl.Glenum.GL_STENCIL_BUFFER_BIT) == fuzzGl.Glenum.GL_STENCIL_BUFFER_BIT)
//            _backBuffer.clearStencilBuffer(clearStencil);
    }

    public void glClearAccum(double r, double g, double b, double a)
    {
        clearAcum=color4fToInt(r,g,b,a);
    }

    public void glClearColor(double r, double g, double b, double a)
    {
        clearColor=color4fToInt(r,g,b,a);
    }

    public void glClearStencil(int c)
    {
        clearStencil=c;
    }

    public void glClearDepth(double c)
    {
        clearDepth=c*65536.0;
    }

    public void glClearIndex(double c)
    {
        //todo
    }

    public Glenum glGetError()
    {
        return glError;
    }

    public void glEnable(Glenum n)
    {
        if (isBegin)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }
        
        switch(n)
        {
            case GL_FOG:
                enableFog=true;
                break;
            case GL_LIGHTING:   //http://www.sjbaker.org/steve/omniv/opengl_lighting.html
                lightEnable=true;
                break;
            case GL_TEXTURE_1D:
                break;
            case GL_TEXTURE_2D:
                textue2dEnable=true;
                break;
            case GL_LINE_STIPPLE:
                break;
            case GL_POLYGON_STIPPLE:
                break;
            case GL_CULL_FACE:
                cullFace=true;
                break;
            case GL_ALPHA_TEST:
                break;
            case GL_BLEND:
                break;
            case GL_INDEX_LOGIC_OP:
                break;
            case GL_COLOR_LOGIC_OP:
                break;
            case GL_DITHER:
                break;
            case GL_STENCIL_TEST:
                break;
            case GL_DEPTH_TEST:
                depthTestEnable=true;
                break;
            case GL_CLIP_PLANE0:
                break;
            case GL_CLIP_PLANE1:
                break;
            case GL_CLIP_PLANE2:
                break;
            case GL_CLIP_PLANE3:
                break;
            case GL_CLIP_PLANE4:
                break;
            case GL_CLIP_PLANE5:
                break;
            case GL_LIGHT0:
                enableLight0=true;
                break;
            case GL_LIGHT1:
                enableLight1=true;
                break;
            case GL_LIGHT2:
                enableLight2=true;
                break;
            case GL_LIGHT3:
                enableLight3=true;
                break;
            case GL_LIGHT4:
                enableLight4=true;
                break;
            case GL_LIGHT5:
                enableLight5=true;
                break;
            case GL_LIGHT6:
                enableLight6=true;
                break;
            case GL_LIGHT7:
                enableLight7=true;
                break;
            case GL_TEXTURE_GEN_S:
                break;
            case GL_TEXTURE_GEN_T:
                break;
            case GL_TEXTURE_GEN_R:
                break;
            case GL_TEXTURE_GEN_Q:
                break;
            case GL_MAP1_VERTEX_3:
                break;
            case GL_MAP1_VERTEX_4:
                break;
            case GL_MAP1_COLOR_4:
                break;
            case GL_MAP1_INDEX:
                break;
            case GL_MAP1_NORMAL:
                break;
            case GL_MAP1_TEXTURE_COORD_1:
                break;
            case GL_MAP1_TEXTURE_COORD_2:
                break;
            case GL_MAP1_TEXTURE_COORD_3:
                break;
            case GL_MAP1_TEXTURE_COORD_4:
                break;
            case GL_MAP2_VERTEX_3:
                break;
            case GL_MAP2_VERTEX_4:
                break;
            case GL_MAP2_COLOR_4:
                break;
            case GL_MAP2_INDEX:
                break;
            case GL_MAP2_NORMAL:
                break;
            case GL_MAP2_TEXTURE_COORD_1:
                break;
            case GL_MAP2_TEXTURE_COORD_2:
                break;
            case GL_MAP2_TEXTURE_COORD_3:
                break;
            case GL_MAP2_TEXTURE_COORD_4:
                break;
            case GL_POINT_SMOOTH:
                break;
            case GL_LINE_SMOOTH:
                break;
            case GL_POLYGON_SMOOTH:
                break;
            case GL_SCISSOR_TEST:
                break;
            case GL_COLOR_MATERIAL:
                enableColorMaterial=true;
                break;
            case GL_NORMALIZE:
                break;
            case GL_AUTO_NORMAL:
                break;
            case GL_VERTEX_ARRAY:
                break;
            case GL_NORMAL_ARRAY:
                break;
            case GL_COLOR_ARRAY:
                break;
            case GL_INDEX_ARRAY:
                break;
            case GL_TEXTURE_COORD_ARRAY:
                break;
            case GL_EDGE_FLAG_ARRAY:
                break;
            case GL_POLYGON_OFFSET_POINT:
                break;
            case GL_POLYGON_OFFSET_LINE:
                break;
            case GL_POLYGON_OFFSET_FILL:
                break;
            default:
                glError=Glenum.GL_INVALID_ENUM;
                break;
        }
    }

    //http://cairns.it.jcu.edu.au/Subjects/cp2060/resources/bluebook/glGetError.html
/*
    public int glGetError()
    {
        return currentError;
    }
*/
    public void glDisable(Glenum n)
    {
        if (isBegin)
        {
            glError=Glenum.GL_INVALID_OPERATION;
            return;
        }
        
        switch(n)
        {
            case GL_FOG:
                enableFog=false;
                break;
            case GL_LIGHTING:
                lightEnable=false;
                break;
            case GL_TEXTURE_1D:
                break;
            case GL_TEXTURE_2D:
                textue2dEnable=false;
                break;
            case GL_LINE_STIPPLE:
                break;
            case GL_POLYGON_STIPPLE:
                break;
            case GL_CULL_FACE:
                cullFace=false;
                break;
            case GL_ALPHA_TEST:
                break;
            case GL_BLEND:
                break;
            case GL_INDEX_LOGIC_OP:
                break;
            case GL_COLOR_LOGIC_OP:
                break;
            case GL_DITHER:
                break;
            case GL_STENCIL_TEST:
                break;
            case GL_DEPTH_TEST:
                depthTestEnable=false;
                break;
            case GL_CLIP_PLANE0:
                break;
            case GL_CLIP_PLANE1:
                break;
            case GL_CLIP_PLANE2:
                break;
            case GL_CLIP_PLANE3:
                break;
            case GL_CLIP_PLANE4:
                break;
            case GL_CLIP_PLANE5:
                break;
            case GL_LIGHT0:
                enableLight0=false;
                break;
            case GL_LIGHT1:
                enableLight1=false;
                break;
            case GL_LIGHT2:
                enableLight2=false;
                break;
            case GL_LIGHT3:
                enableLight3=false;
                break;
            case GL_LIGHT4:
                enableLight4=false;
                break;
            case GL_LIGHT5:
                enableLight5=false;
                break;
            case GL_LIGHT6:
                enableLight6=false;
                break;
            case GL_LIGHT7:
                enableLight7=false;
                break;
            case GL_TEXTURE_GEN_S:
                break;
            case GL_TEXTURE_GEN_T:
                break;
            case GL_TEXTURE_GEN_R:
                break;
            case GL_TEXTURE_GEN_Q:
                break;
            case GL_MAP1_VERTEX_3:
                break;
            case GL_MAP1_VERTEX_4:
                break;
            case GL_MAP1_COLOR_4:
                break;
            case GL_MAP1_INDEX:
                break;
            case GL_MAP1_NORMAL:
                break;
            case GL_MAP1_TEXTURE_COORD_1:
                break;
            case GL_MAP1_TEXTURE_COORD_2:
                break;
            case GL_MAP1_TEXTURE_COORD_3:
                break;
            case GL_MAP1_TEXTURE_COORD_4:
                break;
            case GL_MAP2_VERTEX_3:
                break;
            case GL_MAP2_VERTEX_4:
                break;
            case GL_MAP2_COLOR_4:
                break;
            case GL_MAP2_INDEX:
                break;
            case GL_MAP2_NORMAL:
                break;
            case GL_MAP2_TEXTURE_COORD_1:
                break;
            case GL_MAP2_TEXTURE_COORD_2:
                break;
            case GL_MAP2_TEXTURE_COORD_3:
                break;
            case GL_MAP2_TEXTURE_COORD_4:
                break;
            case GL_POINT_SMOOTH:
                break;
            case GL_LINE_SMOOTH:
                break;
            case GL_POLYGON_SMOOTH:
                break;
            case GL_SCISSOR_TEST:
                break;
            case GL_COLOR_MATERIAL:
                enableColorMaterial=false;
                break;
            case GL_NORMALIZE:
                break;
            case GL_AUTO_NORMAL:
                break;
            case GL_VERTEX_ARRAY:
                break;
            case GL_NORMAL_ARRAY:
                break;
            case GL_COLOR_ARRAY:
                break;
            case GL_INDEX_ARRAY:
                break;
            case GL_TEXTURE_COORD_ARRAY:
                break;
            case GL_EDGE_FLAG_ARRAY:
                break;
            case GL_POLYGON_OFFSET_POINT:
                break;
            case GL_POLYGON_OFFSET_LINE:
                break;
            case GL_POLYGON_OFFSET_FILL:
                break;
            default:
                glError=Glenum.GL_INVALID_ENUM;
                break;
        }
    }

    /*
    GL_POINTS
GL_LINES
GL_LINE_STRIP
GL_LINE_LOOP
GL_TRIANGLES
GL_TRIANGLE_STRIP
GL_TRIANGLE_FAN
GL_QUADS
GL_QUAD_STRIP
GL_POLYGON

http://www-evasion.imag.fr/Membres/Antoine.Bouthors/teaching/opengl/opengl3.html
    */
    public void glBegin(Glenum mode)
    {
        isBegin=true;

        //on calcul la matrice finale
        updateWorldToScreenMatrix();

        b=new blockRenderType();
        b.indexVertexStart=vertexListCurrentIndex;
        b.blockType=mode;
        b.idTexture=currentTextureId;
        b.glDepthFar=glDepthRangeFar;
        b.glDepthNear=glDepthRangeNear;
        blockRendertypeList.add(b);        
    }

    public void glEnd()
    {
        isBegin=false;
    }

    /*
    * GL_MODELVIEW
    * GL_PROJECTION
    * GL_TEXTURE
    */
    public void glMatrixMode(Glenum mode)
    {
        if (mode==Glenum.GL_MODELVIEW)
            matrixCurrentStack=matrixMODELVIEW;
        
        if (mode==Glenum.GL_PROJECTION)
            matrixCurrentStack=matrixPROJECTION;
    }

    void glPointSize( double size )
    {
        
    }
    
    public Matrix4x4 getCurrent()
    {
        return matrixCurrentStack.peek();
    }

    public void glTexCoord4d(double s, double t, double r, double q)
    {
        currentTextureCood.set(s, t, r, q);
    }

    public void glTexCoord3d(double s, double t, double r)
    {
        currentTextureCood.set(s, t, r, 1.0);
    }

    public void glTexCoord2d(double s, double t)
    {
//        double d=0xff0000;
        double d=511*65536;
        currentTextureCood.set(s * d, t * d, 0.0, 1.0);
    }
    
    public void glTexCoord1d(double s)
    {
        currentTextureCood.set(s, 0.0, 0.0, 1.0);
    }

    public void glColor3f(double red, double green, double blue)
    {
        currentColor.set(red, green, blue, 1.0);
    }

    public void glColor4f(double red, double green, double blue, double alpha)
    {
        currentColor.set(red, green, blue, alpha);
    }

    public void glNormal3d(double nx, double ny, double nz)
    {
        currentNormal.set(nx,ny,nz);
    }

    public void glVertex2d(double x, double y)
    {
        glVertex4d(x,y,0.0,1.0);
    }

    public void glVertex3d(double x, double y, double z)
    {
        glVertex4d(x,y,z,1.0);
    }

    private static Vector4f tempV4=new Vector4f(0.0,0.0,0.0,0.0);
    public void glVertex4d(double x, double y, double z, double w)
    {
        if (isBegin==false)
            return;

        //fill vertex at vertexListCurrentIndex index position in global list
        if (MAXVERTEX<vertexListCurrentIndex)
            return;

        tempV4.set(x, y, z, w);

        Vertex2 v=vertexList.get(vertexListCurrentIndex++);

//        v.posClip.set(worldToScreen.get_mul(tempV4));
        v.posClip.set_mul(tempV4, worldToScreen);

        v.colore.set(currentColor);
        v.normal.set(currentNormal);
        v.texturePos.set(currentTextureCood.m_x, 1.0 - currentTextureCood.m_y, currentTextureCood.m_z, 0.0);

        double wInv=1.0/v.posClip.m_w;
        v.posNormalizedDeviceCoordinates.m_x =v.posClip.m_x *wInv;
        v.posNormalizedDeviceCoordinates.m_y =v.posClip.m_y *wInv;
        v.posNormalizedDeviceCoordinates.m_z =v.posClip.m_z *wInv;
        v.posNormalizedDeviceCoordinates.m_w=1.0;

        b.count++;
    }

    public void glLoadMatrixd(double []m)
    {
        if (isBegin)
        return;
        
        //todo
    }
    
    public void glMultMatrixd(double []m)
    {
        if (isBegin)
            return;
    }

    public void glLoadIdentity()
    {
        if (isBegin)
        return;
        
        (matrixCurrentStack.peek()).set_identity();
    }

    public void glPushMatrix()
    {
        if (isBegin)
        return;
        
        matrixCurrentStack.push(new Matrix4x4(matrixCurrentStack.peek()));
    }

    public void glPopMatrix()
    {
        if (isBegin)
        return;
        
        matrixCurrentStack.pop();
    }
    
    public void glFrustum(double left,double right,double bottom,double top,double zNear,double zFar)
    {
        if (isBegin)
        return;
        
        Matrix4x4 n= Matrix4x4.create_frustum(left, right, bottom, top, zNear, zFar);

        //store
        Matrix4x4 m= matrixCurrentStack.pop();
        matrixCurrentStack.push(n.get_mul(m));
    }

    public void glOrtho(double left,double right,double bottom,double top,double near,double far)
    {
        if (isBegin)
        return;
        
        Matrix4x4 n= Matrix4x4.create_orthonormal(left, right, bottom, top, near, far);
        
        //store
        Matrix4x4 m= matrixCurrentStack.pop();
        matrixCurrentStack.push(n.get_mul(m));
    }

    public void gluOrtho2D(double left, double right, double bottom, double top)
    {
        if (isBegin)
            return;
        
        Matrix4x4 n= Matrix4x4.create_orthonormal_2d(left, right, bottom, top);

        //store
        Matrix4x4 m= matrixCurrentStack.pop();
        matrixCurrentStack.push(n.get_mul(m));
    }

    void glCopyTexImage2D(Glenum target, int level, Glenum internalformat,int x,int y, int width,int height, int border)
    {
        //todo
    }
    
    void glCopyTexSubImage2D(Glenum target, int	level,int xoffset,int yoffset,int x,int y,int width,int height)
    {
        //todo
    }


    /*
    m_x, m_y:
    Specify the lower left corner of the viewport rectangle, in pixels. The default is (0, 0).

    width, height:
    Specify the width and height, respectively, of the viewport. When a GL context is first attached to a window, width and height are set to the dimensions of that window.

    xw=(width/2)*xndc + (viewport_x+ (xndc/2.0))
    yw=(height/2)*yndc + (viewport_y+ (yndc/2.0))
    zw=(far-near)/2 * zndc + (far+near)/2
     */
    //http://www.songho.ca/opengl/gl_transform.html
    private double doViewPortX(double v)
    {
        double c=(v+1.0)/2*(viewPortWidth-1); // [-1..1] => [0...viewPortWidth-1]
        return c+viewPortX;
    }

    //http://www.songho.ca/opengl/gl_transform.html
    private double doViewPortY(double v)
    {
        double c=(1.0-v)/2*(viewPortHeight-1);  //[-1..1] => [0...viewPortHeight-1] -1 because must revers verticaly
        return c+viewPortY;  //-1 because must revers verticaly
    }
    
    //http://www.songho.ca/opengl/gl_transform.html
    private double doViewPortZ(double v, double _glDepthRangeFar,double _glDepthRangeNear)
    {
        double c=(v*(_glDepthRangeFar-_glDepthRangeNear)/2.0)+((_glDepthRangeFar+_glDepthRangeNear)/2);
        return c;  //-1 because must revers verticaly
    }


    public void glViewport(int x,int y,int width,int height)
    {
        if (isBegin)
        return;

        viewPortX=x;
        viewPortY=y;
        viewPortHeight=height;
        viewPortWidth=width;
    }

    public void glRotated(double angle,double x,double y,double z)
    {
        if (isBegin)
        return;

        Vector3f v=new Vector3f(x,y,z);
        v.set_normalize();
        
        Matrix4x4 r=new Matrix4x4();
        r.set_rotate(angle, v.m_x, v.m_y, v.m_z);

        Matrix4x4 m= matrixCurrentStack.pop();
        matrixCurrentStack.push(r.get_mul(m));
    }

    public void glScaled(double x, double y, double z)
    {
        if (isBegin)
        return;
        
        Matrix4x4 s=new Matrix4x4();
        s.set_scale(x, y, z);

        Matrix4x4 m= matrixCurrentStack.pop();
        matrixCurrentStack.push(s.get_mul(m));
    }

    public void glTranslated(double x,double y, double z)
    {
        if (isBegin)
        return;
        
        Matrix4x4 t=new Matrix4x4();
        t.set_translate(x, y, z);

        Matrix4x4 m= matrixCurrentStack.pop();
        matrixCurrentStack.push(t.get_mul(m));
    }

    public void glShadeModel(Glenum n)
    {
        if (isBegin)
        {
            currentError=Glenum.GL_INVALID_OPERATION;
            return;
        }
        
        switch(n)
        {
            case GL_SMOOTH:
                shadeModel=Glenum.GL_SMOOTH;
                break;
            case GL_FLAT:
                shadeModel=Glenum.GL_FLAT;
                break;
            default:
                currentError=Glenum.GL_INVALID_ENUM;
        }
    }

    public void glFlush()
    {
        if (isBegin)
        return;
        
        if (m_backBuffer==null)
            return;

        //
        //
        //

        Enumeration<blockRenderType> e = Collections.enumeration(blockRendertypeList);
        while(e.hasMoreElements())
        {
            Vertex2 v1=null;
            Vertex2 v2=null;
            Vertex2 v3=null;
            Vertex2 v4=null;
            ArrayList<Vertex2> clipList=new ArrayList<Vertex2>();

            blockRenderType b= e.nextElement();

            if (b.count==0) break; //nothing to draw

            switch(b.blockType)
            {
                case GL_POINTS:
                    for(int i=0;i<b.count;i++)
                    {
                        v1=vertexList.get(i+b.indexVertexStart);

                        v1.posNormalizedDeviceCoordinates.m_x =v1.posClip.m_x /v1.posClip.m_w;
                        v1.posNormalizedDeviceCoordinates.m_y =v1.posClip.m_y /v1.posClip.m_w;
                        v1.posNormalizedDeviceCoordinates.m_z =v1.posClip.m_z /v1.posClip.m_w;
                        v1.posNormalizedDeviceCoordinates.m_w=1.0;

                        if (v1.posNormalizedDeviceCoordinates.m_x <-1.0 || v1.posNormalizedDeviceCoordinates.m_y <-1.0 || v1.posNormalizedDeviceCoordinates.m_x >1.0 || v1.posNormalizedDeviceCoordinates.m_y >1.0) break;

                        v1.posScreen.set(
                                doViewPortX(v1.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                doViewPortY(v1.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                doViewPortZ(v1.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                0.0);

                        Dot.drawPoint(m_backBuffer, v1, color4fToInt(v1.colore));
                    }
                    break;
                case GL_LINES:
/*
                    if ((b.count&1)==1) break; //count impaire
                    for(int i=0;i<b.count;i+=2)
                    {
                        v1=vertexList.get(i+b.indexVertexStart);
                        v2=vertexList.get(i+b.indexVertexStart+1);
                        CLine.drawLine(m_backBuffer,doViewPortX(v1.posClip.m_x/v1.posClip.w),doViewPortY(v1.posClip.m_y/v1.posClip.w),doViewPortX(v2.posClip.m_x/v2.posClip.w),doViewPortY(v2.posClip.m_y/v2.posClip.w),color4fToInt(v1.colore));
                    }
*/
                    break;
                case GL_LINE_STRIP:
/*
                    v1=vertexList.get(b.indexVertexStart);
                    for(int i=0;i<b.count-1;i++)
                    {
                        v2=vertexList.get(i+b.indexVertexStart+1);
                        CLine.drawLine(m_backBuffer,doViewPortX(v1.posClip.m_x/v1.posClip.w),doViewPortY(v1.posClip.m_y/v1.posClip.w),doViewPortX(v2.posClip.m_x/v2.posClip.w),doViewPortY(v2.posClip.m_y/v2.posClip.w),color4fToInt(v1.colore));
                        v1=v2;
                    }
*/
                    break;

                case GL_LINE_LOOP: //todo: must remove edge
                {
                    //clip
                    clipList.clear();
                    for(int k=0;k<b.count;k++)
                    {
                        Vertex2 v=vertexList.get(k+b.indexVertexStart);
//                        v.isEdge=true;
                        clipList.add(v);
                    }
                    int count= Clipping.getInstance().clip(clipList);

                    if (count==0)
                            break;
                    
                    //render
                    v1= Clipping.getInstance().getClipOut().get(count-1);
                    for(int i=0;i<count;i++)
                    {
                        v2= Clipping.getInstance().getClipOut().get(i);

//                        if (v1.isEdge)
                        {
                            v1.posScreen.set(
                                    doViewPortX(v1.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                    doViewPortY(v1.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                    doViewPortZ(v1.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                    0.0);
                            v2.posScreen.set(
                                    doViewPortX(v2.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                    doViewPortY(v2.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                    doViewPortZ(v1.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                    0.0);

                            Line.drawLine(m_backBuffer, v1, v2, 0xccccccff);
                        }

                        v1=v2;
                    }
                }
                break;

                case GL_TRIANGLES: //ok
                {
                    if ((b.count%3)!=0) break; //count not multiple of 3

                    for(int i=0;i<b.count/3;i++)
                    {
                        //cull face
                        if (cullFace)
                        {
                            Vertex2 va=vertexList.get(b.indexVertexStart+(i*3)+0);
                            Vertex2 vb=vertexList.get(b.indexVertexStart+(i*3)+1);
                            Vertex2 vc=vertexList.get(b.indexVertexStart+(i*3)+2);
                            Vector3f vva=new Vector3f(va.posNormalizedDeviceCoordinates.m_x -vb.posNormalizedDeviceCoordinates.m_x,va.posNormalizedDeviceCoordinates.m_y -vb.posNormalizedDeviceCoordinates.m_y,va.posNormalizedDeviceCoordinates.m_z -vb.posNormalizedDeviceCoordinates.m_z);
                            Vector3f vvb=new Vector3f(vc.posNormalizedDeviceCoordinates.m_x -vb.posNormalizedDeviceCoordinates.m_x,vc.posNormalizedDeviceCoordinates.m_y -vb.posNormalizedDeviceCoordinates.m_y,vc.posNormalizedDeviceCoordinates.m_z -vb.posNormalizedDeviceCoordinates.m_z);
                            vva.set_normalize();
                            vvb.set_normalize();
                            switch(cullFaceType)
                            {
                                case GL_BACK:
                                    if (vvb.get_cross(vva).m_z <0)
                                    {
                                        polyBackfaceCount++;
                                        continue;
                                    }
                                    break;
                                case GL_FRONT:
                                    if (vvb.get_cross(vva).m_z >0)
                                    {
                                        polyBackfaceCount++;
                                        continue;
                                    }
                                    break;
                                case GL_FRONT_AND_BACK:
                                    return;
                            }
                        }

                        //clip
                        clipList.clear();
                        clipList.add(vertexList.get(b.indexVertexStart+(i*3)+0));
                        clipList.add(vertexList.get(b.indexVertexStart+(i*3)+1));
                        clipList.add(vertexList.get(b.indexVertexStart+(i*3)+2));
                        int count= Clipping.getInstance().clip(clipList);

                        //render
                        if (count>=3)
                        {
                            for(int j=0;j<count-2;j++)
                            {
                                v1= Clipping.getInstance().getClipOut().get(0);
                                v2= Clipping.getInstance().getClipOut().get(j+1);
                                v3= Clipping.getInstance().getClipOut().get(j+2);

                                v1.posScreen.set(
                                        doViewPortX(v1.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                        doViewPortY(v1.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                        doViewPortZ(v1.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                        0.0);
                                v2.posScreen.set(
                                        doViewPortX(v2.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                        doViewPortY(v2.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                        doViewPortZ(v2.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                        0.0);
                                v3.posScreen.set(
                                        doViewPortX(v3.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                        doViewPortY(v3.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                        doViewPortZ(v3.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                        0.0);

                                polyRenderedCount++;

                                if (depthTestEnable)
                                {
                                    if (textue2dEnable)
                                        TriangleMapping.mappingZLinear(m_backBuffer, v1, v2, v3, textureList.getTextureById(currentTextureId).getTexturePixel(0), isDepthBufferWritable);
                                    if (shadeModel==Glenum.GL_FLAT)
                                        TriangleFlat.flatFastZ(m_backBuffer, v1, v2, v3, color4fToInt(v1.colore), isDepthBufferWritable);
                                    if (shadeModel==Glenum.GL_SMOOTH)
                                        TriangleGouraud.gouraudZLinear(m_backBuffer, v1, v2, v3, isDepthBufferWritable);
                                }
                                else
                                {
                                    if (textue2dEnable)
                                        TriangleMapping.mappingLinear(m_backBuffer, v1, v2, v3, textureList.getTextureById(currentTextureId).getTexturePixel(0));
                                    if (shadeModel==Glenum.GL_FLAT)
                                        TriangleFlat.flatFast(m_backBuffer, v1, v2, v3, color4fToInt(v1.colore));
                                    if (shadeModel==Glenum.GL_SMOOTH)
                                        TriangleGouraud.gouraudLinear(m_backBuffer, v1, v2, v3, 0);
                                }
                                
                            }
                        }
                    }                
                }
                break;

                case GL_TRIANGLE_FAN:
                    //todo
                    break;

                case GL_TRIANGLE_STRIP: //ok
                {
                    if (b.count<3) break; //need 3 vertex at least

                    //cull face
                    Vertex2 va=null;
                    Vertex2 vb=null;
                    Vertex2 vc=null;
                    
                     for(int i=0;i<(b.count-2);i++) //nb triangle
                     {
                         if ((i&1)==1)
                         {
                             va=vertexList.get(b.indexVertexStart+i+0);
                             vb=vertexList.get(b.indexVertexStart+i+1);
                             vc=vertexList.get(b.indexVertexStart+i+2);
                         }
                         else
                         {
                             va=vertexList.get(b.indexVertexStart+i);
                             vb=vertexList.get(b.indexVertexStart+i+2);
                             vc=vertexList.get(b.indexVertexStart+i+1);
                         }

                         if (cullFace)
                         {
                             Vector3f vva=new Vector3f(va.posNormalizedDeviceCoordinates.m_x -vb.posNormalizedDeviceCoordinates.m_x,va.posNormalizedDeviceCoordinates.m_y -vb.posNormalizedDeviceCoordinates.m_y,va.posNormalizedDeviceCoordinates.m_z -vb.posNormalizedDeviceCoordinates.m_z);
                             Vector3f vvb=new Vector3f(vc.posNormalizedDeviceCoordinates.m_x -vb.posNormalizedDeviceCoordinates.m_x,vc.posNormalizedDeviceCoordinates.m_y -vb.posNormalizedDeviceCoordinates.m_y,vc.posNormalizedDeviceCoordinates.m_z -vb.posNormalizedDeviceCoordinates.m_z);
                             vva.set_normalize();
                             vvb.set_normalize();
                             switch(cullFaceType)
                             {
                                 case GL_BACK:
                                     if (vvb.get_cross(vva).m_z <0)
                                     {
                                         polyBackfaceCount++;
                                         continue;
                                     }
                                     break;
                                 case GL_FRONT:
                                     if (vvb.get_cross(vva).m_z >0)
                                     {
                                         polyBackfaceCount++;
                                         continue;
                                     }
                                     break;
                                 case GL_FRONT_AND_BACK:
                                     return;
                             }

                         }

                         clipList.clear();
                         clipList.add(va);
                         clipList.add(vb);
                         clipList.add(vc);
                         int count= Clipping.getInstance().clip(clipList);

                         if (count>=3) //todo: usefull ?
                         {
                             for(int j=0;j<count-2;j++)
                             {
                                 v1= Clipping.getInstance().getClipOut().get(0);
                                 v2= Clipping.getInstance().getClipOut().get(j+1);
                                 v3= Clipping.getInstance().getClipOut().get(j+2);

                                 v1.posScreen.set(
                                         doViewPortX(v1.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                         doViewPortY(v1.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                         doViewPortZ(v1.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                         0.0);
                                 v2.posScreen.set(
                                         doViewPortX(v2.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                         doViewPortY(v2.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                         doViewPortZ(v2.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                         0.0);
                                 v3.posScreen.set(
                                         doViewPortX(v3.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                         doViewPortY(v3.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                         doViewPortZ(v3.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                         0.0);

                                 polyRenderedCount++;

                                 if (depthTestEnable)
                                 {
                                     if (textue2dEnable)
                                         TriangleMapping.mappingZLinear(m_backBuffer, v1, v2, v3, textureList.getTextureById(currentTextureId).getTexturePixel(0), isDepthBufferWritable);
                                     if (shadeModel==Glenum.GL_FLAT)
                                         TriangleFlat.flatFastZ(m_backBuffer, v1, v2, v3, color4fToInt(v1.colore), isDepthBufferWritable);
                                     if (shadeModel==Glenum.GL_SMOOTH)
                                         TriangleGouraud.gouraudZLinear(m_backBuffer, v1, v2, v3, isDepthBufferWritable);
                                 }
                                 else
                                 {
                                     if (textue2dEnable)
                                         TriangleMapping.mappingLinear(m_backBuffer, v1, v2, v3, textureList.getTextureById(currentTextureId).getTexturePixel(0));
                                     if (shadeModel==Glenum.GL_FLAT)
                                         TriangleFlat.flatFast(m_backBuffer, v1, v2, v3, color4fToInt(v1.colore));
                                     if (shadeModel==Glenum.GL_SMOOTH)
                                         TriangleGouraud.gouraudLinear(m_backBuffer, v1, v2, v3, 0);
                                 }

                             }
                         }
                     }
                }
                break;

                case GL_QUADS: //ok
                {
                   if ((b.count&3)!=0) break; //count not multiple of 4

                    for(int i=0;i<b.count>>2;i++)
                    {
                        if (cullFace)
                        {                        
                            //cull face
                            Vertex2 va=vertexList.get(b.indexVertexStart+(i<<2)+0);
                            Vertex2 vb=vertexList.get(b.indexVertexStart+(i<<2)+1);
                            Vertex2 vc=vertexList.get(b.indexVertexStart+(i<<2)+2);
                            Vector3f vva=new Vector3f(va.posNormalizedDeviceCoordinates.m_x -vb.posNormalizedDeviceCoordinates.m_x,va.posNormalizedDeviceCoordinates.m_y -vb.posNormalizedDeviceCoordinates.m_y,va.posNormalizedDeviceCoordinates.m_z -vb.posNormalizedDeviceCoordinates.m_z);
                            Vector3f vvb=new Vector3f(vc.posNormalizedDeviceCoordinates.m_x -vb.posNormalizedDeviceCoordinates.m_x,vc.posNormalizedDeviceCoordinates.m_y -vb.posNormalizedDeviceCoordinates.m_y,vc.posNormalizedDeviceCoordinates.m_z -vb.posNormalizedDeviceCoordinates.m_z);
                            vva.set_normalize();
                            vvb.set_normalize();
                            switch(cullFaceType)
                            {
                                case GL_BACK:
                                    if (vvb.get_cross(vva).m_z <0)
                                    {
                                        polyBackfaceCount++;
                                        continue;
                                    }
                                    break;
                                case GL_FRONT:
                                    if (vvb.get_cross(vva).m_z >0)
                                    {
                                        polyBackfaceCount++;
                                        continue;
                                    }
                                    break;
                                case GL_FRONT_AND_BACK:
                                    return;
                            }                            
                        }

                        clipList.clear();
                        clipList.add(vertexList.get(b.indexVertexStart+(i<<2)+0));
                        clipList.add(vertexList.get(b.indexVertexStart+(i<<2)+1));
                        clipList.add(vertexList.get(b.indexVertexStart+(i<<2)+2));
                        clipList.add(vertexList.get(b.indexVertexStart+(i<<2)+3));
                        int count= Clipping.getInstance().clip(clipList);

                        if (count>=3) //todo: usefull ?
                        {
                            for(int j=0;j<count-2;j++)
                            {
                                v1= Clipping.getInstance().getClipOut().get(0);
                                v2= Clipping.getInstance().getClipOut().get(j+1);
                                v3= Clipping.getInstance().getClipOut().get(j+2);

                                v1.posScreen.set(
                                        doViewPortX(v1.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                        doViewPortY(v1.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                        doViewPortZ(v1.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                        0.0);
                                v2.posScreen.set(
                                        doViewPortX(v2.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                        doViewPortY(v2.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                        doViewPortZ(v2.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                        0.0);
                                v3.posScreen.set(
                                        doViewPortX(v3.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                        doViewPortY(v3.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                        doViewPortZ(v3.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                        0.0);

                                polyRenderedCount++;

                                if (depthTestEnable)
                                {
                                    if (textue2dEnable)
                                        TriangleMapping.mappingZLinear(m_backBuffer, v1, v2, v3, textureList.getTextureById(b.idTexture).getTexturePixel(0), isDepthBufferWritable);
                                    else if (shadeModel==Glenum.GL_FLAT)
                                        TriangleFlat.flatFastZ(m_backBuffer, v1, v2, v3, color4fToInt(v1.colore), isDepthBufferWritable);
                                    else if (shadeModel==Glenum.GL_SMOOTH)
                                        TriangleGouraud.gouraudZLinear(m_backBuffer, v1, v2, v3, isDepthBufferWritable);
                                }
                                else
                                {
                                    if (textue2dEnable)
                                        TriangleMapping.mappingLinear(m_backBuffer, v1, v2, v3, textureList.getTextureById(currentTextureId).getTexturePixel(0));
                                    else if (shadeModel==Glenum.GL_FLAT)
                                        TriangleFlat.flatFast(m_backBuffer, v1, v2, v3, color4fToInt(v1.colore));
                                    else if (shadeModel==Glenum.GL_SMOOTH)
                                        TriangleGouraud.gouraudLinear(m_backBuffer, v1, v2, v3, 0);
                                }

                            }
                        }                        
                    }
                }
                break;

                case GL_QUAD_STRIP: //ok
                {
                    if (b.count<4 &&((b.count>>1)!=0)) break; //count not multiple of 4 and pair

                     for(int i=0;i<((b.count-2)>>1);i++) //nb quad
                     {
                        if (cullFace)
                        {
                            //cull face
                            Vertex2 va=vertexList.get(b.indexVertexStart+(i*2)+0);
                            Vertex2 vb=vertexList.get(b.indexVertexStart+(i*2)+1);
                            Vertex2 vc=vertexList.get(b.indexVertexStart+(i*2)+2);

                             Vector3f vva=new Vector3f(va.posNormalizedDeviceCoordinates.m_x -vb.posNormalizedDeviceCoordinates.m_x,va.posNormalizedDeviceCoordinates.m_y -vb.posNormalizedDeviceCoordinates.m_y,va.posNormalizedDeviceCoordinates.m_z -vb.posNormalizedDeviceCoordinates.m_z);
                             Vector3f vvb=new Vector3f(vc.posNormalizedDeviceCoordinates.m_x -vb.posNormalizedDeviceCoordinates.m_x,vc.posNormalizedDeviceCoordinates.m_y -vb.posNormalizedDeviceCoordinates.m_y,vc.posNormalizedDeviceCoordinates.m_z -vb.posNormalizedDeviceCoordinates.m_z);
                             vva.set_normalize();
                             vvb.set_normalize();
                             switch(cullFaceType)
                             {
                                 case GL_BACK:
                                     if (vvb.get_cross(vva).m_z <0)
                                     {
                                         polyBackfaceCount++;
                                         continue;
                                     }
                                     break;
                                 case GL_FRONT:
                                     if (vvb.get_cross(vva).m_z >0)
                                     {
                                         polyBackfaceCount++;
                                         continue;
                                     }
                                     break;
                                 case GL_FRONT_AND_BACK:
                                     return;
                             }
                         }

                         clipList.clear();
                         clipList.add(vertexList.get(b.indexVertexStart+(i<<1)+0));
                         clipList.add(vertexList.get(b.indexVertexStart+(i<<1)+1));
                         clipList.add(vertexList.get(b.indexVertexStart+(i<<1)+3));
                         clipList.add(vertexList.get(b.indexVertexStart+(i<<1)+2));
                         int count= Clipping.getInstance().clip(clipList);

                         if (count>=3) //todo: usefull ?
                         {
                             for(int j=0;j<count-2;j++)
                             {
                                 v1= Clipping.getInstance().getClipOut().get(0);
                                 v2= Clipping.getInstance().getClipOut().get(j+1);
                                 v3= Clipping.getInstance().getClipOut().get(j+2);

                                 v1.posScreen.set(
                                         doViewPortX(v1.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                         doViewPortY(v1.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                         doViewPortZ(v1.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                         0.0);
                                 v2.posScreen.set(
                                         doViewPortX(v2.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                         doViewPortY(v2.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                         doViewPortZ(v2.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                         0.0);
                                 v3.posScreen.set(
                                         doViewPortX(v3.posNormalizedDeviceCoordinates.m_x) * 65536.0,
                                         doViewPortY(v3.posNormalizedDeviceCoordinates.m_y) * 65536.0,
                                         doViewPortZ(v3.posNormalizedDeviceCoordinates.m_z, b.glDepthFar, b.glDepthNear) * 65536.0,
                                         0.0);

                                 polyRenderedCount++;

                                 if (depthTestEnable)
                                 {
                                     if (textue2dEnable)
                                         TriangleMapping.mappingZLinear(m_backBuffer, v1, v2, v3, textureList.getTextureById(currentTextureId).getTexturePixel(0), isDepthBufferWritable);
                                     if (shadeModel==Glenum.GL_FLAT)
                                         TriangleFlat.flatFastZ(m_backBuffer, v1, v2, v3, color4fToInt(v1.colore), isDepthBufferWritable);
                                     if (shadeModel==Glenum.GL_SMOOTH)
                                         TriangleGouraud.gouraudZLinear(m_backBuffer, v1, v2, v3, isDepthBufferWritable);
                                 }
                                 else
                                 {
                                     if (textue2dEnable)
                                         TriangleMapping.mappingLinear(m_backBuffer, v1, v2, v3, textureList.getTextureById(currentTextureId).getTexturePixel(0));
                                     if (shadeModel==Glenum.GL_FLAT)
                                         TriangleFlat.flatFast(m_backBuffer, v1, v2, v3, color4fToInt(v1.colore));
                                     if (shadeModel==Glenum.GL_SMOOTH)
                                         TriangleGouraud.gouraudLinear(m_backBuffer, v1, v2, v3, 0);
                                 }

                             }
                         }
                     }
                 }
                 break;

                case GL_POLYGON:
                    //todo:render each
                    break;
            }
        }
        
        vertexListCurrentIndex=0;
        blockRendertypeList.clear();
    }

    public void glFinish()
    {       
        //same as glFlush
        glFlush();
    }

    public void gluLookAt(double eyex, double eyey, double eyez, double centerx, double centery, double centerz, double upx, double upy, double upz)
    {
        if (isBegin)
        return;
        
        glMatrixMode(Glenum.GL_MODELVIEW);
        glLoadIdentity();
        
        //build view matrix
        Matrix4x4 n= Matrix4x4.create_look_at(eyex, eyey, eyez, centerx, centery, centerz, upx, upy, upz);

        //store it
        Matrix4x4 m= matrixCurrentStack.pop();
        matrixCurrentStack.push(n.get_mul(m));
    }

    public void gluPerspective(double fovy, double aspect, double zNear, double zFar)
    {
        if (isBegin)
        return;
        
        glMatrixMode(Glenum.GL_PROJECTION);
        glLoadIdentity();
/*
        double ymax = zNear * Math.tan(fovy *Matrix4x4.TO_RAD);
        double ymin = -ymax;
        double xmin = ymin * aspect;
        double xmax = ymax * aspect;
        glFrustum(xmin, xmax, ymin, ymax, zNear, zFar);*/

        Matrix4x4 n= Matrix4x4.create_perspective(fovy, aspect, zNear, zFar);

        Matrix4x4 m= matrixCurrentStack.pop();
        matrixCurrentStack.push(n.get_mul(m));
        
    }
/*
GL commands
glAlphaFunc                   glAccum
glBitmap                      glBegin
glCallList                    glBlendFunc
glClear                       glCallLists
glClearColor                  glClearAccum
glClearIndex                  glClearDepth
glClipPlane                   glClearStencil
glColorMask                   glColor
glCopyPixels                  glColorMaterial
glDeleteLists                 glCullFace
glDepthMask                   glDepthFunc
glDisable                     glDepthRange
glDrawPixels                  glDrawBuffer
glEnable                      glEdgeFlag
glEndList                     glEnd
glEvalMesh                    glEvalCoord
glFeedbackBuffer              glEvalPoint
glFlush                       glFinish
glFrontFace                   glFog
glGenLists                    *glFrustum
glGetClipPlane                glGet
glGetLight                    glGetError
glGetMaterial                 glGetMap
glGetPolygonStipple           glGetPixelMap
glGetTexEnv                   glGetString
glGetTexImage                 glGetTexGen
glGetTexParameter             glGetTexLevelParameter
glIndex                       glHint
glInitNames                   glIndexMask
glIsList                      glIsEnabled
glLightModel                  glLight
glLineWidth                   glLineStipple
glLoadIdentity                glListBase
glLoadName                    glLoadMatrix
glMap1                        glLogicOp
glMapGrid                     glMap2
glMatrixMode                  glMaterial
glNewList                     glMultMatrix
*glOrtho                       glNormal
glPixelMap                    glPassThrough
glPixelTransfer               glPixelStore
glPointSize                   glPixelZoom
glPolygonStipple              glPolygonMode
*glPopMatrix                   glPopAttrib
glPushAttrib                  glPopName
glPushName                    *glPushMatrix
glReadBuffer                  glRasterPos
glRect                        glReadPixels
glRotate                      glRenderMode
glScissor                     glScale
glShadeModel                  glSelectBuffer
glStencilMask                 glStencilFunc
glTexCoord                    glStencilOp
glTexGen                      glTexEnv
glTexImage2D                  glTexImage1D
glTranslate                   glTexParameter
*glViewport                    glVertex


GLU commands
gluBeginPolygon               gluBeginCurve
gluBeginTrim                  gluBeginSurface
gluBuild2DMipmaps             gluBuild1DMipmaps
gluDeleteNurbsRenderer        gluCylinder
gluDeleteTess                 gluDeleteQuadric
gluEndCurve                   gluDisk
gluEndSurface                 gluEndPolygon
gluErrorString                gluEndTrim
gluLoadSamplingMatrices       gluGetNurbsProperty
gluNewNurbsRenderer           gluLookAt
gluNewTess                    gluNewQuadric
gluNurbsCallback              gluNextContour
gluNurbsProperty              gluNurbsCurve
gluOrtho2D                    gluNurbsSurface
gluPerspective                gluPartialDisk
gluProject                    gluPickMatrix
gluQuadricCallback            gluPwlCurve
gluQuadricNormals             gluQuadricDrawStyle
gluQuadricTexture             gluQuadricOrientation
gluSphere                     gluScaleImage
gluTessVertex                 gluTessCallback
gluUnProject

GLX commands
glXCopyContext                glXChooseVisual
glXCreateGLXPixmap            glXCreateContext
glXDestroyGLXPixmap           glXDestroyContext
glXGetCurrentContext          glXGetConfig
glXIntro                      glXGetCurrentDrawable
glXMakeCurrent                glXIsDirect
glXQueryVersion               glXQueryExtension
glXUseXFont                   glXSwapBuffers
glXWaitX                      glXWaitGL   

*/
}
