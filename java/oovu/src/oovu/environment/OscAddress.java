package oovu.environment;

import java.util.regex.*;

public class OscAddress {

    static final String osc_name_regex = "([a-z][a-z0-9~]{2,}(.[a-z0-9]+))";
    static final Pattern osc_name_pattern = Pattern.compile(osc_name_regex);
    static final Pattern interface_handler_name_pattern = Pattern.compile(
        ".*(:" + osc_name_regex + "(/" + osc_name_regex + ")*)");    
    
    public String interface_handler_name;
    public String module_name;
    public String member_name;
    
    public OscAddress(String input) {

    }
    
    public static boolean all_are_valid_names(String[] osc_address_parts) {
    	for (String osc_address_part : osc_address_parts) {
    		if (! OscAddress.osc_name_pattern.matcher(osc_address_part).matches()) {
    			return false;
    		}
    	}
    	return true;
    }
    
}
