package ua.servlet.restaurant.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Orders {
    private Long id;
    private Status status;
    private LocalDateTime time;
    private BigDecimal totalPrice;
    private Logins login;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Logins getLogin() {
        return login;
    }
    public void setLogin(Logins login) {
        this.login = login;
    }

    public static Orders.Builder builder() {
        return new Orders().new Builder();
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", status=" + status +
                ", time=" + time +
                ", totalPrice=" + totalPrice +
                ", login=" + login +
                '}';
    }

    public class Builder {
        private Builder() { }
        public Orders.Builder id(Long id) {
            Orders.this.id = id;
            return this;
        }
        public Orders.Builder status(Status status) {
            Orders.this.status = status;
            return this;
        }
        public Orders.Builder time(LocalDateTime time) {
            Orders.this.time = time;
            return this;
        }
        public Orders.Builder totalPrice(BigDecimal totalPrice) {
            Orders.this.totalPrice = totalPrice;
            return this;
        }
        public Orders.Builder login(Logins login) {
            Orders.this.login = login;
            return this;
        }

        public Orders build() {
            return Orders.this;
        }

    }
}
