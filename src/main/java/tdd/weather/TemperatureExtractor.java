package tdd.weather;

import com.google.gson.JsonElement;

public class TemperatureExtractor {
    private JsonElement weather;

    public TemperatureExtractor(JsonElement weather) {
        this.weather = weather;
    }

    public int extract() {
        if (weather.getAsJsonObject().get("cod").getAsString().equals("200")) {
            return weather.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsInt();
        }
        throw new TemperatureFetcher.UnknownCityException("Failed to fetch temperature");
    }
}
