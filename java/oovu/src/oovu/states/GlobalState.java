package oovu.states;

public class GlobalState extends State {

    public final State[] state_components;

    public GlobalState(State[] state_components) {
        this.state_components = state_components;
    }
}
