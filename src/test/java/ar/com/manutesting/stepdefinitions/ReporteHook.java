	package ar.com.manutesting.stepdefinitions;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import ar.com.manutesting.utiles.enums.SistemaOperativoMobile;
import ar.com.manutesting.utiles.enums.TomarCaptura;
import ar.com.manutesting.utiles.soporte.Soporte;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.log4j.Log4j;

@Log4j
@Scope("cucumber-glue")
public class ReporteHook{

	@Autowired
	private Soporte soporte;
	
	@Value("${tomar.captura:nunca}")
	private TomarCaptura tomarCaptura;
	
	@Value("${so}")
	private SistemaOperativoMobile mobile;
	
	private String nombreEscenario;
	private String estadoEscenario;
	
	
	@Before
	public void iniciarEscenarioLog(Scenario escenario) throws InterruptedException {
		nombreEscenario = escenario.getName().toUpperCase();
		log.info("<-- EL ESCENARIO: "+nombreEscenario+" COMENZO. -->");
	}
	
	@After
	public void tomarCapturaCuandoFallaEscenario(Scenario escenario) throws Exception {
		if (escenario.isFailed() && TomarCaptura.fallaEscenario == tomarCaptura) {
			capturarPantalla(escenario);
		}
	}

	@After
	public void finalizaEscenarioLog(Scenario escenario) throws Exception {
		try {
			nombreEscenario = escenario.getName().toUpperCase();
			estadoEscenario = escenario.getStatus().toString();
			if (escenario.isFailed()) 
				log.error("<-- EL ESCENARIO: "+ nombreEscenario+" FALLO. -->");
			if (estadoEscenario == "PASSED") {
					soporte.adherirCapturaAlReporteAllure();
					capturarPantalla(escenario);
				log.info("<-- EL ESCENARIO: "+ nombreEscenario+" FINALIZÓ CORRECTAMENTE. -->");
			}			
		} catch (Exception e) {
			String message = soporte.getMensajeDeExcepcion(e);
			soporte.adherirErrorAlReporteAllure(message);
			log.error(message);
			throw e;
		}
	}
		
	public void capturarPantalla(Scenario escenario) throws Exception {
		try {
			escenario.embed(soporte.capturarPantallaParaReporteCucumber(), "image/png", UUID.randomUUID().toString());
		} catch (Exception e) {
			String message = soporte.getMensajeDeExcepcion(e);
			soporte.adherirErrorAlReporteAllure(message);
			log.error(message);
			throw e;
		}
	}

}
