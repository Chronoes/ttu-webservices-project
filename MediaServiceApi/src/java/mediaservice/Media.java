/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;
import ee.ttu.idu0075._143076.mediaservice._1.GenreShortType;
import ee.ttu.idu0075._143076.mediaservice._1.MediaType;
import ee.ttu.idu0075._143076.mediaservice._1.TypeOfMediaType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 *
 * @author chronoes
 */
class Media extends MediaType {
    private List<RatingType> ratings = new ArrayList();

    public Media(BigInteger id, TypeOfMediaType type, String name, String description) {
        mediaId = id;
        this.type = type;
        this.name = name;
        this.description = description;
    }
    
    void addRating(RatingType rating) {
        ratings.add(rating);
        
        Optional<BigDecimal> optRating = ratings.stream()
                .map(RatingType::getValue)
                .reduce(BigDecimal::add);
        if (optRating.isPresent()) {
            aggregateRating = optRating.get().divide(BigDecimal.valueOf(ratings.size()), RoundingMode.HALF_UP).setScale(1);
        }
    }
    
    boolean hasGenre(GenreShortType genre) {
        return genres.getGenre()
                .stream()
                .anyMatch(g -> 
                        g.getId() != null && g.getId().equals(genre.getId()) || 
                        g.getName() != null && g.getName().equalsIgnoreCase(genre.getName()));
    }
    
    Media xmlRepresentation() {
        Media media = new Media(mediaId, type, name, description);
        media.ratings = ratings;
        media.aggregateRating = aggregateRating;
        Media.Genres newGenres = new Media.Genres();
        newGenres.getGenre()
                .addAll(genres.getGenre()
                    .stream()
                    .map((GenreShortType g) -> {
                        GenreShortType genre = new GenreShortType();
                        genre.setId(g.getId());
                        return genre;
                    })
                    .collect(Collectors.toList()));
        media.genres = newGenres;
        return media;
        
    }
    
    @Override
    public void setGenres(Genres genres) {
        if (genres.getGenre().size() < 1 || genres.getGenre().size() > 5) {
            throw new IllegalArgumentException("Media must have between 1 and 5 genres");
        }
        super.setGenres(genres);
    }
    

    @Override
    public void setMediaId(BigInteger value) {}
    
    @Override
    public void setAggregateRating(BigDecimal value) {}
}
