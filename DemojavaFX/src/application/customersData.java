package application;

import java.sql.Date;

public class customersData {

	private Integer id;
	private Integer customerID;
	private Float total;
	private Date date;
	private String emUsername;

	public customersData(Integer id, Integer customerID, Float total, Date date, String emUsername) {
		this.id = id;
		this.customerID = customerID;
		this.total = total;
		this.date = date;
		this.emUsername = emUsername;

	}

	public Integer getId() {
		return id;
	}

	public Integer getCustomerID() {
		return customerID;
	}

	public Float getTotal() {
		return total;
	}

	public Date getDate() {
		return date;
	}

	public String getEmUsername() {
		return emUsername;
	}

}
