package test.tdd.weather.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static java.lang.Thread.sleep;

public class WeatherMapMock {
    public static final String UNKNOWN_CITY = "bambaluba";
    public static final String BOSTON = "Boston";
    public static final int PORT = 9090;
    public static final String URL = "http://localhost:" + PORT;

    private final WireMockServer wireMockServer = new WireMockServer(options().port(PORT));

    public void start() {
        wireMockServer.start();
    }

    public void hasReceived(String city) {
        wireMockServer.verify(getRequestedFor(
                urlMatching("/data/2.5/weather.*")).withQueryParam("q", equalTo(city)));
    }

    public void stop() {
        wireMockServer.stop();
    }

    public static WeatherMapMock create() {
        WeatherMapMock wm = new WeatherMapMock();
        wm.start();
        return wm;
    }

    public static void main(String[] args) {
        new WiremockRecorder("https://api.openweathermap.org").record(
                createUrlFor(BOSTON),
                createUrlFor(UNKNOWN_CITY)
        );
    }

    private static String createUrlFor(String city) {
        return String.format("/data/2.5/weather?q=%s&appid=b408dce18a7ea0d86f37a0603c0ba0b2&units=metric", city);
    }

    public static int temperatureFor(String city) {
        return new WireMockServer().getStubMappings().stream()
                .filter(s -> isOfCity(s, city))
                .map(WeatherMapMock::tempOf).findFirst()
                .get();
    }

    private static boolean isOfCity(StubMapping s, String city) {
        return s.getRequest().getUrl().contains(city);
    }

    private static int tempOf(StubMapping s) {
        return new JsonParser().parse(bodyOf(s)).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsInt();
    }

    private static String bodyOf(StubMapping s) {
        try {
            return readFile("__files/" + s.getResponse().getBodyFileName());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Failed to load body for: " + s);
        }
    }

    private static String readFile(String filePath) throws URISyntaxException, IOException {
        Path path = Paths.get(WeatherMapMock.class.getClassLoader().getResource(filePath).toURI());
        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        return data;
    }
}
