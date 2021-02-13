package ua.servlet.restaurant.dao.entity;

public class Baskets {
    private Long id;
    private Long dishId;
    private Long loginId;

    public Baskets() {}
    public Baskets(Long id, Long dishId, Long loginId) {
        this.id = id;
        this.dishId = dishId;
        this.loginId = loginId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getDishId() {
        return dishId;
    }
    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getLoginId() {
        return loginId;
    }
    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    @Override
    public String toString() {
        return "Baskets{" +
                "id=" + id +
                ", dishId=" + dishId +
                ", loginId=" + loginId +
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
        public Baskets.Builder dishId(Long id) {
            Baskets.this.id = id;
            return this;
        }
        public Baskets.Builder loginId(Long id) {
            Baskets.this.id = id;
            return this;
        }

        public Baskets build() {
            return Baskets.this;
        }

    }
}
