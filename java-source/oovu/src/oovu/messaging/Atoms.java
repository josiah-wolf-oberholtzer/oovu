package oovu.messaging;

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
}
