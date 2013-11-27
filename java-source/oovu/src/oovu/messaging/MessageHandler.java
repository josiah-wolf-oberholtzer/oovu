package oovu.messaging;

import oovu.servers.Server;

import com.cycling74.max.Atom;

public class MessageHandler {
    private final BooleanMessageHandlerCallback is_meta_relevant_callback;
    private final BooleanMessageHandlerCallback is_rampable_callback;
    public final MessageHandlerCallback getter;
    private final Integer arity;
    private final IntegerMessageHandlerCallback arity_callback;
    public final Server client;
    public final Setter callback;
    public final Setter setter;
    private final String name;
    private final boolean is_binding_relevant;
    private final boolean is_meta_relevant;
    private final boolean is_rampable;
    private final boolean is_state_relevant;

    public MessageHandler(
        Server client,
        Integer arity,
        IntegerMessageHandlerCallback arity_callback,
        Setter callback,
        MessageHandlerCallback getter,
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

    public Integer get_arity() {
        if (this.arity_callback != null) {
            return this.arity_callback.execute(this);
        }
        return this.arity;
    }

    public String get_getter_name() {
        return "get" + this.name;
    }

    public boolean get_is_binding_relevant() {
        return this.is_binding_relevant;
    }

    public boolean get_is_meta_relevant() {
        if (this.is_meta_relevant_callback != null) {
            return this.is_meta_relevant_callback.execute(this);
        }
        return this.is_meta_relevant;
    }

    public boolean get_is_rampable() {
        if (this.is_rampable_callback != null) {
            return this.is_rampable_callback.execute(this);
        }
        return this.is_rampable;
    }

    public boolean get_is_state_relevant() {
        return this.is_state_relevant;
    }

    public String get_name() {
        return this.name;
    }

    public Atom[][] handle_message(String message) {
        return this.handle_message(message, null);
    }

    public Atom[][] handle_message(String message, Atom[] arguments) {
        Atom[][] result = null;
        if (message.equals(this.get_getter_name())) {
            result = this.getter.execute(this, arguments);
        } else if (message.equals(this.get_name())) {
            result = this.setter.execute(this, arguments);
        }
        return result;
    }
}
