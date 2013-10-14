package oovu.datatypes.tests;

import oovu.datatypes.RangeDatatype;

import org.junit.Assert;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__RangeDatatype {

    @Test
    public void test_01() {
        Atom[] arguments = Atom.parse("");
        RangeDatatype datatype = new RangeDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            0, 1
        }), datatype.get_value());
    }

    @Test
    public void test_02() {
        Atom[] arguments = Atom.parse(":default 0.1 9.5");
        RangeDatatype datatype = new RangeDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            0.1, 9.5
        }), datatype.get_value());
    }

    @Test
    public void test_03() {
        Atom[] arguments = Atom.parse(":default 1 2 3 4 5 6 7 8");
        RangeDatatype datatype = new RangeDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            1, 2
        }), datatype.get_value());
    }

    @Test
    public void test_04() {
        Atom[] arguments =
            Atom.parse(":default 0.5 3.5 :minimum 1 :maximum 2.3");
        RangeDatatype datatype = new RangeDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            1, 2.3
        }), datatype.get_value());
    }

    @Test
    public void test_05() {
        Atom[] arguments = Atom.parse("");
        RangeDatatype datatype = new RangeDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            0, 1
        }), datatype.get_value());
        datatype.apply_new_center(new double[] {
            0.75
        });
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            0.25, 1.25
        }), datatype.get_value());
        datatype.apply_new_width(new double[] {
            0.125
        });
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            0.625, 0.875
        }), datatype.get_value());
    }

    @Test
    public void test_06() {
        Atom[] arguments =
            Atom.parse(":minimum 0. :maximum 1. :default 0.5 1.");
        RangeDatatype datatype = new RangeDatatype(arguments);
        Assert.assertTrue(datatype.get_value()[0].isFloat());
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            0.5, 1.
        }), datatype.get_value());
        datatype.apply_new_center(new double[] {
            1.
        });
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            0.75, 1.
        }), datatype.get_value());
        datatype.apply_new_width(new double[] {
            0.5
        });
        Assert.assertArrayEquals(Atom.newAtom(new double[] {
            0.375, 1.
        }), datatype.get_value());
    }
}
