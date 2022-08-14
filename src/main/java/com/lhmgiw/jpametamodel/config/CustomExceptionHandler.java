package com.lhmgiw.jpametamodel.config;

import com.lhmgiw.jpametamodel.dto.common.ErrorResponseDTO;
import com.lhmgiw.jpametamodel.exception.CommonServerException;
import com.lhmgiw.jpametamodel.exception.ObjectAlreadyExistException;
import com.lhmgiw.jpametamodel.exception.ObjectNotFoundException;
import com.lhmgiw.jpametamodel.util.MessageResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageResource messageResource;

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField()+ ": "+ messageResource.getMessage(fieldError.getDefaultMessage() ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponseDTO<>(errorList,
                LocalDateTime.now(), request.getDescription(false)), status);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request){
        return new ResponseEntity<>(new ErrorResponseDTO<>(ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ObjectAlreadyExistException.class)
    public final ResponseEntity<Object> handleObjectAlreadyExistsException(Exception ex, WebRequest request){
        return new ResponseEntity<>(new ErrorResponseDTO<>(ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false)), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CommonServerException.class)
    public final ResponseEntity<Object> handleCommonServerException(Exception ex, WebRequest request){
        return new ResponseEntity<>(new ErrorResponseDTO<>(ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
        return new ResponseEntity<>(new ErrorResponseDTO<>(ex.getMessage(),
                LocalDateTime.now(), request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
