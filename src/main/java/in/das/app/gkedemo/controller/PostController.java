package in.das.app.gkedemo.controller;

import in.das.app.gkedemo.models.Post;
import in.das.app.gkedemo.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        log.info("invoked PostController::getAllPosts");
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") int id) {
        log.info("invoked PostController::getPostById : " + id);
        return postService.getPostById(id);
    }

}
