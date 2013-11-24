package oovu.messaging;

import oovu.servers.Server;

import com.cycling74.max.Callback;

public class MessageHandlerBuilder {

    private Callback callback;
    private Getter getter;
    private Integer arity;
    private Setter setter;
    private String name;
    private boolean is_binding_relevant;
    private boolean is_meta_relevant;
    private boolean is_rampable;
    private boolean is_state_relevant;

    public MessageHandlerBuilder(String name) {
        this.arity = null;
        this.callback = null;
        this.getter = null;
        this.is_binding_relevant = false;
        this.is_meta_relevant = false;
        this.is_rampable = false;
        this.is_state_relevant = false;
        this.name = name;
        this.setter = null;
    }

    public BuiltMessageHandler build(Server client) {
        return new BuiltMessageHandler(this.callback, this.getter, this.arity,
            client, this.setter, this.name, this.is_binding_relevant,
            this.is_meta_relevant, this.is_rampable, this.is_state_relevant);
    }

    public MessageHandlerBuilder with_arity(Integer arity) {
        this.arity = arity;
        return this;
    }

    public MessageHandlerBuilder with_is_binding_relevant(
        boolean is_binding_relevant) {
        this.is_binding_relevant = is_binding_relevant;
        return this;
    }

    public
        MessageHandlerBuilder
        with_is_meta_relevant(boolean is_meta_relevant) {
        this.is_meta_relevant = is_meta_relevant;
        return this;
    }
}
