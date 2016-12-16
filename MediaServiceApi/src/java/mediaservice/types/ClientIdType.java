/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice.types;

import mediaservice.types.TokenType;
import java.util.Calendar;

/**
 *
 * @author chronoes
 */
public class ClientIdType extends TokenType implements Comparable<ClientIdType> {
    protected final Calendar createdAt = Calendar.getInstance();
    
    
    public ClientIdType(String value) {
        super(value);
    }
    
    @Override
    public int compareTo(ClientIdType o) {
        return createdAt.compareTo(o.createdAt);
    }
    
    public boolean hasExpired(int timeout) {
        Calendar current = Calendar.getInstance();
        current.add(Calendar.SECOND, -timeout);
        return createdAt.before(current);
    }
}
