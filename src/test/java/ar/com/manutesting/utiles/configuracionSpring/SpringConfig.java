package ar.com.manutesting.utiles.configuracionSpring;

import java.io.IOException;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import ar.com.manutesting.utiles.factories.DriverFactory;
import ar.com.manutesting.utiles.factories.ServiceAppiumFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

@Configuration
public class SpringConfig {
	
	@Value("${time.out}")
	private int timeOut;
	
	@Autowired
	@Lazy
	private ServiceAppiumFactory servicioAppium;
	
	@Autowired
	@Lazy
	private DriverFactory driverFactory;
	
	@Bean(destroyMethod="stop")
	@Scope("cucumber-glue")
	public AppiumDriverLocalService iniciarServicioAppium(){return servicioAppium.iniciaService();}
	
	@Scope("cucumber-glue")
	@Bean(destroyMethod="quit")
	@DependsOn("iniciarServicioAppium")
	public AndroidDriver<MobileElement> getDriver() throws IOException, InterruptedException {return driverFactory.getDriver();}
		
	@Scope("cucumber-glue")
	@Bean
	@DependsOn("getDriver")
	public WebDriverWait waitFor() throws IOException, InterruptedException {return new WebDriverWait(getDriver(), timeOut);}
}
