package oovu.messaging;

import oovu.servers.members.AttributeServer;

abstract public class DatatypeMessageHandler extends MessageHandler {

    public final AttributeServer attribute_server;

    public DatatypeMessageHandler(AttributeServer attribute_server) {
        this.attribute_server = attribute_server;
    }
}
