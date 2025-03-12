package org.example.springhomework01.controller;


import jakarta.validation.Valid;
import org.example.springhomework01.model.entity.Ticket;
import org.example.springhomework01.model.request.APITicketResponseTicket;
import org.example.springhomework01.model.request.TicketRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final static List<Ticket> TICKETS = new ArrayList<>();
    private final static AtomicLong ATOMIC_LONG = new AtomicLong(TICKETS.size());

    public TicketController() {
        TICKETS.add(new Ticket(ATOMIC_LONG.getAndIncrement(), "Lika", LocalDate.now(), "SK", "CB", 10.11, true, "booking", "01"));
        TICKETS.add(new Ticket(ATOMIC_LONG.getAndIncrement(), "Soda", LocalDate.now(), "SK", "CB", 10.11, true, "booking", "04"));
        TICKETS.add(new Ticket(ATOMIC_LONG.getAndIncrement(), "Jonh", LocalDate.now(), "US", "AL", 1001.11, false, "No", "09"));
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return TICKETS;

    }

    @PostMapping
    public Ticket createTicket(@RequestBody TicketRequest ticketRequest) {
        Ticket ticket = new Ticket(ATOMIC_LONG.getAndIncrement(),
                ticketRequest.getPassengerName(),
                ticketRequest.getTravelDate(),
                ticketRequest.getSourceStation(),
                ticketRequest.getDestinationStation(),
                ticketRequest.getPrice(),
                ticketRequest.getPaymentStatus(),
                ticketRequest.getTicketStatus(),
                ticketRequest.getSeatNumber());
        TICKETS.add(ticket);
        return ticket;
    }

    @GetMapping("/{ticket-id}")
    public Ticket getTicketById(@PathVariable("ticket-id") Long ticketId) {
        for (Ticket ticket : TICKETS) {
            if (ticket.getTicketId().equals(ticketId)) {
                return ticket;
            }
        }
        return null;
    }

    @PutMapping("/{ticket-id}")
    public List<Ticket> updateTicketById(
            @PathVariable("ticket-id") Long ticketId,
            @Valid @RequestBody TicketRequest request) {
        Optional<Ticket> ticketOpt = TICKETS.stream()
                .filter(t -> t.getTicketId().equals(ticketId))
                .findFirst();

        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setPassengerName(request.getPassengerName());
            ticket.setTravelDate(request.getTravelDate());
            ticket.setSourceStation(request.getSourceStation());
            ticket.setDestinationStation(request.getDestinationStation());
            ticket.setPrice(request.getPrice());
            ticket.setPaymentStatus(request.getPaymentStatus());
            ticket.setTicketStatus(request.getTicketStatus());
            ticket.setSeatNumber(request.getSeatNumber());
            return this.getAllTickets();
        }
        return null;
    }

    @PutMapping("/tickets-ticket")
    public ResponseEntity<Ticket> updateByIdTicketStatus(
            @RequestParam("tickets-ticket") Long ticketId, APITicketResponseTicket apiTicketResponseTicket) {

        for (Ticket ticket: TICKETS){
            if (ticket.getTicketId().equals(ticketId)){
                ticket.setPaymentStatus(apiTicketResponseTicket.getPaymentStatus());
            }
        }

        return null;
    }
//    @DeleteMapping
//    public List<Ticket> deleteById(@RequestParam ){
//        return null;
//    }
}
