package com.newsprovider.portal.loader;

import com.newsprovider.portal.model.Category;
import com.newsprovider.portal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class CategoriesLoader implements ApplicationRunner {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        categoryService.save(new Category(null, "business", "All about business", null));
        categoryService.save(new Category(null, "entertainment", "All about entertainment", null));
        categoryService.save(new Category(null, "environment", "All about environment", null));
        categoryService.save(new Category(null, "food", "All about food", null));
        categoryService.save(new Category(null, "health", "All about health", null));
        categoryService.save(new Category(null, "politics", "All about politics", null));
        categoryService.save(new Category(null, "science", "All about science", null));
        categoryService.save(new Category(null, "sports", "All about sports", null));
        categoryService.save(new Category(null, "top", "All about top", null));
        categoryService.save(new Category(null, "technology", "All about technology", null));
        categoryService.save(new Category(null, "world", "All about world", null));
    }
}
