package cms;

import cms.domain.Article;
import cms.repository.ArcticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.util.calendar.LocalGregorianCalendar;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private ArcticleRepository arcticleRepository;
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

}
