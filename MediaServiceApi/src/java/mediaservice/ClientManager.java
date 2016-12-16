/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chronoes
 */
class ClientManager {
    final int TIMEOUT;
    private final Queue<ClientIdType> clientIds = new PriorityQueue<>();
    private final Thread poller;

    ClientManager(int timeout) {
        TIMEOUT = timeout;
        
        poller = new Thread(() -> {
            int interval = TIMEOUT / 10 * 1000;
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MediaService.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
                clearLastClientId();
            }
        });
        
        poller.start();
    }
        
    private synchronized void clearLastClientId() {
        if (clientIds.size() > 0) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.INFO, "Oldest client ID {0}", clientIds.peek().getValue());           
            if (clientIds.peek().hasExpired(TIMEOUT)) {
                clientIds.remove();
            }
        }
    }
    
    synchronized boolean addClientId(String token) {
        ClientIdType t = new ClientIdType(token);
        if (clientIds.contains(t)) {
            throw new IllegalArgumentException("Client ID already exists");
        }
        clientIds.add(t);
        return true;
    }
   
}
