package com.facenet.bt2.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary("cloudinary://255529535265179:lcPjnvokXVLrBjecFfud4T18sVI@dwfqbhqcr");
    }
}
