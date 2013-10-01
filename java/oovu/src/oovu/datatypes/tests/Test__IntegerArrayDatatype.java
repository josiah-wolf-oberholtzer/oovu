package oovu.datatypes.tests;

import oovu.addressing.Environment;
import oovu.datatypes.IntegerArrayDatatype;

import org.junit.Assert;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__IntegerArrayDatatype {

    @Test
    public void test_01() {
        Atom[] arguments = Atom.parse(":default 0");
        IntegerArrayDatatype datatype = new IntegerArrayDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertArrayEquals(new double[] {
            0
        }, Atom.toDouble(datatype.get_value()), 0);
    }

    @Test
    public void test_02() {
        Atom[] arguments = Atom.parse(":default 5 4 3 :length 3");
        IntegerArrayDatatype datatype = new IntegerArrayDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertArrayEquals(new double[] {
            5, 4, 3
        }, Atom.toDouble(datatype.get_value()), 0);
    }

    @Test
    public void test_03() {
        Atom[] arguments = Atom.parse(":default 5 4 3 2 1 :length 3");
        IntegerArrayDatatype datatype = new IntegerArrayDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertArrayEquals(new double[] {
            5, 4, 3
        }, Atom.toDouble(datatype.get_value()), 0);
    }

    @Test
    public void test_04() {
        Atom[] arguments = Atom.parse(":default -1.1 :length 3");
        IntegerArrayDatatype datatype = new IntegerArrayDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertArrayEquals(new float[] {
            -1, 0, 0
        }, Atom.toFloat(datatype.get_value()), 0);
    }

    @Test
    public void test_05() {
        Atom[] arguments = Atom
            .parse(":default 0 10 20 30 :length 3 :minimum 2 :maximum 19.5");
        IntegerArrayDatatype datatype = new IntegerArrayDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertArrayEquals(new double[] {
            2, 10, 19
        }, Atom.toDouble(datatype.get_value()), 0);
    }

    @Test
    public void test_06() {
        Atom[] arguments = Atom
            .parse(":default 0 10 20 30 :length 3 :minimum 2 :maximum 19.5");
        IntegerArrayDatatype datatype = new IntegerArrayDatatype(arguments);
        Environment.log(datatype.get_length());
        Assert.assertTrue(datatype.get_value()[0].isInt());
        Assert.assertArrayEquals(new double[] {
            2, 10, 19
        }, Atom.toDouble(datatype.get_value()), 0);
        datatype.set_length(7);
        Assert.assertArrayEquals(new double[] {
            2, 10, 19, 2., 2., 2., 2.
        }, Atom.toDouble(datatype.get_value()), 0);
        // too few values:
        Atom[] input = Atom.parse("0 10 20 30");
        Atom[] output = datatype.process_input(input);
        Assert.assertArrayEquals(new double[] {
            2, 10, 19, 2, 2, 2, 2
        }, Atom.toDouble(output), 0);
        // correct number of values:
        input = Atom.parse("0 10 20 30 40 50 60");
        output = datatype.process_input(input);
        Assert.assertArrayEquals(new double[] {
            2, 10, 19, 19, 19, 19, 19
        }, Atom.toDouble(output), 0);
    }
}
