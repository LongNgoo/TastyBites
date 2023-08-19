package com.poly.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

    private String username;
    private int rating;
    private String comment;

    @Temporal(TemporalType.TIMESTAMP) // Use this annotation to specify the type of the date property
    private Date reviewDate;


    // Getter and Setter methods for reviewDate
    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

 // Phương thức getter và setter cho thuộc tính product
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}


