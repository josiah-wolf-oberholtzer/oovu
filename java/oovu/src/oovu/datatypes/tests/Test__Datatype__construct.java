package oovu.datatypes.tests;

import oovu.Module;
import oovu.Property;
import oovu.addresses.Environment;
import oovu.datatypes.BooleanDatatype;
import oovu.datatypes.Datatype;
import oovu.datatypes.DecimalArrayDatatype;
import oovu.datatypes.DecimalDatatype;
import oovu.datatypes.FilesystemPathDatatype;
import oovu.datatypes.GenericDatatype;
import oovu.datatypes.IntegerArrayDatatype;
import oovu.datatypes.IntegerDatatype;
import oovu.datatypes.OptionDatatype;
import oovu.datatypes.OscAddressDatatype;
import oovu.datatypes.RangeDatatype;
import oovu.datatypes.StringDatatype;
import oovu.servers.AttributeServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__Datatype__construct {

    @Before
    public void setUp() throws Exception {
        Environment.reset();
    }

    @After
    public void tearDown() throws Exception {
        Environment.reset();
    }

    @Test
    public void test_01() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype boolean"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(BooleanDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_02() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype decimalarray"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(DecimalArrayDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_03() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype decimal"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(DecimalDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_04() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype filesystempath"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(FilesystemPathDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_05() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype generic"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(GenericDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_06() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype integerarray"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(IntegerArrayDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_07() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype integer"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(IntegerDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_08() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype option"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(OptionDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_09() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype oscaddress"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(OscAddressDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_10() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype range"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(RangeDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }

    @Test
    public void test_11() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property =
            new Property(Atom.parse("1001 bar :datatype string"));
        AttributeServer server = (AttributeServer) property.get_server();
        Datatype datatype = server.datatype;
        Assert.assertEquals(StringDatatype.class, datatype.getClass());
        property.notifyDeleted();
        module.notifyDeleted();
    }
}
