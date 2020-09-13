package ar.com.manutesting.paginas;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.manutesting.utiles.enums.BotonCelular;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
@Scope("cucumber-glue")
public class AppiumBase {
	
	@Value("${demo}")
	private String demo; 
		
	protected AndroidDriver<MobileElement> driver;
	
	@Autowired
	protected WebDriverWait wait;
   
	@Autowired
	 public AppiumBase(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	} 

	/**
	 * @Definición:  Busca un elemento dentro del DOM de la página según el xpath que le pasamos.
     *				Espera que el elemento esté visible hasta que se cumpla el tiempo que definimos para wait.
	 * @param locator -- Xpath por el cual se va a encontrar el elemento dentro de la página.
	 * @return MobileElement Un elemento de la página con el cual interacturar.
	 * @throws Exception
	 */
	private MobileElement encontrarElemento(By locator) throws Exception {
		if (demo.equals("true")) {esperarMilisegundos(1000);}
		return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	private MobileElement encontrarElemento(String textoDeElementoABuscar) throws Exception {
		if (demo.equals("true")) {esperarMilisegundos(1000);}
		return (MobileElement) wait.until(ExpectedConditions.visibilityOf(driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\""+textoDeElementoABuscar+"\").instance(0);")));
	}
	
	/**
	 * @Definición:  Hace click sobre un elemento de la página que fue encontrado según el xpath que le pasamos.
	 * @param locator -- Xpath por el cual se va a encontrar el elemento dentro de la página.
	 * @param nombreElemento -- Nombre con el cual se va a representar el elemento en el log
	 * @throws Exception
	 */	
	public void clickElemento(String locator, String nombreElemento) throws Exception {
		encontrarElemento(locator).click();
		log.info("Se hizo click en el elemento: "+nombreElemento);
	}
	
	public void clickElemento(By locator, String nombreElemento) throws Exception {
		encontrarElemento(locator).click();
		log.info("Se hizo click en el elemento: "+nombreElemento);
	}
	
	/**
	 * @Definición:  Escribe el texto que le pasamos dentro de un elemento de la página.
	 * @param locator -- Xpath por el cual se va a encontrar el elemento dentro de la página.
	 * @param texto -- Texto a ingresar dentro del elemento.
	 * @param nombreElemento -- Nombre con el cual se va a representar el elemento en el log
	 * @throws Exception
	 */
	public void escribirTextoEnElemento(String locator, String texto, String nombreElemento) throws Exception{
		encontrarElemento(locator).clear();
		encontrarElemento(locator).sendKeys(texto);
		log.info("Se escribió: "+texto+" sobre el elemento: "+nombreElemento);
	}
	
	public void escribirTextoEnElemento(By locator, String texto, String nombreElemento) throws Exception{
		encontrarElemento(locator).clear();
		encontrarElemento(locator).sendKeys(texto);
		log.info("Se escribió: "+texto+" sobre el elemento: "+nombreElemento);
	}
	
	/**
	 * @Definición:  Escribe el texto que le pasamos con el teclado del celular.
     *				 Primero levanta el teclado del celular y escribe presionando las teclas del mismo
	 * @param locator -- Xpath por el cual se va a encontrar el elemento dentro de la página.
	 * @param texto -- Texto a ingresar dentro del elemento. 
	 * @param nombreElemento -- En el log se visualiza mas claramente el nombre del elemento y no el xpath
	 * @throws Exception
	 */
	public void escribirTextoEnElementoConTecladoMobile(By locator, String texto, String nombreElemento) throws Exception{
		eliminarTextoDeElemento(locator);
		Actions action = new Actions(driver);
		action.sendKeys(texto).perform();
		bajarTeclado();
		log.info("Se escribió: "+texto+" sobre el elemento: "+nombreElemento);
	}

