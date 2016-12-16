/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import mediaservice.types.RatingType;
import ee.ttu.idu0075._143076.mediaservice._1.GenreShortType;
import ee.ttu.idu0075._143076.mediaservice._1.GenreType;
import ee.ttu.idu0075._143076.mediaservice._1.GetAllGenresRequest;
import ee.ttu.idu0075._143076.mediaservice._1.GetAllMediaRequest;
import ee.ttu.idu0075._143076.mediaservice._1.MediaType;
import ee.ttu.idu0075._143076.mediaservice._1.TypeOfMediaType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    private BigInteger nextGenresId() {
        genresSequence = genresSequence.add(BigInteger.ONE);
        return genresSequence;
    }
    
    private BigInteger nextMediaId() {
        mediaSequence = mediaSequence.add(BigInteger.ONE);
        return mediaSequence;
    }
    
    MockDatabase fillDatabase() {
        List<GenreShortType> genres = new ArrayList();
        genres.add(addGenre("Action", "Things happen here explosively").getShortType());
        genres.add(addGenre("Adventure", "Things happen here progressively").getShortType());
        genres.add(addGenre("Drama", "Things happen here and get repeated over and over for extra effect").getShortType());
        genres.add(addGenre("Romance", "Things happen here between two or more people in love").getShortType());
        genres.add(addGenre("Classical", "Things happen here in a classical way").getShortType());
        genres.add(addGenre("Electronic", "Things happen here in a clickity-clack beep-boop way").getShortType());
        
        List<GenreShortType> specificGenres = new ArrayList();
        specificGenres.add(genres.get(2));
        specificGenres.add(genres.get(3));
        addMedia(TypeOfMediaType.BOOK, "256 Shades of Grey", "8-bit representation of colour", specificGenres);
        specificGenres.clear();
        
        specificGenres.add(genres.get(1));
        addMedia(TypeOfMediaType.MOVIE, "Fast & Furious 1337", "It's pretty fast (and furious)", specificGenres);
        specificGenres.clear();
        
        addMedia(TypeOfMediaType.OTHER, "Some OTHER type media", "Contains all 5 initial genres", genres.subList(0, 5));
        
        specificGenres.add(genres.get(0));
        specificGenres.add(genres.get(1));
        addMedia(TypeOfMediaType.MOVIE, "Indiana Jones and the Endless Series", "Yet another installment of the adventurer", specificGenres);
        specificGenres.clear();
        
        specificGenres.add(genres.get(4));
        addMedia(TypeOfMediaType.MUSIC, "Claude Debussy - Clair de Lune", "A most beautiful melodical impressionist piece https://www.youtube.com/watch?v=CvFH_6DNRCY", specificGenres);
        specificGenres.clear();
        
        return this;
    }
    
    Genre addGenre(String name, String description) {
        Genre g = new Genre(nextGenresId(), name, description);
        genres.put(g.getGenreId(), g);
        genresByName.put(g.getName().toLowerCase(), g.getGenreId());
        return g;
    }
       
    Genre editGenre(GenreType genre) {
        Genre g = genres.get(genre.getGenreId());
        if (g == null) {
            throw new IllegalArgumentException("No such genre exists");
        }
        g.setName(genre.getName());
        g.setDescription(genre.getDescription());
        return g;
    }

    Genre getGenre(GenreShortType genre) {
        if (genre.getId() != null) {
            return genres.get(genre.getId());
        }
        return genres.get(genresByName.get(genre.getName().toLowerCase()));
    }

    Collection<Genre> getGenres(GetAllGenresRequest.Filters filters) {
        if (filters == null) {
            return genres.values();
        }
        return genres.values()
                .stream()
                .filter((Genre genre) -> {
                    boolean include = true;
                    if (filters.getCreatedBefore() != null) {
                        include = filters.getCreatedBefore().compare(genre.getCreatedAt()) == DatatypeConstants.GREATER;
                    }
                    if (include && filters.getCreatedAfter() != null) {
                        include = filters.getCreatedAfter().compare(genre.getCreatedAt()) == DatatypeConstants.LESSER;
                    }
                    return include;
                })
                .collect(Collectors.toList());
    }

    Media getMedia(BigInteger mediaId) {
        return media.get(mediaId);
    }
    
    private List<GenreShortType> getGenresByShortTypes(Collection<GenreShortType> genres) throws IllegalArgumentException {
        List<GenreShortType> genresList = new ArrayList();
        for (GenreShortType genre : genres) {
            if (this.genres.containsKey(genre.getId())) {
                genresList.add(this.genres.get(genre.getId()).getShortType());
            } else if (this.genresByName.containsKey(genre.getName().toLowerCase())) {
                genresList.add(this.genres.get(genresByName.get(genre.getName().toLowerCase())).getShortType());
            } else {
                throw new IllegalArgumentException("No such genre exists: " + (genre.getId() != null ? genre.getId() : genre.getName()));
            }
        }
        return genresList;
    }

    Media addMedia(TypeOfMediaType type, String name, String description, Collection<GenreShortType> genres) {
        Media m = new Media(nextMediaId(), type, name, description);
        Media.Genres g = new Media.Genres();
        g.getGenre().addAll(getGenresByShortTypes(genres));        
        m.setGenres(g);
        media.put(m.getMediaId(), m);
        return m;
    }

    Media editMedia(MediaType media) {
        Media m = this.media.get(media.getMediaId());
        if (m == null) {
            throw new IllegalArgumentException("No such media exists");
        }
        m.setType(media.getType());
        m.setName(media.getName());
        m.setDescription(media.getDescription());
        m.getGenres().getGenre().clear();
        m.getGenres().getGenre().addAll(getGenresByShortTypes(media.getGenres().getGenre()));        
        return m;
    }

    Media addMediaRating(BigInteger mediaId, BigDecimal rating) {
        if (!media.containsKey(mediaId)) {
            return null;
        }
        Media m = media.get(mediaId);
        m.addRating(new RatingType(rating));
        return m;
    }

    Collection<Media> getMediaByGenre(GenreShortType genre) {
        return media.values()
                .stream()
                .filter(m -> m.hasGenre(genre))
                .collect(Collectors.toList());
    }

    Collection<Media> getMediaList(GetAllMediaRequest.Filters filters) {
        if (filters == null) {
            return media.values();
        }
        return media.values()
                .stream()
                .filter((Media currentMedia) -> {
                    boolean include = true;
                    if (filters.getType() != null) {
                        include = filters.getType() == currentMedia.getType();
                    }
                    
                    if (include && filters.getRatingLessThan() != null) {
                        include = currentMedia.getAggregateRating() == null || 
                                filters.getRatingLessThan().compareTo(currentMedia.getAggregateRating()) > 0;
                    }
                    if (include && filters.getRatingMoreThan() != null) {
                        include = currentMedia.getAggregateRating() == null ||
                                filters.getRatingMoreThan().compareTo(currentMedia.getAggregateRating()) < 0;
                    }
                    
                    if (include && filters.getGenres() != null) {
                        include = filters.getGenres().getGenre().stream().anyMatch(currentMedia::hasGenre);
                    }
                    
                    if (include && filters.getKeywords() != null) {
                        final String searchable = (currentMedia.getName() + 
                                (currentMedia.getDescription() != null ? " " + currentMedia.getDescription() : ""))
                                .toLowerCase();
                        include = Arrays.asList(filters.getKeywords().split(" "))
                                .stream()
                                .anyMatch((String keyword) -> searchable.contains(keyword.toLowerCase()));
                    }
                    return include;
                })
                .collect(Collectors.toList());
    }
}
