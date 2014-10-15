package fuzzGl;

import fuzzGl.geometry.Vertex2;
import math.Vector4f;

import java.util.ArrayList;

public class Clipping
{
    private static ArrayList<Vertex2> vOutClipLeft=new ArrayList<Vertex2>();
    private static ArrayList<Vertex2> vOutClipRight=new ArrayList<Vertex2>();
    private static ArrayList<Vertex2> vOutClipTop=new ArrayList<Vertex2>();
    private static ArrayList<Vertex2> vOutClipBottom=new ArrayList<Vertex2>();
    private static ArrayList<Vertex2> vOutClipZNear=new ArrayList<Vertex2>();
    private static ArrayList<Vertex2> vOutClipZfar=new ArrayList<Vertex2>();

    private static Clipping instance=null;

    public ArrayList<Vertex2> getClipOut()
    {
        return vOutClipZfar;
    }

    public static Clipping getInstance()
    {
        if (instance==null)
            instance=new Clipping();
        return instance;
    }

    public Clipping()
    {
        for(int i=0;i<12;i++)
        {
            vOutClipLeft.add(new Vertex2());
            vOutClipRight.add(new Vertex2());
            vOutClipTop.add(new Vertex2());
            vOutClipBottom.add(new Vertex2());
            vOutClipZNear.add(new Vertex2());
            vOutClipZfar.add(new Vertex2());
        }
    }

    //
    //clip triangle (v1 v2 v3) by the camera frustum in homogenous camera space (-w<..<w).
    //return number of vertex of the new poly created by the triangle/frustum clipping.
    //return 0 if triange if full outside the frustum.
    //

    public int clip(ArrayList<Vertex2> vIn)
    {
        int nbOut=0;
        nbOut = clippAxis(vIn, vOutClipLeft, vIn.size(), 0);
        nbOut = clippAxis(vOutClipLeft, vOutClipRight,nbOut, 1);
        nbOut = clippAxis(vOutClipRight, vOutClipBottom,nbOut, 2);
        nbOut = clippAxis(vOutClipBottom, vOutClipTop,nbOut, 3);
        nbOut = clippAxis(vOutClipTop, vOutClipZNear,nbOut, 4);
        nbOut = clippAxis(vOutClipZNear, vOutClipZfar,nbOut, 5);
        return nbOut;
    }

    //
    //
    //clip inV ( vertex array) with axis in homogeneous space
    //result in outV
    //return nb of vertex in clipped array
    private int clippAxis(ArrayList<Vertex2> vIn, ArrayList<Vertex2> vOut, int size, int axis)
    {
        int nbOut = 0;        //nb of vertex in clipped array
        double f=0;
        boolean outA=false;
        boolean outB=false;
        Vector4f vA=null;
        Vector4f vB=null;
        Vertex2 vxA=null;
        Vertex2 vxB=null;

        for (int i = 0; i < size; i++)
        {
            vxA=vIn.get(i);
            vxB=vIn.get((i + 1) %  size);

            vA = vxA.posClip;    //get last point
            vB = vxB.posClip;

            switch (axis)
            {
                case 0:
                    outA = vA.m_x < -vA.m_w;
                    outB = vB.m_x < -vB.m_w;
                    break;    //LEFT
                case 1:
                    outA = vA.m_x > vA.m_w;
                    outB = vB.m_x > vB.m_w;
                    break;        //RIGHT
                case 2:
                    outA = vA.m_y < -vA.m_w;
                    outB = vB.m_y < -vB.m_w;
                    break;    //BOTTOM
                case 3:
                    outA = vA.m_y > vA.m_w;
                    outB = vB.m_y > vB.m_w;
                    break;        //TOP
                case 4:
                    outA = vA.m_z < -vA.m_w;
                    outB = vB.m_z < -vB.m_w;
                    break;    //NEAR
                case 5:
                    outA = vA.m_z > vA.m_w;
                    outB = vB.m_z > vB.m_w;
                    break;        //FAR
            }

            if (outA != outB)        //need to clip
            {
                switch (axis)
                {
                    case 0:
                        f = (-vA.m_w - vA.m_x) / (vB.m_x - vA.m_x + vB.m_w - vA.m_w);
                        break;    //LEFT
                    case 1:
                        f = (vA.m_w - vA.m_x) / (vB.m_x - vA.m_x - vB.m_w + vA.m_w);
                        break;    //RIGHT
                    case 2:
                        f = (-vA.m_w - vA.m_y) / (vB.m_y - vA.m_y + vB.m_w - vA.m_w);
                        break;    //BOTTOM
                    case 3:
                        f = (vA.m_w - vA.m_y) / (vB.m_y - vA.m_y - vB.m_w + vA.m_w);
                        break;    //TOP
                    case 4:
                        f = (-vA.m_w - vA.m_z) / (vB.m_z - vA.m_z + vB.m_w - vA.m_w);
                        break;    //NEAR
                    case 5:
                        f = (vA.m_w - vA.m_z) / (vB.m_z - vA.m_z - vB.m_w + vA.m_w);
                        break;    //FAR
                }

                //LINEAR interpol               
                vOut.get(nbOut).setLerp(vxA, vxB, f);//build a new vertex by interpolating 2 other
                nbOut++;
            }

            if ((outA && !outB) || (!outA && !outB))
            {
                vOut.get(nbOut).set(vxB);
                nbOut++;
            }
        }
        return nbOut;
    }
}