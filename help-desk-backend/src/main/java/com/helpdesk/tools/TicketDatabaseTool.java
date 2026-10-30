package com.helpdesk.tools;

import com.helpdesk.entity.Priority;
import com.helpdesk.entity.Status;
import com.helpdesk.entity.Ticket;
import com.helpdesk.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketDatabaseTool {

    private final TicketService service;

    public record CreateTicketRequest(
            @ToolParam(description = "Brief summary of the issue") String summary,
            @ToolParam(description = "Detailed problem description") String description,
            @ToolParam(description = "Category such as Technical, Hardware, Software, Account") String category,
            @ToolParam(description = "Priority: LOW, MEDIUM, HIGH, or URGENT") String priority,
            @ToolParam(description = "User's email address") String email
    ) {}

    public record UpdateTicketRequest(
            @ToolParam(description = "Existing ticket ID") Long id,
            @ToolParam(description = "New notes or description to append") String description,
            @ToolParam(description = "Updated status: OPEN, CLOSED, or RESOLVED") String status,
            @ToolParam(description = "Updated priority: LOW, MEDIUM, HIGH, or URGENT") String priority
    ) {}

    @Tool(description = "Save a new support ticket in the database. Call this tool only when the user's issue and email are collected.")
    public Ticket createTicketTool(@ToolParam(description = "Ticket details to save") CreateTicketRequest request) {
        log.info("Tool invoked: createTicketTool with summary='{}', email='{}', priority='{}'",
                request.summary(), request.email(), request.priority());

        Ticket ticket = new Ticket();
        ticket.setSummary(request.summary());
        ticket.setDescription(request.description());
        ticket.setCategory(request.category());
        ticket.setEmail(request.email());
        ticket.setStatus(Status.OPEN);

        if (request.priority() != null && !request.priority().isBlank()) {
            try {
                ticket.setPriority(Priority.valueOf(request.priority().trim().toUpperCase()));
            } catch (Exception e) {
                ticket.setPriority(Priority.MEDIUM);
            }
        } else {
            ticket.setPriority(Priority.MEDIUM);
        }

        Ticket saved = service.createTicket(ticket);
        log.info("Tool finished: createTicketTool created ticket ID={}", saved.getId());
        return saved;
    }

    @Tool(description = "Retrieve all existing tickets for a user using their email address.")
    public List<Ticket> getTicketsByEmail(@ToolParam(description = "User's email address") String email) {
        log.info("Tool invoked: getTicketsByEmail with email='{}'", email);
        List<Ticket> tickets = service.getTicketsByEmail(email);
        log.info("Found {} ticket(s) for email='{}'", tickets.size(), email);
        return tickets;
    }

    @Tool(description = "Update an existing ticket in the database with new comments or status.")
    public Ticket updateTicket(@ToolParam(description = "Ticket update details") UpdateTicketRequest request) {
        log.info("Tool invoked: updateTicket for ID={}", request.id());
        Ticket ticket = service.getTicket(request.id());
        if (ticket == null) {
            log.warn("Ticket ID={} not found for update", request.id());
            return null;
        }

        if (request.description() != null && !request.description().isBlank()) {
            if (ticket.getDescription() == null || ticket.getDescription().isBlank()) {
                ticket.setDescription(request.description());
            } else {
                ticket.setDescription(ticket.getDescription() + "\nUpdate: " + request.description());
            }
        }
        if (request.status() != null && !request.status().isBlank()) {
            try {
                ticket.setStatus(Status.valueOf(request.status().trim().toUpperCase()));
            } catch (Exception ignored) {}
        }
        if (request.priority() != null && !request.priority().isBlank()) {
            try {
                ticket.setPriority(Priority.valueOf(request.priority().trim().toUpperCase()));
            } catch (Exception ignored) {}
        }

        Ticket updated = service.updateTicket(ticket);
        log.info("Tool finished: updateTicket updated ticket ID={}", updated.getId());
        return updated;
    }

    @Tool(description = "This tool helps to get current system time.")
    public String getCurrentTime() {
        return String.valueOf(System.currentTimeMillis());
    }
}
