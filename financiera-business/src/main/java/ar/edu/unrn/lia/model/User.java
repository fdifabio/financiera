package ar.edu.unrn.lia.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class User extends BaseEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = 327336392782096608L;

    public static final short MAX_FAILED_LOGIN_ATTEMPTS = 5;

    private String username;
    private String password;
    private String email;
    private boolean active = false;
    private Role role;
    private List<Cliente> clientes;
    private int failedLoginAttempts;


    public User(String username, String password, boolean active, Role role) {
        super();
        this.username = username;
        this.password = password;
        this.active = active;
        this.role = role;
        this.clientes = new ArrayList<>();
    }

    public User(Role role) {
        super();
        this.role = role;
    }

    public User() {
        // TODO Auto-generated constructor stub
    }

    @NotNull(message = "{name.notnull}")
    @Column(name = "password", nullable = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "{name.notnull}")
    @Column(name = "username", unique = true, nullable = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    public boolean isAccountNonLocked() {
        return this.failedLoginAttempts < MAX_FAILED_LOGIN_ATTEMPTS;
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // @Transient
    public boolean isEnabled() {
        return active;
    }

    public void setEnabled(boolean active) {
        this.active = active;
    }

    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        result.add(this.getRole());
        return result;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = true)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "intentos_de_login")
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public void increaseFailedLoginAttempts() {
        setFailedLoginAttempts(getFailedLoginAttempts() + 1);
    }

    public void resetFailedLoginAttempts() {
        setFailedLoginAttempts(0);
    }

    @Column(name = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
    @NotNull(message = "{name.notnull}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
