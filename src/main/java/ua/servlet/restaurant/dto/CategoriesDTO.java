package ua.servlet.restaurant.dto;

public class CategoriesDTO {
    private Long id;
    private String category;

    public CategoriesDTO() {}

    public CategoriesDTO(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }

    public static CategoriesDTO.Builder builder() {
        return new CategoriesDTO().new Builder();
    }

    public class Builder {
        private Builder() { }
        public CategoriesDTO.Builder id(Long id) {
            CategoriesDTO.this.id = id;
            return this;
        }
        public CategoriesDTO.Builder category(String category) {
            CategoriesDTO.this.category = category;
            return this;
        }
        public CategoriesDTO build() {
            return CategoriesDTO.this;
        }

    }
}
