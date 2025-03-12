package org.example.springhomework01.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Long ticketId;
    private String passengerName;
    private LocalDate travelDate;
    private String sourceStation;
    private String destinationStation;
    private Double price;
    private Boolean paymentStatus;
    private String ticketStatus;
    private String seatNumber;

}
