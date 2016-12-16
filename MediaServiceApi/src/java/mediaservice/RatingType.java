/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice;

import java.math.BigDecimal;

/**
 *
 * @author chronoes
 */
class RatingType implements CustomType<BigDecimal> {
    private BigDecimal value;
    
    RatingType() {}
    
    RatingType(BigDecimal value) {
        setValue(value);
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
        return validate(value);
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public void setValue(BigDecimal value) {
        if (validate(value)) {
            this.value = value;        
        }
    }

}
