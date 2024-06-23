package ts.myapp.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandlerService {

    @ExceptionHandler(value = { org.springframework.web.servlet.NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException() {
        ModelAndView modelAndView = new ModelAndView("404"); // nazwa widoku dla błędu 404
        return modelAndView;
    }
}