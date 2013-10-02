package oovu;

import oovu.clients.LazyServerClient;

import com.cycling74.max.Atom;

public class LazyMethod extends LazyServerClient {

    public LazyMethod(Atom lazy_module_id, Atom lazy_name, Atom[] lazy_arguments) {
        super(lazy_module_id, lazy_name, lazy_arguments);
    }
}
