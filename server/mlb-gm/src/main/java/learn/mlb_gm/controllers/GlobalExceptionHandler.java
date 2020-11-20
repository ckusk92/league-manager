package learn.mlb_gm.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // DataAccessException is the super class of many Spring database exceptions
    // including BadSqlGrammarException.
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {
        // TODO Log the exception?
        ex.printStackTrace();
        return new ResponseEntity<>(
                new ErrorResponse("We can't show you the details, but something went wrong in our database. Sorry :("),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // IllegalArgumentException is the super class for many Java exceptions
    // including all formatting (number, date) exceptions.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
        // TODO Log the exception?
        ex.printStackTrace();
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // This is our absolute last resort. Yuck.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) throws Exception {
        // TODO Log the exception?
        ex.printStackTrace();
        return new ResponseEntity<>(
                new ErrorResponse("Something went wrong on our end. Your request failed. :("),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
