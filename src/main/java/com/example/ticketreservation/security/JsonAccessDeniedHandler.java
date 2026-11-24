package com.example.ticketreservation.security;
import jakarta.servlet.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    private final ErrorResponseWriter writer;
    public JsonAccessDeniedHandler(ErrorResponseWriter writer) { this.writer=writer; }
    @Override public void handle(HttpServletRequest req,HttpServletResponse res,AccessDeniedException ex) throws IOException {
        writer.write(res,403,"Access denied");
    }
}
