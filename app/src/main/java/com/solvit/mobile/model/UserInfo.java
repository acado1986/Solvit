package com.solvit.mobile.model;

import java.io.Serializable;

/**
 * Aditional user information
 */
public class UserInfo implements Serializable {

    private String email;
    private Role role;
    private Boolean active;

    public UserInfo(String email, Role role, Boolean active) {
        this.email = email;
        this.role = role;
        this.active = active;
    }

    public UserInfo() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
