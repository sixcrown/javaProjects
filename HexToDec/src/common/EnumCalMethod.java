package common;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 2.0
 */
public enum EnumCalMethod {
    HEXTODEC("Hex To Dec"), DECTOHEX("Dec To Hex");

    /**
     * String value
     */
    public String key;

    /**
     * constructor for the enum
     *
     * @param key String
     */
    private EnumCalMethod(String key) {
        this.key = key;
    }

    /**
     * getter for key 
     * @return String value
     */
    public String getKey() {
        return key;
    }

    /**
     * Lookup table
     */
    private static final Map<String, EnumCalMethod> lookup = new HashMap<>();

    /**
    populating the lookup table
    */
    static {
        for (EnumCalMethod functionType : EnumCalMethod.values()) {
            lookup.put(functionType.getKey(), functionType);
        }
    }

    /**
     * @param key the String of wanted EnumCalMethod
     * @return EnumCalMethod
     */
    public static EnumCalMethod get(String key) {
        return lookup.get(key);
    }

    /**
     *
     * @return String key of the enum
     */
    @Override
    public String toString() {
        return this.key;
    }
}
