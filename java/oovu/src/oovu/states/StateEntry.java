package oovu.states;

import java.util.Arrays;

import com.cycling74.max.Atom;


public class StateEntry {
    
    public final String address;
    public final Atom[] arguments;
    
    public StateEntry(String address, Atom[] arguments) {
        this.address = address;
        this.arguments = arguments;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
            + ((this.address == null) ? 0 : this.address.hashCode());
        result = prime * result + Arrays.hashCode(this.arguments);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StateEntry other = (StateEntry) obj;
        if (this.address == null) {
            if (other.address != null)
                return false;
        } else if (!this.address.equals(other.address))
            return false;
        if (!Arrays.equals(this.arguments, other.arguments))
            return false;
        return true;
    }
    
}
