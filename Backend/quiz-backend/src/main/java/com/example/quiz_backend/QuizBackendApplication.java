package com.example.quiz_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class QuizBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(QuestionRepository repository) {
		return args -> {
			List<Question> allQuestions = new ArrayList<>();

			// UPDATED: All questions now have 4 options (6 arguments total)
			allQuestions.add(new Question("Which of these is not a Java keyword?", "static", "Boolean", "void", "private", "Boolean"));
			allQuestions.add(new Question("Which of these cannot be used for a variable name in Java?", "identifier", "keyword", "variable", "_temp", "keyword"));
			allQuestions.add(new Question("Which method must be implemented by all threads?", "start()", "stop()", "run()", "main()", "run()"));
			allQuestions.add(new Question("Which class is the superclass of all exceptions in Java?", "Exception", "Throwable", "Error", "Object", "Throwable"));
			allQuestions.add(new Question("Which of the following is not a valid access modifier in Java?", "public", "friendly", "protected", "private", "friendly"));
			allQuestions.add(new Question("Which of these collections allows duplicate elements?", "Set", "List", "Map", "HashSet", "List"));
			allQuestions.add(new Question("Which of the following is used to find and fix bugs in Java programs?", "JVM", "JRE", "JDK", "JDB", "JDB"));
			allQuestions.add(new Question("Which is true about interfaces in Java?", "Interfaces can have constructors", "Interfaces can extend multiple interfaces", "Interfaces can implement classes", "Interfaces can contain static variables only", "Interfaces can extend multiple interfaces"));
			allQuestions.add(new Question("Which feature of Java allows it to run on any platform?", "Encapsulation", "Inheritance", "Bytecode", "Polymorphism", "Bytecode"));
			allQuestions.add(new Question("Which will not be a valid generic declaration?", "List<?> list", "List<? super Number> list", "List<Object> list = new ArrayList<String>()", "List<? extends Number> list", "List<Object> list = new ArrayList<String>()"));
			allQuestions.add(new Question("Which method retrieves metadata about a class in Java?", "getClass()", "getMeta()", "classType()", "type()", "getClass()"));
			allQuestions.add(new Question("Which statement is true for final methods?", "They can be overridden", "They cannot be inherited", "They cannot be overridden", "They can be abstract", "They cannot be overridden"));
			allQuestions.add(new Question("Which Java keyword prevents method overriding?", "static", "final", "super", "abstract", "final"));
			allQuestions.add(new Question("Which exception is thrown for division by zero?", "NullPointerException", "ArithmeticException", "IllegalArgumentException", "ArrayIndexOutOfBoundsException", "ArithmeticException"));
			allQuestions.add(new Question("Which pattern restricts a class to one object?", "Factory Pattern", "Builder Pattern", "Singleton Pattern", "Observer Pattern", "Singleton Pattern"));

			repository.saveAll(allQuestions);
			System.out.println("-----> All 15 sample questions (with 4 options) have been saved! <-----");
		};
	}
}