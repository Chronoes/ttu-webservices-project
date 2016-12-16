/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaservice.types;

/**
 *
 * @author chronoes
 */
interface CustomType<T> {
    boolean validate();
    boolean validate(T value);
}
