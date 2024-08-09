package com.car.model;

public class BuyReviewBoardDTO {

	private int buy_review_board_no;
	private String buy_review_board_writer_id;
	private String buy_review_board_title;
	private String buy_review_board_cont;
	private String buy_review_board_date;
	private String buy_review_board_update;
	private int buy_review_board_hit;
	private String buy_review_board_file;
	private int Buy_review_transaction_no;

	public int getBuy_review_transaction_no() {
		return Buy_review_transaction_no;
	}

	public void setBuy_review_transaction_no(int buy_review_transaction_no) {
		Buy_review_transaction_no = buy_review_transaction_no;
	}

	public int getBuy_review_board_no() {
		return buy_review_board_no;
	}

	public void setBuy_review_board_no(int buy_review_board_no) {
		this.buy_review_board_no = buy_review_board_no;
	}

	public String getBuy_review_board_writer_id() {
		return buy_review_board_writer_id;
	}

	public void setBuy_review_board_writer_id(String buy_review_board_writer_id) {
		this.buy_review_board_writer_id = buy_review_board_writer_id;
	}

	public String getBuy_review_board_title() {
		return buy_review_board_title;
	}

	public void setBuy_review_board_title(String buy_review_board_title) {
		this.buy_review_board_title = buy_review_board_title;
	}

	public String getBuy_review_board_cont() {
		return buy_review_board_cont;
	}

	public void setBuy_review_board_cont(String buy_review_board_cont) {
		this.buy_review_board_cont = buy_review_board_cont;
	}

	public String getBuy_review_board_date() {
		return buy_review_board_date;
	}

	public void setBuy_review_board_date(String buy_review_board_date) {
		this.buy_review_board_date = buy_review_board_date;
	}

	public String getBuy_review_board_update() {
		return buy_review_board_update;
	}

	public void setBuy_review_board_update(String buy_review_board_update) {
		this.buy_review_board_update = buy_review_board_update;
	}

	public int getBuy_review_board_hit() {
		return buy_review_board_hit;
	}

	public void setBuy_review_board_hit(int buy_review_board_hit) {
		this.buy_review_board_hit = buy_review_board_hit;
	}

	public String getBuy_review_board_file() {
		return buy_review_board_file;
	}

	public void setBuy_review_board_file(String buy_review_board_file) {
		this.buy_review_board_file = buy_review_board_file;
	}

}
