package io.github.stockinventory.app.teste.ExceptionsTestes;

import java.util.List;
import java.util.function.Function;

import org.assertj.core.util.Arrays;

class PersonTest {
	
	private String name;
	PersonTest(String name) {
		this.name = name;
	}
	
	void dizerOla() {
		System.out.println("Olá, meu nome é: " + name);
	}
	static int dobrar(int x) {
		return x * 2;
	}
	
	interface testeBom {
		void bomDia(String msg);
	}
	
	// Referencia de metodos de instancia
	public static void main(String[] args) {
		PersonTest p  = new PersonTest("Jose");
		Runnable r = p::dizerOla;
		r.run();
		
		
		// Referencia a métodos estáticos
		Function<Integer, Integer> func = PersonTest::dobrar; // Equivalente a (x) -> Util.dobrar(x)
		Function<Integer, Integer> func2 = (x) -> PersonTest.dobrar(x);
		System.out.println(func.apply(4));
		System.out.println(func2.apply(45));
		
		// Referencia a Métodos de Instancia de um Tipo Arbitrário
		
		List<String> nomes = List.of("Ana", "Bruno", "Carlos");

		// Em vez de nomes.forEach(nome -> System.out.println(nome));
		nomes.forEach(System.out::println);
		
		// Referencia a construtores
		
		Function<String, PersonTest> criador = PersonTest::new;
		PersonTest personTest = criador.apply("Jsoe");
		System.out.println(personTest.name);

		Class<?> ref = testeBom.class;
		
		System.out.println(ref.getMethods());
		
	}
	
	
	
}

