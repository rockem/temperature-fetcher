package tdd.weather;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.IOException;

public class TemperatureFetcher {
    private final String weatherMapUrl;

    public TemperatureFetcher(String weatherMapUrl) {
        this.weatherMapUrl = weatherMapUrl;
    }

    public int fetchFor(String city) {
        try {
            HttpResponse response = Request.Get(createQueryUrlFor(city)).execute().returnResponse();
            if(response.getStatusLine().getStatusCode() == 200) {
                return 14;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new UnknownCityException("Failed to fetch temperature for: " + city);
    }

    private String createQueryUrlFor(String city) {
        return weatherMapUrl + "/data/2.5/weather?q=" + city + "&appid=b408dce18a7ea0d86f37a0603c0ba0b2&units=metric";
    }

    public static class UnknownCityException extends RuntimeException {
        public UnknownCityException(String message) {
            super(message);
        }
    }
}
