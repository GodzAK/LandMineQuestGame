import java.util.*;

/**
 * Holds every "landmine" cell on the board plus its trivia question.
 * Kept separate from the board/UI code so new questions can be added
 * without touching any rendering logic.
 */
public final class QuestionBank {

    public static final class Question {
        public final String prompt;
        public final String[] choices; // 4 choices, plain text (no "A) " prefix)
        public final int correctIndex;

        public Question(String prompt, String[] choices, int correctIndex) {
            this.prompt = prompt;
            this.choices = choices;
            this.correctIndex = correctIndex;
        }
    }

    private static final Map<Integer, Question> QUESTIONS = new LinkedHashMap<>();

    private static void add(int position, String prompt, int correctIndex, String... choices) {
        QUESTIONS.put(position, new Question(prompt, choices, correctIndex));
    }

    static {
        add(44, "What is the purpose of a constructor in Java?", 1,
                "To initialize class variables", "To create objects", "To allocate memory", "To define class methods");

        add(7, "Which of the following is used to achieve multiple inheritance in Java?", 0,
                "Interfaces", "Abstract classes", "Inheritance", "Polymorphism");

        add(84, "What is the difference between == and .equals() in Java?", 0,
                "== compares references, .equals() compares content",
                "== compares content, .equals() compares references",
                "Both compare references", "Both compare content");

        add(25, "Which keyword prevents a method from being overridden?", 0,
                "final", "static", "abstract", "private");

        add(59, "What does the static keyword mean on a method?", 0,
                "It belongs to the class, not an instance", "Only static methods can call it",
                "It cannot be overridden", "It's only callable within the package");

        add(91, "What is the purpose of the super keyword?", 1,
                "To call the superclass constructor only", "To access superclass methods and variables",
                "To override superclass methods", "To create subclass instances");

        add(38, "What is the difference between throw and throws?", 0,
                "throw propagates an exception, throws declares one",
                "throws propagates an exception, throw declares one",
                "Both declare exceptions", "Both propagate exceptions");

        add(18, "What is the purpose of the this keyword?", 0,
                "To refer to the current class instance", "To refer to the superclass instance",
                "To create a new class instance", "To initialize class variables");

        add(66, "What is the default value of an uninitialized instance variable?", 3,
                "0", "null", "false", "It depends on the data type");

        add(74, "Which collection does not allow duplicate elements?", 2,
                "ArrayList", "LinkedList", "HashSet", "HashMap");

        add(5, "What is method overloading in Java?", 2,
                "Multiple definitions of the same method", "Same name, different return types",
                "Same name, different parameters", "Same parameters, different names");

        add(29, "How are exceptions typically handled in Java?", 1,
                "Using only the finally block", "Using try, catch, and finally blocks",
                "Using the assert keyword", "Using a switch statement");

        add(64, "What does the default keyword do in a Java interface?", 2,
                "Sets a default variable value", "Sets a default constructor",
                "Provides a default method implementation", "Sets a default access modifier");

        add(33, "What is the difference between == and equals() for objects?", 1,
                "No difference, both compare references", "== compares references, equals() compares values",
                "== compares values, equals() compares references", "Both compare values");

        add(11, "What does the volatile keyword indicate?", 0,
                "A variable may be modified asynchronously", "A variable can never be modified",
                "A variable's access is synchronized", "A variable is constant");

        add(23, "What is a lambda expression in Java?", 0,
                "A way to represent anonymous methods", "An exception-handling mechanism",
                "A key-value data structure", "A method that takes another method as input");

        add(40, "What does the break statement do?", 0,
                "Exits the current loop or switch", "Skips to the next iteration",
                "Jumps to a labeled section", "Throws an exception");

        add(45, "What does the transient keyword do?", 0,
                "Prevents a variable from being serialized", "Marks a variable for serialization",
                "Restricts access outside the class", "Allows access outside the class");

        add(55, "Which collection lets you access elements by index?", 2,
                "Set", "Map", "List", "Queue");

        add(70, "What is the purpose of finalize() in Java?", 1,
                "Explicitly deallocates resources immediately", "Runs cleanup before garbage collection",
                "Checks GC eligibility", "Prevents garbage collection");

        add(78, "What does the new keyword do?", 0,
                "Creates a new instance of a class", "Allocates memory for a primitive",
                "Declares a new variable", "Initializes a variable to default");

        add(89, "What is a static block used for?", 0,
                "Initializing static variables of a class", "Initializing instance variables",
                "Initializing the superclass", "Declaring static methods");

        add(95, "What's the difference between StringBuilder and StringBuffer?", 1,
                "StringBuilder is synchronized, StringBuffer isn't", "StringBuffer is synchronized, StringBuilder isn't",
                "Both are synchronized", "Neither is synchronized");

        add(99, "Which access modifier is most restrictive in Java?", 2,
                "public", "protected", "private", "default");
    }

    public static Set<Integer> minePositions() {
        return QUESTIONS.keySet();
    }

    public static Question get(int position) {
        return QUESTIONS.get(position);
    }
}
