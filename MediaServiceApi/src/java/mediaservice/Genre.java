/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import ee.ttu.idu0075._143076.mediaservice._1.GenreShortType;
import ee.ttu.idu0075._143076.mediaservice._1.GenreType;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
/**
 *
 * @author chronoes
 */
public class Genre extends GenreType {
    
    public Genre(BigInteger id, String name, String description) {
        genreId = id;
        this.name = name;
        try {
            this.createdAt = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(Genre.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.description = description;
        
    }

    @Override
    public void setGenreId(BigInteger genreId) {}

    @Override
    public void setCreatedAt(XMLGregorianCalendar value) {}

    GenreShortType getShortType() {
       GenreShortType type = new GenreShortType();
       type.setId(genreId);
       type.setName(name);
       return type;
    }
}
