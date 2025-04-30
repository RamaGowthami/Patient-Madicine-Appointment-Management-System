package com.example.patientapp.exception;



import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // This method will handle all types of exceptions
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        // Add the exception message to the model, which will be displayed in the error page
        model.addAttribute("error", e.getMessage());

        // Return to a custom error page (error.html) to show the error message
        return "error";  // Return the name of the error page (error.html)
    }
}

