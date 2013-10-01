package oovu.datatypes;

import java.util.Map;

import oovu.servers.AttributeServer;
import oovu.servers.Server;
import oovu.timewise.MultiEnvelope;

import com.cycling74.max.Atom;

public class IntegerArrayDatatype extends BoundedArrayDatatype {

    public IntegerArrayDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public IntegerArrayDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        this.multi_envelope = new MultiEnvelope(this, Atom.toDouble(this.value));
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new int[] {
            0
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        Atom[] result = this.ensure_length(input);
        double[] doubles = this.extract_doubles_from_atoms(result);
        doubles = this.multi_envelope.control_all_envelopes(doubles);
        doubles = this.bound_doubles(doubles);
        for (int i = 0, j = result.length; i < j; i++) {
            result[i] = Atom.newAtom(new Double(doubles[i]).intValue());
        }
        return result;
    }
}
