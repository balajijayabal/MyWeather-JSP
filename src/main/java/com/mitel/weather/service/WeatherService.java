package com.mitel.weather.service;

import java.net.URI;

import com.mitel.weather.exception.WeatherAppRestException;
import com.mitel.weather.service.WeatherService;
import com.weatherlibrary.datamodel.Current;
import com.weatherlibrary.datamodel.Forecast;
import com.weatherlibrary.datamodel.Location;
import com.weatherlibrary.datamodel.WeatherModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

@Service
public class WeatherService {
	
	@Value("${app.weather.api.key}")
	private String apiKey;

	private static final String WEATHER_URL =
			"http://api.apixu.com/v1/current.json?key={key}&q={city}";

	private static final String FORECAST_URL =
			"http://api.apixu.com/v1/forecast.json?key={key}&q={city}&days=7";
	
	private static final String SEARCH_URL =
			"http://api.apixu.com/v1/search.json?key={key}&q={city}";

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

	private final RestTemplate restTemplate;

	public WeatherService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public WeatherModel getPresentReport(String city) throws WeatherAppRestException {
		logger.info("Requesting present weather report for ", city);
		try {
			URI url = new UriTemplate(WEATHER_URL).expand(this.apiKey, city);
			return invoke(url, WeatherModel.class);
		} catch (HttpClientErrorException e) {
			throw new WeatherAppRestException("Authentication Error. Please check your key.", e);
		} catch (WeatherAppRestException e) {
			throw new WeatherAppRestException("Application Error.", e);
		} 
	}

	public WeatherModel getWeeklyReport(String city) throws WeatherAppRestException{
		logger.info("Requesting weekly weather report for ", city);
		try {
		URI url = new UriTemplate(FORECAST_URL).expand(this.apiKey,city);
		return invoke(url, WeatherModel.class);
		} catch (HttpClientErrorException e) {
			throw new WeatherAppRestException("Authentication Error. Please check your key.", e);
		} catch (WeatherAppRestException e) {
			throw new WeatherAppRestException("Application Error.", e);
		} 
	}
	
	public Location[] getSearchResults(String city) throws WeatherAppRestException, HttpClientErrorException{
		logger.info("Requesting auto suggest results for ", city);
		try {
		URI url = new UriTemplate(SEARCH_URL).expand(this.apiKey,city);
		RequestEntity<?> request = RequestEntity.get(url)
				.accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Location[]> exchange = this.restTemplate
				.exchange(request,  Location[].class);
		return exchange.getBody();
		} catch (HttpClientErrorException e) {
			throw new WeatherAppRestException("Authentication Error.", e);
		}
	}

	private WeatherModel invoke(URI url, Class<WeatherModel> responseType) throws WeatherAppRestException, HttpClientErrorException{
		RequestEntity<?> request = RequestEntity.get(url)
				.accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<WeatherModel> exchange = this.restTemplate
				.exchange(request, responseType);
		return exchange.getBody();
	}
	
}