package oovu.addresses.tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;

import org.junit.Assert;
import org.junit.Test;

public class Test__OscAddressNode__search {
    public Map<String, OscAddressNode> create_node_tree() {
        OscAddressNode root = new OscAddressNode("");
        OscAddressNode synth_one = new OscAddressNode("synth.1");
        OscAddressNode synth_two = new OscAddressNode("synth.2");
        OscAddressNode synth_three = new OscAddressNode("synth.3");
        OscAddressNode synth_one_child = new OscAddressNode("child");
        OscAddressNode synth_one_grandchild = new OscAddressNode("grandchild");
        OscAddressNode synth_two_child = new OscAddressNode("child");
        OscAddressNode effect_one = new OscAddressNode("effect.1");
        OscAddressNode effect_two = new OscAddressNode("effect.2");
        OscAddressNode effect_one_child = new OscAddressNode("child");
        root.add_child(synth_one);
        synth_one.add_child(synth_one_child);
        synth_one_child.add_child(synth_one_grandchild);
        root.add_child(synth_two);
        synth_two.add_child(synth_two_child);
        root.add_child(synth_three);
        root.add_child(effect_one);
        effect_one.add_child(effect_one_child);
        root.add_child(effect_two);
        Map<String, OscAddressNode> map = new HashMap<String, OscAddressNode>();
        map.put("/", root);
        map.put("/synth.1", synth_one);
        map.put("/synth.2", synth_two);
        map.put("/synth.3", synth_three);
        map.put("/synth.1/child", synth_one_child);
        map.put("/synth.1/child/grandchild", synth_one_grandchild);
        map.put("/synth.2/child", synth_two_child);
        map.put("/synth.3", synth_three);
        map.put("/effect.1", effect_one);
        map.put("/effect.2", effect_two);
        map.put("/effect.1/child", effect_one_child);
        return map;
    }

    @Test
    public void test_search_01() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache("/synth.1");
        OscAddressNode root = map.get("/");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/synth.1"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_search_02() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache("*");
        OscAddressNode root = map.get("/");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/synth.1"));
        expected.add(map.get("/synth.2"));
        expected.add(map.get("/synth.3"));
        expected.add(map.get("/effect.1"));
        expected.add(map.get("/effect.2"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_search_03() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache("*/*");
        OscAddressNode root = map.get("/");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/synth.1/child"));
        expected.add(map.get("/synth.2/child"));
        expected.add(map.get("/effect.1/child"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_search_04() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache("*.1/child");
        OscAddressNode root = map.get("/");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/synth.1/child"));
        expected.add(map.get("/effect.1/child"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_search_05() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache("synth.*/child");
        OscAddressNode root = map.get("/");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/synth.1/child"));
        expected.add(map.get("/synth.2/child"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_search_06() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache("../child");
        OscAddressNode root = map.get("/synth.1/child");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/synth.1/child"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_search_07() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache(".");
        OscAddressNode root = map.get("/synth.1/child");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/synth.1/child"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_search_08() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache("/");
        OscAddressNode root = map.get("/synth.1/child");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_search_09() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache("../../*/child");
        OscAddressNode root = map.get("/synth.1/child");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/synth.1/child"));
        expected.add(map.get("/synth.2/child"));
        expected.add(map.get("/effect.1/child"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_search_10() {
        Map<String, OscAddressNode> map = this.create_node_tree();
        OscAddress address = OscAddress.from_cache("../../*/*/*");
        OscAddressNode root = map.get("/synth.1/child");
        Set<OscAddressNode> expected = new HashSet<OscAddressNode>();
        expected.add(map.get("/synth.1/child/grandchild"));
        Set<OscAddressNode> actual = root.search(address);
        Assert.assertEquals(expected, actual);
    }
}
