package oovu.datatypes;

import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class TriggerDatatype extends Datatype {
    public TriggerDatatype(Atom[] arguments) {
        this(null, MaxIO.from_serialized_dict(arguments));
    }

    public TriggerDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Integer get_arity() {
        return 0;
    }

    @Override
    public Atom[] get_default() {
        return new Atom[] {
            Atom.newAtom("bang")
        };
    }

    @Override
    protected void initialize_prerequisites(Map<String, Atom[]> argument_map) {
    }

    @Override
    public boolean is_binding_relevant() {
        return true;
    }

    @Override
    public boolean is_rampable() {
        return false;
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        return new Atom[] {
            Atom.newAtom("bang")
        };
    }
}
