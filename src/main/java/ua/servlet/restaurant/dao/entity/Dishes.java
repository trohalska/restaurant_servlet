package ua.servlet.restaurant.dao.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonPropertyOrder(alphabetic = true)
public class Dishes {
    private Long id;
    private String nameEn;
    private String nameUa;
    private BigDecimal price;
    private Categories categories;
    private LocalDateTime time;

    public Dishes() {}

    public Dishes(Long id, String nameEn, String nameUa,
                  BigDecimal price, Categories categories, LocalDateTime time) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameUa = nameUa;
        this.price = price;
        this.categories = categories;
        this.time = time;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameUa() {
        return nameUa;
    }
    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Categories getCategories() {
        return categories;
    }
    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Dishes{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                ", nameUa='" + nameUa + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                ", time=" + time +
                '}';
    }

    public static Dishes.Builder builder() {
        return new Dishes().new Builder();
    }

    public class Builder {
        private Builder() {
            // private constructor
        }
        public Dishes.Builder id(Long id) {
            Dishes.this.id = id;
            return this;
        }
        public Dishes.Builder nameEn(String name) {
            Dishes.this.nameEn = name;
            return this;
        }
        public Dishes.Builder nameUa(String name) {
            Dishes.this.nameUa = name;
            return this;
        }
        public Dishes.Builder price(BigDecimal price) {
            Dishes.this.price = price;
            return this;
        }
        public Dishes.Builder category(Categories categories) {
            Dishes.this.categories = categories;
            return this;
        }
        public Dishes.Builder time(LocalDateTime time) {
            Dishes.this.time = time;
            return this;
        }

        public Dishes build() {
            return Dishes.this;
        }

    }
}
