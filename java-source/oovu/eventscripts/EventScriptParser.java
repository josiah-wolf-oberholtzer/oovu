package oovu.eventscripts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import oovu.addresses.Environment;
import oovu.states.State;
import oovu.states.StateComponent;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;

public class EventScriptParser {
    public Map<String, State> parse_file(String filename) {
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
        return events;
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
}
