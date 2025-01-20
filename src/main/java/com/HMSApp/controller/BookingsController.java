package com.HMSApp.controller;

import com.HMSApp.entity.Booking;
import com.HMSApp.entity.Property;
import com.HMSApp.entity.RoomAvailability;
import com.HMSApp.repository.BookingRepository;
import com.HMSApp.repository.PropertyRepository;
import com.HMSApp.repository.RoomAvailabilityRepository;
import com.HMSApp.service.PDFGenerator;
import com.HMSApp.service.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingsController {
    private RoomAvailabilityRepository roomAvailabilityRepository;
    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;
    private PDFGenerator pdfGenerator;
    private SmsService smsService;

    public BookingsController(RoomAvailabilityRepository roomAvailabilityRepository, PropertyRepository propertyRepository, BookingRepository bookingRepository, PDFGenerator pdfGenerator, SmsService smsService) {
        this.roomAvailabilityRepository = roomAvailabilityRepository;
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.pdfGenerator = pdfGenerator;
        this.smsService = smsService;
    }

    @GetMapping("/search/rooms")
    public ResponseEntity<?> searchRoomsAndBook(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam String roomType,
            @RequestParam Long propertyId,
            @RequestBody Booking bookings
            )
    {
        List<RoomAvailability> rooms = roomAvailabilityRepository
                .findAvailableRooms(fromDate,toDate,roomType);

        Optional<Property> property = propertyRepository.findById(propertyId);


        for(RoomAvailability r:rooms){
            if(r.getTotalRooms() == 0){
                return new ResponseEntity<>("No rooms available", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
            if (property.isPresent()){
                bookings.setProperty(property.get());
            }
            Booking savedBookings = bookingRepository.save(bookings);
            pdfGenerator.generatePdf("D:\\Documents\\intellij projects\\generatedpdf\\booking"+"_"+savedBookings.getId()+".pdf",savedBookings);
            smsService.sendSms(bookings.getMobile(),"Hello!! This message is from twilio");
        return new ResponseEntity<>(rooms,HttpStatus.OK);
    }
}
