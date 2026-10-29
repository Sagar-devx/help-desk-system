package com.helpdesk.service;

import com.helpdesk.entity.Ticket;
import com.helpdesk.repository.TicketRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class TicketService {

    private final TicketRepository ticketRepository;

    //create Ticket
    public Ticket createTicket(Ticket ticket)
    {
        return ticketRepository.save(ticket);
    }


    //update Ticket
    public Ticket updateTicket(Ticket ticket)
    {
        return ticketRepository.save(ticket);
    }

    //get Ticket
    public Ticket getTicket(Long ticketId)
    {
        return ticketRepository.findById(ticketId).orElse(null);
    }

    //Get ticket by username

    public Ticket getTicketByUserName(String username)
    {
        return ticketRepository.findByUsername(username).orElse(null);
    }

    // Get Current System time
    @Tool(description = "This tool helps to get current system time.")
    public String getCurrentTime()
    {
        return String.valueOf(System.currentTimeMillis());
    }
}
