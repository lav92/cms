package cms;

import cms.domain.Article;
import cms.repository.ArcticleRepository;
import cms.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.*;

@Controller
public class MainController {
    private final ArcticleRepository arcticleRepository;

    private final ArticleService articleService;

    public MainController(ArcticleRepository arcticleRepository, ArticleService articleService) {
        this.arcticleRepository = arcticleRepository;
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        Iterable<Article> publishedArticles;
        publishedArticles = arcticleRepository.findAll();
        Iterator<Article> iter = publishedArticles.iterator();

        List <Article> list= new ArrayList<Article>();
        List <Article> publishedList = new ArrayList<Article>();
        List <Article> unpublishedList = new ArrayList<>();

        Calendar date = Calendar.getInstance();

        while (iter.hasNext()) {
            list.add(iter.next());
        }

        for (Article articles : list){
            long longDate = date.getTimeInMillis();
            long lon = articles.getPublishedAt().getTime();

            if (longDate > lon) {
                publishedList.add(articles);
            } else {
                unpublishedList.add(articles);
            }
        }

        model.put("publishedArticles", publishedList);
        model.put("unpublishedArticles", unpublishedList);

        return "index";
    }

    @GetMapping("/newArticle")
    public String newArticle(Map<String, Object> model) {
        Iterable<Article> articles = arcticleRepository.findAll();

        model.put("articles", articles);

        return "newArticle";
    }

    @PostMapping("/newArticle")
    public String addNewArticle (@RequestParam String title,
                       @RequestParam String description,
                       @RequestParam String slug,
                       @RequestParam String menuLabel,
                       @RequestParam String h1,
                       @RequestParam String content,
                       @RequestParam int priority,
                       @RequestParam Date publishedAt,
                       Map <String, Object> model) {
        Article article = new Article(title, description, slug, menuLabel, h1, content, priority, publishedAt);

        arcticleRepository.save(article);

        return "/newArticle";
    }

    @GetMapping(value = "/{slug}")
    public String showArticle(@PathVariable(required = false) String slug,
                                Map<String, Object> model) {
        Iterable<Article> articles;
        articles = arcticleRepository.findBySlug(slug);
        model.put("articles", articles);
        return "article";
    }

    @GetMapping(value = "/editArticle/{slug}")
    public String editArticle (@PathVariable(required = false) String slug,
                               Map<String, Object> model) {
        Iterable<Article> articles;
        articles = arcticleRepository.findBySlug(slug);

        System.out.println(articles.toString());

        model.put("articles", articles);
        return "editArticle";
    }

    @PostMapping("/editArticle/{slug}")
    public String saveEditArticle (Article article) {
        articleService.saveArticle(article);
        return "redirect:/";
    }
}
