package in.das.app.gkedemo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "external.apis.posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostConfigProperties {
    String baseUrl;
    Duration connectTimeout;
    Duration readTimeout;
}
