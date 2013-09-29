package oovu.clients;

import oovu.addressing.OscAddress;
import oovu.messaging.Request;

import com.cycling74.max.Atom;

public abstract class AttributeServerClient extends ServerClient {

    @Override
    public void anything(String message, Atom[] arguments) {
        OscAddress osc_address = null;
        if (this.getInlet() == 1) {
            osc_address = OscAddress.from_cache("./:" + message);
        } else {
            arguments = Atom.newAtom(message, arguments);
            osc_address = OscAddress.from_cache("./:value");
        }
        Request request = new Request(this, osc_address, arguments);
        this.handle_request(request);
    }
}
