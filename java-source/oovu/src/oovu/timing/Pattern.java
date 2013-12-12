package oovu.timing;

import java.util.ArrayList;
import java.util.Map;

import oovu.addresses.OscAddress;
import oovu.datatypes.BooleanDatatype;
import oovu.datatypes.BoundedDatatype;
import oovu.events.Event;
import oovu.events.Subscription;
import oovu.events.types.ClockEvent;
import oovu.messaging.Atoms;
import oovu.messaging.MessageHandler;
import oovu.messaging.Request;
import oovu.servers.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class Pattern extends Subscription {
    public static Pattern from_atoms(Server client, Atom[] atoms) {
        return Pattern.from_mapping(client, Atoms.to_map(atoms));
    }

    public static Pattern from_mapping(
        Server client,
        Map<String, Atom[]> arguments) {
        String message = null;
        String name = null;
        ValueRange[] timings = null;
        ValueRange[] values = null;
        int arity = 0;
        if (arguments.containsKey("message")) {
            message = arguments.get("message")[0].toString();
        } else {
            message = "value";
        }
        if (arguments.containsKey("name")) {
            name = arguments.get("name")[0].toString();
        } else {
            name = message;
        }
        MessageHandler message_handler = client.get_message_handler(message);
        if ((message_handler == null)
            || message.equals(message_handler.get_getter_name())) {
            return null;
        } else if (!message_handler.get_is_binding_relevant()) {
            return null;
        }
        arity = message_handler.get_arity();
        if (arguments.containsKey("timings")) {
            Atom[] atoms = arguments.get("timings");
            timings = new ValueRange[atoms.length];
            for (int i = 0, j = atoms.length; i < j; i++) {
                timings[i] = new ValueRange(atoms[i]);
            }
        }
        if ((timings == null) || (0 == timings.length)) {
            timings = new ValueRange[] {
                new ValueRange(250)
            };
        }
        if (arguments.containsKey("values")) {
            Atom[] atoms = arguments.get("values");
            values = new ValueRange[atoms.length];
            for (int i = 0, j = atoms.length; i < j; i++) {
                values[i] = new ValueRange(atoms[i]);
            }
        }
        if (((values == null) || (0 == values.length)) && (0 < arity)) {
            if (client instanceof AttributeServer) {
                AttributeServer attribute = (AttributeServer) client;
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
        } else {
            values = new ValueRange[0];
        }
        return new Pattern(arity, client, message, name, timings, values);
    }

    public double next_event_time = 0;
    public int current_timing_step = 0;
    public int current_value_step = 0;
    public final String message;
    public final ValueRange[] timings;
    public final ValueRange[] values;
    public final int arity;
    public final String name;

    public Pattern(
        int arity,
        Server subscriber,
        String message,
        String name,
        ValueRange[] timings,
        ValueRange[] values) {
        super(subscriber, ClockEvent.class, null);
        this.arity = arity;
        this.message = message;
        this.name = name;
        this.timings = timings;
        this.values = values;
    }

    @Override
    public void handle_event(Event event) {
        ClockEvent clock_event = (ClockEvent) event;
        if (clock_event.current_time < this.next_event_time) {
            return;
        }
        double previous_event_time = this.next_event_time;
        double timing = this.timings[this.current_timing_step].execute();
        Atom[] payload = new Atom[0];
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
        this.current_timing_step =
            (this.current_timing_step + 1) % this.timings.length;
        if (0 < this.values.length) {
            this.current_value_step =
                (this.current_value_step + 1) % this.values.length;
        }
        OscAddress osc_address = OscAddress.from_cache(":" + this.message);
        Request request =
            new Request(this.subscriber, osc_address, payload, false);
        this.subscriber.handle_request(request);
    }

    public void set_next_event_time(double next_event_time) {
        this.next_event_time = next_event_time;
    }

    public Atom[] to_atoms() {
        ArrayList<Atom> result = new ArrayList<Atom>();
        result.add(Atom.newAtom(":message"));
        result.add(Atom.newAtom(this.message));
        result.add(Atom.newAtom(":timings"));
        for (ValueRange value_range : this.timings) {
            result.add(value_range.to_atom());
        }
        result.add(Atom.newAtom(":values"));
        for (ValueRange value_range : this.values) {
            result.add(value_range.to_atom());
        }
        return result.toArray(new Atom[0]);
    }
}
