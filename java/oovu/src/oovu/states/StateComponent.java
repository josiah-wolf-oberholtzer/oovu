package oovu.states;

import java.util.Arrays;

import com.cycling74.max.Atom;

public class StateComponent {

    public final String address;
    public final Atom[] arguments;

    public StateComponent(String address, Atom[] arguments) {
        this.address = address;
        this.arguments = arguments;
    }

    public StateComponent(String address_string, String unparsed_arguments) {
        this(address_string, Atom.parse(unparsed_arguments));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        StateComponent other = (StateComponent) obj;
        if (this.address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!this.address.equals(other.address)) {
            return false;
        }
        if (!Arrays.equals(this.arguments, other.arguments)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result)
            + ((this.address == null) ? 0 : this.address.hashCode());
        result = (prime * result) + Arrays.hashCode(this.arguments);
        return result;
    }
}
