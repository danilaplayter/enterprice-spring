/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.power.domain.model.Booking;
import ru.mentee.power.domain.model.Event;
import ru.mentee.power.domain.model.Seat;
import ru.mentee.power.domain.repository.BookingRepository;
import ru.mentee.power.domain.repository.EventRepository;
import ru.mentee.power.domain.repository.SeatRepository;
import ru.mentee.power.service.AuditService;
import ru.mentee.power.service.model.BookingRequest;
import ru.mentee.power.service.model.BookingResponse;
import ru.mentee.power.service.support.EventNotFoundException;
import ru.mentee.power.service.support.SeatAlreadyBookedException;
import ru.mentee.power.service.support.SeatNotAvailableException;
import ru.mentee.power.service.support.SeatNotBelongToEventException;
import ru.mentee.power.service.support.SeatNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
  private final EventRepository eventRepository;
  private final SeatRepository seatRepository;
  private final BookingRepository bookingRepository;
  private final PlatformTransactionManager transactionManager;
  private final AuditService auditService;

  @Transactional(
      propagation = Propagation.REQUIRES_NEW,
      isolation = Isolation.SERIALIZABLE,
      rollbackFor = {
        SeatNotAvailableException.class,
        SeatAlreadyBookedException.class,
        SeatNotBelongToEventException.class
      },
      noRollbackFor = {EventNotFoundException.class, SeatNotFoundException.class})
  public BookingResponse bookSeat(BookingRequest request) {
    try {
      // Аудит начала попытки бронирования
      auditService.auditBooking(request, "STARTED", "Booking attempt started");

      // 1. Получаем событие с пессимистичной блокировкой
      Event event =
          eventRepository
              .findByIdForUpdate(request.getEventId())
              .orElseThrow(() -> new EventNotFoundException(request.getEventId()));

      // 2. Получаем место с пессимистичной блокировкой
      Seat seat =
          seatRepository
              .findByIdForUpdate(request.getSeatId())
              .orElseThrow(() -> new SeatNotFoundException(request.getSeatId()));

      // 3. Проверяем принадлежность места событию
      if (!seat.getEvent().getId().equals(event.getId())) {
        throw new SeatNotBelongToEventException(seat.getId(), event.getId());
      }

      // 4. Проверяем доступность места
      if (!"AVAILABLE".equals(seat.getStatus())) {
        throw new SeatNotAvailableException(seat.getId(), seat.getStatus());
      }

      // 5. Проверяем, не забронировано ли уже место
      if (bookingRepository.existsBySeatIdAndStatusNot(seat.getId(), "CANCELLED")) {
        throw new SeatAlreadyBookedException(seat.getId());
      }

      // 6. Создаем бронь
      Booking booking = new Booking();
      booking.setEvent(event);
      booking.setSeat(seat);
      booking.setUserId(request.getUserId());
      booking.setStatus("CONFIRMED");
      booking.setCreatedAt(LocalDateTime.now());

      // 7. Обновляем статус места
      seat.setStatus("BOOKED");

      // 8. Обновляем счетчик забронированных мест
      event.setBookedSeats(event.getBookedSeats() + 1);

      // 9. Сохраняем изменения
      bookingRepository.save(booking);
      seatRepository.save(seat);
      eventRepository.save(event);

      // 10. Формируем ответ
      BookingResponse response =
          BookingResponse.builder()
              .bookingId(booking.getId())
              .status(booking.getStatus())
              .seatNumber(seat.getSeatNumber())
              .eventName(event.getName())
              .build();

      // Аудит успешного завершения
      auditService.auditBooking(request, "SUCCESS", "Booking completed successfully");
      return response;

    } catch (Exception e) {
      // Аудит неудачного завершения
      auditService.auditBooking(request, "FAILED", "Booking failed: " + e.getMessage());
      throw e; // Перебрасываем исключение дальше
    }
  }
}
