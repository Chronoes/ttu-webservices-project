/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import ee.ttu.idu0075._143076.mediaservice._1.GenreShortType;
import ee.ttu.idu0075._143076.mediaservice._1.GenreType;
import ee.ttu.idu0075._143076.mediaservice._1.GetAllGenresRequest;
import ee.ttu.idu0075._143076.mediaservice._1.MediaType.Genres;
import ee.ttu.idu0075._143076.mediaservice._1.TypeOfMediaType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.datatype.DatatypeConstants;

/**
 *
 * @author chronoes
 */
class MockDatabase {
    private BigInteger genresSequence = BigInteger.ZERO;
    private BigInteger mediaSequence = BigInteger.ZERO;
    private final Map<BigInteger, Genre> genres = new HashMap<>();
    private final Map<String, BigInteger> genresByName = new HashMap<>();
    private final Map<BigInteger, Media> media = new HashMap<>();

    MockDatabase() {
        fillDatabase();
    }
    
    private BigInteger nextGenresId() {
        genresSequence = genresSequence.add(BigInteger.ONE);
        return genresSequence;
    }
    
    private BigInteger nextMediaId() {
        mediaSequence = mediaSequence.add(BigInteger.ONE);
        return mediaSequence;
    }
    
    private void fillDatabase() {
        List<Genre> genres = new ArrayList();
        genres.add(addGenre("Action", "Things happen here explosively"));
        genres.add(addGenre("Adventure", "Things happen here progressively"));
        genres.add(addGenre("Drama", "Things happen here and get repeated over and over for extra effect"));
        genres.add(addGenre("Romance", "Things happen here between two or more people in love"));
        genres.add(addGenre("Classical", "Things happen here in a classical way"));
        genres.add(addGenre("Electronic", "Things happen here in a clickity-clack beep-boop way"));
        
        List<Genre> specificGenres = new ArrayList();
        specificGenres.add(genres.get(2));
        specificGenres.add(genres.get(3));
        addMedia(TypeOfMediaType.BOOK, "256 Shades of Grey", "8-bit representation of colour", specificGenres);
        specificGenres.clear();
        
        specificGenres.add(genres.get(1));
        addMedia(TypeOfMediaType.MOVIE, "Fast & Furious 1337", "It's pretty fast (and furious)", specificGenres);
        specificGenres.clear();
        
        addMedia(TypeOfMediaType.OTHER, "Some OTHER type media", "Contains all 5 initial genres", genres.subList(0, 4));
        
        specificGenres.add(genres.get(0));
        specificGenres.add(genres.get(1));
        addMedia(TypeOfMediaType.MOVIE, "Indiana Jones and the Endless Series", "Yet another installment of the adventurer", specificGenres);
        specificGenres.clear();
        
        specificGenres.add(genres.get(4));
        addMedia(TypeOfMediaType.MUSIC, "Claude Debussy - Clair de Lune", "A most beautiful melodical impressionist piece https://www.youtube.com/watch?v=CvFH_6DNRCY", specificGenres);
        specificGenres.clear();
    }
    
    Genre addGenre(String name, String description) {
        Genre g = new Genre(nextGenresId(), name, description);
        genres.put(g.getGenreId(), g);
        genresByName.put(g.getName().toLowerCase(), g.getGenreId());
        return g;
    }
       
    Genre editGenre(GenreType genre) {
        Genre g = genres.get(genre.getGenreId());
        g.setName(genre.getName());
        g.setDescription(genre.getDescription());
        return g;
    }

    Media addMedia(TypeOfMediaType type, String name, String description, List<Genre> genres) {
        Media m = new Media(nextMediaId(), type, name, description);
        Genres g = new Genres();
        for (Genre genre : genres) {
            g.getGenre().add(genre.getShortType());
        }
        m.setGenres(g);
        media.put(m.getMediaId(), m);
        return m;
    }

    Genre getGenre(GenreShortType genre) {
        if (genre.getId() != null) {
            return genres.get(genre.getId());
        }
        return genres.get(genresByName.get(genre.getName().toLowerCase()));
    }

    Collection<? extends GenreType> getGenres(GetAllGenresRequest.Filters filters) {
        if (filters == null) {
            return genres.values();
        }
        return genres.values()
                .stream()
                .filter(g -> {
                    Genre genre = (Genre) g;
                    boolean include = true;
                    if (filters.getCreatedBefore() != null) {
                        include = include && filters.getCreatedBefore().compare(genre.getCreatedAt()) == DatatypeConstants.GREATER;
                    }
                    if (filters.getCreatedAfter() != null) {
                        include = include && filters.getCreatedAfter().compare(genre.getCreatedAt()) == DatatypeConstants.LESSER;
                    }
                    return include;
                })
                .collect(Collectors.toList());
    }
}
