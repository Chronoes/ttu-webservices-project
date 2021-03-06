/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chronoes
 */
public class TokenType implements CustomType<String> {
    protected static Pattern pattern = Pattern.compile("^[A-Za-z0-9]{8,16}$");
    protected String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) throws IllegalArgumentException {
        if (validate(value)) {
           this.value = value;
        }
    }

    public TokenType() {}
    
    public TokenType(String value) {
        setValue(value);
    }

    @Override
    public boolean validate(String value) throws IllegalArgumentException {
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            return true;
        }
        throw new IllegalArgumentException("Token does not match " + pattern.pattern());
    }
    
    @Override
    public boolean validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TokenType) {
            return value.equals(((TokenType) obj).getValue());
        }
        return value.equals(obj);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
