package ar.com.manutesting.utiles.factories;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@Scope("cucumber-glue")
public class ServiceAppiumFactory {
	
	protected AppiumDriverLocalService service;
	
	@Value("${ip.adress}")
	protected String iPAdress;
	
	@Value("${port}")
	protected int port;
	
	public AppiumDriverLocalService iniciaService() {
		AppiumServiceBuilder builder;
		log.info("Construyendo e iniciando el servicio Appium...");
		builder = new AppiumServiceBuilder();
		builder.withIPAddress(iPAdress);
		builder.usingPort(port);
		builder.withAppiumJS(new File("C:\\Users\\Manu\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"));
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error"); //debug, error, info
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		log.info("Servicio iniciado en la direccion "+ service.getUrl());	
		return service;
	}
}
