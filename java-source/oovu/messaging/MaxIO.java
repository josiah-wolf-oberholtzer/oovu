package oovu.messaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.cycling74.max.Atom;

public class MaxIO {
    public static Map<String, Atom[]> from_serialized_dict(Atom[] atoms) {
        HashMap<String, Atom[]> map = new HashMap<String, Atom[]>();
        if ((atoms == null) || (atoms.length == 0)) {
            return map;
        }
        String current_key = null;
        ArrayList<Atom> collected_atoms = new ArrayList<Atom>();
        Atom previous_atom = atoms[0];
        if (previous_atom.isString() && previous_atom.getString().endsWith(":")) {
            current_key = previous_atom.getString();
            current_key = current_key.substring(0, current_key.length() - 1);
            previous_atom = null;
        }
        for (Atom current_atom : Atom.removeFirst(atoms)) {
            if (current_atom.isString()) {
                if (current_atom.getString().equals(":")) {
                    if ((current_key != null) && (0 < collected_atoms.size())) {
                        map.put(current_key, collected_atoms.toArray(new Atom[0]));
                    }
                    collected_atoms.clear();
                    current_key = previous_atom.getString();
                    previous_atom = null;
                } else if (current_atom.getString().endsWith(":")) {
                    if (previous_atom != null) {
                        collected_atoms.add(previous_atom);
                    }
                    if ((current_key != null) && (0 < collected_atoms.size())) {
                        map.put(current_key, collected_atoms.toArray(new Atom[0]));
                    }
                    collected_atoms.clear();
                    previous_atom = null;
                    current_key = current_atom.getString();
                    current_key = current_key.substring(0, current_key.length() - 1);
                } else {
                    if (previous_atom != null) {
                        collected_atoms.add(previous_atom);
                    }
                    if (current_atom.getString().equals("*")) {
                        previous_atom = null;
                    } else {
                        previous_atom = current_atom;
                    }
                }
            } else {
                if (previous_atom != null) {
                    collected_atoms.add(previous_atom);
                }
                previous_atom = current_atom;
            }
        }
        if (previous_atom != null) {
            collected_atoms.add(previous_atom);
        }
        if ((current_key != null) && (0 < collected_atoms.size())) {
            map.put(current_key, collected_atoms.toArray(new Atom[0]));
        }
        return map;
    }

    // public static Map<String, Atom[]> from_serialized_dict(Atom[] atoms) {
    // HashMap<String, Atom[]> map = new HashMap<String, Atom[]>();
    // String current_key = null;
    // ArrayList<Atom> current_atoms = new ArrayList<Atom>();
    // for (Atom argument : atoms) {
    // if ((current_key == null) && (!argument.toString().endsWith(":"))) {
    // continue;
    // }
    // if (argument.toString().endsWith(":")) {
    // if ((current_key != null) && (0 < current_atoms.size())) {
    // map.put(current_key,
    // current_atoms.toArray(new Atom[current_atoms.size()]));
    // current_atoms.clear();
    // }
    // current_key = argument.toString();
    // current_key = current_key.substring(0, current_key.length() - 1);
    // } else {
    // current_atoms.add(argument);
    // }
    // }
    // if ((0 < current_atoms.size()) && (current_key != null)) {
    // map.put(current_key, current_atoms.toArray(new
    // Atom[current_atoms.size()]));
    // }
    // return map;
    // }
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
            return MaxIO.to_atoms(message);
        }
        return MaxIO.to_atoms(message, new Atom[] {
            atom
        });
    }

    public static Atom[][] to_atoms(String message, Atom[] atoms) {
        Atom[][] result = new Atom[1][];
        if (atoms == null) {
            return MaxIO.to_atoms(message);
        }
        result[0] = Atom.newAtom(message, atoms);
        return result;
    }

    public static Atom[][] to_atoms(String message, boolean argument) {
        return MaxIO.to_atoms(message, Atom.newAtom(argument));
    }

    public static Atom[][] to_atoms(String message, Double argument) {
        if (argument == null) {
            return MaxIO.to_atoms(message);
        }
        return MaxIO.to_atoms(message, Atom.newAtom(argument));
    }

    public static Atom[][] to_atoms(String message, double[] arguments) {
        if (arguments == null) {
            return MaxIO.to_atoms(message);
        }
        return MaxIO.to_atoms(message, Atom.newAtom(arguments));
    }

    public static Atom[][] to_atoms(String message, Integer argument) {
        if (argument == null) {
            return MaxIO.to_atoms(message);
        }
        return MaxIO.to_atoms(message, Atom.newAtom(argument));
    }

    public static Atom[][] to_atoms(String message, String argument) {
        if (argument == null) {
            return MaxIO.to_atoms(message);
        }
        return MaxIO.to_atoms(message, Atom.newAtom(argument));
    }

    public static Atom[][] to_atoms(String message, String[] arguments) {
        if (arguments == null) {
            return MaxIO.to_atoms(message);
        }
        return MaxIO.to_atoms(message, Atom.newAtom(arguments));
    }

    public static Atom[] to_serialized_dict(Map<String, Atom[]> map) {
        ArrayList<Atom> result = new ArrayList<Atom>();
        String[] keys = map.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            result.add(Atom.newAtom(key + ":"));
            result.addAll(Arrays.asList(map.get(key)));
        }
        return result.toArray(new Atom[0]);
    }
}
