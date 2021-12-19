package sample.multimodule.service;

import java.util.Map;
import java.util.function.Supplier;

public interface HttpBinService {
    Map get();

    Map delay(int seconds);

    Supplier<Map> delaySuppplier(int seconds);
}
