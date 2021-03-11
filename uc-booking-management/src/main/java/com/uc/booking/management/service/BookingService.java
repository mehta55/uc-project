package com.uc.booking.management.service;

import java.util.List;

import com.uc.booking.management.enity.Booking;
import com.uc.booking.management.request.BookingRequest;
import com.uc.booking.management.response.BookingDetailsReponse;
import com.uc.booking.management.response.BookingResponse;
import com.uc.common.dto.BookingStatusChangeDto;

public interface BookingService {

	public BookingResponse bookService(String userId, String userEmail, BookingRequest req);
	
	public List<Booking> getBookings(String userId, String role);
	
	public BookingDetailsReponse getBookingDetails(String bookingId);
	
	public void confirmBooking(String bookingId, boolean confirmation);
	
	public void changeBookingStatus(BookingStatusChangeDto dto);
	
	public void requestBookingCompletion(String bookingId, String providerId);

	public void requestBookingVerify(String bookingId, String providerId, String otp);
}
