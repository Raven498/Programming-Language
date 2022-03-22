import java.util.*;
public class Main{
  public static void main(String[] args){
    Lexer lexer = new Lexer();
    Parser parser = new Parser();
    Executor executor = new Executor();
    ArrayList<String> processedCode = lexer.lex();
    ArrayList<String> codeTokens = parser.parse(processedCode);
    
    executor.execute(codeTokens, executor);
  }
}