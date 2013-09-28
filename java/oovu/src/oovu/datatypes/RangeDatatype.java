package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.messaging.DatatypeMessageHandler;
import oovu.servers.Server;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class RangeDatatype extends BoundedDatatype {

    private class CenterMessageHandler extends DatatypeMessageHandler {

        public CenterMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public void call_after() {
            this.attribute_server.reoutput_value();
        }

        @Override
        public String get_name() {
            return "center";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 == arguments.length) {
                return null;
            }
            double new_center = arguments[0].toDouble();
            RangeDatatype.this.apply_new_center(new_center);
            return null;
        }
    }

    private class WidthMessageHandler extends DatatypeMessageHandler {

        public WidthMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public void call_after() {
            this.attribute_server.reoutput_value();
        }

        @Override
        public String get_name() {
            return "width";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 == arguments.length) {
                return null;
            }
            double new_width = arguments[0].toDouble();
            RangeDatatype.this.apply_new_width(new_width);
            return null;
        }
    }

    public RangeDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public RangeDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_message_handler(new CenterMessageHandler(
                this.client));
            this.client
                .add_message_handler(new WidthMessageHandler(this.client));
        }
    }

    public void apply_new_center(double new_center) {
        double[] current_range = Atom.toDouble(this.get_value());
        double[] center_width = this.range_to_center_width(current_range[0],
            current_range[1]);
        double[] new_range = this.center_width_to_range(new_center,
            center_width[1]);
        this.set_value(Atom.newAtom(new_range));
    }

    public void apply_new_width(double new_width) {
        double[] current_range = Atom.toDouble(this.get_value());
        double[] center_width = this.range_to_center_width(current_range[0],
            current_range[1]);
        double[] new_range = this.center_width_to_range(center_width[0],
            Math.abs(new_width));
        this.set_value(Atom.newAtom(new_range));
    }

    protected double[] center_width_to_range(double center, double width) {
        return new double[] {
            center - width, center + width
        };
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] {
            0., 1.
        });
    }

    @Override
    public void initialize_default_value(Map<String, Atom[]> argument_map) {
        Atom[] value = this.get_default();
        if (argument_map.containsKey("default")) {
            Atom[] default_value = argument_map.get("default");
            if (1 < default_value.length) {
                value = default_value;
            }
        }
        this.set_value(value);
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        if (input.length < 2) {
            return this.get_value();
        }
        double[] values = this.extract_bounded_doubles_from_atoms(input);
        double[] range = new double[] {
            values[0], values[1]
        };
        Arrays.sort(range);
        return Atom.newAtom(range);
    }

    protected
        double[]
        range_to_center_width(double range_low, double range_high) {
        double center = (range_high + range_low) / 2.;
        double width = range_high - center;
        return new double[] {
            center, width
        };
    }
}
