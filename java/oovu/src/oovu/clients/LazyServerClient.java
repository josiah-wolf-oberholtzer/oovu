package oovu.clients;

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
}
