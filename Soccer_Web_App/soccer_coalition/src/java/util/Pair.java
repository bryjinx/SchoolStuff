/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author bryonnaklumker
 */
public class Pair {
    private int key;
    private int value;
    
    public Pair() {
        
    }
    
    public Pair(int key, int value) {
        this.key = key;
        this.value = value;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(int key) {
        this.key = key;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
}
