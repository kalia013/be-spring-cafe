package codesquad.springcafe.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/qna/form")
    public String qna() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String qnaCreate(@ModelAttribute Article article) {
        article.setTime(LocalDateTime.now());
        article.setArticleNum(articleRepository.articleSize() + 1);
        articleRepository.add(article);
        return "redirect:/" ;
    }


    @GetMapping("/qna/{articleNum}")
    public String show(@PathVariable int articleNum, Model model) {
        Optional<Article> optionalArticle = articleRepository.findByIndex(articleNum);
        if (optionalArticle.isPresent()) {
            model.addAttribute("article", optionalArticle.get());
            return "qna/show";
        }
        return "redirect:/"; // 게시글을 찾을 수 없는 경우 메인 페이지로 리다이렉트합니다.
    }
}
