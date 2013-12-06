package oovu.clients;

import oovu.adapters.MaxAdapter;
import oovu.messaging.MessagePasser;
import oovu.messaging.Response;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public abstract class MaxPeer extends MaxObject implements MessagePasser {
    protected MaxAdapter max_adapter;

    @Override
    public abstract void anything(String message, Atom[] arguments);

    @Override
    public void handle_response(Response response) {
        this.max_adapter.handle_response(response);
    }
}
