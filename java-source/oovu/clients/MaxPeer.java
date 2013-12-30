package oovu.clients;

import oovu.addresses.OscAddress;
import oovu.maxadapters.MaxAdapter;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;
import oovu.messaging.Response;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public abstract class MaxPeer extends MaxObject implements MessagePasser {
    protected MaxAdapter max_adapter;

    @Override
    public void anything(String message, Atom[] arguments) {
        OscAddress osc_address = null;
        if (this.getInlet() == 1) {
            osc_address = OscAddress.from_cache("./:" + message);
        } else if ((1 < message.length()) && (message.charAt(0) == ':')) {
            osc_address = OscAddress.from_cache("./" + message);
        } else {
            if (message.charAt(0) == '/') {
                osc_address = OscAddress.from_cache("." + message);
            } else {
                osc_address = OscAddress.from_cache(message);
            }
            if (osc_address.message_handler_name == null) {
                osc_address = OscAddress.from_cache(osc_address.toString() + "/:value");
            }
        }
        Request request = new Request(this, osc_address, arguments, true);
        this.handle_request(request);
    }

    @Override
    public void bang() {
        this.anything("bang", new Atom[0]);
    }

    @Override
    public void handle_response(Response response) {
        this.max_adapter.handle_response(response);
    }
}