	/**
	 * @Definición:  Presiona un boton del celular
	 * @param tecla -- Boton a presionar. Proviene del ENUM Teclas.
	 * @throws Exception
	 */
	public void presionarBotonMobile(BotonCelular tecla) throws Exception {
		esperarMilisegundos(1000);
		switch (tecla) {
		case ATRAS:
			driver.navigate().back();
			break;
		default:
			break;
		}
		log.info("Se presionó "+tecla);
	}

	/**
	 * @Definición:  Valida el texto que tiene un elemento de la página.
	 * @param locator -- Xpath por el cual se va a encontrar el elemento dentro de la página.
	 * @param text -- Texto a validar.
	 * @param nombreElemento -- Nombre con el cual se va a representar el elemento en el log
	 * @throws Exception
	 */
	public void validarTextoDeElemento(String locator, String text, String nombreElemento) throws Exception {
		Assert.assertEquals("El valor: \'"+text+"\' no se encuentra dentro de: \'"+encontrarElemento(locator).getText()+"\'",text, encontrarElemento(locator).getText());
		log.info("Se validó que "+text+" se visualiza en el elemento con locator: "+nombreElemento);
	}
	
	public void validarTextoDeElemento(By locator, String text, String nombreElemento) throws Exception {
		Assert.assertEquals("El valor: \'"+text+"\' no se encuentra dentro de: \'"+encontrarElemento(locator).getText()+"\'", text, encontrarElemento(locator).getText());
		log.info("Se validó que "+text+" se visualiza en el elemento: "+nombreElemento);
	}
	
	
	/**
	 * @Definición:  Valida que un elemento de la página contenga un texto determinado.
	 * @param locator -- Xpath por el cual se va a encontrar el elemento dentro de la página.
	 * @param textoEsperado -- Texto a validar.
	 * @param nombreElemento -- Nombre con el cual se va a representar el elemento en el log
	 * @throws Exception
	 */
	public void validarQueElementoContengaUnTexto(String locator, String textoEsperado, String nombreElemento) throws Exception {
		String textoQueSeVisualiza = encontrarElemento(locator).getText();
		Assert.assertTrue("El valor: \'"+textoEsperado+"\' no se encuentra dentro de: \'"+textoQueSeVisualiza+"\'", textoQueSeVisualiza.contains(textoEsperado));
		log.info("Se validó que "+textoEsperado+" se visualiza dentro del elemento con locator: "+nombreElemento);
	}
	
	public void validarQueElementoContengaUnTexto(By locator, String textoEsperado, String nombreElemento) throws Exception {
		String textoQueSeVisualiza = encontrarElemento(locator).getText();
		Assert.assertTrue("El valor: \'"+textoEsperado+"\' no se encuentra dentro de: \'"+textoQueSeVisualiza+"\'", textoQueSeVisualiza.contains(textoEsperado));
		log.info("Se validó que "+textoEsperado+" se visualiza dentro del elemento: "+nombreElemento);
	}

	/**
	 * @Definición:  Presiona un combo de la página y busca un elemento dentro de el.
     *				 Hace click en el combo y luego click en el valor dentro de el.
	 * @param combo -- Xpath del combo a presionar.
	 * @param value -- Xpath del valor a seleccionar dentro del combo.
	 * @param nombreElemento -- Nombre con el cual se va a representar el elemento en el log
	 */
	public void seleccionarUnElementoDeUnCombo(String combo, String value, String nombreElemento) throws Exception {
		encontrarElemento(combo).click();
		encontrarElemento(value).click();
		log.info("Se seleccionó el valor: "+value+" del elemento: "+nombreElemento);
	}
	
	public void seleccionarUnElementoDeUnCombo(By combo, String value, String nombreElemento) throws Exception {
		encontrarElemento(combo).click();
		encontrarElemento(value).click();
		log.info("Se seleccionó el valor: "+value+" del elemento: "+nombreElemento);
	}
	
