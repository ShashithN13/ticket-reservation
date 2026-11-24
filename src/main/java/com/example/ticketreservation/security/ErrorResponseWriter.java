package com.example.ticketreservation.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.ticketreservation.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;
@Component
public class ErrorResponseWriter {
    private final ObjectMapper mapper=new ObjectMapper().findAndRegisterModules();
    public void write(HttpServletResponse response,int status,String message) throws IOException {
        response.setStatus(status); response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(),new ErrorResponse(LocalDateTime.now(),status,
          status==401?"Unauthorized":"Forbidden",message));
    }
}
