import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class Lexer{
  private String[] tokens = {"ACT"};
  private File sourceProgram = new File("test.txt");
  public void lex(Parser parser, Executor executor){
    try{
      Scanner reader = new Scanner(sourceProgram);
      while(reader.hasNextLine()){
        String line = reader.nextLine();
        Scanner tokenizer = new Scanner(line);
        ArrayList<String> processedLine = new ArrayList<>();
        while(tokenizer.hasNext()){
          String token = tokenizer.next();
          processedLine.add(token);
        }
        parser.parse(processedLine, executor);
        tokenizer.close();
      }
      reader.close();
    } catch(FileNotFoundException e){
      System.out.println("An error occurred");
    }

    /*
    Scanner scanner = new Scanner(rawCode);
    ArrayList<String> processedCode = new ArrayList<>();
    while(scanner.hasNext()){
      String processedToken = scanner.next();
      processedCode.add(processedToken);
    }
    System.out.println(processedCode);
    return processedCode;
    */
  }
}