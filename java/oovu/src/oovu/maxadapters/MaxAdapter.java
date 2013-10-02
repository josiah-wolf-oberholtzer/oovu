package oovu.maxadapters;

import oovu.clients.MaxPeer;


public abstract class MaxAdapter {
    
    protected final MaxPeer max_peer;
    
    public MaxAdapter(MaxPeer max_peer) {
        this.max_peer = max_peer;
    }
}
