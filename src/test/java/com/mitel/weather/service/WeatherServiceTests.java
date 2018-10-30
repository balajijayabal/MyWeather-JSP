package com.mitel.weather.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.mitel.weather.exception.WeatherAppRestException;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTests {

	@Mock
    WeatherService weatherService;
    
    @Mock
    WeatherAppRestException weatherAppRestException;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void getPresentReportTest()throws Exception {
    	String city = "helsingborg";  
    	weatherService.getPresentReport(city);
    }
}
