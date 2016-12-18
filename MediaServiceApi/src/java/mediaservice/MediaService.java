/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import mediaservice.types.TokenType;
import ee.ttu.idu0075._143076.mediaservice._1.*;
import java.util.stream.Collectors;
import javax.jws.WebService;

/**
 *
 * @author chronoes
 */
@WebService(serviceName = "MediaService", portName = "MediaPort", endpointInterface = "ee.ttu.idu0075._143076.mediaservice._1.MediaPortType", targetNamespace = "http://www.ttu.ee/idu0075/143076/MediaService/1.0", wsdlLocation = "WEB-INF/wsdl/MediaService/project.wsdl")
public class MediaService {
    private static final TokenType API_TOKEN = new TokenType("realToken123");
    private static final TokenType API_TEST_TOKEN = new TokenType("TESTTOKEN");
    // Reference to current database
    private MockDatabase db;
    private static final MockDatabase realDb = new MockDatabase().fillDatabase();
    private static final MockDatabase testDb = new MockDatabase().fillDatabase();
    private static final ClientManager clientManager = new ClientManager(10);

    public MediaService() {
        db = realDb;
    }
    
    private boolean validateToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Missing required token");
        }
        TokenType t = new TokenType(token);
        if (t.equals(API_TOKEN)) {
            db = realDb;
            return true;
        } else if (t.equals(API_TEST_TOKEN)) {
            db = testDb;
            return true;
        }
        return false;
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
            if (clientManager.addClientId(parameter.getClientId())) {
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
            if (clientManager.addClientId(parameter.getClientId())) {
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
        if (validateToken(parameter.getAPITOKEN())) {
            GetMediaByIdResponse response = new GetMediaByIdResponse();
            Media media = db.getMedia(parameter.getMediaId()).xmlRepresentation();
            response.setMedia(media);
            return response;
        }
        return null;
    }

    public ee.ttu.idu0075._143076.mediaservice._1.AddNewMediaResponse addNewMedia(ee.ttu.idu0075._143076.mediaservice._1.AddNewMediaRequest parameter) {
        if (validateToken(parameter.getAPITOKEN())) {
            AddNewMediaResponse response = new AddNewMediaResponse();
            if (clientManager.addClientId(parameter.getClientId())) {
                Media media = db.addMedia(parameter.getType(), parameter.getName(), parameter.getDescription(), parameter.getGenres().getGenre());
                response.setMedia(media.xmlRepresentation());
                response.setClientId(parameter.getClientId());
                return response;
            }
        }
        return null;
    }

    public ee.ttu.idu0075._143076.mediaservice._1.AddMediaRatingResponse addMediaRating(ee.ttu.idu0075._143076.mediaservice._1.AddMediaRatingRequest parameter) {
        if (validateToken(parameter.getAPITOKEN())) {
            AddMediaRatingResponse response = new AddMediaRatingResponse();
            if (clientManager.addClientId(parameter.getClientId())) {
                Media media = db.addMediaRating(parameter.getMediaId(), parameter.getRating());
                if (media == null) {
                    return null;
                }
                response.setMedia(media.xmlRepresentation());
                response.setClientId(parameter.getClientId());
                return response;
            }
        }
        return null;
    }

    public ee.ttu.idu0075._143076.mediaservice._1.EditMediaResponse editMedia(ee.ttu.idu0075._143076.mediaservice._1.EditMediaRequest parameter) {
        if (validateToken(parameter.getAPITOKEN())) {
            EditMediaResponse response = new EditMediaResponse();
            if (clientManager.addClientId(parameter.getClientId())) {
                Media media = db.editMedia(parameter.getMedia());
                response.setMedia(media.xmlRepresentation());
                response.setClientId(parameter.getClientId());
                return response;
            }
        }
        return null;
    }

    public ee.ttu.idu0075._143076.mediaservice._1.GetMediaByGenreResponse getMediaByGenre(ee.ttu.idu0075._143076.mediaservice._1.GetMediaByGenreRequest parameter) {
        if (validateToken(parameter.getAPITOKEN())) {
            GetMediaByGenreResponse response = new GetMediaByGenreResponse();
            GetMediaByGenreResponse.Media responseMedia = new GetMediaByGenreResponse.Media();
            responseMedia.getMedia().addAll(db.getMediaByGenre(parameter.getGenre())
                    .stream()
                    .map(Media::xmlRepresentation)
                    .collect(Collectors.toList()));
            response.setMedia(responseMedia);
            return response;
        }
        return null;
    }

    public ee.ttu.idu0075._143076.mediaservice._1.GetAllMediaResponse getAllMedia(ee.ttu.idu0075._143076.mediaservice._1.GetAllMediaRequest parameter) {
        if (validateToken(parameter.getAPITOKEN())) {
            GetAllMediaResponse response = new GetAllMediaResponse();
            GetAllMediaResponse.Media mediaParam = new GetAllMediaResponse.Media();
            mediaParam.getMedia().addAll(db.getMediaList(parameter.getFilters())
                    .stream()
                    .map(Media::xmlRepresentation)
                    .collect(Collectors.toList())
            );
            response.setMedia(mediaParam);
            return response;
        }
        return null;
    }
    
}
