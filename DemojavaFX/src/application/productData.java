package application;

import java.sql.Date;

public class productData {
	private Integer id;
	private String productId;
	private String productName;
	private Integer stock;
	private String type;
	private Float price;
	private String status;
	private String image;
	private Date date;
	private Integer quantity;

	public productData(Integer id, String productId, String productName, String type, Integer stock, Float price,
			String status, String image, Date date) {
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.type = type;
		this.stock = stock;
		this.price = price;
		this.status = status;
		this.image = image;
		this.date = date;
	}

	public productData(Integer id, String productId, String productName, String type, Integer quantity, Float price, String image, Date date) {
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.type = type;
		this.price = price;
		this.image = image;
		this.date = date;
		this.quantity = quantity;

	}

	public Integer getId() {
		return id;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public Integer getStock() {
		return stock;
	}

	public Float getPrice() {
		return price;
	}

	public String getStatus() {
		return status;
	}

	public String getImage() {
		return image;
	}

	public Date getDate() {
		return date;
	}

	public String getType() {
		return type;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
}
