package fuzzGl.rasterizer;

import fuzzGl.BackBuffer;
import fuzzGl.texture.GlTexture;
import fuzzGl.geometry.Vertex2;
import math.Vector2i;

public class TriangleMapping
{
    private static double temp_f;
    public final static int FRACBITS = 16;

    //triangle interpolation
    private static int start;

    private static int right_x;
    private static int left_x;

    private static int temp_x;
    private static int y;

    private static Vector2i m_SCREEN_left=new Vector2i(0,0);
    private static Vector2i m_SCREEN_right=new Vector2i(0,0);

    private static Vector2i m_SCREEN_DY_left=new Vector2i(0,0);
    private static Vector2i m_SCREEN_DY_right=new Vector2i(0,0);

    //zbuffer
    private static double left_z;
    private static double right_z;
    private static double const_z;
    private static double z_left;
    private static double zs;
    private static double z;

    //color
    private static double left_color_r;
    private static double left_color_g;
    private static double left_color_b;
    private static double right_color_r;
    private static double right_color_g;
    private static double right_color_b;
    private static double const_color_r;
    private static double const_color_g;
    private static double const_color_b;
    private static double r_color_left;
    private static double g_color_left;
    private static double b_color_left;
    private static double rs;
    private static double gs;
    private static double bs;
    private static double color_r_scanlineInterpolate;
    private static double color_g_scanlineInterpolate;
    private static double color_b_scanlineInterpolate;

    //texture
    private static int right_tx2;
    private static int right_ty2;

    private static Vector2i m_UV_DX=new Vector2i(0,0);

    private static int dx_left2;
    private static int dy_left2;
    private static int dx_right2;
    private static int dy_right2;
    private static int left_tx2;
    private static int left_ty2;
    private static int tx_left2;
    private static int ty_left2;
    private static int txs2;
    private static int tys2;
    private static int tx2;
    private static int ty2;

    //temp variable for rendering
    private static int scanLengt;
    private static int offsetStart;
    private static int scanStart;
    private static int scanEnd;
    private static  Vertex2 v4=null;

    private static ScanLine m_scanLine=new ScanLine();

    public static void swap(Object a, Object b)
    {
        Object tmp=a;
        a=b;
        b=tmp;
    }

    public static void mappingLinear(BackBuffer bb, Vertex2 v1,Vertex2 v2,Vertex2 v3, GlTexture texture)
    {
        //
        //Y	sort with bubble sort
        //
        if (v1.posScreen.m_y >v2.posScreen.m_y) { Vertex2 v4 = v1;v1 = v2;v2 = v4;}
        if (v2.posScreen.m_y >v3.posScreen.m_y) { Vertex2 v4 = v2;v2 = v3;v3 = v4;}
        if (v1.posScreen.m_y >v2.posScreen.m_y) { Vertex2 v4 = v1;v1 = v2;v2 = v4;}

        double coef;
        int height;

        left_x = (int) (v2.posScreen.m_x);
        long h = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS);
        if (h == 0) return;

