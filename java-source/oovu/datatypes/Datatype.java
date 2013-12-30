package oovu.datatypes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public abstract class Datatype {
    public static Class<?> from_label(String label) {
        if (Datatype.datatype_classes_by_label.containsKey(label)) {
            return Datatype.datatype_classes_by_label.get(label);
        }
        MaxObject.ouch("Bad datatype label: " + label + "\n");
        return GenericDatatype.class;
    }

    protected Atom[] value;
    public final AttributeServer client;
    private static final Map<String, Class<?>> datatype_classes_by_label;
    static {
        Map<String, Class<?>> map = new HashMap<String, Class<?>>();
        map.put("audiosend", AudioSendDatatype.class);
        map.put("boolean", BooleanDatatype.class);
        map.put("decimalarray", DecimalArrayDatatype.class);
        map.put("decimal", DecimalDatatype.class);
        map.put("filesystempath", FilesystemPathDatatype.class);
        map.put("generic", GenericDatatype.class);
        map.put("integerarray", IntegerArrayDatatype.class);
        map.put("integer", IntegerDatatype.class);
        map.put("option", OptionDatatype.class);
        map.put("oscaddress", OscAddressDatatype.class);
        map.put("range", RangeDatatype.class);
        map.put("string", StringDatatype.class);
        map.put("trigger", TriggerDatatype.class);
        map.put(null, GenericDatatype.class);
        datatype_classes_by_label = Collections.unmodifiableMap(map);
    }

    public Datatype(AttributeServer client, Map<String, Atom[]> argument_map) {
        this.client = client;
        this.initialize_prerequisites(argument_map);
        this.initialize_default_value(argument_map);
        if (this.client != null) {
            this.configure_datatype_message_handler();
        }
    }

    public void cleanup_resources() {
    }

    private void configure_datatype_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("datatype");
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                AttributeServer server = (AttributeServer) built_message_handler.client;
                return MaxIO.to_atoms(built_message_handler.get_name(), server.datatype
                    .getClass().getSimpleName().toLowerCase().replace("datatype", ""));
            }
        });
        this.client.add_message_handler(builder.build(this.client));
    }

    abstract public Integer get_arity();

    public String get_datatype() {
        return this.getClass().getSimpleName().toLowerCase().replace("datatype", "");
    }

    abstract public Atom[] get_default();

    public Atom[] get_value() {
        return this.value;
    }

    public void initialize_default_value(Map<String, Atom[]> argument_map) {
        Atom[] default_value = null;
        if (argument_map.containsKey("default")) {
            default_value = argument_map.get("default");
        } else {
            default_value = this.get_default();
        }
        this.set_value(default_value);
    }

    abstract protected void initialize_prerequisites(Map<String, Atom[]> argument_map);

    abstract public boolean is_binding_relevant();

    abstract public boolean is_rampable();

    public abstract Atom[] process_input(Atom[] input);

    public void set_value(Atom[] value) {
        this.value = this.process_input(value);
    }
}
