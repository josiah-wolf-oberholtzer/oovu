package oovu.datatypes.tests;

import java.util.Arrays;

import oovu.datatypes.OptionDatatype;

import org.junit.Assert;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__OptionDatatype {

    @Test
    public void test_01() {
        Atom[] arguments = Atom.parse("");
        OptionDatatype datatype = new OptionDatatype(arguments);
        Assert.assertEquals("---", datatype.get_value()[0].toString());
        Assert.assertEquals(Arrays.asList(new String[] {
            "---"
        }), datatype.get_options());
        Assert.assertEquals(0, datatype.get_option_index());
    }

    @Test
    public void test_02() {
        Atom[] arguments = Atom.parse(":default foo");
        OptionDatatype datatype = new OptionDatatype(arguments);
        Assert.assertEquals("---", datatype.get_value()[0].toString());
        Assert.assertEquals(Arrays.asList(new String[] {
            "---"
        }), datatype.get_options());
        Assert.assertEquals(0, datatype.get_option_index());
    }

    @Test
    public void test_03() {
        Atom[] arguments = Atom.parse(":default foo :options bar baz foo");
        OptionDatatype datatype = new OptionDatatype(arguments);
        Assert.assertEquals("foo", datatype.get_value()[0].toString());
        Assert.assertEquals(Arrays.asList(new String[] {
            "bar", "baz", "foo"
        }), datatype.get_options());
        Assert.assertEquals(2, datatype.get_option_index());
    }

    @Test
    public void test_04() {
        Atom[] arguments = Atom.parse(":default invalid :options bar baz foo");
        OptionDatatype datatype = new OptionDatatype(arguments);
        Assert.assertEquals("bar", datatype.get_value()[0].toString());
        Assert.assertEquals(Arrays.asList(new String[] {
            "bar", "baz", "foo"
        }), datatype.get_options());
        Assert.assertEquals(0, datatype.get_option_index());
    }

    @Test
    public void test_05() {
        Atom[] arguments = Atom.parse(":default 1 :options foo bar baz");
        OptionDatatype datatype = new OptionDatatype(arguments);
        Assert.assertEquals(Arrays.asList(new String[] {
            "foo", "bar", "baz"
        }), datatype.get_options());
        Assert.assertEquals("bar", datatype.get_value()[0].toString());
        Assert.assertEquals(1, datatype.get_option_index());
        datatype.next_option();
        Assert.assertEquals("baz", datatype.get_value()[0].toString());
        Assert.assertEquals(2, datatype.get_option_index());
        datatype.next_option();
        Assert.assertEquals("foo", datatype.get_value()[0].toString());
        Assert.assertEquals(0, datatype.get_option_index());
        datatype.previous_option();
        Assert.assertEquals("baz", datatype.get_value()[0].toString());
        Assert.assertEquals(2, datatype.get_option_index());
        datatype.previous_option();
        Assert.assertEquals("bar", datatype.get_value()[0].toString());
        Assert.assertEquals(1, datatype.get_option_index());
        datatype.previous_option();
        Assert.assertEquals("foo", datatype.get_value()[0].toString());
        Assert.assertEquals(0, datatype.get_option_index());
        datatype.previous_option();
        Assert.assertEquals("baz", datatype.get_value()[0].toString());
        Assert.assertEquals(2, datatype.get_option_index());
    }

    @Test
    public void test_07() {
        Atom[] arguments = Atom.parse(":default 2 :options foo bar baz");
        OptionDatatype datatype = new OptionDatatype(arguments);
        Assert.assertEquals(Arrays.asList(new String[] {
            "foo", "bar", "baz"
        }), datatype.get_options());
        Assert.assertEquals("baz", datatype.get_value()[0].toString());
        Assert.assertEquals(2, datatype.get_option_index());
        datatype.set_options(new String[] {
            "one", "two", "three"
        });
        Assert.assertEquals("one", datatype.get_value()[0].toString());
        Assert.assertEquals(0, datatype.get_option_index());
        datatype.set_options(new String[] {
            "ein", "ichi", "one", "uno"
        });
        Assert.assertEquals("one", datatype.get_value()[0].toString());
        Assert.assertEquals(2, datatype.get_option_index());
    }
}
