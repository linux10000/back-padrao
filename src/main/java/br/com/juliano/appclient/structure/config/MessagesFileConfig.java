package br.com.juliano.appclient.structure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.google.common.base.Strings;

import br.com.juliano.appclient.structure.exception.business.MessageKeyNotFoundException;
import br.com.juliano.appclient.structure.props.MessagesProp;
import pl.touk.throwing.ThrowingFunction;

@Configuration
public class MessagesFileConfig {

	@PostConstruct
	public void init() {
        try (InputStream input = MessagesFileConfig.class.getClassLoader().getResourceAsStream("messages.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            Stream.of(MessagesProp.class.getDeclaredFields())
            .filter(f -> java.lang.reflect.Modifier.isStatic(f.getModifiers()))
            .map(ThrowingFunction.unchecked(f -> (String) f.get(null)))
            .forEach(key -> {
            	if ( Strings.isNullOrEmpty(prop.getProperty(key)) )
            		throw new MessageKeyNotFoundException(key);
            });
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
}
