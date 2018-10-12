package test.tdd.weather;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tdd.weather.TemperatureFetcher;

import static org.junit.jupiter.api.Assertions.fail;

class TemperatureFetcherTest {

    private static final String UNKNOWN_CITY = "bambaluba";

    private final WeatherMapMock weatherMapMock = new WeatherMapMock();

    @BeforeEach
    void startWeatherMap() {
        weatherMapMock.start();
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

    @AfterEach
    void stopWeatherMap() {
        weatherMapMock.stop();
    }
}
