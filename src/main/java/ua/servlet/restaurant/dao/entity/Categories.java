package ua.servlet.restaurant.dao.entity;

public class Categories {
    private Long id;
    private String categoryEn;
    private String categoryUa;

    public Categories() {}

    public Categories(Long id, String categoryEn, String categoryUa) {
        this.id = id;
        this.categoryEn = categoryEn;
        this.categoryUa = categoryUa;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryEn() {
        return categoryEn;
    }
    public void setCategoryEn(String categoryEn) {
        this.categoryEn = categoryEn;
    }

    public String getCategoryUa() {
        return categoryUa;
    }
    public void setCategoryUa(String categoryUa) {
        this.categoryUa = categoryUa;
    }


    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", categoryEn='" + categoryEn + '\'' +
                ", categoryUa='" + categoryUa + '\'' +
                '}';
    }

    public static Categories.Builder builder() {
        return new Categories().new Builder();
    }

    public class Builder {
        private Builder() {
            // private constructor
        }
        public Categories.Builder id(Long id) {
            Categories.this.id = id;
            return this;
        }
        public Categories.Builder categoryUa(String category) {
            Categories.this.categoryUa = category;
            return this;
        }
        public Categories.Builder categoryEn(String category) {
            Categories.this.categoryEn = category;
            return this;
        }
        public Categories build() {
            return Categories.this;
        }

    }
}
