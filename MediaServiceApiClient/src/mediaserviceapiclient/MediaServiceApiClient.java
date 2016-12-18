/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaserviceapiclient;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author chronoes
 */
public class MediaServiceApiClient {
    static final String API_TOKEN = "realToken123";
    static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(mediaListToString(getMediaByGenre("classical")));
        System.out.println();
        
        List<String> genres = new ArrayList();
        genres.add("classical");
        System.out.println(mediaToString(addNewMedia(
                TypeOfMediaType.MUSIC, 
                "Johann Strauss II - Kaiserwalzer",  
                "Amazing waltz by Strauss, very engaging https://www.youtube.com/watch?v=FkoRSojz7_g",
                genres)));
        System.out.println();
        
        System.out.println(mediaListToString(getMediaByGenre("classical")));
    }
    
    static GenreShortType getGenreShortType(String genreNameOrId) {
        GenreShortType genre = new GenreShortType();
        if (genreNameOrId.matches("^[0-9]+$")) {
            genre.setId(new BigInteger(genreNameOrId));
        } else {
            genre.setName(genreNameOrId);
        }
        return genre;
    }
    
    static List<MediaType> getMediaByGenre(String genreNameOrId) {
        GetMediaByGenreRequest request = new GetMediaByGenreRequest();
        request.setAPITOKEN(API_TOKEN);
        
        request.setGenre(getGenreShortType(genreNameOrId));
        
        MediaService service = new MediaService();
        GetMediaByGenreResponse response = service.getMediaPort().getMediaByGenre(request);
        return response.getMedia().getMedia();
    }

    static MediaType addNewMedia(TypeOfMediaType type, String name, String description, List<String> genreNamesOrIds) {
        AddNewMediaRequest request = new AddNewMediaRequest();
        request.setAPITOKEN(API_TOKEN);
        request.setClientId(randomId());
        
        request.setType(type);
        request.setName(name);
        request.setDescription(description);
        
        AddNewMediaRequest.Genres genres = new AddNewMediaRequest.Genres();
        
        for (String genreNameOrId : genreNamesOrIds) {
            genres.getGenre().add(getGenreShortType(genreNameOrId));
        }
        
        request.setGenres(genres);
        
        MediaService service = new MediaService();
        AddNewMediaResponse response = service.getMediaPort().addNewMedia(request);
        return response.getMedia();
    }
    
    static String mediaToString(MediaType media) {
        return String.format(
                "Media ID %d {type = %s}\n"
                + "  %s\n"
                + "  %s\n"
                + "  - rating: %s\n"
                + "  - genres: %s",
                media.getMediaId(),
                media.getType(), 
                media.getName(), 
                media.getDescription(), 
                media.getAggregateRating(),
                genreListToString(media.getGenres().getGenre()));
    }

    static String mediaListToString(List<MediaType> media) {
        return "------ List of media -------\n" 
                + media.stream().map(m -> mediaToString(m)).reduce((str, m) -> str + "\n" + m).get()
                + "\n--------------------------";
    }
    
    static String genreListToString(List<GenreShortType> genres) {
        return genres.stream()
                .map(g -> g.getId() != null ? g.getId() : g.getName())
                .collect(Collectors.toList())
                .toString();
    }
    
    private static String randomId() {
        Random rng = new Random();
        StringBuilder sb = new StringBuilder();
        int length = 8 + rng.nextInt(9);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(rng.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