        //magic	coef
        coef = ((double) (((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS))) / (double) h;

        //poly incrment
        double tt = (double) ((int) v3.posScreen.m_x) - ((int) v1.posScreen.m_x);
        right_x = (int) v1.posScreen.m_x + (int) (coef * tt);

        //texture2 coord au point 4 & 2
        left_tx2 = (int) v2.texturePos.m_x;
        tt =  ((int) v3.texturePos.m_x) - ((int) v1.texturePos.m_x);
        right_tx2 = (int) v1.texturePos.m_x + (int) (coef * tt);

        left_ty2 = (int) v2.texturePos.m_y;
        tt =  ((int) v3.texturePos.m_y) - ((int) v1.texturePos.m_y);
        right_ty2 = (int) v1.texturePos.m_y + (int) (coef * tt);

        if (right_x < left_x)
        {
            //for	scanline
            swap(right_x,left_x);

            //for	Texture	mappingFast
            swap(right_tx2,left_tx2);
            swap(right_ty2,left_ty2);
        }

        //
        //les increment constant pour tout le triangle sur les X
        //

        //mappingFast
        m_UV_DX.m_x = (int) (65536.0f * (float) (right_tx2 - left_tx2) / (float) ((right_x - left_x)));
        m_UV_DX.m_y = (int) (65536.0f * (float) (right_ty2 - left_ty2) / (float) ((right_x - left_x)));

        start = bb.get_width() * ((int) (v1.posScreen.m_y) >> FRACBITS);

        height = ((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS);
        if (height > 0)
        {
            //start m_x for scanline
            m_SCREEN_left.set((int) v1.posScreen.m_x, 0);
            m_SCREEN_right.set((int) v1.posScreen.m_x, 0);

            //
            //les increments sur les Y pour la 1ere partie du triangle
            //
            m_SCREEN_DY_left.set((left_x - (int) v1.posScreen.m_x) / height, 1);
            m_SCREEN_DY_right.set((right_x - (int) v1.posScreen.m_x) / height, 1);

            //Texture mappingFast
            tx_left2 = (left_tx2 - (int) v1.texturePos.m_x) / height;
            ty_left2 = (left_ty2 - (int) v1.texturePos.m_y) / height;
            txs2 = (int) v1.texturePos.m_x;
            tys2 = (int) v1.texturePos.m_y;

            doitLinear(bb,texture,(int) v1.posScreen.m_y >> FRACBITS,(int) v2.posScreen.m_y >> FRACBITS);
        }

        height = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v2.posScreen.m_y >> FRACBITS);
        if (height > 0)
        {
            //start for m_x scanline
            m_SCREEN_left.set(left_x, 0);
            m_SCREEN_right.set(right_x, 0);

            m_SCREEN_DY_left.set(((int) v3.posScreen.m_x - right_x) / height, 1);
            m_SCREEN_DY_right.set(((int) v3.posScreen.m_x - left_x) / height, 1);

            //
            //les increments sur les Y
            //

            //mappingFast
            tx_left2 = ((int) v3.texturePos.m_x - left_tx2) / height;
            ty_left2 = ((int) v3.texturePos.m_y - left_ty2) / height;
            txs2 = left_tx2;
            tys2 = left_ty2;

            doitLinear(bb,texture,(int) v2.posScreen.m_y >> FRACBITS,(int) v3.posScreen.m_y >> FRACBITS);
        }
    }

    private static void doitLinear(BackBuffer bb, GlTexture texture, int y1,int y2)
    {
        for (int y=y1; y < y2; y++)
        {
            scanStart = m_SCREEN_left.m_x >> FRACBITS;
            scanEnd = m_SCREEN_right.m_x >> FRACBITS;

            if (y >= bb.get_height())
                break;

            m_scanLine.m_UV.m_x =txs2;
            m_scanLine.m_UV.m_y=tys2;

            m_scanLine.m_UVDX.set(m_UV_DX);

            m_scanLine.m_scanStart=start + scanStart;
            m_scanLine.m_scanLenght=scanEnd - scanStart;
            m_scanLine.m_colorBuffer=bb.get_raw_color_buffer();
            m_scanLine.m_texture1= texture;
            m_scanLine.drawMapping();

            //
            //update Y index
            //

            //scanline increment
            m_SCREEN_left.set_add(m_SCREEN_DY_left);
            m_SCREEN_right.set_add(m_SCREEN_DY_right);

            //mappingFast
            txs2 += tx_left2;
            tys2 += ty_left2;

            start += bb.get_width();
        }

    }


    public static void mappingZLinear(BackBuffer bb, Vertex2 v1,Vertex2 v2,Vertex2 v3, GlTexture texture,boolean isDepthBufferWritable)
    {
        //
        //Y	sort with bubble sort
        //
        if (v1.posScreen.m_y >v2.posScreen.m_y) { Vertex2 v4 = v1;v1 = v2;v2 = v4;}
        if (v2.posScreen.m_y >v3.posScreen.m_y) { Vertex2 v4 = v2;v2 = v3;v3 = v4;}
        if (v1.posScreen.m_y >v2.posScreen.m_y) { Vertex2 v4 = v1;v1 = v2;v2 = v4;}


        double coef;
        int height;

        left_x = (int) (v2.posScreen.m_x);
        long h = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS);
        if (h == 0) return;

