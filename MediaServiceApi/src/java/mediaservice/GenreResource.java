/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import ee.ttu.idu0075._143076.mediaservice._1.*;
import java.math.BigInteger;
import java.util.logging.Level;
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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * REST Web Service
 *
 * @author chronoes
 */
@Path("genre")
public class GenreResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenreResource
     */
    public GenreResource() {
    }
    
    static GenreShortType parseShortType(String id, String name) {
        GenreShortType genre = new GenreShortType();
        if (id == null && name == null) {
            throw new IllegalArgumentException("id or name parameter must be present");
        }
        if (id != null) {
            genre.setId(new BigInteger(id));
        }
        if (name != null) {
            genre.setName(name);
        }
        return genre;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GetGenreByIdOrNameResponse getGenreByIdOrName(
            @QueryParam("token") String token, 
            @QueryParam("id") String id, 
            @QueryParam("name") String name) {
        MediaService service = new MediaService();
        GetGenreByIdOrNameRequest request = new GetGenreByIdOrNameRequest();
        request.setAPITOKEN(token);
        
        request.setGenre(parseShortType(id, name));
        return service.getGenreByIdOrName(request);
    }    

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AddNewGenreResponse addNewGenre(
            @QueryParam("token") String token,
            @QueryParam("clientId") String clientId,
            GenreType genre) {
        MediaService service = new MediaService();
        AddNewGenreRequest request = new AddNewGenreRequest();
        request.setAPITOKEN(token);
        request.setClientId(clientId);
        
        request.setName(genre.getName());
        request.setDescription(genre.getDescription());
        return service.addNewGenre(request);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EditGenreResponse editGenre(
            @QueryParam("token") String token,
            @QueryParam("clientId") String clientId,
            GenreType genre) {
        MediaService service = new MediaService();
        EditGenreRequest request = new EditGenreRequest();
        request.setAPITOKEN(token);
        request.setClientId(clientId);
        
        request.setGenre(genre);
        return service.editGenre(request);
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public GetAllGenresResponse getAllGenres(
            @QueryParam("token") String token,
            @QueryParam("createdBefore") String createdBefore,
            @QueryParam("createdAfter") String createdAfter) {
        MediaService service = new MediaService();
        GetAllGenresRequest request = new GetAllGenresRequest();
        request.setAPITOKEN(token);
        
        GetAllGenresRequest.Filters filters = new GetAllGenresRequest.Filters();
        try {
            DatatypeFactory factory = DatatypeFactory.newInstance();
            if (createdBefore != null) {
                filters.setCreatedBefore(factory.newXMLGregorianCalendar(createdBefore));            
            }
            
            if (createdAfter != null) {
                filters.setCreatedAfter(factory.newXMLGregorianCalendar(createdAfter));
            }
        } catch (DatatypeConfigurationException ex) {
            java.util.logging.Logger.getLogger(GenreResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setFilters(filters);
        return service.getAllGenres(request);
    }    
}
