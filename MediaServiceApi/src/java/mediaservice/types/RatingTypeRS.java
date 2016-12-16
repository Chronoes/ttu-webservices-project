/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice.types;

import java.math.BigInteger;

/**
 *
 * @author chronoes
 */
public class RatingTypeRS extends RatingType {
    protected BigInteger mediaId;

    public BigInteger getMediaId() {
        return mediaId;
    }

    public void setMediaId(BigInteger mediaId) {
        this.mediaId = mediaId;
    }
}
