package oovu.messaging;

import oovu.servers.Server;

import com.cycling74.max.Atom;

public class MessageHandler {
    public final BooleanMessageHandlerCallback is_meta_relevant_callback;
    public final BooleanMessageHandlerCallback is_rampable_callback;
    public final Getter getter;
    public final Integer arity;
    public final IntegerMessageHandlerCallback arity_callback;
    public final Server client;
    public final Setter callback;
    public final Setter setter;
    public final String name;
    public final boolean is_binding_relevant;
    public final boolean is_meta_relevant;
    public final boolean is_rampable;
    public final boolean is_state_relevant;

    public MessageHandler(
        Server client,
        Integer arity,
        IntegerMessageHandlerCallback arity_callback,
        Setter callback,
        Getter getter,
        boolean is_binding_relevant,
        boolean is_meta_relevant,
        BooleanMessageHandlerCallback is_meta_relevant_callback,
        boolean is_rampable,
        BooleanMessageHandlerCallback is_rampable_callback,
        boolean is_state_relevant,
        String name,
        Setter setter) {
        this.arity = arity;
        this.arity_callback = arity_callback;
        this.callback = callback;
        this.client = client;
        this.getter = getter;
        this.is_binding_relevant = is_binding_relevant;
        this.is_meta_relevant = is_meta_relevant;
        this.is_meta_relevant_callback = is_meta_relevant_callback;
        this.is_rampable = is_rampable;
        this.is_rampable_callback = is_rampable_callback;
        this.is_state_relevant = is_state_relevant;
        this.name = name;
        this.setter = setter;
    }

    public String get_getter_name() {
        return "get" + this.name;
    }

    public String get_setter_name() {
        return this.name;
    }

    public Atom[][] handle_message(String message) {
        return this.handle_message(message, null);
    }

    public Atom[][] handle_message(String message, Atom[] arguments) {
        Atom[][] result = null;
        if (message.equals(this.get_getter_name())) {
            result = this.getter.execute(this, arguments);
        } else if (message.equals(this.get_setter_name())) {
            result = this.setter.execute(this, arguments);
        }
        return result;
    }
}
