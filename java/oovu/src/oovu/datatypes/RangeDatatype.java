package oovu.datatypes;

import java.util.Map;

import oovu.servers.Server;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class RangeDatatype extends BoundedDatatype {

    public RangeDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public RangeDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] {
            0., 1.
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        if (input.length < 2) {
            return this.get_value();
        }
        double[] values = this.extract_bounded_doubles_from_atoms(input);
        return new Atom[0];
    }
    
    @Override
    public void initialize_default_value(Map<String, Atom[]> argument_map) {
        Atom[] value = this.get_default();
        if (argument_map.containsKey("default")){
            Atom[] default_value = argument_map.get("default");
            if (1 < default_value.length) {
                value = default_value;
            }
        }
        this.set_value(value);
    }
    
    protected double[] center_width_to_range(float center, float width) {
        return new double[]{ center - width, center + width }; 
    }
    
    protected double[] range_to_center_width(float range_low, float range_high) {
        double center = (range_high + range_low) / 2.;
        double width = range_high - center;
        return new double[]{ center, width};
    }
}
