package com.grekoff.market.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;

@Jacksonized
@Getter
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель пользователя")
public class UserDto {
    @Schema(description = "ID пользователя", requiredMode = Schema.RequiredMode.AUTO,  example = "1")
    public Long id;
    @Schema(description = "Имя пользователя в сети", requiredMode = Schema.RequiredMode.REQUIRED,  example = "user")
    private String username;
    @Schema(description = "Пароль пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "PROTECTED")
    private String password;
    @Schema(description = "Имя пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "Иван")
    private String firstname;
    @Schema(description = "Фамилия пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "Иванов")
    private String lastname;
    @Schema(description = "Адрес электронной почты пользователя", requiredMode = Schema.RequiredMode.REQUIRED,  example = "user@gmail.com")
    private String email;
    @Schema(description = "Список ролей пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    private Collection<RoleDto> roles;

    private UserDto(Builder builder) {
        id = builder.id;
        username = builder.username;
        password = builder.password;
        firstname = builder.firstname;
        lastname = builder.lastname;
        email = builder.email;
        roles = builder.roles;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(UserDto copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.username = copy.getUsername();
        builder.password = copy.getPassword();
        builder.firstname = copy.getFirstname();
        builder.lastname = copy.getLastname();
        builder.email = copy.getEmail();
        builder.roles = copy.getRoles();
        return builder;
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Collection<RoleDto> getRoles() {
        return roles;
    }

    public static final class Builder {
        private Long id;
        private String username;
        private String password;
        private String firstname;
        private String lastname;
        private String email;
        private Collection<RoleDto> roles;

        private Builder() {
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withPassword(String val) {
            password = val;
            return this;
        }

        public Builder withFirstname(String val) {
            firstname = val;
            return this;
        }

        public Builder withLastname(String val) {
            lastname = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withRoles(Collection<RoleDto> val) {
            roles = val;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }


//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setRoles(Collection<RoleDto> roles) {
//        this.roles = roles;
//    }
//
//    public UserDto() {
//    }
//
//    public UserDto(Long id, String username, String password, String firstname, String lastname, String email, Collection<RoleDto> roles) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.email = email;
//        this.roles = roles;
//    }
}
