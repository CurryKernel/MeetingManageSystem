package cn.edu.ncu.meeting.user.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * User Role Model.
 * @author lorry
 * @author lin864464995@163.com
 * @see org.springframework.security.core.GrantedAuthority
 */
@Entity
public class UserRole implements GrantedAuthority, Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private User user;

    @Id
    @ManyToOne
    @JoinColumn
    private Role role;

    public UserRole() { }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UserRole)) return false;
        UserRole tmp = (UserRole) o;
        return tmp.user.equals(user) && tmp.role.equals(role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }

    @Override
    public String toString() {
        return getAuthority();
    }
}

