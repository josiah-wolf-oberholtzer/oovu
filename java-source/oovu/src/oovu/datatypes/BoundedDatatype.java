package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.messaging.Atoms;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.servers.AttributeServer;
import oovu.timing.EnvelopeHandler;
import oovu.timing.MultiEnvelope;

import com.cycling74.max.Atom;

abstract public class BoundedDatatype extends Datatype implements
    EnvelopeHandler {
    protected Double minimum;
    protected Double maximum;
    protected MultiEnvelope multi_envelope;

    public BoundedDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client
                .add_message_handler(new MessageHandlerBuilder("maximum")
                    .with_callback(new MessageHandlerCallback() {
                        @Override
                        public Atom[][] execute(
                            MessageHandler built_message_handler,
                            Atom[] arguments) {
                            BoundedDatatype.this.client.reoutput_value();
                            return null;
                        }
                    })
                    .with_getter(new MessageHandlerCallback() {
                        @Override
                        public Atom[][] execute(
                            MessageHandler built_message_handler,
                            Atom[] arguments) {
                            return Atoms.to_atoms(
                                built_message_handler.get_name(),
                                BoundedDatatype.this.get_maximum());
                        }
                    }).with_is_meta_relevant(true).with_is_state_relevant(true)
                    .with_setter(new MessageHandlerCallback() {
                        @Override
                        public Atom[][] execute(
                            MessageHandler built_message_handler,
                            Atom[] arguments) {
                            Double maximum = null;
                            if (0 < arguments.length) {
                                maximum = arguments[0].toDouble();
                            }
                            BoundedDatatype.this.set_maximum(maximum);
                            return null;
                        }
                    }).build(this.client));
            this.client
                .add_message_handler(new MessageHandlerBuilder("minimum")
                    .with_callback(new MessageHandlerCallback() {
                        @Override
                        public Atom[][] execute(
                            MessageHandler built_message_handler,
                            Atom[] arguments) {
                            BoundedDatatype.this.client.reoutput_value();
                            return null;
                        }
                    })
                    .with_getter(new MessageHandlerCallback() {
                        @Override
                        public Atom[][] execute(
                            MessageHandler built_message_handler,
                            Atom[] arguments) {
                            return Atoms.to_atoms(
                                built_message_handler.get_name(),
                                BoundedDatatype.this.get_minimum());
                        }
                    }).with_is_meta_relevant(true).with_is_state_relevant(true)
                    .with_setter(new MessageHandlerCallback() {
                        @Override
                        public Atom[][] execute(
                            MessageHandler built_message_handler,
                            Atom[] arguments) {
                            Double minimum = null;
                            if (0 < arguments.length) {
                                minimum = arguments[0].toDouble();
                            }
                            BoundedDatatype.this.set_minimum(minimum);
                            return null;
                        }
                    }).build(this.client));
        }
    }

    protected double[] bound_doubles(double[] doubles) {
        return this.bound_doubles_by_minimum(this
            .bound_doubles_by_maximum(doubles));
    }

    protected double[] bound_doubles_by_maximum(double[] doubles) {
        if (this.maximum == null) {
            return doubles;
        }
        for (int i = 0, j = doubles.length; i < j; i++) {
            if (this.maximum < doubles[i]) {
                doubles[i] = this.maximum;
            }
        }
        return doubles;
    }

    protected double[] bound_doubles_by_minimum(double[] doubles) {
        if (this.minimum == null) {
            return doubles;
        }
        for (int i = 0, j = doubles.length; i < j; i++) {
            if (doubles[i] < this.minimum) {
                doubles[i] = this.minimum;
            }
        }
        return doubles;
    }

    @Override
    public void cleanup_resources() {
        this.multi_envelope.cleanup_resources();
    }

    protected double[] extract_bounded_doubles_from_atoms(Atom[] atoms) {
        double[] doubles = this.extract_doubles_from_atoms(atoms);
        return this.bound_doubles(doubles);
    }

    protected double[] extract_doubles_from_atoms(Atom[] atoms) {
        return Atom.toDouble(atoms);
    }

    public Double get_maximum() {
        return this.maximum;
    }

    public Double get_minimum() {
        return this.minimum;
    }

    @Override
    public void handle_envelope_response(double[] response) {
        Atom[] value = Atom.newAtom(response);
        this.value = value;
        this.client.handle_asynchronous_datatype_value_output(value);
    }

    protected void initialize_extrema(Map<String, Atom[]> argument_map) {
        if (argument_map.containsKey("minimum")) {
            this.set_minimum(this.extract_doubles_from_atoms(argument_map
                .get("minimum"))[0]);
        } else {
            this.minimum = null;
        }
        if (argument_map.containsKey("maximum")) {
            this.set_maximum(argument_map.get("maximum")[0].toDouble());
        } else {
            this.maximum = null;
        }
        if (argument_map.containsKey("range")) {
            Atom[] range = argument_map.get("range");
            if (range.length == 2) {
                if (range[0].isString() && range[0].getString().equals("NULL")) {
                    this.minimum = null;
                } else {
                    this.minimum = range[0].toDouble();
                }
                if (range[1].isString() && range[1].getString().equals("NULL")) {
                    this.maximum = null;
                } else {
                    this.maximum = range[1].toDouble();
                }
            }
        }
    }

    @Override
    protected void initialize_prerequisites(Map<String, Atom[]> argument_map) {
        this.initialize_extrema(argument_map);
    }

    @Override
    public boolean is_binding_relevant() {
        return true;
    }

    @Override
    public boolean is_rampable() {
        return true;
    }

    public void set_maximum(Double maximum) {
        this.maximum = maximum;
        this.sort_extrema();
    }

    public void set_minimum(Double minimum) {
        this.minimum = minimum;
        this.sort_extrema();
    }

    protected void sort_extrema() {
        if ((this.minimum != null) && (this.maximum != null)) {
            Double[] extrema = new Double[] {
                this.minimum, this.maximum
            };
            Arrays.sort(extrema);
            this.minimum = extrema[0];
            this.maximum = extrema[1];
        }
    }
}
