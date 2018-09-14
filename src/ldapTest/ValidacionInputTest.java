package ldapTest;

import java.util.Scanner;

public class ValidacionInputTest {

	public static void main(String[] args) {
		Number num = validarInteger();
		System.out.println("El Integer leido es " + num.toString());
	}

	/**
	 * Si te fijas la clase escaner tiene muchos metodos Scanner.hasNext... como
	 * por ejemplo hasNextInt() que devuelve un booleano (true|false) si la
	 * entrada coincide con el chequeo de int que hace el metodo, lo mismo con
	 * los demas metodos Scanner.hasNext...()..En este metodo "validarInteger"
	 * validamos numeros Integer podes hacer lo mismo para mas tipos de numeros
	 * o para otro tipo de dato. El rango para el Integer es de -2147483648 a
	 * 2147483647 si pones numeros mas chicos o mas grandes que el rango o
	 * letras ya deja de ser Integer por lo tanto pide un nuevo ingreso.
	 */
	public static Integer validarInteger() {
		Integer number = null;
		boolean esNumero = false;
		do {
			Scanner sc = new Scanner(System.in);
			esNumero = sc.hasNextInt();
			if (esNumero) {
				number = Integer.parseInt(sc.nextLine());
			} else {
				System.out.println("Ingrese un numero Integer ");
			}

		} while (!esNumero);
		return number;
	}
	
}
