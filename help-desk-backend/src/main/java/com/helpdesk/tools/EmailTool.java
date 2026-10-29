package com.helpdesk.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class EmailTool {


    //send the email to support team regarding new ticket

    @Tool(description = "This tool helps to send email to support team regarding new ticket")
    public void sendEmailToSupportTeam(@ToolParam(description = "Email id associated with ticket for contact information.") String email,@ToolParam(description = "short description of Ticket") String message)
    {
        //sending email to support team
    }
}
