package cms.service;

import cms.domain.Article;
import cms.repository.ArcticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArcticleRepository arcticleRepository;

    public ArticleService(ArcticleRepository arcticleRepository) {
        this.arcticleRepository = arcticleRepository;
    }

    public Article saveArticle (Article article) {
        return arcticleRepository.save(article);
    }

    public void deleteArticle (int id) {
        arcticleRepository.deleteById(id);
    }
}
