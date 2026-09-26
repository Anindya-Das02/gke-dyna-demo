package in.das.app.gkedemo.config;

import in.das.app.gkedemo.interceptor.TimingClientInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(PostConfigProperties.class)
public class AppConfig {

    @Bean(name = "postClient")
    public RestClient postsClient(RestClient.Builder builder, PostConfigProperties props, TimingClientInterceptor timingClientInterceptor) {
        HttpClientSettings settings = HttpClientSettings.defaults()
                .withConnectTimeout(props.getConnectTimeout())
                .withReadTimeout(props.getReadTimeout());

        return builder.baseUrl(props.getBaseUrl())
                .requestFactory(ClientHttpRequestFactoryBuilder.detect().build(settings))
                .requestInterceptor(timingClientInterceptor)
                .build();
    }
}