        //magic	coef
        coef = ((double) (((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS))) / (double) h;

        //poly incrment
        double tt = (double) ((int) v3.posScreen.m_x) - ((int) v1.posScreen.m_x);
        right_x = (int) v1.posScreen.m_x + (int) (coef * tt);

        //zbuffer	au point 4 & 2
        left_z = v2.posScreen.m_z;
        tt = v3.posScreen.m_z - v1.posScreen.m_z;
        right_z = v1.posScreen.m_z + (coef * tt);

        //texture2 coord au point 4 & 2
        left_tx2 = (int) v2.texturePos.m_x;
        tt =  ((int) v3.texturePos.m_x) - ((int) v1.texturePos.m_x);
        right_tx2 = (int) v1.texturePos.m_x + (int) (coef * tt);
        left_ty2 = (int) v2.texturePos.m_y;
        tt =  ((int) v3.texturePos.m_y) - ((int) v1.texturePos.m_y);
        right_ty2 = (int) v1.texturePos.m_y + (int) (coef * tt);


        if (right_x < left_x)
        {
            //for	scanline
            temp_x = right_x;            right_x = left_x;            left_x = temp_x;

            //for	zbuffer
            temp_f = right_z;            right_z = left_z;            left_z = temp_f;

            //for	Texture	mappingFast
            temp_x = right_tx2;
            right_tx2 = left_tx2;
            left_tx2 = temp_x;
            temp_x = right_ty2;
            right_ty2 = left_ty2;
            left_ty2 = temp_x;
        }

        //
        //les increment constant pour tout le triangle sur les X
        //

        //zbuffer
        const_z = ((right_z - left_z) / (double) (right_x - left_x)) * 65536.0;

        //mappingFast
        m_UV_DX.m_x = (int) (65536.0f * (float) (right_tx2 - left_tx2) / (float) ((right_x - left_x)));
        m_UV_DX.m_y = (int) (65536.0f * (float) (right_ty2 - left_ty2) / (float) ((right_x - left_x)));

        start = bb.get_width() * ((int) (v1.posScreen.m_y + 0.5) >> FRACBITS);

        height = ((int) (v2.posScreen.m_y) >> FRACBITS) - ((int) (v1.posScreen.m_y) >> FRACBITS);
        if (height > 0)
        {
            //start m_x for scanline
            m_SCREEN_left.set((int) v1.posScreen.m_x, 0);
            m_SCREEN_right.set((int) v1.posScreen.m_x, 0);

            //
            //les increments sur les Y pour la 1ere partie du triangle
            //
            m_SCREEN_DY_left.set((left_x - (int) v1.posScreen.m_x) / height, 1);
            m_SCREEN_DY_right.set((right_x - (int) v1.posScreen.m_x) / height, 1);

            //zbuffer
            z_left = (left_z - v1.posScreen.m_z) / (double)height;
            zs = v1.posScreen.m_z;

            //Texture mappingFast
            tx_left2 = (left_tx2 - (int) v1.texturePos.m_x) / height;
            ty_left2 = (left_ty2 - (int) v1.texturePos.m_y) / height;
            txs2 = (int) v1.texturePos.m_x;
            tys2 = (int) v1.texturePos.m_y;

            doitZLinear(bb,isDepthBufferWritable,texture,(int) v1.posScreen.m_y >> FRACBITS,(int) v2.posScreen.m_y >> FRACBITS);
        }

        height = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v2.posScreen.m_y >> FRACBITS);
        if (height > 0)
        {
            //start for m_x scanline
            m_SCREEN_left.set(left_x, 0);
            m_SCREEN_right.set(right_x, 0);

            m_SCREEN_DY_left.set(((int) v3.posScreen.m_x - left_x) / height, 1);
            m_SCREEN_DY_right.set(((int) v3.posScreen.m_x - right_x) / height, 1);

            //zbuffer
            zs = left_z;
            z_left = (v3.posScreen.m_z - left_z) / (double)height;

            //mappingFast
            tx_left2 = ((int) v3.texturePos.m_x - left_tx2) / height;
            ty_left2 = ((int) v3.texturePos.m_y - left_ty2) / height;
            txs2 = left_tx2;
            tys2 = left_ty2;

            doitZLinear(bb,isDepthBufferWritable,texture,(int) v2.posScreen.m_y >> FRACBITS,(int) v3.posScreen.m_y >> FRACBITS);
        }
    }

    private static void doitZLinear(BackBuffer bb,boolean isDepthBufferWritable, GlTexture texture, int y1,int y2)
    {
        for (int y =y1; y < y2; y++)
        {
            scanStart = m_SCREEN_left.m_x >> FRACBITS;
            scanEnd = m_SCREEN_right.m_x >> FRACBITS;

            if (y >= bb.get_height())
                break;

            m_scanLine.m_UV.m_x =txs2;
            m_scanLine.m_UV.m_y=tys2;
            m_scanLine.m_zStart=zs;

            m_scanLine.m_UVDX.set(m_UV_DX);
            m_scanLine.m_zIncr=const_z;

            m_scanLine.m_scanStart=start + scanStart;
            m_scanLine.m_scanLenght=scanEnd - scanStart;
            m_scanLine.m_colorBuffer=bb.get_raw_color_buffer();
            m_scanLine.m_zBuffer=bb.get_raw_depth_buffer();
            m_scanLine.m_texture1= texture;
             if (isDepthBufferWritable)
                 m_scanLine.drawMappingZUpdate();
            else
                m_scanLine.drawMappingZNoUpdate();

            //
            //update Y index
            //

            //scanline increment
            m_SCREEN_left.set_add(m_SCREEN_DY_left);
            m_SCREEN_right.set_add(m_SCREEN_DY_right);

            //zbuffer
            zs += z_left;

            //mappingFast
            txs2 += tx_left2;
            tys2 += ty_left2;

            start += bb.get_width();
        }
        
    }


    public static void mappingPerspectiveCorrected(BackBuffer bb, Vertex2 v1,Vertex2 v2,Vertex2 v3, GlTexture texture)
    {
/*
        Vertex2 v4=null;

        //
        //Y	sort with bubble sort
        //
        if (v1.posScreen.m_y > v2.posScreen.m_y) //swap 0 & 1
        {
            v4 = v1;            v1 = v2;            v2 = v4;
        }
        if ( v2.posScreen.m_y  >  v3.posScreen.m_y) //swap 1 & 2
        {
            v4 = v2;            v2 = v3;            v3 = v4;
        }
        if ( v1.posScreen.m_y >  v2.posScreen.m_y) //swap 0 & 1
        {
            v4 = v1;            v1 = v2;            v2 = v4;
        }

        double coef;
        int height;

        left_x = (int) (v2.posScreen.m_x);
        long h = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS);
        if (h == 0) return;

        //magic	coef
        coef = ((double) (((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS))) / (double) h;

        //poly incrment
        double tt = (double) ((int) v3.posScreen.m_x) - ((int) v1.posScreen.m_x);
        right_x = (int) v1.posScreen.m_x + (int) (coef * tt);

        //texture2 coord au point 4 & 2
        left_tx2 = (int) v2.texturePos.m_x;
        tt =  ((int) v3.texturePos.m_x) - ((int) v1.texturePos.m_x);
        right_tx2 = (int) v1.texturePos.m_x + (int) (coef * tt);
        left_ty2 = (int) v2.texturePos.m_y;
        tt =  ((int) v3.texturePos.m_y) - ((int) v1.texturePos.m_y);
        right_ty2 = (int) v1.texturePos.m_y + (int) (coef * tt);


        if (right_x < left_x)
        {
            //for	scanline
            temp_x = right_x;            right_x = left_x;            left_x = temp_x;

            //for	Texture	mappingFast
            temp_x = right_tx2;
            right_tx2 = left_tx2;
            left_tx2 = temp_x;
            temp_x = right_ty2;
            right_ty2 = left_ty2;
            left_ty2 = temp_x;
        }

        //
        //les increment constant pour tout le triangle sur les X
        //

        //mappingFast
        m_UV_DX.m_x = (int) (65536.0f * (float) (right_tx2 - left_tx2) / (float) ((right_x - left_x)));
        m_UV_DX.m_y = (int) (65536.0f * (float) (right_ty2 - left_ty2) / (float) ((right_x - left_x)));


        start = bb.getWidth() * ((int) (v1.posScreen.m_y + 0.5) >> FRACBITS);

        height = ((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS);
        if (height > 0)
        {
            //start m_x for scanline
            xs = (int) v1.posScreen.m_x;
            xe = (int) v1.posScreen.m_x;

            //
            //les increments sur les Y pour la 1ere partie du triangle
            //

            m_X_DY_left = (left_x - (int) v1.posScreen.m_x) / height;
            m_X_DY_right = (right_x - (int) v1.posScreen.m_x) / height;

            //Texture mappingFast
            tx_left2 = (left_tx2 - (int) v1.texturePos.m_x) / height;
            ty_left2 = (left_ty2 - (int) v1.texturePos.m_y) / height;
            txs2 = (int) v1.texturePos.m_x;
            tys2 = (int) v1.texturePos.m_y;

            for (m_y = (int) v1.posScreen.m_y >> FRACBITS; m_y < (int) v2.posScreen.m_y >> FRACBITS; m_y++)
            {
                scanStart = xs >> FRACBITS;
                scanEnd = xe >> FRACBITS;

                if (m_y >= bb.getHeight())
                    break;

                //texture2 start for scanline
                tx2 = txs2;
                ty2 = tys2;

                scanLengt = scanEnd - scanStart;
                offsetStart = start + scanStart;

                while ((scanLengt--) > 0)
                {
                    int mappingColor = texture.getData()[(((tx2 >> 16)) & (texture.getWidth()-1)) + ((((ty2 >> 16)) & (texture.getHeight()-1)) << texture.getWidthLog2())];

                    bb.get_raw_color_buffer()[offsetStart] = mappingColor;

                    //scanline increment
                    offsetStart++;

                    //mappingFast
//                    tx2 += const_tx2;
//                    ty2 += const_ty2;
                }

                //
                //update Y index
                //

                //scanline increment
                xs += m_X_DY_left;
                xe += m_X_DY_right;

                //mappingFast
                txs2 += tx_left2;
                tys2 += ty_left2;

                //new m_y screen start
                start += bb.getWidth();
            }
        }

        height = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v2.posScreen.m_y >> FRACBITS);
        if (height > 0)
        {
            m_X_DY_right = ((int) v3.posScreen.m_x - right_x) / height;
            m_X_DY_left = ((int) v3.posScreen.m_x - left_x) / height;

            //start for m_x scanline
            xs = left_x;
            xe = right_x;

            //
            //les increments sur les Y
            //

            //mappingFast
            tx_left2 = ((int) v3.texturePos.m_x - left_tx2) / height;
            ty_left2 = ((int) v3.texturePos.m_y - left_ty2) / height;
            txs2 = left_tx2;
            tys2 = left_ty2;

            for (m_y = (int) v2.posScreen.m_y >> FRACBITS; m_y < ((int) v3.posScreen.m_y >> FRACBITS); m_y++)
            {
                scanStart = xs >> FRACBITS;
                scanEnd = xe >> FRACBITS;

                if (m_y >= bb.getHeight())
                    break;

                //mappingFast
                tx2 = txs2;
                ty2 = tys2;

                scanLengt = scanEnd - scanStart;
                offsetStart = start + scanStart;

                while ((scanLengt--) > 0)
                {
                    int mappingColor = texture.getData()[(((tx2 >> 16)) & (texture.getWidth()-1)) + ((((ty2 >> 16)) & (texture.getHeight()-1)) << texture.getWidthLog2())];

                    bb.get_raw_color_buffer()[offsetStart] = mappingColor;

                    //scanline increment
                    offsetStart++;

                    //mappingFast
//                    tx2 += const_tx2;
//                    ty2 += const_ty2;
                }

                //
                //update Y index
                //

                //scanline increment
                xs += m_X_DY_left;
                xe += m_X_DY_right;

                //mappingFast
                txs2 += tx_left2;
                tys2 += ty_left2;

                start += bb.getWidth();
            }
        }
        */
    }

    public static void mappingZPerspectiveCorrected(BackBuffer bb, Vertex2 v1,Vertex2 v2,Vertex2 v3, GlTexture texture,boolean isDepthBufferWritable)
    {
/*
        Vertex2 v4=null;

        //
        //Y	sort with bubble sort
        //
        if (v1.posScreen.m_y > v2.posScreen.m_y) //swap 0 & 1
        {
            v4 = v1;            v1 = v2;            v2 = v4;
        }
        if ( v2.posScreen.m_y  >  v3.posScreen.m_y) //swap 1 & 2
        {
            v4 = v2;            v2 = v3;            v3 = v4;
        }
        if ( v1.posScreen.m_y >  v2.posScreen.m_y) //swap 0 & 1
        {
            v4 = v1;            v1 = v2;            v2 = v4;
        }

        double coef;
        int height;

        left_x = (int) (v2.posScreen.m_x);
        long h = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS);
        if (h == 0) return;

        //magic	coef
        coef = ((double) (((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS))) / (double) h;

        //poly incrment
        double tt = (double) ((int) v3.posScreen.m_x) - ((int) v1.posScreen.m_x);
        right_x = (int) v1.posScreen.m_x + (int) (coef * tt);

        //zbuffer	au point 4 & 2
        left_z = v2.posScreen.m_z;
        tt = v3.posScreen.m_z - v1.posScreen.m_z;
        right_z = v1.posScreen.m_z + (coef * tt);

        //texture2 coord au point 4 & 2
        left_tx2 = (int) v2.texturePos.m_x;
        tt =  ((int) v3.texturePos.m_x) - ((int) v1.texturePos.m_x);
        right_tx2 = (int) v1.texturePos.m_x + (int) (coef * tt);
        left_ty2 = (int) v2.texturePos.m_y;
        tt =  ((int) v3.texturePos.m_y) - ((int) v1.texturePos.m_y);
        right_ty2 = (int) v1.texturePos.m_y + (int) (coef * tt);


        if (right_x < left_x)
        {
            //for	scanline
            temp_x = right_x;            right_x = left_x;            left_x = temp_x;

            //for	zbuffer
            temp_f = right_z;            right_z = left_z;            left_z = temp_f;

            //for	Texture	mappingFast
            temp_x = right_tx2;
            right_tx2 = left_tx2;
            left_tx2 = temp_x;
            temp_x = right_ty2;
            right_ty2 = left_ty2;
            left_ty2 = temp_x;
        }

        //
        //les increment constant pour tout le triangle sur les X
        //

        //zbuffer
        const_z = ((right_z - left_z) / (double) (right_x - left_x)) * 65536.0;

        //mappingFast
        m_UV_DX.m_x = (int) (65536.0f * (float) (right_tx2 - left_tx2) / (float) ((right_x - left_x)));
        m_UV_DX.m_y = (int) (65536.0f * (float) (right_ty2 - left_ty2) / (float) ((right_x - left_x)));


        start = bb.getWidth() * ((int) (v1.posScreen.m_y + 0.5) >> FRACBITS);

        height = ((int) v2.posScreen.m_y >> FRACBITS) - ((int) v1.posScreen.m_y >> FRACBITS);
        if (height > 0)
        {
            //start m_x for scanline
            xs = (int) v1.posScreen.m_x;
            xe = (int) v1.posScreen.m_x;

            //
            //les increments sur les Y pour la 1ere partie du triangle
            //

            m_X_DY_left = (left_x - (int) v1.posScreen.m_x) / height;
            m_X_DY_right = (right_x - (int) v1.posScreen.m_x) / height;

            //zbuffer
            z_left = (left_z - v1.posScreen.m_z) / (double)height;
            zs = v1.posScreen.m_z;

            //Texture mappingFast
            tx_left2 = (left_tx2 - (int) v1.texturePos.m_x) / height;
            ty_left2 = (left_ty2 - (int) v1.texturePos.m_y) / height;
            txs2 = (int) v1.texturePos.m_x;
            tys2 = (int) v1.texturePos.m_y;

            for (m_y = (int) v1.posScreen.m_y >> FRACBITS; m_y < (int) v2.posScreen.m_y >> FRACBITS; m_y++)
            {
                scanStart = xs >> FRACBITS;
                scanEnd = xe >> FRACBITS;

                if (m_y >= bb.getHeight())
                    break;

                //zbuffer	start	for	scanline
                m_z = zs;

                //texture2 start for scanline
                tx2 = txs2;
                ty2 = tys2;

                scanLengt = scanEnd - scanStart;
                offsetStart = start + scanStart;

                while ((scanLengt--) >= 0)
                {
                    if (m_z < bb.get_raw_depth_buffer()[offsetStart])    //need to update pixel ?
                    {
                        //update zbuffer
                        if (isDepthBufferWritable)
                            bb.get_raw_depth_buffer()[offsetStart] = m_z;

                        int mappingColor = texture.getData()[(((tx2 >> 16)) & (texture.getWidth()-1)) + ((((ty2 >> 16)) & (texture.getHeight()-1)) << texture.getWidthLog2())];

                        bb.get_raw_color_buffer()[offsetStart] = mappingColor;
                    }

                    //scanline increment
                    offsetStart++;

                    //zbuffer increment
                    m_z += const_z;

                    //mappingFast
//                    tx2 += const_tx2;
//                    ty2 += const_ty2;
                }

                //
                //update Y index
                //

                //scanline increment
                xs += m_X_DY_left;
                xe += m_X_DY_right;

                //m_z
                zs += z_left;

                //mappingFast
                txs2 += tx_left2;
                tys2 += ty_left2;

                //new m_y screen start
                start += bb.getWidth();
            }
        }

        height = ((int) v3.posScreen.m_y >> FRACBITS) - ((int) v2.posScreen.m_y >> FRACBITS);
        if (height > 0)
        {
            m_X_DY_right = ((int) v3.posScreen.m_x - right_x) / height;
            m_X_DY_left = ((int) v3.posScreen.m_x - left_x) / height;

            //start for m_x scanline
            xs = left_x;
            xe = right_x;

            //
            //les increments sur les Y
            //

            //zbuffer
            zs = left_z;
            z_left = (v3.posScreen.m_z - left_z) / (double)height;

            //mappingFast
            tx_left2 = ((int) v3.texturePos.m_x - left_tx2) / height;
            ty_left2 = ((int) v3.texturePos.m_y - left_ty2) / height;
            txs2 = left_tx2;
            tys2 = left_ty2;

            for (m_y = (int) v2.posScreen.m_y >> FRACBITS; m_y < ((int) v3.posScreen.m_y >> FRACBITS); m_y++)
            {
                scanStart = xs >> FRACBITS;
                scanEnd = xe >> FRACBITS;

                if (m_y >= bb.getHeight())
                    break;

                //zbuffer	start	for	scanline
                m_z = zs;

                //mappingFast
                tx2 = txs2;
                ty2 = tys2;

                scanLengt = scanEnd - scanStart;
                offsetStart = start + scanStart;

                while ((scanLengt--) > 0)
                {
                    if (bb.get_raw_depth_buffer()[offsetStart] > m_z)
                    {
                        //update zbuffer
                        if (isDepthBufferWritable)
                            bb.get_raw_depth_buffer()[offsetStart] = m_z;

                        int mappingColor = texture.getData()[(((tx2 >> 16)) & (texture.getWidth()-1)) + ((((ty2 >> 16)) & (texture.getHeight()-1)) << texture.getWidthLog2())];

                        bb.get_raw_color_buffer()[offsetStart] = mappingColor;
                    }

                    //scanline increment
                    offsetStart++;

                    //zbuffer increment
                    m_z += const_z;

                    //mappingFast
//                    tx2 += const_tx2;
//                    ty2 += const_ty2;
                }

                //
                //update Y index
                //

                //scanline increment
                xs += m_X_DY_left;
                xe += m_X_DY_right;

                //zbuffer
                zs += z_left;

                //mappingFast
                txs2 += tx_left2;
                tys2 += ty_left2;

                start += bb.getWidth();
            }
        }
        */
    }

}
