package fuzzGl.geometry;

import math.Vector3f;
import math.Vector4f;

public class Vertex2
{
    public Vector4f posClip =new Vector4f();
    public Vector4f posNormalizedDeviceCoordinates =new Vector4f();
    public Vector4f posScreen =new Vector4f();
    public Vector4f texturePos=new Vector4f();
    public Vector4f colore=new Vector4f();
    public Vector3f normal=new Vector3f();

    public void set(Vertex2 v)
    {
        posClip.set(v.posClip);
        posNormalizedDeviceCoordinates.set(v.posNormalizedDeviceCoordinates);
        posScreen.set(v.posScreen);
        colore.set(v.colore);
        texturePos.set(v.texturePos);
    }

    public void setLerp(Vertex2 a, Vertex2 b, double c)
    {
        posClip.set_lerp(a.posClip, b.posClip, c);
//        posNormalizedDeviceCoordinates.set_lerp(a.posNormalizedDeviceCoordinates,b.posNormalizedDeviceCoordinates,c);
        posNormalizedDeviceCoordinates.set(posClip.m_x / posClip.m_w, posClip.m_y / posClip.m_w, posClip.m_z / posClip.m_w,1.0);
//        posScreen.set_lerp(a.posScreen,b.posScreen,c);
        colore.set_lerp(a.colore, b.colore, c);
        texturePos.set_lerp(a.texturePos, b.texturePos, c);
    }
}


