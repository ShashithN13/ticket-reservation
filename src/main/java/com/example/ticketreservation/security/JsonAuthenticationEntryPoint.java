package com.example.ticketreservation.security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ErrorResponseWriter writer;
    public JsonAuthenticationEntryPoint(ErrorResponseWriter writer) { this.writer=writer; }
    @Override public void commence(HttpServletRequest req,HttpServletResponse res,AuthenticationException ex) throws IOException {
        writer.write(res,401,"Authentication required");
    }
}
