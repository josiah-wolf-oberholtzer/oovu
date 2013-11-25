package oovu.messaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.cycling74.max.Atom;

public class Atoms {
    public static Atom[][] to_atoms(String message) {
        if (message == null) {
            return null;
        }
        Atom[][] result = new Atom[1][1];
        result[0][0] = Atom.newAtom(message);
        return result;
    }

    public static Atom[][] to_atoms(String message, Atom atom) {
        if (atom == null) {
            return Atoms.to_atoms(message);
        }
        return Atoms.to_atoms(message, new Atom[] {
            atom
        });
    }

    public static Atom[][] to_atoms(String message, Atom[] atoms) {
        Atom[][] result = new Atom[1][];
        if (atoms == null) {
            return Atoms.to_atoms(message);
        }
        result[0] = Atom.newAtom(message, atoms);
        return result;
    }

    public static Atom[][] to_atoms(String message, Double argument) {
        if (argument == null) {
            return Atoms.to_atoms(message);
        }
        return Atoms.to_atoms(message, Atom.newAtom(argument));
    }

    public static Atom[][] to_atoms(String message, Integer argument) {
        if (argument == null) {
            return Atoms.to_atoms(message);
        }
        return Atoms.to_atoms(message, Atom.newAtom(argument));
    }

    public static Atom[][] to_atoms(String message, String argument) {
        if (argument == null) {
            return Atoms.to_atoms(message);
        }
        return Atoms.to_atoms(message, Atom.newAtom(argument));
    }

    public static Atom[][] to_atoms(String message, String[] arguments) {
        if (arguments == null) {
            return Atoms.to_atoms(message);
        }
        return Atoms.to_atoms(message, Atom.newAtom(arguments));
    }

    public static Map<String, Atom[]> to_map(Atom[] arguments) {
        HashMap<String, Atom[]> argument_map = new HashMap<String, Atom[]>();
        String current_key = null;
        ArrayList<Atom> current_list = new ArrayList<Atom>();
        for (Atom argument : arguments) {
            if ((current_key == null) && (argument.toString().charAt(0) != ':')) {
                continue;
            }
            if (argument.toString().charAt(0) == ':') {
                if ((current_key != null) && (0 < current_list.size())) {
                    argument_map.put(current_key,
                        current_list.toArray(new Atom[current_list.size()]));
                    current_list.clear();
                }
                current_key = argument.toString().substring(1);
            } else {
                current_list.add(argument);
            }
        }
        if (0 < current_list.size()) {
            argument_map.put(current_key,
                current_list.toArray(new Atom[current_list.size()]));
        }
        return Collections.unmodifiableMap(argument_map);
    }
}
