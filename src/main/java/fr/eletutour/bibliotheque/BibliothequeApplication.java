package fr.eletutour.bibliotheque;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme("my-theme")
public class BibliothequeApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(BibliothequeApplication.class, args);
	}

}
