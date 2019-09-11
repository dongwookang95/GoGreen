//package server;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import server.entity.habit.category.Category;
//import server.entity.habit.category.SubCategory;
//import server.entity.user.Badge;
//import server.repository.habit.CategoryRepository;
//import server.repository.habit.SubCategoryRepository;
//import server.repository.user.BadgeRepository;
//
//@Component
//@Slf4j
//public class DataInitializer implements CommandLineRunner {
//
//    @Autowired
//    private BadgeRepository badgeRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private SubCategoryRepository subCategoryRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        badgeRepository.save(Badge.builder().name("bronze").build());
//        badgeRepository.save(Badge.builder().name("silver").build());
//        badgeRepository.save(Badge.builder().name("gold").build());
//
//        categoryRepository.save(Category.builder().description("Food").build());
//        categoryRepository.save(Category.builder().description("Transport").build());
//        categoryRepository.save(Category.builder().description("Energy").build());
//
//        subCategoryRepository.save(SubCategory.builder().description("Vegetarian Meal").build());
//        subCategoryRepository.save(SubCategory.builder().description("Local Produce").build());
//        subCategoryRepository.save(SubCategory.builder().description("Public Transport").build());
//        subCategoryRepository.save(SubCategory.builder().description("Travel By Bike").build());
//        subCategoryRepository.save(SubCategory.builder().description("Solar Panel").build());
//        subCategoryRepository.save(SubCategory.builder().description("Lower Temperature")
//        .build());
//    }
//}
