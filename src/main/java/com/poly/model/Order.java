package com.poly.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	private String address;
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	private Date createDate= new Date();
	@ManyToOne
	@JoinColumn(name = "Discountid")
	private Discount discount;
	@Column(name = "Status")
	private String status = "Đợi xác nhận";
	@Column(name = "Cancellationreason")
	private String cancellationReason = null;
	@Column(name = "Paypalorderid")
	private String paypalOrderId;
	@Column(name = "Paypalorderstatus")
	private String paypalOrderStatus;
	@ManyToOne
	@JoinColumn(name = "Username")
	private Account account;
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetails;
}
