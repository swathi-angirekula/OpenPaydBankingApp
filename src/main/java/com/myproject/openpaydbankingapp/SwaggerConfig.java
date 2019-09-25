package com.myproject.openpaydbankingapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Swathi Angirekula
 * 
 *         This class is used for Integrating Swagger 2 into the Project. This
 *         will generate the Swagger documentation for the users to access. The
 *         same will be accessible under :http://localhost:8080/swagger-ui.html
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * After the Docket bean is defined, its select() method returns an instance of
	 * ApiSelectorBuilder, which provides a way to control the endpoints exposed by
	 * Swagger.
	 * 
	 * @return Docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
}
