package cms.repository;

import cms.domain.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArcticleRepository extends CrudRepository<Article, Integer> {
}
