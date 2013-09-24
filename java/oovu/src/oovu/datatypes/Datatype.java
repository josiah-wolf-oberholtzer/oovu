package oovu.datatypes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import oovu.messaging.MessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public abstract class Datatype {

    private class GetDatatypeMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getdatatype";
        }

        @Override
        public Atom[][] run(Server context, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            AttributeServer node = (AttributeServer) context;
            String datatype_name = node.datatype.getClass().getSimpleName()
                .toLowerCase().replace("datatype", "");
            result[0] = Atom
                .newAtom(new String[] { "datatype", datatype_name });
            return result;
        }
    }

    public static Class<?> from_label(String label) {
        if (Datatype.datatype_classes_by_label.containsKey(label)) {
            return Datatype.datatype_classes_by_label.get(label);
        }
        MaxObject.ouch("Bad datatype label: " + label + "\n");
        return GenericDatatype.class;
    }

    protected Atom[] value = new Atom[0];

    protected AttributeServer client = null;

    private static final Map<String, Class<?>> datatype_classes_by_label;

    static {
        Map<String, Class<?>> map = new HashMap<String, Class<?>>();
        map.put("boolean", BooleanDatatype.class);
        map.put("decimalarray", DecimalArrayDatatype.class);
        map.put("decimal", DecimalDatatype.class);
        map.put("filesystempath", FilesystemPathDatatype.class);
        map.put("generic", GenericDatatype.class);
        map.put("integerarray", IntegerArrayDatatype.class);
        map.put("integer", IntegerDatatype.class);
        map.put("option", OptionDatatype.class);
        map.put("oscaddress", OscAddressDatatype.class);
        map.put("pull", PullDatatype.class);
        map.put("push", PushDatatype.class);
        map.put("range", RangeDatatype.class);
        map.put("string", StringDatatype.class);
        map.put(null, GenericDatatype.class);
        datatype_classes_by_label = Collections.unmodifiableMap(map);
    }

    public Datatype(AttributeServer client, Map<String, Atom[]> argument_map) {
        this.client = client;
        if (this.client != null) {
            this.client
                .add_message_handler(new GetDatatypeMessageHandler());
        }
    }

    public String get_datatype() {
        return this.getClass().getSimpleName().toLowerCase()
            .replace("datatype", "");
    }

    abstract public Atom[] get_default();

    public Atom[] get_value() {
        return this.value;
    }

    public abstract Atom[] process_input(Atom[] input);

    public void set_value(Atom[] value) {
        this.value = this.process_input(value);
    }

}
