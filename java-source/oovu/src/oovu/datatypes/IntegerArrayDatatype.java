package oovu.datatypes;

import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.servers.AttributeServer;
import oovu.timing.MultiEnvelope;

import com.cycling74.max.Atom;

public class IntegerArrayDatatype extends BoundedArrayDatatype {
    public IntegerArrayDatatype(Atom[] arguments) {
        this(null, MaxIO.to_map(arguments));
    }

    public IntegerArrayDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        this.multi_envelope =
            new MultiEnvelope(this, Atom.toDouble(this.value));
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new int[] {
            0
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        if (this.multi_envelope == null) {
            input = this.ensure_length(input);
        }
        double[] doubles = this.extract_doubles_from_atoms(input);
        if (this.multi_envelope != null) {
            doubles = this.multi_envelope.control_all_envelopes(doubles);
        }
        doubles = this.bound_doubles(doubles);
        Atom[] output = new Atom[doubles.length];
        for (int i = 0, j = doubles.length; i < j; i++) {
            output[i] = Atom.newAtom(new Double(doubles[i]).intValue());
        }
        return output;
    }
}
