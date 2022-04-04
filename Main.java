import java.util.*;
public class Main{
  public static void main(String[] args){
    Lexer lexer = new Lexer();
    Parser parser = new Parser();
    Executor executor = new Executor();
    lexer.lex(parser, executor);
  }
}