package test.tdd.weather.support;

import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Arrays;

public class WiremockRecorder {

    private final String proxy;

    public WiremockRecorder(String proxy) {
        this.proxy = proxy;
    }

    public void record(String... slags) {
        WireMockServerRunner wiremock = new WireMockServerRunner();
        wiremock.run(
                "--proxy-all=" + proxy,
                "--root-dir=src/test/resources",
                "--record-mappings",
                "--verbose");
        getAll(slags);
        wiremock.stop();
    }

    private void getAll(String[] slags) {
        Arrays.stream(slags).forEach((slag) -> {
            try {
                Request.Get("http://localhost:8080" + slag).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
