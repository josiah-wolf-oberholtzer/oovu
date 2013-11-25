package oovu.datatypes;

import java.util.Map;

import oovu.messaging.Atoms;
import oovu.servers.AttributeServer;
import oovu.timing.MultiEnvelope;

import com.cycling74.max.Atom;

public class DecimalDatatype extends BoundedDatatype {
    public DecimalDatatype(Atom[] arguments) {
        this(null, Atoms.to_map(arguments));
    }

    public DecimalDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        this.multi_envelope =
            new MultiEnvelope(this, Atom.toDouble(this.value));
    }

    @Override
    public Integer get_arity() {
        return 1;
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] {
            0.
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        double[] doubles = this.extract_doubles_from_atoms(input);
        if (this.multi_envelope != null) {
            doubles = this.multi_envelope.control_all_envelopes(doubles);
        }
        doubles = this.bound_doubles(doubles);
        Atom[] result = new Atom[1];
        if (0 < input.length) {
            result[0] = Atom.newAtom(doubles[0]);
        }
        return result;
    }
}
