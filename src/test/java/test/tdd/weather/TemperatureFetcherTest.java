package test.tdd.weather;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tdd.weather.TemperatureFetcher;

import static org.junit.jupiter.api.Assertions.fail;

class TemperatureFetcherTest {

    private static final String UNKNOWN_CITY = "bambaluba";


    @BeforeEach
    void startWeatherMap() {
    }

    @Test
    void failOnUnknownCity() {
        WeatherMapMock weatherMapMock = WeatherMapMock.create();
        try {
            new TemperatureFetcher(WeatherMapMock.URL).fetchFor(UNKNOWN_CITY);
            fail(); // We didn't get an exception and thus our test fail
        } catch(TemperatureFetcher.UnknownCityException e) {
            // Got an exception as expected
        }
        weatherMapMock.hasReceived(UNKNOWN_CITY);
        weatherMapMock.stop();
    }

    @AfterEach
    void stopWeatherMap() {
    }
}
