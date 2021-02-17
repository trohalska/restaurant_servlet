package ua.servlet.restaurant.dto;

import java.util.List;

public class Page {
    List<DishesDTO> dishes;
    List<CategoriesDTO> categories;
    Long totalPages;

    public List<DishesDTO> getDishes() {
        return dishes;
    }
    public void setDishes(List<DishesDTO> dishes) {
        this.dishes = dishes;
    }

    public Long getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public static Page.Builder builder() {
        return new Page().new Builder();
    }

    public List<CategoriesDTO> getCategories() {
        return categories;
    }
    public void setCategories(List<CategoriesDTO> categories) {
        this.categories = categories;
    }

    public class Builder {
        private Builder() {}
        public Page.Builder dishes(List<DishesDTO> dishes) {
            Page.this.dishes = dishes;
            return this;
        }
        public Page.Builder categories(List<CategoriesDTO> categories) {
            Page.this.categories = categories;
            return this;
        }
        public Page.Builder totalPages(Long totalPages) {
            Page.this.totalPages = totalPages;
            return this;
        }
        public Page build() {
            return Page.this;
        }
    }
}