	/**
	 * @Definición:  Hace scroll dentro la página hasta el elemento que contenga el texto que le pasamos
	 * @param textoDeElementoABuscar -- Texto que contiene el elemento dentro de la página.
	 * @throws Exception
	 */
	public void hacerScrollHaciaUnElemento(String textoDeElementoABuscar) throws Exception {
		esperarMilisegundos(1000);
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView("                      
		        + "new UiSelector().text(\""+textoDeElementoABuscar+"\").instance(0));");	
		log.info("Se hizo scroll hasta el elemento con texto: "+textoDeElementoABuscar);
	}
	
	/**
	 * Definición:  Hace scroll hacia arriba hasta llegar al comienzo de la página
	 * @throws Exception
	 */
	public void hacerScrollHaciaArriba() throws Exception {
		Dimension dim = driver.manage().window().getSize();
		int x = dim.getWidth()/2;
		int startY = (int) (dim.getHeight() * 0.2);
		int endY = (int) (dim.getHeight() * 0.8);
		esperarMilisegundos(1000);
		new TouchAction<>(driver)
		.press(PointOption.point(x,startY))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
		.moveTo(PointOption.point(x, endY))
		.release().perform();
		log.info("Se hizo scroll hacia arriba");
	}

	/**
	 * @Definición:  Valida si el elemento esta habilitado
	 * @param locator
	 * @return
	 * @throws Exception
	 */
	public Boolean estaHabilitadoElElemento(By locator) throws Exception {
		Boolean habilitado = encontrarElemento(locator).isEnabled();
		log.info("Elemento habilitado "+ habilitado.toString());
		return habilitado;
	}
	
	/**
	 * @Definición:  Valida si el elemento esta habilitado
	 * @param locator -- elemento a esperar
	 * @param tiempo -- Tiempo en segundos a esperar como máximo que aparezca el elemento
	 * @throws Exception
	 */
	public void esperarElemento(By locator, long tiempo) throws Exception{
		WebDriverWait esperar = new WebDriverWait(driver, tiempo);
		esperar.until(ExpectedConditions.attributeToBe(locator, "displayed", "true"));
		log.info("Se esperó al elemento "+locator+" por "+tiempo+" segundos.");
	}
	
	public void esperarElemento(String locator, long tiempo) throws Exception{
		WebDriverWait esperar = new WebDriverWait(driver, tiempo);
		esperar.until(ExpectedConditions.attributeToBe(encontrarElemento(locator), "displayed", "true"));
		log.info("Se esperó al elemento "+locator+" por "+tiempo+" segundos.");
	}
	
	/**
	 * @Definición: Espera un tiempo fijo en milisegundos. NO SE DEBE UTILIZAR EN ABUSO, SOLO CASO EXTREMO.
	 * @param milisegundos -- Cantidad de milisegundos que queremos que espere
	 * @throws InterruptedException
	 */
	public void esperarMilisegundos(long milisegundos) throws InterruptedException {
		Thread.sleep(milisegundos);		
	}
	
	/**
	 * @Definición:  Dentro de un elemento a completar con texto, se dirige al final del texto y eliminar con el DELETE del teclado del celular todo el texto.
	 * @param locator -- elemento que contiene texto a eliminar
	 * @throws Exception
	 */
	public void eliminarTextoDeElemento(By locator) throws Exception {
		encontrarElemento(locator).click();
		int longitudTexto = encontrarElemento(locator).getText().length();
		for (int i = 0; i < longitudTexto; i++) {
			driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
		}
		for (int i = 0; i < longitudTexto; i++) {
			driver.pressKey(new KeyEvent(AndroidKey.DEL));
		}
		esperarMilisegundos(1000);
	}
	
	/**
	 * @Definición:  Minimiza el teclado del celular
	 * @throws InterruptedException
	 */
	public void bajarTeclado() throws InterruptedException {
		driver.hideKeyboard();
		esperarMilisegundos(1000);
	}
}
