package test.tdd.weather;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WeatherMapMock {
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

}
