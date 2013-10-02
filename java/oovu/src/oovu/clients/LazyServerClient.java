package oovu.clients;

import oovu.addressing.OscAddress;
import oovu.messaging.Request;

import com.cycling74.max.Atom;

abstract public class LazyServerClient extends ServerClient {

    protected Atom lazy_module_id;
    protected Atom lazy_name;
    protected Atom[] lazy_arguments;

    public LazyServerClient(Atom lazy_module_id, Atom lazy_name,
        Atom[] lazy_arguments) {
        this.declareIO(3, 3);
        this.lazy_module_id = lazy_module_id;
        this.lazy_name = lazy_name;
        this.lazy_arguments = lazy_arguments;
    }

    @Override
    public void anything(String message, Atom[] arguments) {
        if (this.getInlet() == 2) {
            if (message.equals("bind")) {
                this.bind(arguments);
            }
        } else {
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
                    osc_address = OscAddress.from_cache(osc_address.toString()
                        + "/:value");
                }
            }
            Request request = new Request(this, osc_address, arguments);
            this.handle_request(request);
        }
    }

    abstract public void bind(Atom[] arguments);

    public String complete_lazy_name(Atom[] arguments) {
        String lazy_name = this.lazy_name.getString();
        while (lazy_name.contains("{}") && (0 < arguments.length)) {
            String substitution = arguments[0].toString();
            lazy_name = lazy_name.replace("{}", substitution);
            arguments = Atom.removeFirst(arguments);
        }
        return lazy_name;
    }
}
