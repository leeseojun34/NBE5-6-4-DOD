//package com.grepp.spring.infra.config;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public OpenAPI openApiSpec() {
//        return new OpenAPI()
//                   .info(new Info()
//                             .title("API 문서")
//                             .description("API 명세입니다. 에러 코드는 [링크]를 참조해 주세요.")
//                             .version("v1.0.0"))
//                   .components(
//                       new Components()
//                           .addSecuritySchemes("bearerAuth"
//                               , new SecurityScheme()
//                                     .name("bearerAuth")
//                                     .type(
//                                         SecurityScheme.Type.HTTP)
//                                     .scheme(
//                                         "bearer")
//                                     .bearerFormat(
//                                         "JWT")
//                                     .description(
//                                         "JWT 토큰을 입력하세요. Bearer 는 생략하세요")
//                           ))
//                   .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
//
//    }
//}
