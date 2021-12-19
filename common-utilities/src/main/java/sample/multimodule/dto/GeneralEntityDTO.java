/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author brijeshdhaker
 */
public class GeneralEntityDTO {
    
    private long id;
    private String name;
    private String value;
    private Map<String,String> attributes = new HashMap<>();
    
    public GeneralEntityDTO() {
        
    }
    
    public GeneralEntityDTO(long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
