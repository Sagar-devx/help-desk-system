package com.helpdesk.tools;

import com.helpdesk.entity.Ticket;
import com.helpdesk.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketDatabaseTool {

    private final TicketService service;

    //crete ticket create tool
    @Tool(description = "This tool helps to create new ticket in database.")
    public Ticket createTicketTool(@ToolParam(description = "Ticket details") Ticket ticket)
    {
        return service.createTicket(ticket);
    }

    //get ticket using username
    @Tool(description = "This tool helps to get the ticket by username.")
    public Ticket getTicketByUserName(@ToolParam(description = "username whose ticket is required") String username)
    {
        return service.getTicketByUserName(username);
    }

    //update the ticket tool
    @Tool(description = "This tool helps to update the ticket.")
    public Ticket updateTicket(@ToolParam(description= "new ticket details with ticket id") Ticket ticket)
    {
        return service.updateTicket(ticket);
    }

}
