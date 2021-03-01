package ua.servlet.restaurant.dao.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonPropertyOrder(alphabetic = true)
public class Logins {
    private Long id;
    private String login;
    private String password;
    private String email;
    private RoleType role;
    private LocalDateTime time;

    public Logins() {}

    // todo delete constructors if they not necessary
    // todo use builders if there are more than tree fields

    public Logins(Long id, String login, String password,
                  String email, RoleType role, LocalDateTime time) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.time = time;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRole() {
        return role;
    }
    public void setRole(RoleType role) {
        this.role = role;
    }

    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logins logins = (Logins) o;
        return Objects.equals(id, logins.id) && Objects.equals(login, logins.login) && Objects.equals(password, logins.password) && Objects.equals(email, logins.email) && role == logins.role && Objects.equals(time, logins.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, role, time);
    }

    @Override
    public String toString() {
        return "Logins{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", time=" + time +
                '}';
    }


    public static Builder builder() {
        return new Logins().new Builder();
    }

    public class Builder {
        private Builder() {
            // private constructor
        }
        public Builder id(Long userId) {
            Logins.this.id = userId;
            return this;
        }
        public Builder login(String login) {
            Logins.this.login = login;
            return this;
        }
        public Builder password(String password) {
            Logins.this.password = password;
            return this;
        }
        public Builder email(String email) {
            Logins.this.email = email;
            return this;
        }
        public Builder role(RoleType role) {
            Logins.this.role = role;
            return this;
        }
        public Logins build() {
            return Logins.this;
        }

    }
}
