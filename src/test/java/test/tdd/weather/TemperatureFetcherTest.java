package test.tdd.weather;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tdd.weather.TemperatureFetcher;
import test.tdd.weather.support.WeatherMapMock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test.tdd.weather.support.WeatherMapMock.*;

class TemperatureFetcherTest {

    private final TemperatureFetcher temperatureFetcher = new TemperatureFetcher(WeatherMapMock.URL);
    private WeatherMapMock weatherMapMock;

    @BeforeEach
    void startWeatherMap() {
        weatherMapMock = WeatherMapMock.create();
    }

    @Test
    void failOnUnknownCity() throws Exception {
        assertThrows(TemperatureFetcher.UnknownCityException.class,
                () -> temperatureFetcher.fetchFor(UNKNOWN_CITY));
        weatherMapMock.hasReceived(UNKNOWN_CITY);
    }

    @Test
    void retrieveTemperatureForGivenCity() throws Exception {
        assertThat(temperatureFetcher.fetchFor(BOSTON), is(temperatureFor(BOSTON)));
    }

    @AfterEach
    void stopWeatherMap() {
        weatherMapMock.stop();
    }
}
