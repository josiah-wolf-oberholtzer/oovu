package oovu.addressing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class OscAddress {

    static final String osc_name_regex = "(\\*|(([a-z][a-z0-9~]*|\\*)(\\.([a-z0-9]+|\\*))?))";
    static final String node_attribute_regex = ".*(:"
        + OscAddress.osc_name_regex + "(/" + OscAddress.osc_name_regex + ")*)$";
    static final Pattern node_attribute_pattern = Pattern
        .compile(OscAddress.node_attribute_regex);
    static final Pattern osc_name_pattern = Pattern.compile("^"
        + OscAddress.osc_name_regex + "$");
    private static final Map<String, OscAddress> cached_addresses = new HashMap<String, OscAddress>();

    public static boolean all_are_node_name_tokens(String[] tokens) {
        for (String token : tokens) {
            if (!OscAddress.is_node_name_token(token)) {
                return false;
            }
        }
        return true;
    }

    public static OscAddress from_cache(String osc_address_string) {
        OscAddress osc_address = OscAddress.cached_addresses
            .get(osc_address_string);
        if (osc_address == null) {
            osc_address = new OscAddress(osc_address_string);
            OscAddress.cached_addresses.put(osc_address_string, osc_address);
        }
        return osc_address;
    }

    public static boolean is_current_node_token(String token) {
        if (token.equals(".") || token.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean is_node_name_token(String token) {
        return OscAddress.osc_name_pattern.matcher(token).matches();
    }

    public static boolean is_parent_node_token(String token) {
        if (token.equals("..")) {
            return true;
        }
        return false;
    }

    public static boolean is_valid_token(String token) {
        if (OscAddress.is_node_name_token(token)
            || OscAddress.is_current_node_token(token)
            || OscAddress.is_parent_node_token(token)) {
            return true;
        }
        return false;
    }

    public final String node_attribute_name;
    public final String[] address_items;
    public final String raw_address;
    public final boolean is_relative;
    public final boolean has_wildcard_tokens;
    public final boolean has_parent_path_tokens;

    private OscAddress(String input) {
        this.raw_address = input;
        boolean has_parent_path_tokens = false;
        boolean has_wildcard_tokens = false;
        Matcher matcher = OscAddress.node_attribute_pattern.matcher(input);
        if (matcher.matches()) {
            this.node_attribute_name = matcher.group(1).substring(1);
            input = input.replace(matcher.group(1), "");
            if ((0 < input.length())
                && (input.charAt(input.length() - 1) != '/')) {
                throw new RuntimeException("Bad address: " + input);
            }
        } else {
            this.node_attribute_name = null;
        }
        if ((0 < input.length()) && (input.charAt(0) == '/')) {
            this.is_relative = false;
            input = input.substring(1);
        } else {
            this.is_relative = true;
        }
        ArrayList<String> old_address_items = new ArrayList<String>(
            Arrays.asList(input.split("/")));
        ArrayList<String> new_address_items = new ArrayList<String>();
        for (String address_item : old_address_items) {
            if (!OscAddress.is_valid_token(address_item)) {
                throw new RuntimeException("Bad address item: '" + address_item
                    + "'");
            }
            if (!OscAddress.is_current_node_token(address_item)) {
                new_address_items.add(address_item);
            }
            if (address_item.contains("*")) {
                has_wildcard_tokens = true;
            } else if (address_item.equals("..")) {
                has_parent_path_tokens = true;
            }
        }
        this.has_parent_path_tokens = has_parent_path_tokens;
        this.has_wildcard_tokens = has_wildcard_tokens;
        this.address_items = new_address_items.toArray(new String[0]);
    }

    public boolean is_cached() {
        return OscAddress.cached_addresses.containsKey(this.raw_address);
    }

    @Override
    public String toString() {
        StringBuilder string_builder = new StringBuilder();
        if (!this.is_relative) {
            string_builder.append("/");
        }
        string_builder.append(StringUtils.join(this.address_items, "/"));
        if (this.node_attribute_name != null) {
            if (1 < string_builder.length()) {
                string_builder.append("/");
            }
            string_builder.append(this.node_attribute_name);
        }
        return string_builder.toString();
    }
}
