//package com.jobjunction.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//
//@Configuration
//public class FileStorageConfig {
//
//    @Value("${document.upload.directory}")
//    private String uploadDirectory;
//
//    public CommonsMultipartResolver commonsMultipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("utf-8");
//        resolver.setResolveLazily(true);
//        resolver.setMaxUploadSize(5242880); // 5MB
//        return resolver;
//    }
//}
