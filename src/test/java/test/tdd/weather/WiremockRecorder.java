package test.tdd.weather;

import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;

public class WiremockRecorder {

    private final String proxy;

    public WiremockRecorder(String proxy) {
        this.proxy = proxy;
    }

    public void record() {
        new WireMockServerRunner().run(
                "--proxy-all=" + proxy,
                "--root-dir=src/test/resources",
                "--record-mappings",
                "--verbose");
    }
}
