/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;
import ee.ttu.idu0075._143076.mediaservice._1.MediaType;
import ee.ttu.idu0075._143076.mediaservice._1.TypeOfMediaType;
import java.math.BigInteger;
/**
 *
 * @author chronoes
 */
class Media extends MediaType {

    public Media(BigInteger id, TypeOfMediaType type, String name, String description) {
        mediaId = id;
        this.type = type;
        this.name = name;
        this.description = description;
    }
}
