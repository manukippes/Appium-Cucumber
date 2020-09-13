package ar.com.manutesting.paginas;


import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

@Component
@Scope("cucumber-glue")
public class Sumar extends AppiumBase {
	
	@Autowired
	public Sumar(AndroidDriver<MobileElement> driver) {
		super(driver);
	}

	private static By numero;
	private static By botonIgual = By.id("com.android.calculator2:id/eq");
	private static By botonSuma = By.id("com.android.calculator2:id/op_add");
	private static By resultado = By.id("com.android.calculator2:id/result");
	
	public void ingresarNumero(String pNumero) throws Exception {
		numero = By.id(String.format("com.android.calculator2:id/digit_%s", pNumero));
		clickElemento(numero, "Numero: "+pNumero);
	}

	public void presionarSuma() throws Exception {
		clickElemento(botonSuma, "Boton Suma");
	}
	
	public void presionarIgual() throws Exception {
		clickElemento(botonIgual, "Boton Igual");
	}
	
	public void verificarResultado(String pResultado) throws Exception {
		validarTextoDeElemento(resultado, pResultado, "Resultado");
	}
	


	
}
