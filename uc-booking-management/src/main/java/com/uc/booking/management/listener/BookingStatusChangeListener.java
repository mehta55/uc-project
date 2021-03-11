package com.uc.booking.management.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uc.booking.management.service.BookingService;
import com.uc.common.dto.BookingStatusChangeDto;

@Component
public class BookingStatusChangeListener {

	@Autowired
	private BookingService bookingService;
	
	@RabbitListener(queues = "${rabbit.booking.status.change.queue.name}")
	public void bookingStatusChangeLitsener(BookingStatusChangeDto dto) {
		bookingService.changeBookingStatus(dto);
	}
	
}
