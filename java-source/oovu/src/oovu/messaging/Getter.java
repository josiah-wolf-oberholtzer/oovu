package oovu.messaging;

import com.cycling74.max.Atom;

public interface Getter {

    public Atom[][] execute(Atom[] arguments);
}
