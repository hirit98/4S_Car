package com.car.model;

public class PaymentDTO {

	private String payment_no;
	private String payment_buyer_no;
	private String payment_method;
	private String payment_card_company;
	private String payment_card_no;
	private int payment_card_div_month;
	private String payment_car_pwd;
	private int payment_amount;
	private int payment_sell_board_no;

	public String getPayment_no() {
		return payment_no;
	}

	public void setPayment_no(String payment_no) {
		this.payment_no = payment_no;
	}

	public String getPayment_buyer_no() {
		return payment_buyer_no;
	}

	public void setPayment_buyer_no(String payment_buyer_no) {
		this.payment_buyer_no = payment_buyer_no;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getPayment_card_company() {
		return payment_card_company;
	}

	public void setPayment_card_company(String payment_card_company) {
		this.payment_card_company = payment_card_company;
	}

	public String getPayment_card_no() {
		return payment_card_no;
	}

	public void setPayment_card_no(String payment_card_no) {
		this.payment_card_no = payment_card_no;
	}

	public int getPayment_card_div_month() {
		return payment_card_div_month;
	}

	public void setPayment_card_div_month(int payment_card_div_month) {
		this.payment_card_div_month = payment_card_div_month;
	}

	public String getPayment_car_pwd() {
		return payment_car_pwd;
	}

	public void setPayment_car_pwd(String payment_car_pwd) {
		this.payment_car_pwd = payment_car_pwd;
	}

	public int getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(int payment_amount) {
		this.payment_amount = payment_amount;
	}

	public int getPayment_sell_board_no() {
		return payment_sell_board_no;
	}

	public void setPayment_sell_board_no(int payment_sell_board_no) {
		this.payment_sell_board_no = payment_sell_board_no;
	}

}
