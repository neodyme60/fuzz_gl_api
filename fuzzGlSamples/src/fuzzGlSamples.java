import fuzzGl.BackBuffer;
import samples.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;

public class fuzzGlSamples extends JFrame
{
    float   m_fps =60;
    float   m_frame =500;
    int     m_width=600;
    int     m_height=600;

    //sample
    SamplesBase         m_sample;
    Image               m_offscreen_image;
    MemoryImageSource   m_memory_image_source;
    ColorModel          m_DirectColorModel;
    Thread              m_player_thread = null;
    Image               m_back_image;
    long                m_time =System.currentTimeMillis ();
    Graphics            m_back_graphics;		// back Graphics
    BackBuffer          m_back_buffer;

    fuzzGlSamples()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //backbuffer
        m_back_buffer =new BackBuffer(m_width,m_height);
        m_back_buffer.add_color_buffer();
        m_back_buffer.add_depth_buffer();

        //sample
//        m_sample=new SimpleCube();
//        m_sample=new SierpinskiGasket3DSmooth();
//      m_sample=new Nehe07();
        m_sample = new Gear();
//        m_sample=new SkyBox();
//        m_sample=new PQTorusKnot();
//        m_sample=new Simple2CubesDepthRange();
        m_sample=new Simple2Cubes();

        m_sample.init(m_back_buffer);

        setUndecorated(false);
        setVisible(true);
        setSize(m_width,m_height);
        setResizable( false );

        // Init MemoryImageSource - pixels will be in 32bit ARGB format
        m_DirectColorModel = new DirectColorModel(32, 0x00FF0000, 0x000FF00, 0x000000FF, 0);
        m_memory_image_source = new MemoryImageSource(m_width, m_height, m_DirectColorModel, m_back_buffer.get_raw_color_buffer(), 0, m_width);
        m_memory_image_source.setFullBufferUpdates(true);
        m_memory_image_source.setAnimated(true);
        m_offscreen_image = createImage(m_memory_image_source);

        // Init double buffering
        m_back_image = createImage(m_width, m_height);
        m_back_graphics = m_back_image.getGraphics();

        m_player_thread = new Thread()
        {
            public void run()
            {
                boolean loop=true;
                while(loop)
                {

                    try
                    {
                        Thread.sleep(20);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    repaint();
                }
                m_sample.deinit();
            }
          };
        m_player_thread.start();

    }

    public static void main(String args[])
    {
        fuzzGlSamples SG = new fuzzGlSamples();
    }

    @Override
    public synchronized void update(Graphics g)
    {
       paint(g);                           
    }


    private void renderScene()
    {

    }

    // Overwrite imageUpdate
    public boolean imageUpdate(Image image, int i1, int j1, int k, int i2, int j2)
    {
        return true;
    }

    @Override
    public synchronized void paint(Graphics g)
    {
        m_sample.update();
        
        //fps
        long time2 = System.currentTimeMillis ();
        m_frame += m_fps *(time2- m_time)/1000;
        m_time =time2;

        //
        //copy to color buffer
        //
        m_memory_image_source.newPixels(0, 0, m_width, m_height, false);
        m_back_graphics.drawImage(m_offscreen_image, 0, 0, this);

        //
        g.drawImage(m_back_image, 0, 0, this);
        g.setColor(Color.white);

        g.setColor(Color.white);
        g.drawString("m_frame:" + java.lang.String.valueOf(m_frame), 5, 90);
        g.drawString("FPS : " + m_fps, 5, 150);
    }

}
