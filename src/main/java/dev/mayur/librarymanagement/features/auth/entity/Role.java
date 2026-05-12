package dev.mayur.librarymanagement.features.auth.entity;


// Spring Security looks for roles prefixed with "ROLE_"
// So ROLE_ADMIN and ROLE_MEMBER are the actual authority strings
public enum Role {
    ADMIN,
    MEMBER
}

