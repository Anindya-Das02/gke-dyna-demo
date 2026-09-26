package in.das.app.gkedemo.services;

import in.das.app.gkedemo.exceptions.ExternalServiceException;
import in.das.app.gkedemo.exceptions.ResourceNotFoundException;
import in.das.app.gkedemo.models.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final RestClient postClient;

    public List<Post> getAllPosts() {
        var posts = postClient.get()
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    if(response.getStatusCode().is5xxServerError()) {
                        throw new ExternalServiceException("Received 5xx error from external API");
                    }
                    if(response.getStatusCode().is4xxClientError()) {
                        throw new ResourceNotFoundException("Received 4xx error from external API");
                    }
                })
                .body(new ParameterizedTypeReference<List<Post>>() {
                });
        if(CollectionUtils.isEmpty(posts)) {
            log.warn("Received Empty or null posts");
            return Collections.emptyList();
        }
        log.info("received %d posts".formatted(posts.size()));
        return posts;
    }

    public Post getPostById(final int postId) {
        return postClient.get()
                .uri("/{id}", postId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    if(response.getStatusCode().is5xxServerError()) {
                        throw new ExternalServiceException("Received 5xx error from external API");
                    }
                    if(response.getStatusCode().is4xxClientError()) {
                        throw new ResourceNotFoundException("Received 4xx error from external API");
                    }
                })
                .body(Post.class);
    }

}
