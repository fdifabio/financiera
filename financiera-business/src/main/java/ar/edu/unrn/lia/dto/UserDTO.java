package ar.edu.unrn.lia.dto;

import ar.edu.unrn.lia.model.Role;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;


public class UserDTO extends AbstractDTO<Long> implements Serializable {

    private static final long serialVersionUID = 327336392782096608L;

    private String username;
    private String password;
    private String email;
    private boolean active = false;
    private Role role;

    public UserDTO(Long id) {
        this.id = id;
    }

    public UserDTO(Long id,String username, String email, boolean active, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.active = active;
        this.role = role;
    }

    public UserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public UserDTO(Role role) {
        super();

        this.role = role;
    }

    public UserDTO() {
        // TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public boolean isEnabled() {
        return active;
    }

    public void setEnabled(boolean active) {
        this.active = active;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = true)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
