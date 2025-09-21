package quiz.app;

public class QuestionData {

    private String[][] questions;
    private String[][] answers;

    public QuestionData() {
        questions = new String[][] {
            {"Which of these is not a Java keyword?", "static", "Boolean", "void", "private"},
            {"Which of these cannot be used for a variable name in Java?", "identifier", "keyword", "variable", "_temp"},
            {"Which method must be implemented by all threads?", "start()", "stop()", "run()", "main()"},
            {"Which class is the superclass of all exceptions in Java?", "Exception", "Throwable", "Error", "Object"},
            {"Which of the following is not a valid access modifier in Java?", "public", "friendly", "protected", "private"},
            {"Which of these collections allows duplicate elements?", "Set", "List", "Map", "HashSet"},
            {"Which of the following is used to find and fix bugs in Java programs?", "JVM", "JRE", "JDK", "JDB"},
            {"Which of the following statements is true about interfaces in Java?", "Interfaces can have constructors", "Interfaces can extend multiple interfaces", "Interfaces can implement classes", "Interfaces can contain static variables only"},
            {"Which feature of Java allows it to run on any platform?", "Encapsulation", "Inheritance", "Bytecode", "Polymorphism"},
            {"Which of the following will not be a valid generic declaration?", "List<?> list", "List<? super Number> list", "List<Object> list = new ArrayList<String>()", "List<? extends Number> list"},
            {"Which method is used to retrieve metadata about a class in Java?", "getClass()", "getMeta()", "classType()", "type()"},
            {"Which statement is true for final methods?", "They can be overridden", "They cannot be inherited", "They cannot be overridden", "They can be abstract"},
            {"Which Java keyword is used to prevent method overriding?", "static", "final", "super", "abstract"},
            {"Which of the following exception is thrown when division by zero occurs?", "NullPointerException", "ArithmeticException", "IllegalArgumentException", "ArrayIndexOutOfBoundsException"},
            {"Which design pattern is used to restrict the instantiation of a class to one object?", "Factory Pattern", "Builder Pattern", "Singleton Pattern", "Observer Pattern"}
        };

        answers = new String[][] {
            {"Boolean"},
            {"keyword"},
            {"run()"},
            {"Throwable"},
            {"friendly"},
            {"List"},
            {"JDB"},
            {"Interfaces can extend multiple interfaces"},
            {"Bytecode"},
            {"List<Object> list = new ArrayList<String>()"},
            {"getClass()"},
            {"They cannot be overridden"},
            {"final"},
            {"ArithmeticException"},
            {"Singleton Pattern"}
        };
    }

    public String[][] getQuestions() {
        return questions;
    }

    public String[][] getAnswers() {
        return answers;
    }

    public int getTotalQuestions() {
        return questions.length;
    }
}