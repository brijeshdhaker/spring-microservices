package sample.multimodule.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.Supplier;

@Service
public class HttpBinServiceImpl implements sample.multimodule.service.HttpBinService {


    private RestTemplate rest;

    public HttpBinServiceImpl(RestTemplate rest) {
        this.rest = rest;
    }

    @Override
    public Map get() {
        return rest.getForObject("https://httpbin.org/get", Map.class);

    }

    @Override
    public Map delay(int seconds) {
        return rest.getForObject("https://httpbin.org/delay/" + seconds, Map.class);
    }

    @Override
    public Supplier<Map> delaySuppplier(int seconds) {
        return () -> this.delay(seconds);
    }
}
