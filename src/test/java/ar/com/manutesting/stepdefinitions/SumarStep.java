package ar.com.manutesting.stepdefinitions;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.manutesting.paginas.Sumar;
import ar.com.manutesting.utiles.soporte.Soporte;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j;

@Log4j
public class SumarStep {
	
	@Autowired
	private Sumar paginaSumar;
	@Autowired
	private Soporte soporte;
	
	@Given("Dos numeros {string} mas {string}")
	public void dos_numeros_mas(String numero1, String numero2) throws Exception {
		try {
			paginaSumar.ingresarNumero(numero1);
			paginaSumar.presionarSuma();
			paginaSumar.ingresarNumero(numero2);
		} catch (Exception e) {
			String message = soporte.getMensajeDeExcepcion(e);
			soporte.adherirErrorAlReporteAllure(message);
			log.error(message);	
			throw e;
		};
	}
	
	@When("Presionas igual")
	public void presionas_igual() throws Exception {
		try {
			paginaSumar.presionarIgual();
		} catch (Exception e) {
			String message = soporte.getMensajeDeExcepcion(e);
			soporte.adherirErrorAlReporteAllure(message);
			log.error(message);	
			throw e;
		};
	}
	@Then("Se muestra {string}")
	public void se_muestra(String resultado) throws Exception {
		try {
			paginaSumar.verificarResultado(resultado);
		} catch (Exception e) {
			String message = soporte.getMensajeDeExcepcion(e);
			soporte.adherirErrorAlReporteAllure(message);
			log.error(message);	
			throw e;
		};
	}
}
