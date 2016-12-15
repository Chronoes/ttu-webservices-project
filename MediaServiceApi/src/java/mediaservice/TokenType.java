/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chronoes
 */
class TokenType {
    private static Pattern pattern = Pattern.compile("^[A-Za-z0-9]{8,16}$");
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) throws IllegalArgumentException {
        if (validate(value)) {
           this.value = value;
        }
    }

    TokenType() {}
    
    TokenType(String value) {
        setValue(value);
    }

    static boolean validate(String value) throws IllegalArgumentException {
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            return true;
        }
        throw new IllegalArgumentException("Token does not match " + pattern.pattern());
    }

    @Override
    public boolean equals(Object obj) {
        return value.equals(obj);
    }
}
