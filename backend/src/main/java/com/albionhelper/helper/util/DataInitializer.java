package com.albionhelper.helper.util;

import com.albionhelper.helper.domain.board.Category;
import com.albionhelper.helper.domain.user.Role;
import com.albionhelper.helper.domain.user.User;
import com.albionhelper.helper.board.CategoryRepository;
import com.albionhelper.helper.killApiScheduler.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
    }

    // 초기 카테고리 데이터를 추가하는 작업.
    @Bean
    public CommandLineRunner initDataCategory(){
        return args -> {
            String[] cateArr = {"전체", "일반", "질문", "공략/팁", "길드홍보"};
            if(categoryRepository.findAll().isEmpty()){
                Arrays.stream(cateArr).forEach(s ->{
                    Category category = new Category();
                    category.setCategory(s);
                    categoryRepository.save(category);
                });
            }
        };
    }

    // admin 계정을 추가하는 작업.
    @Bean
    public CommandLineRunner initDataAdminData() {
        return args -> {
            if (userRepository.findByUserName("admin") == null) {
                User admin = new User();
                admin.setUserName("admin");
                admin.setPassword(passwordEncoder.encode("pjb950313!"));
                admin.setEnabled(true);
                admin.setRoles(Set.of(new Role(null, "admin", "ROLE_ADMIN")));
                userRepository.save(admin);
            }
        };
    }


}
