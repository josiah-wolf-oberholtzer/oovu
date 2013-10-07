package oovu.clients;

import oovu.addresses.OscAddress;
import oovu.messaging.Request;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

abstract public class LazyServerClient extends ServerClient {

    protected int lazy_module_id;
    protected String lazy_name;
    protected Atom[] lazy_arguments;

    public LazyServerClient(Atom[] arguments) {
        this.declareIO(3, 2);
        if (arguments.length < 2) {
            MaxObject
                .bail("Lazy clients require a module ID and name template.");
        }
        this.lazy_module_id = arguments[0].toInt();
        this.lazy_name = arguments[1].toString();
        this.lazy_arguments = Atom.removeFirst(arguments, 2);
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
        String lazy_name = this.lazy_name;
        while (lazy_name.contains("{}") && (0 < arguments.length)) {
            String substitution = arguments[0].toString();
            lazy_name = lazy_name.replace("{}", substitution);
            arguments = Atom.removeFirst(arguments);
        }
        return lazy_name;
    }
}
