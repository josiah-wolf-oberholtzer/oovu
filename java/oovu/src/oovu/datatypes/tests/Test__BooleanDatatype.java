package oovu.datatypes.tests;

import oovu.datatypes.BooleanDatatype;
import oovu.datatypes.Datatype;

import org.junit.Assert;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__BooleanDatatype {

    @Test
    public void test() {
        Datatype datatype = new BooleanDatatype(new Atom[0]);
        Assert.assertTrue(BooleanDatatype.class.isInstance(datatype));
        Assert.assertEquals(false, datatype.get_value()[0].toBoolean());
    }
}
