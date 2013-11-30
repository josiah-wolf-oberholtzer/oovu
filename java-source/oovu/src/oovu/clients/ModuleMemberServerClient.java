package oovu.clients;

import oovu.adapters.GenericMaxAdapter;
import oovu.addresses.OscAddress;
import oovu.messaging.Request;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public abstract class ModuleMemberServerClient extends ServerClient {
    protected Integer lazy_module_id;
    protected String lazy_name;
    protected Atom[] lazy_arguments;

    public ModuleMemberServerClient(Atom[] arguments) {
        this.declareIO(3, 2);
        if (arguments.length < 2) {
            MaxObject
                .bail("Lazy clients require a module ID and name template.");
        }
        if (arguments[0].isString() && arguments[0].getString().equals("#1")) {
            return;
        }
        this.lazy_module_id = arguments[0].toInt();
        this.lazy_name = arguments[1].toString();
        this.lazy_arguments = Atom.removeFirst(arguments, 2);
        this.max_adapter = new GenericMaxAdapter(this);
        if (!this.lazy_name.contains("{}")) {
            this.bind(new Atom[0]);
        }
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
            } else {
                if (message != null) {
                    arguments = Atom.newAtom(message, arguments);
                }
                osc_address = OscAddress.from_cache("./:value");
            }
            Request request = new Request(this, osc_address, arguments, true);
            this.handle_request(request);
        }
    }

    abstract public void bind(Atom[] arguments);

    public String complete_lazy_name(Atom[] arguments) {
        String lazy_name = this.lazy_name;
        if (lazy_name == null) {
            return null;
        }
        while (lazy_name.contains("{}") && (0 < arguments.length)) {
            String substitution = arguments[0].toString();
            lazy_name = lazy_name.replace("{}", substitution);
            arguments = Atom.removeFirst(arguments);
        }
        return lazy_name;
    }

    @Override
    public void list(Atom[] input) {
        if (this.getInlet() == 1) {
            this.anything("value", input);
        } else if (this.getInlet() == 0) {
            this.anything(null, input);
        }
    }
}
