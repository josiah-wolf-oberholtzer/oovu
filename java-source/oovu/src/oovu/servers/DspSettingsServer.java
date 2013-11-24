package oovu.servers;

import java.util.Map;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.events.types.DspSettingsChangedEvent;
import oovu.messaging.Atoms;
import oovu.messaging.GetterMessageHandler;
import oovu.messaging.InfoGetterMessageHandler;
import oovu.messaging.Request;
import oovu.messaging.SetterMessageHandler;
import oovu.states.State;

import com.cycling74.max.Atom;

public class DspSettingsServer extends ModuleMemberServer {

    private class GetActiveMessageHandler extends GetterMessageHandler {

        public GetActiveMessageHandler(Server client) {
            super(client, "getactive");
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return true;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("active");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.get_is_active());
            return result;
        }
    }

    private class GetInputCountMessageHandler extends InfoGetterMessageHandler {

        public GetInputCountMessageHandler(Server client) {
            super(client, "getinputcount");
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("inputcount");
            result[0][1] =
                Atom.newAtom(DspSettingsServer.this.get_input_count());
            return result;
        }
    }

    private class GetLimitingMessageHandler extends GetterMessageHandler {

        public GetLimitingMessageHandler(Server client) {
            super(client, "getlimiting");
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return true;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("limiting");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.get_limiting());
            return result;
        }
    }

    private class GetOutputCountMessageHandler extends InfoGetterMessageHandler {

        public GetOutputCountMessageHandler(Server client) {
            super(client, "getoutputcount");
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("outputcount");
            result[0][1] =
                Atom.newAtom(DspSettingsServer.this.get_output_count());
            return result;
        }
    }

    private class GetSendCountMessageHandler extends GetterMessageHandler {

        public GetSendCountMessageHandler(Server client) {
            super(client, "getsendcount");
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return true;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("sendcount");
            result[0][1] =
                Atom.newAtom(DspSettingsServer.this.get_send_count());
            return result;
        }
    }

    private class GetVoiceCountMessageHandler extends GetterMessageHandler {

        public GetVoiceCountMessageHandler(Server client) {
            super(client, "getvoicecount");
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return true;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("voicecount");
            result[0][1] =
                Atom.newAtom(DspSettingsServer.this.get_voice_count());
            return result;
        }
    }

    private class SetActiveMessageHandler extends SetterMessageHandler {

        public SetActiveMessageHandler(Server client) {
            super(client, "active");
        }

        @Override
        public void call_after() {
            Request request =
                new Request(DspSettingsServer.this,
                    OscAddress.from_cache("./:getactive"), new Atom[0], false);
            DspSettingsServer.this.handle_request(request);
        }

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 < arguments.length) {
                boolean argument = arguments[0].toBoolean();
                DspSettingsServer.this.set_is_active(argument);
            }
            return null;
        }
    }

    private class SetLimitingMessageHandler extends SetterMessageHandler {

        public SetLimitingMessageHandler(Server client) {
            super(client, "limiting");
        }

        @Override
        public void call_after() {
            Request request =
                new Request(DspSettingsServer.this,
                    OscAddress.from_cache("./:getlimiting"), new Atom[0], false);
            DspSettingsServer.this.handle_request(request);
        }

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 < arguments.length) {
                boolean argument = arguments[0].toBoolean();
                DspSettingsServer.this.set_limiting(argument);
            }
            return null;
        }
    }

    private class SetSendCountMessageHandler extends SetterMessageHandler {

        public SetSendCountMessageHandler(Server client) {
            super(client, "sendcount");
        }

        @Override
        public void call_after() {
            Request request =
                new Request(DspSettingsServer.this,
                    OscAddress.from_cache("./:getsendcount"), new Atom[0],
                    false);
            DspSettingsServer.this.handle_request(request);
        }

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 < arguments.length) {
                int argument = arguments[0].toInt();
                DspSettingsServer.this.set_send_count(argument);
            }
            return null;
        }
    }

    private class SetVoiceCountMessageHandler extends SetterMessageHandler {

        public SetVoiceCountMessageHandler(Server client) {
            super(client, "voicecount");
        }

        @Override
        public void call_after() {
            Request voice_request =
                new Request(DspSettingsServer.this,
                    OscAddress.from_cache("./:getvoicecount"), new Atom[0],
                    false);
            Request input_request =
                new Request(DspSettingsServer.this,
                    OscAddress.from_cache("./:getinputcount"), new Atom[0],
                    false);
            Request output_request =
                new Request(DspSettingsServer.this,
                    OscAddress.from_cache("./:getoutputcount"), new Atom[0],
                    false);
            DspSettingsServer.this.handle_request(voice_request);
            DspSettingsServer.this.handle_request(input_request);
            DspSettingsServer.this.handle_request(output_request);
        }

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 < arguments.length) {
                int argument = arguments[0].toInt();
                DspSettingsServer.this.set_voice_count(argument);
            }
            return null;
        }
    }

    private boolean is_active = false;
    private Integer input_count = null;
    private Integer output_count = null;
    private Integer send_count = 1;
    private Integer voice_count = 1;
    private boolean limiting = true;

    public DspSettingsServer(ModuleServer module_server) {
        super(module_server);
        this.add_message_handler(new GetActiveMessageHandler(this));
        this.add_message_handler(new GetInputCountMessageHandler(this));
        this.add_message_handler(new GetLimitingMessageHandler(this));
        this.add_message_handler(new GetOutputCountMessageHandler(this));
        this.add_message_handler(new GetSendCountMessageHandler(this));
        this.add_message_handler(new GetVoiceCountMessageHandler(this));
        this.add_message_handler(new SetActiveMessageHandler(this));
        this.add_message_handler(new SetLimitingMessageHandler(this));
        this.add_message_handler(new SetSendCountMessageHandler(this));
        this.add_message_handler(new SetVoiceCountMessageHandler(this));
    }

    public void configure(Atom[] arguments) {
        if (this.is_configured) {
            return;
        }
        Map<String, Atom[]> argument_map = Atoms.to_map(arguments);
        if (argument_map.containsKey("inputs")) {
            int input_count = argument_map.get("inputs")[0].getInt();
            if (input_count < 0) {
                input_count = 0;
            } else if (8 < input_count) {
                input_count = 8;
            }
            this.input_count = input_count;
        }
        if (argument_map.containsKey("outputs")) {
            int output_count = argument_map.get("outputs")[0].getInt();
            if (output_count < 0) {
                output_count = 0;
            } else if (8 < output_count) {
                output_count = 8;
            }
            this.output_count = output_count;
        }
        this.is_configured = true;
        Environment.event_service.publish(new DspSettingsChangedEvent(this));
    }

    public int get_input_count() {
        if (this.input_count_is_static()) {
            return this.input_count;
        }
        return this.voice_count;
    }

    public boolean get_is_active() {
        return this.is_active;
    }

    public boolean get_limiting() {
        return this.limiting;
    }

    public int get_output_count() {
        if (this.output_count_is_static()) {
            return this.output_count;
        }
        return this.voice_count;
    }

    public int get_send_count() {
        return this.send_count;
    }

    @Override
    public State get_state() {
        return null;
    }

    public int get_voice_count() {
        if (this.input_count_is_static()) {
            if (0 == this.get_input_count()) {
                return 1;
            }
            return this.input_count;
        }
        return this.voice_count;
    }

    public boolean input_count_is_static() {
        if (this.input_count != null) {
            return true;
        }
        return false;
    }

    public boolean module_has_dsp_receives() {
        ModuleServer module_server = (ModuleServer) this.get_parent_server();
        for (Server server : module_server.child_servers) {
            if (server instanceof DspReceiveServer) {
                return true;
            }
        }
        return false;
    }

    public boolean module_has_dsp_sends() {
        ModuleServer module_server = (ModuleServer) this.get_parent_server();
        for (Server server : module_server.child_servers) {
            if (server instanceof DspSendServer) {
                return true;
            }
        }
        return false;
    }

    public boolean output_count_is_static() {
        if (this.output_count != null) {
            return true;
        }
        return false;
    }

    public void set_is_active(boolean is_active) {
        this.is_active = is_active;
    }

    public void set_limiting(boolean limiting) {
        this.limiting = limiting;
    }

    public void set_send_count(int send_count) {
        if ((0 < send_count) && (send_count <= 8)) {
            this.send_count = send_count;
        }
    }

    public void set_voice_count(int voice_count) {
        if ((0 < voice_count) && (voice_count <= 8)) {
            if (!this.input_count_is_static()) {
                this.voice_count = voice_count;
            }
        }
    }
}
