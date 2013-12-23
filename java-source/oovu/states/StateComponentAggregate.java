package oovu.states;

import java.util.ArrayList;
import java.util.Arrays;

import com.cycling74.max.Atom;

public class StateComponentAggregate extends State {
    public final String name;
    public final State[] state_components;

    public StateComponentAggregate(String name, State[] state_components) {
        this.name = name;
        this.state_components = state_components;
    }

    @Override
    public Atom[][] toAtoms() {
        ArrayList<Atom[]> result = new ArrayList<Atom[]>();
        for (State state_component : this.state_components) {
            result.addAll(Arrays.asList(state_component.toAtoms()));
        }
        return result.toArray(new Atom[0][]);
    }

    @Override
    public String[] toStrings() {
        ArrayList<String> result = new ArrayList<String>();
        for (State state_component : this.state_components) {
            result.addAll(Arrays.asList(state_component.toStrings()));
        }
        return result.toArray(new String[0]);
    }
}
