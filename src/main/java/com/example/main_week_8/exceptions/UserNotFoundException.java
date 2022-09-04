package com.example.main_week_8.exceptions;

public class UserNotFoundException extends RuntimeException {

        public UserNotFoundException(String message) {
            super(message);
        }
}
