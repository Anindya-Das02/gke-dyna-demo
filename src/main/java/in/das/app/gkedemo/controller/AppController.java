package in.das.app.gkedemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class AppController {

    @GetMapping("/test")
    public Map<String, Object> test() {
        log.info("invoked AppController::test");
        return Map.of("message","Server up", "status", 200);
    }
}
