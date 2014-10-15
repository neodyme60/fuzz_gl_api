package fuzzGl.graphics;

//
//some digital image filter
//
//

import fuzzGl.graphics.VideoBuffer;

public class PostProcessing {
    static int index;
    static int x;
    static int y;

    //
    //laplace Z filter
    // TFilter = ( (-1, -1, -1),
//            (-1,  8, -1),

    //            (-1, -1, -1) );
    //use: videoBuffer.buffer1 as input & videoBuffer.buffer1
    static public final void filter01(VideoBuffer b)
    {
        /*
        filter_c.index = b.xSize + 1;
        double zStack;
        int toto;

        for (filter_c.m_y = 1; filter_c.m_y < (b.ySize - 2); filter_c.m_y++) {
            for (filter_c.m_x = 1; filter_c.m_x < (b.xSize - 2); filter_c.m_x++) {
                toto = filter_c.index + filter_c.m_x;
                zStack = b.zBuffer[toto] * 8.0f;
                zStack -= b.zBuffer[toto + 1];
                zStack -= b.zBuffer[toto - 1];
                zStack -= b.zBuffer[toto + b.xSize];
                zStack -= b.zBuffer[toto + 1 + b.xSize];
                zStack -= b.zBuffer[toto - 1 + b.xSize];
                zStack -= b.zBuffer[toto - b.xSize];
                zStack -= b.zBuffer[toto + 1 - b.xSize];
                zStack -= b.zBuffer[toto - 1 - b.xSize];

                if ((0.2 / 1000.0f) > zStack)
                    b.buffer2[toto] = 0x0;
                else
                    b.buffer2[toto] = 0xffffff;
            }
            filter_c.index += b.xSize;
        }
        //swap buffer
        b.swapPixelBuffer();
        */
    }


    //
    //laplace colorRGBA filter ( cartoon like rendering)
    // TFilter = ( (-1, -1, -1),
//            (-1,  8, -1),

    //            (-1, -1, -1) );
    //use: videoBuffer.buffer1 as input & videoBuffer.buffer1
    static public final void filter02(VideoBuffer b)
    {
        /*
        filter_c.index = b.xSize + 1;
        int toto;
        double zStack;
        for (filter_c.m_y = 1; filter_c.m_y < (b.ySize - 2); filter_c.m_y++) {
            for (filter_c.m_x = 1; filter_c.m_x < (b.xSize - 2); filter_c.m_x++) {
                toto = filter_c.index + filter_c.m_x;
                zStack = b.buffer[toto] * 8.0f;
                zStack -= b.buffer[toto + 1];
                zStack -= b.buffer[toto - 1];
                zStack -= b.buffer[toto + b.xSize];
                zStack -= b.buffer[toto + 1 + b.xSize];
                zStack -= b.buffer[toto - 1 + b.xSize];
                zStack -= b.buffer[toto - b.xSize];
                zStack -= b.buffer[toto + 1 - b.xSize];
                zStack -= b.buffer[toto - 1 - b.xSize];

                if ((0.2 / 1000.0f) > zStack)
                    b.buffer2[toto] = b.buffer[toto];
                else
                    b.buffer2[toto] = 0xffffff;
            }
            filter_c.index += b.xSize;
        }
        //swap buffer
        b.swapPixelBuffer();
        */
    }

    //some blur
    //use: videoBuffer.buffer1 as input & videoBuffer.buffer1
    static public final void filter03(VideoBuffer b)
    {
        /*
        filter_c.index = b.xSize + 1;
        int toto;
        int zStack;
        for (filter_c.m_y = 1; filter_c.m_y < (b.ySize - 2); filter_c.m_y++) {
            for (filter_c.m_x = 1; filter_c.m_x < (b.xSize - 2); filter_c.m_x++) {
                toto = filter_c.index + filter_c.m_x;
                zStack = b.buffer[toto];
                zStack = colorRGBA.mix(zStack, b.buffer[toto - 1]);
                zStack = colorRGBA.mix(zStack, b.buffer[toto + 1]);

                zStack = colorRGBA.mix(zStack, b.buffer[toto - 1 + b.xSize]);
                zStack = colorRGBA.mix(zStack, b.buffer[toto + b.xSize]);
                zStack = colorRGBA.mix(zStack, b.buffer[toto + 1 + b.xSize]);

                zStack = colorRGBA.mix(zStack, b.buffer[toto - 1 - b.xSize]);
                zStack = colorRGBA.mix(zStack, b.buffer[toto - b.xSize]);
                zStack = colorRGBA.mix(zStack, b.buffer[toto + 1 - b.xSize]);

                b.buffer2[toto] = zStack;
            }
            filter_c.index += b.xSize;
        }
        //swap buffer
        b.swapPixelBuffer();
        */
    }

    //some blur
    //use: videoBuffer.buffer1 as input & videoBuffer.buffer1
    static public final void filter04(VideoBuffer b)
    {
        /*
        int index = b.xSize + 1;
        int toto;
        int zStack;
        for (int m_y = 1; m_y < (b.ySize - 2); m_y++) {
            for (int m_x = 1; m_x < (b.xSize - 2); m_x++) {
                toto = index + m_x;
                zStack = b.buffer[toto];
                zStack = colorRGBA.mix(zStack, b.buffer[toto - 1]);
                zStack = colorRGBA.mix(zStack, b.buffer[toto + 1]);
                zStack = colorRGBA.mix(zStack, b.buffer[toto + b.xSize]);
                zStack = colorRGBA.mix(zStack, b.buffer[toto - b.xSize]);
                b.buffer2[toto] = zStack;
            }
            index += b.xSize;
        }
        //swap buffer
        b.swapPixelBuffer();
        */
    }

}

