/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice.types;

import mediaservice.types.CustomType;
import java.math.BigDecimal;

/**
 *
 * @author chronoes
 */
public class RatingType implements CustomType<BigDecimal> {
    protected BigDecimal rating;
    
    public RatingType() {}
    
    public RatingType(BigDecimal value) {
        setRating(value);
    }
    
    @Override
    public boolean validate(BigDecimal value) {
        if (value.scale() == 1) {
            return true;
        }
        throw new IllegalArgumentException("Scale of number must be 1");
    }
    
    @Override
    public boolean validate() {
        return validate(rating);
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal value) {
        if (validate(value)) {
            this.rating = value;        
        }
    }

}
