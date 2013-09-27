package oovu.datatypes.tests;

import java.util.Map;

import oovu.datatypes.BooleanDatatype;
import oovu.datatypes.Datatype;

import org.junit.Assert;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__BooleanDatatype {

    @Test
    public void test_01() {
        Datatype datatype = new BooleanDatatype(new Atom[0]);
        Assert.assertEquals(false, datatype.get_value()[0].toBoolean());
    }
    
    @Test
    public void test_02() {
        Atom[] arguments = Atom.parse(":default 0");
        Datatype datatype = new BooleanDatatype(arguments);
        Assert.assertEquals(false, datatype.get_value()[0].toBoolean());
    }

    @Test
    public void test_03() {
        Atom[] arguments = Atom.parse(":default 1");
        Datatype datatype = new BooleanDatatype(arguments);
        Assert.assertEquals(true, datatype.get_value()[0].toBoolean());
    }

    
    @Test
    public void test_04() {
        Atom[] arguments = Atom.parse(":default 3.14159");
        Datatype datatype = new BooleanDatatype(arguments);
        Assert.assertEquals(true, datatype.get_value()[0].toBoolean());
    }

    @Test
    public void test_05() {
        Atom[] arguments = Atom.parse(":default foo");
        Datatype datatype = new BooleanDatatype(arguments);
        Assert.assertEquals(true, datatype.get_value()[0].toBoolean());
    }

    @Test
    public void test_06() {
        Atom[] arguments = Atom.parse(":default -1");
        Datatype datatype = new BooleanDatatype(arguments);
        Assert.assertEquals(true, datatype.get_value()[0].toBoolean());
    }

    @Test
    public void test_07() {
        Atom[] arguments = Atom.parse(":default 0.00001");
        Datatype datatype = new BooleanDatatype(arguments);
        Assert.assertEquals(true, datatype.get_value()[0].toBoolean());
    }

    @Test
    public void test_08() {
        Atom[] arguments = Atom.parse(":default 0 1 2 3 4");
        Datatype datatype = new BooleanDatatype(arguments);
        Assert.assertEquals(false, datatype.get_value()[0].toBoolean());
    }
    
    @Test
    public void test_09() {
        BooleanDatatype datatype = new BooleanDatatype(new Atom[0]);
        Assert.assertEquals(false, datatype.get_value()[0].toBoolean());
        datatype.toggle();
        Assert.assertEquals(true, datatype.get_value()[0].toBoolean());
        datatype.toggle();
        Assert.assertEquals(false, datatype.get_value()[0].toBoolean());
    }

}
