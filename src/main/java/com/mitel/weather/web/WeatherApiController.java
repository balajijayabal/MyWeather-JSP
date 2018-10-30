package com.mitel.weather.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mitel.weather.exception.WeatherAppRestException;
import com.mitel.weather.service.WeatherService;
import com.weatherlibrary.datamodel.Current;
import com.weatherlibrary.datamodel.Location;
import com.weatherlibrary.datamodel.WeatherModel;

@RestController
@RequestMapping("/api/weather")
public class WeatherApiController {

	@Autowired
	private WeatherService weatherService;

	@RequestMapping(method = { RequestMethod.GET }, value = "/now")
		public ModelAndView getPresentWeatherReport(@RequestParam(value = "city", required = true) String city) throws WeatherAppRestException {
			Map<String, Object> model = new LinkedHashMap<>();
			WeatherModel weatherModel;
			try {
				weatherModel = weatherService.getPresentReport(city);
			} catch (WeatherAppRestException e) {
				throw new WeatherAppRestException("Application Error.", e);
			}
			model.put("message", weatherModel);
			return new ModelAndView("currentReport", model);
	}

	@RequestMapping(method = { RequestMethod.GET }, value = "/weekly")
		public ModelAndView getWeeklyWeatherReport(@RequestParam(value = "city", required = true) String city) throws WeatherAppRestException  {
		Map<String, Object> model = new LinkedHashMap<>();
		WeatherModel weatherModel;
		try {
			weatherModel = weatherService.getWeeklyReport(city);
		} catch (WeatherAppRestException e) {
			throw new WeatherAppRestException("Application Error.", e);
		}
		model.put("weather", weatherModel);
		return new ModelAndView("forecastReport", model);
	}
	
	@RequestMapping(method = { RequestMethod.GET }, value = "/search")
	public @ResponseBody ResponseEntity<?> getAutoSuggestResults(@RequestParam(value = "city", required = true) String city)   {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(weatherService.getSearchResults(city));
		} catch (WeatherAppRestException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}

