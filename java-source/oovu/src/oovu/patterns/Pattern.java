package oovu.patterns;

import java.util.ArrayList;
import java.util.Map;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.datatypes.BooleanDatatype;
import oovu.datatypes.BoundedDatatype;
import oovu.messaging.MessageHandler;
import oovu.messaging.Request;
import oovu.servers.AttributeServer;
import oovu.servers.Server;
import oovu.timing.ClockWatcher;

import com.cycling74.max.Atom;

public class Pattern extends ClockWatcher {

    public static Pattern from_atoms(Server client, Atom[] atoms) {
        return Pattern.from_mapping(client,
            Server.process_atom_arguments(atoms));
    }

    public static Pattern from_mapping(
        Server client,
        Map<String, Atom[]> arguments) {
        String message = null;
        ValueRange[] timings = null;
        ValueRange[] values = null;
        int arity = 0;
        if (arguments.containsKey("message")) {
            message = arguments.get("message")[0].toString();
        } else {
            message = "value";
        }
        MessageHandler message_handler = client.get_message_handler(message);
        if (message_handler == null) {
            return null;
        } else if (!message_handler.is_binding_relevant()) {
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
                    double low = bounded_datatype.get_minimum();
                    double high = bounded_datatype.get_maximum();
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
        return new Pattern(client, message, timings, values, arity);
    }

    public double next_event_time = 0;
    public int current_timing_step = 0;
    public int current_value_step = 0;
    public final Server client;
    public final String message;
    public final ValueRange[] timings;
    public final ValueRange[] values;
    public final int arity;

    public Pattern(Server client, String message, ValueRange[] timings,
        ValueRange[] values, int arity) {
        this.client = client;
        this.message = message;
        this.timings = timings;
        this.values = values;
        this.arity = arity;
    }

    @Override
    public void execute(double current_time) {
        synchronized (ClockWatcher.class) {
            if (current_time < this.next_event_time) {
                return;
            }
            double previous_event_time = this.next_event_time;
            double timing = this.timings[this.current_timing_step].execute();
            Atom[] payload = null;
            Environment.log("ARITY: " + this.arity);
            if (0 < this.arity) {
                double[] values = new double[this.arity + 1];
                ValueRange value = this.values[this.current_value_step];
                for (int i = 0, j = this.arity; i < j; i++) {
                    values[i] = value.execute();
                    Environment.log(i + ": " + values[i]);
                }
                values[this.arity] = timing;
                payload = Atom.newAtom(values);
                Environment.log(Atom.toOneString(payload));
            }
            this.next_event_time = previous_event_time + timing;
            this.current_timing_step =
                (this.current_timing_step + 1) % this.timings.length;
            this.current_value_step =
                (this.current_value_step + 1) % this.values.length;
            OscAddress osc_address = OscAddress.from_cache(":" + this.message);
            Request request =
                new Request(this.client, osc_address, payload, false);
            Environment.log("Y: " + request.toString());
            this.client.handle_request(request);
        }
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
