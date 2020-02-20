/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 6.0
 */
public class StringContainsCharacters extends Exception{
    
    public StringContainsCharacters(){}


    /**
     * constructor
     *
     * @param message exception info
     */
    public StringContainsCharacters(String message) {
        super(message);
    }
}
