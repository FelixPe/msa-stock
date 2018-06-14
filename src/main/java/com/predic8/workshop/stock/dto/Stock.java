package com.predic8.workshop.stock.dto;

public class Stock {
	private String uuid;
    private Long quantity;

	public Stock() {
	}

	public Stock(String uuid, Long quantity) {
		this.uuid = uuid;
		this.quantity = quantity;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public String toString() {
		return "Stock(uuid=" + uuid + ", quantity=" + quantity + ")";
	}
}