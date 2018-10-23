package test.tdd.weather;

import com.google.gson.JsonObject;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import tdd.weather.TemperatureExtractor;
import static org.hamcrest.CoreMatchers.is;

class TemperatureExtractorTest {

    public static final int WEATHER_MAP_TEMP = 43;

    @Test
    void retrieveTemperature() {
        MatcherAssert.assertThat(new TemperatureExtractor(createWeatherMapBody()).extract(), is(WEATHER_MAP_TEMP));
    }

    private JsonObject createWeatherMapBody() {
        JsonObject wmBody = new JsonObject();
        wmBody.add("main", createMainSection(wmBody));
        wmBody.addProperty("cod", "200");
        return wmBody;
    }

    private JsonObject createMainSection(JsonObject wmBody) {
        JsonObject value = new JsonObject();
        value.addProperty("temp", String.valueOf(WEATHER_MAP_TEMP));
        return value;
    }
}