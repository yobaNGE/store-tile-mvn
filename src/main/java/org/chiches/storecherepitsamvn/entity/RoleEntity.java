package org.chiches.storecherepitsamvn.entity;

import jakarta.persistence.*;
import org.chiches.storecherepitsamvn.entity.consts.Roles;

import java.util.List;

@Entity
public class RoleEntity extends BaseEntity {
    private Roles role;
    private List<UserEntity> users;

    public RoleEntity(Roles role) {
        this.role = role;
    }

    protected RoleEntity() {}

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
    @ManyToMany(mappedBy = "roles")
    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
