package com.sid.gl.domain;

import com.sid.gl.domain.enumeration.StatusProduct;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Lob
    @Column(name = "image_product")
    private byte[] imageProduct;

    @Column(name = "image_product_content_type")
    private String imageProductContentType;

    @NotNull
    @Column(name = "unitprice", nullable = false)
    private Double unitprice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProduct status;

    @ManyToOne
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Product quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public byte[] getImageProduct() {
        return this.imageProduct;
    }

    public Product imageProduct(byte[] imageProduct) {
        this.setImageProduct(imageProduct);
        return this;
    }

    public void setImageProduct(byte[] imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getImageProductContentType() {
        return this.imageProductContentType;
    }

    public Product imageProductContentType(String imageProductContentType) {
        this.imageProductContentType = imageProductContentType;
        return this;
    }

    public void setImageProductContentType(String imageProductContentType) {
        this.imageProductContentType = imageProductContentType;
    }

    public Double getUnitprice() {
        return this.unitprice;
    }

    public Product unitprice(Double unitprice) {
        this.setUnitprice(unitprice);
        return this;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public StatusProduct getStatus() {
        return this.status;
    }

    public Product status(StatusProduct status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusProduct status) {
        this.status = status;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product category(Category category) {
        this.setCategory(category);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", quantity=" + getQuantity() +
            ", imageProduct='" + getImageProduct() + "'" +
            ", imageProductContentType='" + getImageProductContentType() + "'" +
            ", unitprice=" + getUnitprice() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
