/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import java.util.Calendar;

/**
 *
 * @author chronoes
 */
public class ClientIdType extends TokenType implements Comparable<ClientIdType> {
    private final Calendar createdAt = Calendar.getInstance();
    
    
    ClientIdType(String value) {
        super(value);
    }
    
    @Override
    public int compareTo(ClientIdType o) {
        return -createdAt.compareTo(o.createdAt);
    }
    
    public boolean hasExpired(int timeout) {
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MINUTE, -timeout);
        return createdAt.before(current);
    }
}
