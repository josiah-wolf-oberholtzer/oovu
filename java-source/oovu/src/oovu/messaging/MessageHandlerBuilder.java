package oovu.messaging;

import oovu.servers.Server;

public class MessageHandlerBuilder {
    private MessageHandlerCallback callback;
    private MessageHandlerCallback getter;
    private Integer arity;
    private MessageHandlerCallback MessageHandlerCallback;
    private String name;
    private boolean is_binding_relevant;
    private boolean is_meta_relevant;
    private boolean is_rampable;
    private boolean is_state_relevant;
    private BooleanMessageHandlerCallback is_meta_relevant_callback;
    private BooleanMessageHandlerCallback is_rampable_callback;
    private IntegerMessageHandlerCallback arity_callback;

    public MessageHandlerBuilder(String name) {
        this.arity = null;
        this.callback = null;
        this.getter = null;
        this.is_binding_relevant = false;
        this.is_meta_relevant = false;
        this.is_meta_relevant_callback = null;
        this.is_rampable = false;
        this.is_state_relevant = false;
        this.name = name;
        this.MessageHandlerCallback = null;
    }

    public MessageHandler build(Server client) {
        return new MessageHandler(client, this.arity, this.arity_callback,
            this.callback, this.getter, this.is_binding_relevant,
            this.is_meta_relevant, this.is_meta_relevant_callback,
            this.is_rampable, this.is_rampable_callback,
            this.is_state_relevant, this.name, this.MessageHandlerCallback);
    }

    public MessageHandlerBuilder with_arity(Integer arity) {
        this.arity = arity;
        return this;
    }

    public MessageHandlerBuilder with_arity_callback(
        IntegerMessageHandlerCallback arity_callback) {
        this.arity_callback = arity_callback;
        return this;
    }

    public MessageHandlerBuilder with_callback(MessageHandlerCallback callback) {
        this.callback = callback;
        return this;
    }

    public MessageHandlerBuilder with_getter(MessageHandlerCallback getter) {
        this.getter = getter;
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

    public MessageHandlerBuilder with_is_meta_relevant_callback(
        BooleanMessageHandlerCallback is_meta_relevant_callback) {
        this.is_meta_relevant_callback = is_meta_relevant_callback;
        return this;
    }

    public MessageHandlerBuilder with_is_rampable(boolean is_rampable) {
        this.is_rampable = is_rampable;
        return this;
    }

    public MessageHandlerBuilder with_is_rampable_callback(
        BooleanMessageHandlerCallback is_rampable_callback) {
        this.is_rampable_callback = is_rampable_callback;
        return this;
    }

    public MessageHandlerBuilder with_is_state_relevant(
        boolean is_state_relevant) {
        this.is_state_relevant = is_state_relevant;
        return this;
    }

    public MessageHandlerBuilder with_setter(
        MessageHandlerCallback MessageHandlerCallback) {
        this.MessageHandlerCallback = MessageHandlerCallback;
        return this;
    }
}
