package ir.sharif.iam.iam.domain.models;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    ROLE_CREATE_USER, ROLE_UPDATE_USER, ROLE_DELETE_USER, ROLE_CREATE_ROLE;

    @Override
    public String getAuthority() {
        return this.toString();
    }

    public static class Code {
        public static final String ROLE_CREATE_USER = "ROLE_CREATE_USER";
        public static final String ROLE_UPDATE_USER = "ROLE_UPDATE_USER";
        public static final String ROLE_DELETE_USER = "ROLE_DELETE_USER";
        public static final String ROLE_CREATE_ROLE = "ROLE_CREATE_ROLE";

        private Code() {
        }
    }

}
