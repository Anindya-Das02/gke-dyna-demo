package in.das.app.gkedemo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
@Slf4j
public class TimingClientInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpMethod method = request.getMethod();
        URI uri = request.getURI();

        long start = System.currentTimeMillis();
        ClientHttpResponse response = null;
        try {
            response = execution.execute(request, body);
            return response;
        } finally {
            long duration = System.currentTimeMillis() - start;
            int status = (response != null) ? response.getStatusCode().value() : -1;
            log.info("[External API] Outbound call, method={} url={} status={} durationInMs={}", method, uri, status, duration);
        }
    }
}
