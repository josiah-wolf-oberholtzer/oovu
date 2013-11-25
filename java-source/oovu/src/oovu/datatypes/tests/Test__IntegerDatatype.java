package oovu.datatypes.tests;

import oovu.datatypes.IntegerDatatype;

import org.junit.Assert;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__IntegerDatatype {
    @Test
    public void test_01() {
        IntegerDatatype datatype = new IntegerDatatype(new Atom[0]);
        Assert.assertEquals(0, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_02() {
        Atom[] arguments = Atom.parse(":default 0");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertEquals(0, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_03() {
        Atom[] arguments = Atom.parse(":default 5");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertEquals(5, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_04() {
        Atom[] arguments = Atom.parse(":default 1 2 3");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertEquals(1, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_05() {
        Atom[] arguments = Atom.parse(":default 2.9");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertEquals(2, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_06() {
        Atom[] arguments = Atom.parse(":default foo");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertEquals(0, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_08() {
        Atom[] arguments = Atom.parse(":minimum 3 :default 1");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(3, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_09() {
        Atom[] arguments = Atom.parse(":minimum 3 :default 3");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(3, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_10() {
        Atom[] arguments = Atom.parse(":minimum 3 :default 23");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(23, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_11() {
        Atom[] arguments = Atom.parse(":maximum 7 :default 1");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(1, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_12() {
        Atom[] arguments = Atom.parse(":maximum 7 :default 7");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(7, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_13() {
        Atom[] arguments = Atom.parse(":maximum 7 :default 23");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(7, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_14() {
        Atom[] arguments = Atom.parse(":minimum 3 :maximum 7 :default 1");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(3, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_15() {
        Atom[] arguments = Atom.parse(":minimum 3 :maximum 7 :default 3");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(3, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_16() {
        Atom[] arguments = Atom.parse(":minimum 3 :maximum 7 :default 5");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(5, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_17() {
        Atom[] arguments = Atom.parse(":minimum 3 :maximum 7 :default 7");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(7, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_18() {
        Atom[] arguments = Atom.parse(":minimum 3 :maximum 7 :default 23");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(7, datatype.get_value()[0].toInt());
    }

    @Test
    public void test_19() {
        Atom[] arguments = Atom.parse(":minimum 7 :maximum 3 :default 5");
        IntegerDatatype datatype = new IntegerDatatype(arguments);
        Assert.assertEquals(5, datatype.get_value()[0].toInt());
    }
}
