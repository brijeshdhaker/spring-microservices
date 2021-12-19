/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brijeshdhaker
 */
public class UserRequestDTO implements Serializable {
    
    private Long id;
    private String uuid;
    private String name;
    private String emailAddr;
    private int age;
    private int dob;
    private float height;
    private String status;
    private java.util.List<java.lang.String> roles = new ArrayList<>();
    public Long addTs;
    public Long updTs;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public void addRoles(String role) {
        this.roles.add(role);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    
    public Long getAddTs() {
        return addTs;
    }

    public void setAddTs(Long addTs) {
        this.addTs = addTs;
    }

    public Long getUpdTs() {
        return updTs;
    }

    public void setUpdTs(Long updTs) {
        this.updTs = updTs;
    }
}
