package com.helpdesk.service;

import com.helpdesk.entity.Ticket;
import com.helpdesk.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) {
        ticket.setId(null);
        Ticket saved = ticketRepository.save(ticket);
        log.info("Persisted ticket in database: ID={}, email={}, status={}", saved.getId(), saved.getEmail(), saved.getStatus());
        return saved;
    }

    public Ticket updateTicket(Ticket ticket) {
        Ticket updated = ticketRepository.save(ticket);
        log.info("Updated ticket in database: ID={}", updated.getId());
        return updated;
    }

    public Ticket getTicket(Long ticketId) {
        return ticketRepository.findById(ticketId).orElse(null);
    }

    public List<Ticket> getTicketsByEmail(String email) {
        return ticketRepository.findByEmailOrderByIdDesc(email);
    }
}
