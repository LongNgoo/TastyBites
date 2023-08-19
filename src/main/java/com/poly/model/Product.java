package com.poly.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String image;
	private Double price;
	@Column(name = "Productquantity")
	private Integer productQuantity;

	@Column(name = "Description")
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	private Date createDate = new Date();
	private Boolean available;
	private Integer star;
	
	@ManyToOne
	@JoinColumn(name = "Categoryid")
	private Category category;

	public void setProductId(Integer id) {
		this.id = id;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Favourite> favourites;
}
