import java.util.*;
import java.util.regex.*;

class Lexer {
    private String sourceCode;
    private List<Token> tokens = new ArrayList<>();
    
    private static final Pattern tokenPattern = Pattern.compile(
        "(?<NUMBER>\\d+)|" +           // Números
        "(?<ID>[a-zA-Z_][a-zA-Z_0-9]*)|" + // Identificadores
        "(?<ASSIGN>=)|" +             // Asignación
        "(?<PLUS>\\+)|" +             // Suma
        "(?<MINUS>-)|" +              // Resta
        "(?<TIMES>\\*)|" +            // Multiplicación
        "(?<DIVIDE>/)|" +             // División
        "(?<LPAREN>\\()|" +           // Paréntesis izquierdo
        "(?<RPAREN>\\))|" +           // Paréntesis derecho
        "(?<SKIP>[ \\t]+)|" +         // Espacios
        "(?<NEWLINE>\\n)|" +          // Fin de línea
        "(?<MISMATCH>.)"              // Caracter no esperado
    );

    public Lexer(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public List<Token> tokenize() throws SyntaxException {
        Matcher matcher = tokenPattern.matcher(sourceCode);

        while (matcher.find()) {
            if (matcher.group("SKIP") != null) continue;

            if (matcher.group("NUMBER") != null) {
                tokens.add(new Token("NUMBER", matcher.group("NUMBER")));
            } else if (matcher.group("ID") != null) {
                tokens.add(new Token("ID", matcher.group("ID")));
            } else if (matcher.group("ASSIGN") != null) {
                tokens.add(new Token("ASSIGN", "="));
            } else if (matcher.group("MISMATCH") != null) {
                throw new SyntaxException("Unexpected character: " + matcher.group("MISMATCH"));
            } else {
                tokens.add(new Token(matcher.group(), matcher.group()));
            }
        }
        return tokens;
    }
}

class Token {
    public final String type;
    public final String value;

    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s)", type, value);
    }
}

class SyntaxException extends Exception {
    public SyntaxException(String message) {
        super(message);
    }
}

