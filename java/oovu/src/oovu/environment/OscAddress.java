package oovu.environment;

import java.util.regex.*;

public class OscAddress {

    private final String osc_name = "([a-z][a-z0-9~]{2,}(.[a-z0-9]+))";
    private final Pattern interface_handler_name_pattern = Pattern.compile(
        ".*(:" + osc_name + "(/" + osc_name + ")*)");    
    
    public String interface_handler_name;
    public String module_name;
    public String member_name;
    
    public OscAddress(String input) {

    }
    
}
