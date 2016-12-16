/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import ee.ttu.idu0075._143076.mediaservice._1.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import mediaservice.types.RatingTypeRS;

/**
 * REST Web Service
 *
 * @author chronoes
 */
@Path("media")
public class MediaResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MediaResource
     */
    public MediaResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GetMediaByIdResponse getMediaById(
            @QueryParam("token") String token, 
            @QueryParam("id") String id) {
        MediaService service = new MediaService();
        GetMediaByIdRequest request = new GetMediaByIdRequest();
        request.setAPITOKEN(token);
        
        if (id == null) {
            throw new IllegalArgumentException("id parameter must be present");
        }
        request.setMediaId(new BigInteger(id));
        return service.getMediaById(request);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AddNewMediaResponse addNewGenre(
            @QueryParam("token") String token,
            @QueryParam("clientId") String clientId,
            ee.ttu.idu0075._143076.mediaservice._1.MediaType media) {
        MediaService service = new MediaService();
        AddNewMediaRequest request = new AddNewMediaRequest();
        request.setAPITOKEN(token);
        request.setClientId(clientId);
        
        request.setType(media.getType());
        request.setName(media.getName());
        request.setDescription(media.getDescription());
        
        AddNewMediaRequest.Genres genres = new AddNewMediaRequest.Genres();
        genres.getGenre().addAll(media.getGenres().getGenre());
        request.setGenres(genres);
        
        return service.addNewMedia(request);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EditMediaResponse editGenre(
            @QueryParam("token") String token,
            @QueryParam("clientId") String clientId,
            ee.ttu.idu0075._143076.mediaservice._1.MediaType media) {
        MediaService service = new MediaService();
        EditMediaRequest request = new EditMediaRequest();
        request.setAPITOKEN(token);
        request.setClientId(clientId);
        
        request.setMedia(media);
        return service.editMedia(request);
    }
    
        
    @POST
    @Path("/rating")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AddMediaRatingResponse addMediaRating(
            @QueryParam("token") String token,
            @QueryParam("clientId") String clientId,
            RatingTypeRS rating) {
        MediaService service = new MediaService();
        AddMediaRatingRequest request = new AddMediaRatingRequest();
        request.setAPITOKEN(token);
        request.setClientId(clientId);
        
        request.setMediaId(rating.getMediaId());
        request.setRating(rating.getRating());
        return service.addMediaRating(request);
    }
    
    @GET
    @Path("/list/byGenre")
    @Produces(MediaType.APPLICATION_JSON)
    public GetMediaByGenreResponse getMediaByGenre(
            @QueryParam("token") String token, 
            @QueryParam("id") String id, 
            @QueryParam("name") String name) {
        MediaService service = new MediaService();
        GetMediaByGenreRequest request = new GetMediaByGenreRequest();
        request.setAPITOKEN(token);
        
        request.setGenre(GenreResource.parseShortType(id, name));
        return service.getMediaByGenre(request);
    }
        
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public GetAllMediaResponse getAllMedia(
            @QueryParam("token") String token, 
            @QueryParam("ratingLessThan") BigDecimal ratingLessThan,
            @QueryParam("ratingMoreThan") BigDecimal ratingMoreThan,
            @QueryParam("type") TypeOfMediaType type,
            @QueryParam("genreId") List<BigInteger> genreIds,
            @QueryParam("genreName") List<String> genreNames,
            @QueryParam("keywords") String keywords) {
        MediaService service = new MediaService();
        GetAllMediaRequest request = new GetAllMediaRequest();
        request.setAPITOKEN(token);
        
        GetAllMediaRequest.Filters filters = new GetAllMediaRequest.Filters();
        filters.setKeywords(keywords);
        filters.setRatingLessThan(ratingLessThan);
        filters.setRatingMoreThan(ratingMoreThan);
        filters.setType(type);
        
        if (!genreIds.isEmpty() || !genreNames.isEmpty()) {
            GetAllMediaRequest.Filters.Genres genres = new GetAllMediaRequest.Filters.Genres();

            for (BigInteger genreId : genreIds) {
                GenreShortType g = new GenreShortType();
                g.setId(genreId);
                genres.getGenre().add(g);
            }

            for (String genreName : genreNames) {
                GenreShortType g = new GenreShortType();
                g.setName(genreName);
                genres.getGenre().add(g);
            }

            filters.setGenres(genres);            
        }
        
        request.setFilters(filters);
        return service.getAllMedia(request);
    }
    
}
