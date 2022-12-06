package com.br.model.vo;

import java.sql.Date;

public class ProductIO {
	
	private int ioNum;
	private String productId;
	private String productName;
	private Date ioDate;
	private int amount;
	private String status;
	
	public ProductIO() {}

	public ProductIO(int ioNum, String productId, String productName, Date ioDate, int amount, String status) {
		super();
		this.ioNum = ioNum;
		this.productId = productId;
		this.productName = productName;
		this.ioDate = ioDate;
		this.amount = amount;
		this.status = status;
	}

	public int getIoNum() {
		return ioNum;
	}

	public void setIoNum(int ioNum) {
		this.ioNum = ioNum;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getIoDate() {
		return ioDate;
	}

	public void setIoDate(Date ioDate) {
		this.ioDate = ioDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProductIO [ioNum=" + ioNum + ", productId=" + productId + ", productName=" + productName + ", ioDate="
				+ ioDate + ", amount=" + amount + ", status=" + status + "]";
	}
	
}
