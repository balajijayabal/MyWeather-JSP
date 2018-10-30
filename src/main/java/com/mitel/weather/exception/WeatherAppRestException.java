package com.mitel.weather.exception;

public class WeatherAppRestException extends Exception {

	private static final long serialVersionUID = 1L;

	public WeatherAppRestException(String message, Throwable cause) {
        super(message, cause);
    }
}
