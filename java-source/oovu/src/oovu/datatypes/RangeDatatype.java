package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.messaging.Atoms;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.Setter;
import oovu.servers.AttributeServer;
import oovu.timing.MultiEnvelope;

import com.cycling74.max.Atom;

public class RangeDatatype extends BoundedDatatype {
    public RangeDatatype(Atom[] arguments) {
        this(null, Atoms.to_map(arguments));
    }

    public RangeDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        double[] range = Atom.toDouble(this.value);
        double[] center_width = this.range_to_center_width(range[0], range[1]);
        this.multi_envelope = new MultiEnvelope(this, center_width);
        if (this.client != null) {
            this.client.add_message_handler(new MessageHandlerBuilder("center")
                .with_arity(1)
                .with_callback(new Setter() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        RangeDatatype.this.client.reoutput_value();
                        return null;
                    }
                }).with_is_binding_relevant(true).with_is_rampable(true)
                .with_setter(new Setter() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        if (0 == arguments.length) {
                            return null;
                        }
                        double[] control_values = Atom.toDouble(arguments);
                        RangeDatatype.this.apply_new_center(control_values);
                        return null;
                    }
                }).build(this.client));
            this.client.add_message_handler(new MessageHandlerBuilder("width")
                .with_arity(1)
                .with_callback(new Setter() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        RangeDatatype.this.client.reoutput_value();
                        return null;
                    }
                }).with_is_binding_relevant(true).with_is_rampable(true)
                .with_setter(new Setter() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        if (0 == arguments.length) {
                            return null;
                        }
                        double[] control_values = Atom.toDouble(arguments);
                        RangeDatatype.this.apply_new_width(control_values);
                        return null;
                    }
                }).build(this.client));
        }
    }

    public void apply_new_center(double[] control_values) {
        double[] response =
            this.multi_envelope.control_one_envelope(0, control_values);
        double[] range =
            this.bound_doubles(this.center_width_to_range(response[0],
                response[1]));
        this.value = Atom.newAtom(range);
        this.fix_multi_envelope_values();
    }

    public void apply_new_width(double[] control_values) {
        double[] response =
            this.multi_envelope.control_one_envelope(1, control_values);
        double[] range =
            this.bound_doubles(this.center_width_to_range(response[0],
                response[1]));
        this.value = Atom.newAtom(range);
        this.fix_multi_envelope_values();
    }

    protected double[] center_width_to_range(double center, double width) {
        return new double[] {
            center - width, center + width
        };
    }

    protected void fix_multi_envelope_values() {
        double[] range = Atom.toDouble(this.value);
        if (!this.multi_envelope.has_active_envelopes()) {
            double[] center_width =
                this.range_to_center_width(range[0], range[1]);
            this.multi_envelope.control_all_envelopes(center_width);
        }
    }

    @Override
    public Integer get_arity() {
        return 2;
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] {
            0., 1.
        });
    }

    @Override
    public void handle_envelope_response(double[] response) {
        double[] range =
            this.bound_doubles(this.center_width_to_range(response[0],
                response[1]));
        Atom[] value = Atom.newAtom(range);
        this.value = value;
        this.fix_multi_envelope_values();
        this.client.handle_asynchronous_datatype_value_output(value);
    }

    @Override
    public void initialize_default_value(Map<String, Atom[]> argument_map) {
        Atom[] value = this.get_default();
        if (argument_map.containsKey("default")) {
            Atom[] default_value = argument_map.get("default");
            if (1 < default_value.length) {
                value[0] = default_value[0];
                value[1] = default_value[1];
            }
        }
        this.set_value(value);
    }

    protected double[] preprocess_doubles(double[] doubles) {
        if (doubles.length < 2) {
            return doubles;
        } else if (doubles.length == 2) {
            Arrays.sort(doubles);
            return this.range_to_center_width(doubles[0], doubles[1]);
        }
        for (int i = 0, j = doubles.length; (i + 2) < j; i += 3) {
            double low = Math.min(doubles[i], doubles[i + 1]);
            double high = Math.max(doubles[i], doubles[i + 1]);
            double[] center_width = this.range_to_center_width(low, high);
            doubles[i] = center_width[0];
            doubles[i + 1] = center_width[1];
        }
        return doubles;
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        double[] doubles = this.extract_doubles_from_atoms(input);
        if (this.multi_envelope != null) {
            doubles = this.preprocess_doubles(doubles);
            doubles = this.multi_envelope.control_all_envelopes(doubles);
            doubles = this.center_width_to_range(doubles[0], doubles[1]);
        }
        doubles = this.bound_doubles(doubles);
        Arrays.sort(doubles);
        Atom[] result = Atom.newAtom(doubles);
        return result;
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
