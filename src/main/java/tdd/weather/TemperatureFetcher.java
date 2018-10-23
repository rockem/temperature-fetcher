package tdd.weather;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.io.InputStreamReader;

public class TemperatureFetcher {
    private final String weatherMapUrl;

    public TemperatureFetcher(String weatherMapUrl) {
        this.weatherMapUrl = weatherMapUrl;
    }

    public int fetchFor(String city) throws IOException {
        HttpResponse response = Request.Get(createQueryUrlFor(city)).execute().returnResponse();
        return new TemperatureExtractor(getBodyAsJsonFrom(response)).extract();
    }

    private String createQueryUrlFor(String city) {
        return weatherMapUrl + "/data/2.5/weather?q=" + city + "&appid=b408dce18a7ea0d86f37a0603c0ba0b2&units=metric";
    }

    private JsonElement getBodyAsJsonFrom(HttpResponse response) throws IOException {
        return new JsonParser().parse(new InputStreamReader(response.getEntity().getContent()));
    }

    public static class UnknownCityException extends RuntimeException {
        public UnknownCityException(String message) {
            super(message);
        }
    }

}
