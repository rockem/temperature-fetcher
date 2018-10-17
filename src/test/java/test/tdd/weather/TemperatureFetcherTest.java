package test.tdd.weather;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tdd.weather.TemperatureFetcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static test.tdd.weather.WeatherMapMock.*;

class TemperatureFetcherTest {

    private WeatherMapMock weatherMapMock;

    @BeforeEach
    void startWeatherMap() {
        weatherMapMock = WeatherMapMock.create();
    }

    @Test
    void failOnUnknownCity() {
        try {
            new TemperatureFetcher(WeatherMapMock.URL).fetchFor(UNKNOWN_CITY);
            fail(); // We didn't get an exception and thus our test fail
        } catch(TemperatureFetcher.UnknownCityException e) {
            // Got an exception as expected
        }
        weatherMapMock.hasReceived(UNKNOWN_CITY);
    }

    @Test
    void retrieveTemperatureForGivenCity() {
        assertThat(new TemperatureFetcher(WeatherMapMock.URL).fetchFor(BOSTON), is(temperatureFor(BOSTON)));
    }

    @AfterEach
    void stopWeatherMap() {
        weatherMapMock.stop();
    }
}
