package oovu.datatypes.tests;

import oovu.datatypes.DecimalDatatype;

import org.junit.Assert;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__DecimalDatatype {

    @Test
    public void test_01() {
        DecimalDatatype datatype = new DecimalDatatype(new Atom[0]);
        Assert.assertEquals(0, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_02() {
        Atom[] arguments = Atom.parse(":default 0");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertEquals(0, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_03() {
        Atom[] arguments = Atom.parse(":default 5");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertEquals(5, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_04() {
        Atom[] arguments = Atom.parse(":default 1 2 3");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertEquals(1, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_05() {
        Atom[] arguments = Atom.parse(":default 2.9");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertEquals(Atom.newAtom(2.9), datatype.get_value()[0]);
    }

    @Test
    public void test_06() {
        Atom[] arguments = Atom.parse(":default foo");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertEquals(0, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_08() {
        Atom[] arguments = Atom.parse(":minimum 3.2 :default 1");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(Atom.newAtom(3.2), datatype.get_value()[0]);
    }

    @Test
    public void test_09() {
        Atom[] arguments = Atom.parse(":minimum 3.2 :default 3");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(Atom.newAtom(3.2), datatype.get_value()[0]);
    }

    @Test
    public void test_10() {
        Atom[] arguments = Atom.parse(":minimum 3.2 :default 23");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(23, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_11() {
        Atom[] arguments = Atom.parse(":maximum 7.1 :default 1");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(1, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_12() {
        Atom[] arguments = Atom.parse(":maximum 7.1 :default 7");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(7, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_13() {
        Atom[] arguments = Atom.parse(":maximum 7.1 :default 23");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(Atom.newAtom(7.1), datatype.get_value()[0]);
    }

    @Test
    public void test_14() {
        Atom[] arguments = Atom.parse(":minimum 3.2 :maximum 7.1 :default 1");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(Atom.newAtom(3.2), datatype.get_value()[0]);
    }

    @Test
    public void test_15() {
        Atom[] arguments = Atom.parse(":minimum 3.2 :maximum 7.1 :default 3");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(Atom.newAtom(3.2), datatype.get_value()[0]);
    }

    @Test
    public void test_16() {
        Atom[] arguments = Atom.parse(":minimum 3.2 :maximum 7.1 :default 5");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(5, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_17() {
        Atom[] arguments = Atom.parse(":minimum 3.2 :maximum 7.1 :default 7");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(7, datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_18() {
        Atom[] arguments = Atom.parse(":minimum 3.2 :maximum 7.1 :default 23");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(Atom.newAtom(7.1), datatype.get_value()[0]);
    }

    @Test
    public void test_19() {
        Atom[] arguments = Atom.parse(":minimum 7.1 :maximum 3.2 :default 5");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(5., datatype.get_value()[0].toFloat(), 0);
    }

    @Test
    public void test_20() {
        Atom[] arguments = Atom.parse(":minimum 3.2 :maximum 7.1 :default 23");
        DecimalDatatype datatype = new DecimalDatatype(arguments);
        Assert.assertEquals(Atom.newAtom(7.1), datatype.get_value()[0]);
        Atom[] input = Atom.parse("23");
        datatype.set_maximum(new Float(25));
        Assert.assertEquals(new Float(25), datatype.get_maximum());
        Assert.assertArrayEquals(Atom.newAtom(new float[] {
            23
        }), datatype.process_input(input));
    }
}
