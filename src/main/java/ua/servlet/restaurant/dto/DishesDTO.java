package ua.servlet.restaurant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DishesDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private CategoriesDTO categories;
    private LocalDateTime time;

    public DishesDTO() {}

    public DishesDTO(Long id, String name, BigDecimal price, CategoriesDTO categories, LocalDateTime time) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoriesDTO getCategories() {
        return categories;
    }
    public void setCategories(CategoriesDTO categories) {
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
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                ", time=" + time +
                '}';
    }

    public static DishesDTO.Builder builder() {
        return new DishesDTO().new Builder();
    }

    public class Builder {
        private Builder() {
            // private constructor
        }
        public DishesDTO.Builder id(Long id) {
            DishesDTO.this.id = id;
            return this;
        }
        public DishesDTO.Builder name(String name) {
            DishesDTO.this.name = name;
            return this;
        }
        public DishesDTO.Builder price(BigDecimal price) {
            DishesDTO.this.price = price;
            return this;
        }
        public DishesDTO.Builder category(CategoriesDTO categories) {
            DishesDTO.this.categories = categories;
            return this;
        }
        public DishesDTO.Builder time(LocalDateTime time) {
            DishesDTO.this.time = time;
            return this;
        }

        public DishesDTO build() {
            return DishesDTO.this;
        }

    }
}
