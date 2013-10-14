package oovu.adapters;

import oovu.clients.MaxPeer;
import oovu.messaging.Response;

import com.cycling74.max.Atom;

public abstract class MaxAdapter {

    protected static final Atom value_atom = Atom.newAtom("value");
    protected final MaxPeer max_peer;

    public MaxAdapter(MaxPeer max_peer) {
        this.max_peer = max_peer;
    }

    abstract public void handle_response(Response response);

    protected void outlet(int outlet_index, Atom[] payload) {
        try {
            this.max_peer.outlet(outlet_index, payload);
        } catch (UnsatisfiedLinkError e) {
        }
    }
}
