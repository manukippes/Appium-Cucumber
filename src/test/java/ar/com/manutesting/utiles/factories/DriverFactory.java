package ar.com.manutesting.utiles.factories;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.manutesting.utiles.enums.SistemaOperativoMobile;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
@Scope("cucumber-glue")
public class DriverFactory {

	protected AndroidDriver<MobileElement> driver;
	protected File app;
	
	@Value("${jenkins:false}")
	protected Boolean jenkins;
	
	@Value("${so}")
	private SistemaOperativoMobile mobile;
	
    @Value("${device.name}")
	protected String deviceName;
	
	@Value("${udid}")
	protected String udid;
	
	@Value("${platform.name}")
	protected String platformName;
	
	@Value("${platform.version}")
	protected String platformVersion;
	
	@Value("${app.package:false}")
	protected String appPackage;
	
	@Value("${app.activity:false}")
	protected String appActivity;
	
	@Value("${apk.dir:false}")
	protected String apkDir;
	
	@Value("${apk.name:false}")
	protected String apkName;
	
	@Value("${time.out}")
	private long timeOut;
	
	@Autowired
	private AppiumDriverLocalService service;

	public AndroidDriver<MobileElement> getDriver() throws IOException, InterruptedException {
			switch (mobile) {
			case ANDROID:
				DesiredCapabilities clienteCapacidades = new DesiredCapabilities();
				clienteCapacidades.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				clienteCapacidades.setCapability(MobileCapabilityType.UDID, udid);
				clienteCapacidades.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
				clienteCapacidades.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
				clienteCapacidades.setCapability("unicodeKeyboard", false);
				clienteCapacidades.setCapability("resetKeyboard", false);
				clienteCapacidades.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 360);
				//clienteCapacidades.setCapability("isHeadless", true);
				//clienteCapacidades.setCapability("fullReset", true);
				//clienteCapacidades.setCapability("noReset", false);
				
				//***CON APK
				//app = new File(apkDir, apkName);
				//clienteCapacidades.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
				//*** CON appPackage y appActivity
				clienteCapacidades.setCapability("appPackage", appPackage);
				clienteCapacidades.setCapability("appActivity", appActivity);
				
				log.info("Se configuro correctamente el entorno local. Generando el driver...");
				driver = new AndroidDriver<MobileElement>(service.getUrl(), clienteCapacidades);
				driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
				log.info("Se generó correctamente el driver");
				return driver;
				
			default:
				throw new IllegalArgumentException("Driver no encontrado entre los navegadores: " + mobile);
		}
	
	}
}
