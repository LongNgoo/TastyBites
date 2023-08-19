package com.poly.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Favourites")
public class Favourite implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Favourited")
	private Integer favourited;
	
	@ManyToOne
	@JoinColumn(name = "Username")
	Account account;

	@ManyToOne
	@JoinColumn(name = "Productid")
	Product product;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Vieweddate")
	private Date viewedDate = new Date();
	
	@Column(name = "Isliked")
	private Boolean isLiked;
	
	@Column(name = "Likeddate")
	private Date likedDate = new Date();
	
}
