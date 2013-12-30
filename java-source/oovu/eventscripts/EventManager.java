package oovu.eventscripts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;
import oovu.messaging.Request;
import oovu.servers.RootServer;
import oovu.servers.Server;
import oovu.states.State;
import oovu.states.StateComponent;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;

public class EventManager {
    private String current_event_name = null;
    private Map<String, State> events = null;

    public void execute_state(State state, RootServer root_server) {
        for (Atom[] atoms : state.toAtoms()) {
            String state_address_string = atoms[0].getString();
            OscAddress state_address = OscAddress.from_cache(state_address_string);
            Atom[] state_arguments = Atom.removeFirst(atoms);
            Request request =
                new Request(root_server, state_address, state_arguments, true);
            Set<OscAddressNode> osc_address_nodes =
                root_server.get_osc_address_node().search(request.destination);
            for (OscAddressNode osc_address_node : osc_address_nodes) {
                Server server = osc_address_node.get_server();
                if (server != null) {
                    server.handle_request(request);
                }
            }
        }
    }

    public Integer get_current_event_index() {
        if ((this.events == null) || (this.current_event_name == null)) {
            return -1;
        }
        List<String> event_names = this.get_event_names();
        return event_names.indexOf(this.current_event_name);
    }

    public String get_current_event_name() {
        return this.current_event_name;
    }

    public String get_event_name_by_index(int input) {
        if (this.events == null) {
            return null;
        }
        if (input < 0) {
            return null;
        }
        if (this.events.size() <= input) {
            return null;
        }
        return this.get_event_names().get(input);
    }

    public String get_event_name_by_string(String input) {
        if (input == null) {
            return null;
        }
        if (this.events == null) {
            return null;
        }
        if (!this.get_event_names().contains(input)) {
            return null;
        }
        return input;
    }

    public List<String> get_event_names() {
        return Arrays.asList(this.events.keySet().toArray(new String[0]));
    }

    public Integer get_next_event_index() {
        if (this.events == null) {
            return null;
        } else if (this.current_event_name == null) {
            return 0;
        }
        Integer index = this.get_current_event_index() + 1;
        if (this.events.size() <= index) {
            index -= 1;
        }
        return index;
    }

    public String get_next_event_name() {
        Integer index = this.get_next_event_index();
        if (index == null) {
            return null;
        }
        return this.get_event_names().get(index);
    }

    public Integer get_previous_event_index() {
        if (this.events == null) {
            return null;
        } else if (this.current_event_name == null) {
            return this.events.size() - 1;
        }
        Integer index = this.get_current_event_index() - 1;
        if (index < 0) {
            index = 0;
        }
        return index;
    }

    public String get_previous_event_name() {
        Integer index = this.get_previous_event_index();
        if (index == null) {
            return null;
        }
        return this.get_event_names().get(index);
    }

    public void parse_file(String filename) {
        Map<String, State> events = new LinkedHashMap<String, State>();
        String event_name = null;
        ArrayList<StateComponent> event_components = new ArrayList<StateComponent>();
        for (String line : this.read_file(filename)) {
            line = line.trim();
            if (0 == line.length()) {
                continue;
            } else if (line.charAt(0) == '#') {
                continue;
            }
            Atom[] atoms = Atom.parse(line);
            if (atoms.length == 0) {
                continue;
            }
            if ((1 < atoms.length) && (atoms[0].getString().equals("CUE"))) {
                if (event_name != null) {
                    events.put(event_name, new StateComponentAggregate(event_name,
                        event_components.toArray(new State[0])));
                }
                event_components.clear();
                event_name = Atom.toOneString(Atom.removeFirst(atoms));
            } else {
                String address = atoms[0].getString();
                Atom[] arguments = Atom.removeFirst(atoms);
                event_components.add(new StateComponent(address, arguments));
            }
        }
        if (0 < event_components.size()) {
            events.put(event_name, new StateComponentAggregate(event_name,
                event_components.toArray(new State[0])));
        }
        this.events = events;
        this.current_event_name = null;
    }

    private String[] read_file(String filename) {
        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader buffered_reader;
        try {
            buffered_reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = buffered_reader.readLine()) != null) {
                lines.add(line);
            }
            buffered_reader.close();
        } catch (FileNotFoundException e) {
            Environment.log(e);
        } catch (IOException e) {
            Environment.log(e);
        }
        return lines.toArray(new String[0]);
    }

    public State set_current_event_by_index(int index) {
        String event_name = this.get_event_name_by_index(index);
        if (event_name == null) {
            return null;
        }
        this.current_event_name = event_name;
        return this.events.get(event_name);
    }

    public State set_current_event_by_name(String name) {
        String event_name = this.get_event_name_by_string(name);
        if (event_name == null) {
            return null;
        }
        this.current_event_name = event_name;
        return this.events.get(event_name);
    }
}
