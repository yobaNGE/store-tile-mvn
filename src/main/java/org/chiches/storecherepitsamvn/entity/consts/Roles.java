package org.chiches.storecherepitsamvn.entity.consts;

public enum Roles {
    USER, STAFF, ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
