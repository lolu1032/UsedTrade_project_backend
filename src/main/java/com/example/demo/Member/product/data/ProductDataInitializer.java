package com.example.demo.Member.product.data;

import com.example.demo.Member.product.domain.Product;
import com.example.demo.Member.product.domain.Category;
import com.example.demo.Member.product.domain.Location;
import com.example.demo.Member.Auth.domain.Users;
import com.example.demo.Member.product.repository.BoardRepository;
import com.example.demo.Member.product.repository.CategoryRepository;
import com.example.demo.Member.product.repository.LocationRepository;
import com.example.demo.Member.Auth.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class ProductDataInitializer {

    private final BoardRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public ProductDataInitializer(BoardRepository productRepository, UserRepository userRepository,
                                  CategoryRepository categoryRepository, LocationRepository locationRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    @PostConstruct
    @Transactional
    public void initDummyProducts() {
        Random random = new Random();

        // Users, Category, Location 데이터 확인
        long userCount = userRepository.count();
        long categoryCount = categoryRepository.count();
        long locationCount = locationRepository.count();

        if (userCount == 0 || categoryCount == 0 || locationCount == 0) {
            System.out.println("필요한 데이터가 부족합니다. 더미 데이터를 확인하세요.");
            return;
        }

        // 더미 데이터 생성
        for (int i = 1; i <= 50; i++) {
            Users user = userRepository.findById((long) (random.nextInt(10) + 1)).orElse(null);
            Category category = categoryRepository.findById((long) (random.nextInt(5) + 1)).orElse(null);
            Location location = locationRepository.findById((long) (random.nextInt(10) + 1)).orElse(null);

            if (user == null || category == null || location == null) continue;

            Product product = Product.builder()
                    .title("Product " + i)
                    .description("Description for product " + i)
                    .price(BigDecimal.valueOf(random.nextInt(1000000) + 10000))
                    .views(random.nextInt(100))
                    .status(i % 2 == 0 ? "NEW" : "USED")
                    .user(user)
                    .category(category)
                    .location(location)
                    .build();

            productRepository.save(product);
            System.out.println("Product " + i + " saved successfully!");
        }
    }


}
