package ua.servlet.restaurant.dao.entity;

public class Baskets {
    private Long id;
    private Dishes dish;
    private Logins login;

    public Baskets() {}
    public Baskets(Long id, Dishes dish, Logins login) {
        this.id = id;
        this.dish = dish;
        this.login = login;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Dishes getDish() {
        return dish;
    }
    public void setDish(Dishes dish) {
        this.dish = dish;
    }

    public Logins getLogin() {
        return login;
    }
    public void setLogin(Logins login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Baskets{" +
                "id=" + id +
                ", dish=" + dish +
                ", login=" + login +
                '}';
    }

    public static Baskets.Builder builder() {
        return new Baskets().new Builder();
    }

    public class Builder {
        private Builder() { }
        public Baskets.Builder id(Long id) {
            Baskets.this.id = id;
            return this;
        }
        public Baskets.Builder dish(Dishes dish) {
            Baskets.this.dish = dish;
            return this;
        }
        public Baskets.Builder login(Logins login) {
            Baskets.this.login = login;
            return this;
        }

        public Baskets build() {
            return Baskets.this;
        }

    }
}
