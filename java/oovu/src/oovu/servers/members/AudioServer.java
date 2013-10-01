package oovu.servers.members;

import java.util.Map;

import oovu.messaging.MessageHandler;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.servers.ModuleMemberServer;
import oovu.servers.ModuleServer;

import com.cycling74.max.Atom;

abstract public class AudioServer extends ModuleMemberServer {

    private class GetSendIdMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getsendid";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("sendid");
            result[0][1] = Atom.newAtom(AudioServer.this.get_hash_id());
            return result;
        }
    }

    public AudioServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
        this.add_message_handler(new GetSendIdMessageHandler());
    }

    public int get_hash_id() {
        return System.identityHashCode(this);
    }

    @Override
    public void handle_request(Request request) {
    }

    @Override
    public void handle_response(Response response) {
    }
}
