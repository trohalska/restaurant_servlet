package ua.servlet.restaurant.dto;

import java.math.BigDecimal;
import java.util.List;

public class BasketDTO {
    private List<DishesDTO> dishes;
    private BigDecimal totalPrice;

    public BasketDTO() {}
    public BasketDTO(List<DishesDTO> dishes, BigDecimal totalPrice) {
        this.dishes = dishes;
        this.totalPrice = totalPrice;
    }

    public List<DishesDTO> getDishes() {
        return dishes;
    }
    public void setDishes(List<DishesDTO> dishes) {
        this.dishes = dishes;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "BasketDTO{" +
                "dishes=" + dishes +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public static BasketDTO.Builder builder() {
        return new BasketDTO().new Builder();
    }

    public class Builder {
        private Builder() { }
        public BasketDTO.Builder dishes(List<DishesDTO> dishes) {
            BasketDTO.this.dishes = dishes;
            return this;
        }
        public BasketDTO.Builder totalPrice(BigDecimal totalPrice) {
            BasketDTO.this.totalPrice = totalPrice;
            return this;
        }
        public BasketDTO build() {
            return BasketDTO.this;
        }

    }
}
