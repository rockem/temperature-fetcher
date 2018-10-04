package test.tdd.weather;

import org.junit.jupiter.api.Test;
import tdd.weather.WeatherFetcher;

import static org.junit.jupiter.api.Assertions.fail;

class WeatherFetcherTest {

    private static final String UNKNOWN_CITY = "bambaluba";

    private final WeatherMapMock weatherMapMock = new WeatherMapMock();

    @Test
    void failOnUnknownCity() {
        try {
            new WeatherFetcher().fetchFor(UNKNOWN_CITY);
            fail(); // We didn't get an exception and thus our test fail
        } catch(WeatherFetcher.UnknownCityException e) {
            // Got an exception as expected
        }
        weatherMapMock.shouldRetrieve(UNKNOWN_CITY);
    }
}
