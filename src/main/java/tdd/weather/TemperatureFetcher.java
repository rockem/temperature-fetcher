package tdd.weather;

import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class TemperatureFetcher {
    private final String weatherMapUrl;

    public TemperatureFetcher(String weatherMapUrl) {
        this.weatherMapUrl = weatherMapUrl;
    }

    public void fetchFor(String city) {
        try {
            Request.Get(weatherMapUrl + "/data/2.5/weather?q=" + city).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new UnknownCityException("Failed to fetch temperature for: " + city);
    }

    public static class UnknownCityException extends RuntimeException {
        public UnknownCityException(String message) {
            super(message);
        }
    }
}
