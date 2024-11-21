package jobplatform.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
<<<<<<< HEAD
        // config.addAllowedOrigin("http://3.37.242.106"); 
=======
//         config.addAllowedOrigin("http://3.37.242.106"); 
>>>>>>> 579dc6d90287948950819b562de7b6836d55a614
        config.addAllowedOrigin("http://localhost:8080"); // 모든 원본 허용, 실제 운영 환경에서는 특정 도메인으로 제한해야 함
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
