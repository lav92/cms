package cms.repository;

import cms.domain.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArcticleRepository extends CrudRepository<Article, Integer> {
    List<Article> findBySlug(String slug);
}
