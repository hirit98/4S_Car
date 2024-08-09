package com.car.model;

public class TransactionDTO {
	private int transaction_no;
	private String transaction_seller_no;
	private String transaction_buyer_no;
	private String transaction_dealer_no;
	private String transaction_car_std_no;
	private String transaction_car_no;
	private int transaction_car_distance;
	private int transaction_car_price;
	private String transaction_date;
	private boolean transaction_sell_review_status;
	private boolean transaction_buy_review_status;

	public int getTransaction_no() {
		return transaction_no;
	}

	public void setTransaction_no(int transaction_no) {
		this.transaction_no = transaction_no;
	}

	public String getTransaction_seller_no() {
		return transaction_seller_no;
	}

	public void setTransaction_seller_no(String transaction_seller_no) {
		this.transaction_seller_no = transaction_seller_no;
	}

	public String getTransaction_buyer_no() {
		return transaction_buyer_no;
	}

	public void setTransaction_buyer_no(String transaction_buyer_no) {
		this.transaction_buyer_no = transaction_buyer_no;
	}

	public String getTransaction_dealer_no() {
		return transaction_dealer_no;
	}

	public void setTransaction_dealer_no(String transaction_dealer_no) {
		this.transaction_dealer_no = transaction_dealer_no;
	}

	public String getTransaction_car_std_no() {
		return transaction_car_std_no;
	}

	public void setTransaction_car_std_no(String transaction_car_std_no) {
		this.transaction_car_std_no = transaction_car_std_no;
	}

	public String getTransaction_car_no() {
		return transaction_car_no;
	}

	public void setTransaction_car_no(String transaction_car_no) {
		this.transaction_car_no = transaction_car_no;
	}

	public int getTransaction_car_distance() {
		return transaction_car_distance;
	}

	public void setTransaction_car_distance(int transaction_car_distance) {
		this.transaction_car_distance = transaction_car_distance;
	}

	public int getTransaction_car_price() {
		return transaction_car_price;
	}

	public void setTransaction_car_price(int transaction_car_price) {
		this.transaction_car_price = transaction_car_price;
	}

	public String getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(String transaction_date) {
		this.transaction_date = transaction_date;
	}

	public boolean isTransaction_sell_review_status() {
		return transaction_sell_review_status;
	}

	public void setTransaction_sell_review_status(boolean transaction_sell_review_status) {
		this.transaction_sell_review_status = transaction_sell_review_status;
	}

	public boolean isTransaction_buy_review_status() {
		return transaction_buy_review_status;
	}

	public void setTransaction_buy_review_status(boolean transaction_buy_review_status) {
		this.transaction_buy_review_status = transaction_buy_review_status;
	}

}
