package samples;

import fuzzGl.GlCore;
import fuzzGl.BackBuffer;

public abstract class SamplesBase
{
    GlCore c=null;
    
    abstract public void init(BackBuffer bb);
    abstract public void update();
    abstract public void deinit();    
}
