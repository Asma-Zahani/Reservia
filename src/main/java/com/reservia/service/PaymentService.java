package com.reservia.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.reservia.entity.Booking;
import com.reservia.entity.BookingStatus;
import com.reservia.repository.BookingRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final BookingRepository bookingRepository;

    public String createCheckoutSession(Booking booking) throws StripeException {

        // Fix : totalPrice est un Double, pas un BigDecimal
        long amountInCents = Math.round(booking.getTotalPrice() * 100);

        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("http://localhost:8085/account/bookings/pay/success/" + booking.getId())
            .setCancelUrl("http://localhost:8085/account/bookings/pay/cancel")
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmount(amountInCents)
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName("Réservation #" + booking.getId())
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build();

        Session session = Session.create(params);
        return session.getUrl();
    }

    public void markBookingAsPaid(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.PAID);
        bookingRepository.save(booking);
    }
}
