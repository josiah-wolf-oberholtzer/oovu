package oovu.events;

import java.util.HashMap;
import java.util.Map;

import oovu.datatypes.BooleanDatatype;
import oovu.datatypes.BoundedDatatype;
import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.Server;
import oovu.timing.ValueRange;

import com.cycling74.max.Atom;

public class PatternSubscription extends BindingSubscription {
    public static PatternSubscription from_atoms(Server subscriber, Atom[] atoms) {
        Map<String, Atom[]> arguments = MaxIO.from_serialized_dict(atoms);
        String message_name = null;
        String subscription_name = null;
        ValueRange[] timings = null;
        ValueRange[] values = null;
        int arity = 0;
        if (arguments.containsKey("message")) {
            message_name = arguments.get("message")[0].toString();
        } else {
            message_name = "value";
        }
        if (arguments.containsKey("name")) {
            subscription_name = arguments.get("name")[0].toString();
        } else {
            subscription_name = message_name;
        }
        MessageHandler message_handler = subscriber.get_message_handler(message_name);
        if ((message_handler == null)
            || message_name.equals(message_handler.get_getter_name())) {
            return null;
        } else if (!message_handler.get_is_binding_relevant()) {
            return null;
        }
        arity = message_handler.get_arity();
        if (arguments.containsKey("timings")) {
            Atom[] current_atoms = arguments.get("timings");
            timings = new ValueRange[current_atoms.length];
            for (int i = 0, j = current_atoms.length; i < j; i++) {
                timings[i] = new ValueRange(current_atoms[i]);
            }
        }
        if ((timings == null) || (0 == timings.length)) {
            timings = new ValueRange[] {
                new ValueRange(250)
            };
        }
        if (arguments.containsKey("values")) {
            Atom[] current_atoms = arguments.get("values");
            values = new ValueRange[current_atoms.length];
            for (int i = 0, j = current_atoms.length; i < j; i++) {
                values[i] = new ValueRange(current_atoms[i]);
            }
        }
        if ((values == null) || (0 == values.length)) {
            if ((0 < arity) && (subscriber instanceof AttributeServer)) {
                AttributeServer attribute = (AttributeServer) subscriber;
                if (attribute.datatype instanceof BoundedDatatype) {
                    BoundedDatatype bounded_datatype =
                        (BoundedDatatype) attribute.datatype;
                    Double low = bounded_datatype.get_minimum();
                    Double high = bounded_datatype.get_maximum();
                    if ((low == null) || (high == null)) {
                        low = 0.;
                        high = 1.;
                    }
                    values = new ValueRange[] {
                        new ValueRange(low, high)
                    };
                } else if (attribute.datatype instanceof BooleanDatatype) {
                    values = new ValueRange[] {
                        new ValueRange(0), new ValueRange(1)
                    };
                }
            } else {
                values = new ValueRange[] {
                    new ValueRange(0)
                };
            }
        }
        PatternSubscription subscription =
            new PatternSubscription(arity, subscriber, message_name, timings, values,
                subscription_name);
        return subscription;
    }

    public double next_event_time = 0;
    public int current_timing_step = 0;
    public int current_value_step = 0;
    public final ValueRange[] timings;
    public final ValueRange[] values;
    public final int arity;

    public PatternSubscription(
        int arity,
        Server subscriber,
        String message_name,
        ValueRange[] timings,
        ValueRange[] values,
        String subscription_name) {
        super(subscriber, ClockEvent.class, null, message_name, null, subscription_name);
        this.arity = arity;
        this.timings = timings;
        this.values = values;
    }

    @Override
    public void handle_event(Event event) {
        ClockEvent clock_event = (ClockEvent) event;
        if (clock_event.current_time <= this.next_event_time) {
            return;
        }
        double previous_event_time = this.next_event_time;
        double timing = this.timings[this.current_timing_step].execute();
        Atom[] payload = new Atom[] {
            Atom.newAtom("bang")
        };
        if (0 < this.arity) {
            double[] values = new double[this.arity + 1];
            ValueRange value = this.values[this.current_value_step];
            for (int i = 0, j = this.arity; i < j; i++) {
                values[i] = value.execute();
            }
            values[this.arity] = timing;
            payload = Atom.newAtom(values);
        }
        this.next_event_time = previous_event_time + timing;
        this.current_timing_step = (this.current_timing_step + 1) % this.timings.length;
        if (0 < this.values.length) {
            this.current_value_step = (this.current_value_step + 1) % this.values.length;
        }
        this.subscriber.make_request(null, this.message_name, payload);
    }

    public void set_next_event_time(double next_event_time) {
        this.next_event_time = next_event_time;
    }

    @Override
    public Atom[] to_atoms() {
        Map<String, Atom[]> map = new HashMap<String, Atom[]>();
        if (0 < this.arguments.length) {
            map.put("args", this.arguments);
        }
        map.put("name", Atom.parse(this.subscription_name));
        map.put("message", Atom.parse(this.message_name));
        Atom[] timings = new Atom[this.timings.length];
        for (int i = 0, j = this.timings.length; i < j; i++) {
            timings[i] = this.timings[i].to_atom();
        }
        map.put("timings", timings);
        Atom[] values = new Atom[this.values.length];
        for (int i = 0, j = this.values.length; i < j; i++) {
            values[i] = this.values[i].to_atom();
        }
        map.put("values", values);
        map.put("type", Atom.parse("pattern"));
        return MaxIO.to_serialized_dict(map);
    }
}
