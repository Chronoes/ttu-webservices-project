/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import ee.ttu.idu0075._143076.mediaservice._1.*;
import java.util.Calendar;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

/**
 *
 * @author chronoes
 */
@WebService(serviceName = "MediaService", portName = "MediaPort", endpointInterface = "ee.ttu.idu0075._143076.mediaservice._1.MediaPortType", targetNamespace = "http://www.ttu.ee/idu0075/143076/MediaService/1.0", wsdlLocation = "WEB-INF/wsdl/MediaService/project.wsdl")
public class MediaService {
    private final String API_TOKEN = "TESTTOKEN";
    private final int TIMEOUT = 3 * 60;
    private MockDatabase db = new MockDatabase();
    private Queue<ClientIdType> clientIds = new PriorityQueue<>();

    public MediaService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int interval = TIMEOUT / 20;
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MediaService.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    }
                    clearLastClientId();
                }
            }
        });
    }
    
    private synchronized void clearLastClientId() {
        if (clientIds.peek().hasExpired(TIMEOUT)) {
            clientIds.remove();
        }
    }
    
    private synchronized boolean addClientId(String token) {
        ClientIdType t = new ClientIdType(token);
        if (clientIds.contains(t)) {
            throw new IllegalArgumentException("Client ID already exists");
        }
        clientIds.add(t);
        return true;
    }
   
    private boolean validateToken(String token) {
        TokenType t = new TokenType(token);
        return t.equals(API_TOKEN);
    }
    
    public ee.ttu.idu0075._143076.mediaservice._1.GetGenreByIdOrNameResponse getGenreByIdOrName(ee.ttu.idu0075._143076.mediaservice._1.GetGenreByIdOrNameRequest parameter) {
        if (validateToken(parameter.getAPITOKEN())) {
            GetGenreByIdOrNameResponse response = new GetGenreByIdOrNameResponse();
            Genre genre = db.getGenre(parameter.getGenre());
            response.setGenre(genre);
            return response;
        }
        return null;
    }

    public ee.ttu.idu0075._143076.mediaservice._1.AddNewGenreResponse addNewGenre(ee.ttu.idu0075._143076.mediaservice._1.AddNewGenreRequest parameter) {
        if (validateToken(parameter.getAPITOKEN())) {
            AddNewGenreResponse response = new AddNewGenreResponse();
            if (addClientId(parameter.getClientId())) {
                Genre genre = db.addGenre(parameter.getName(), parameter.getDescription());
                response.setGenre(genre);
                response.setClientId(parameter.getClientId());
                return response;
            }
        }
        return null;
    }

    public ee.ttu.idu0075._143076.mediaservice._1.EditGenreResponse editGenre(ee.ttu.idu0075._143076.mediaservice._1.EditGenreRequest parameter) {
        if (validateToken(parameter.getAPITOKEN())) {
            EditGenreResponse response = new EditGenreResponse();
            if (addClientId(parameter.getClientId())) {
                Genre genre = db.editGenre(parameter.getGenre());
                response.setGenre(genre);
                response.setClientId(parameter.getClientId());
                return response;
            }
        }
        return null;
    }

    public ee.ttu.idu0075._143076.mediaservice._1.GetAllGenresResponse getAllGenres(ee.ttu.idu0075._143076.mediaservice._1.GetAllGenresRequest parameter) {
        if (validateToken(parameter.getAPITOKEN())) {
            GetAllGenresResponse response = new GetAllGenresResponse();
            GetAllGenresResponse.Genres genresParam = new GetAllGenresResponse.Genres();
            genresParam.getGenre().addAll(db.getGenres(parameter.getFilters()));
            response.setGenres(genresParam);
            return response;
        }
        return null;
    }

    public ee.ttu.idu0075._143076.mediaservice._1.GetMediaByIdResponse getMediaById(ee.ttu.idu0075._143076.mediaservice._1.GetMediaByIdRequest parameter) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ee.ttu.idu0075._143076.mediaservice._1.AddNewMediaResponse addNewMedia(ee.ttu.idu0075._143076.mediaservice._1.AddNewMediaRequest parameter) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ee.ttu.idu0075._143076.mediaservice._1.AddMediaRatingResponse addMediaRating(ee.ttu.idu0075._143076.mediaservice._1.AddMediaRatingRequest parameter) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ee.ttu.idu0075._143076.mediaservice._1.EditMediaResponse editMedia(ee.ttu.idu0075._143076.mediaservice._1.EditMediaRequest parameter) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ee.ttu.idu0075._143076.mediaservice._1.GetMediaByGenreResponse getMediaByGenre(ee.ttu.idu0075._143076.mediaservice._1.GetMediaByGenreRequest parameter) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ee.ttu.idu0075._143076.mediaservice._1.GetAllMediaResponse getAllMedia(ee.ttu.idu0075._143076.mediaservice._1.GetAllMediaRequest parameter) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
