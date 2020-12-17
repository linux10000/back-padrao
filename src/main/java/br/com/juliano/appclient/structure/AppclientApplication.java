package br.com.juliano.appclient.structure;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.PreDestroy;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories({
	"br.com.juliano.appclient.repository"
})
@ComponentScan({ 
	"br.com.juliano.appclient.controller", 
	"br.com.juliano.appclient.service",
	"br.com.juliano.appclient.structure",
	"br.com.juliano.appclient.rest"
})
public class AppclientApplication implements ApplicationListener<ApplicationReadyEvent>,AsyncConfigurer,AsyncUncaughtExceptionHandler {

	public static void main(String[] args) {
		SpringApplication.run(AppclientApplication.class, args);
	}
	
	@PreDestroy
	public void onExit() throws IOException {
		System.out.println("aplicacao do juliano vai fechar");
	}
	
	@Override
	public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
		arg0.printStackTrace();
	}
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("aplicacao do juliano inicializou");
	}

}
