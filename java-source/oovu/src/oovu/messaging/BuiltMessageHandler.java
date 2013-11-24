package oovu.messaging;

import oovu.servers.Server;

import com.cycling74.max.Atom;
import com.cycling74.max.Callback;

public class BuiltMessageHandler {

    public final Callback callback;
    public final Getter getter;
    public final Integer arity;
    public final Server client;
    public final Setter setter;
    public final String name;
    public final boolean is_binding_relevant;
    public final boolean is_meta_relevant;
    public final boolean is_rampable;
    public final boolean is_state_relevant;

    public BuiltMessageHandler(Callback callback, Getter getter, Integer arity,
        Server client, Setter setter, String name, boolean is_binding_relevant,
        boolean is_meta_relevant, boolean is_rampable, boolean is_state_relevant) {
        this.callback = callback;
        this.getter = getter;
        this.arity = arity;
        this.client = client;
        this.setter = setter;
        this.name = name;
        this.is_binding_relevant = is_binding_relevant;
        this.is_meta_relevant = is_meta_relevant;
        this.is_rampable = is_rampable;
        this.is_state_relevant = is_state_relevant;
    }

    public String get_getter_name() {
        return "get" + this.name;
    }

    public String get_setter_name() {
        return this.name;
    }

    public Atom[][] handle_message(
        String message,
        Atom[] arguments,
        boolean callback) {
        Atom[][] result = null;
        if (message.equals(this.get_getter_name())) {
            result = this.getter.execute(arguments);
        } else if (message.equals(this.get_setter_name())) {
            this.setter.execute(arguments);
        }
        if (callback && (this.callback != null)) {
            this.callback.execute();
        }
        return result;
    }
}
