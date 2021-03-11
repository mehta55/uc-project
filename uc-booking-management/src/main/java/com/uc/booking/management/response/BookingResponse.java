package com.uc.booking.management.response;

import com.uc.common.response.BaseResponse;

public class BookingResponse extends BaseResponse {
	
	private Long bookingId;
	
	public BookingResponse(String status, String message, Long bookingId) {
		super(status, message);
		this.bookingId = bookingId;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	
}
