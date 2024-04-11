package com.example.keytech;

import com.example.keytech.util.RecommendationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

@SpringBootTest
class KeyTechApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testRecommendationUtil() throws Exception {
        ClassPathResource resource = new ClassPathResource("static/data.in");
        System.out.println(resource.getFile().getAbsolutePath());
        RecommendationUtil.getItemBasedRecommendation(resource.getFile().getAbsolutePath(), 2);
    }
}
