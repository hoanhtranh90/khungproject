package com.dgtt.business.config;

import com.dgtt.business.model.ResponseBody;
import com.dgtt.business.services.LoggerService;
import com.dgtt.core.exception.*;
import com.dgtt.core.model.LoggerRegister;
import com.dgtt.core.utils.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * @author DELL
 */
@ControllerAdvice
@Slf4j
public class RestApiExceptionHandler {

    private final String REQUEST_FROM = "    Exception from request :::::::::: ";
    private final String USER_AGENT = "User-Agent";

    @Autowired
    @Qualifier("LoggerServiceImpl_Main")
    private LoggerService callLogService;

    @ExceptionHandler({ResourceNotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)

    public ResponseEntity<?> resourceNotFoundException(Exception ex, WebRequest request) {
        loggingAdvice(ex, request);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ResponseEntity<>(ResponseBody.builder().status(404).path(path).message(ex.getMessage())
                .time(new Date()).build(), HttpStatus.NOT_FOUND);
    }

    /**
     * @param ex
     * @param request
     */
    @Transactional
    private void loggingAdvice(Exception ex, WebRequest request) {
        List<LoggerRegister> callLoggers = new ArrayList<>();
        String username = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : null;
        String index = new StringBuilder("error").append(username != null ? username.substring(0, 1).toUpperCase() + username.substring(1, username.length()) : "").toString();
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        String error = new StringBuilder(index + " - " + httpServletRequest.getMethod() + " " + httpServletRequest.getRemoteAddr() + ":"
                + httpServletRequest.getLocalPort() + httpServletRequest.getRequestURI()).toString();
        callLoggers.add(LoggerRegister.builder().codeName("API").codeId(111L)
                .text(new StringBuilder(ex.getClass().getSimpleName() + StringUtils.COLON
              + ex.getMessage() + StringUtils.COMMA + error).toString())
                .build());

        log.error(error + " - " + ex.getMessage());
        callLogService.createAll(callLoggers);
    }
    
    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        loggingAdvice(ex, request);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ResponseEntity<>(ResponseBody.builder().status(500).message(ex.getMessage())
                .time(new Date()).path(path).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ProxyAuthenticationRequired.class})
    @ResponseStatus(value = HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
    public ResponseEntity<?> tokenFailedException(ProxyAuthenticationRequired ex, WebRequest request) {

        loggingAdvice(ex, request);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ResponseEntity<>(ResponseBody.builder().status(407).path(path).message(ex.getMessage())
                .time(new Date()).build(), HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> loginFailedException(UnauthorizedException ex, WebRequest request) {

        loggingAdvice(ex, request);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ResponseEntity<>(ResponseBody.builder().status(401).path(path).message(ex.getMessage())
                .time(new Date()).build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ExpiredResourcesDateException.class, PermissionException.class,
        IllegalArgumentException.class, SignatureException.class, MalformedJwtException.class,
        UnsupportedJwtException.class, ExpiredJwtException.class,AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<?> permissionException(Exception ex, WebRequest request) {

        loggingAdvice(ex, request);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ResponseEntity<>(ResponseBody.builder().status(403).path(path).message(ex.getMessage())
                .time(new Date()).build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({MissingRequestHeaderException.class, BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> headMissException(Exception ex, WebRequest request) {
        loggingAdvice(ex, request);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ResponseEntity<>(ResponseBody.builder().status(400).path(path).message(ex.getMessage())
                .time(new Date()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotAllowCallingException.class, FileIOException.class})
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<?> notImplementedException(Exception ex, WebRequest request) {
        loggingAdvice(ex, request);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ResponseEntity<>(ResponseBody.builder().status(501).message(ex.getMessage())
                .time(new Date()).path(path).build(), HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex,
            WebRequest request) {
        loggingAdvice(ex, request);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(ResponseBody.builder().status(400).message(errors.get(0))
                .time(new Date()).path(path).build(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoMethodException(WebRequest request,
            NoHandlerFoundException ex) {
        loggingAdvice(ex, request);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ResponseEntity<>(ResponseBody.builder().status(404).path(path).message(ex.getMessage())
                .time(new Date()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleDefaultException(WebRequest request,Throwable ex) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ResponseEntity<>(ResponseBody.builder().status(404).path(path).message(ex.getMessage())
                .time(new Date()).build(), HttpStatus.NOT_FOUND);
    }

}