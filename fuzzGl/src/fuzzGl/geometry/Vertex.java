package fuzzGl.geometry;

import math.Vector3f;
import math.Vector4f;

public class Vertex
{
    private Vector3f vPos0=new Vector3f();
    private Vector3f vPos1=new Vector3f();   //objet de base
    private Vector4f vPos2=new Vector4f();  //transforme local + clipping in homogeneous space
    private Vector3f vPos3=new Vector3f();  //to screen ( /W )

    private Vector3f vN=new Vector3f();   //normal
    private Vector3f vN2=new Vector3f();   //normal toCameraSpace�
    private Vector3f vM=new Vector3f();   //mapping coordinate [0..1] not scalled
    private Vector3f vM2=new Vector3f();   //mapping coordinate scalled with texture size [0..texture width/height]

    public Vertex()
    {
    }

    public Vertex set(Vertex v)
    {
        vPos0.set(v.getvPos0());
        vPos1.set(v.getvPos1());
        vPos2.set(v.getvPos2());
        vPos3.set(v.getvPos3());
        vM.set(v.getvM());
        vN.set(v.getvN());
        vN2.set(v.getvN2());
        return this;
    }

    public Vertex lerp(Vertex a, Vertex b, double f)
    {
        vPos0.set_lerp(a.getvPos0(), b.getvPos0(), f);
        vPos1.set_lerp(a.getvPos1(), b.getvPos1(), f);
        vPos2.set_lerp(a.getvPos2(), b.getvPos2(), f);
        vPos3.set_lerp(a.getvPos3(), b.getvPos3(), f);
        vM.set_lerp(a.getvM(), b.getvM(), f);
        vN.set_lerp(a.getvN(), b.getvN(), f);
        vN2.set_lerp(a.getvN2(), b.getvN2(), f);
        return this;
    }

    public Vector3f getvPos0()
    {
        return vPos0;
    }

    public void setvPos0(Vector3f _vPos0)
    {
        vPos0.set(_vPos0);
    }

    public Vector3f getvPos1()
    {
        return vPos1;
    }

    public void setvPos1(Vector3f _vPos1)
    {
        vPos1.set(_vPos1);
    }

    public Vector4f getvPos2()
    {
        return vPos2;
    }

    public void setvPos2(Vector4f _vPos2)
    {
        vPos2.set(_vPos2);
    }

    public Vector3f getvPos3()
    {
        return vPos3;
    }

    public void setvPos3(Vector3f _vPos3)
    {
        vPos3.set(_vPos3);
    }

    public Vector3f getvN()
    {
        return vN;
    }

    public void setvN(Vector3f _vN)
    {
        vN.set(_vN);
    }

    public Vector3f getvN2()
    {
        return vN2;
    }

    public void setvN2(Vector3f _vN2)
    {
        vN2.set(_vN2);
    }

    public Vector3f getvM()
    {
        return vM;
    }

    public void setvM(Vector3f _vM)
    {
        vM.set(_vM);
    }

    public Vector3f getvM2()
    {
        return vM2;
    }

    public void setvM2(Vector3f _vM2)
    {
        vM2.set(_vM2);
    }

}