package oovu.states;

import com.cycling74.max.Atom;

public abstract class State {
    public abstract Atom[][] toAtoms();

    public abstract String[] toStrings();
}
