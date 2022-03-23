import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class Lexer{
  private String[] tokens = {"ACT"};
  private File sourceProgram = new File("test.txt");
  private String rawCode = "";
  private ArrayList<String> tokenizedCode = new ArrayList<>();
  public ArrayList<String> lex(){
    try{
      Scanner reader = new Scanner(sourceProgram);
      while(reader.hasNextLine()){
        String line = reader.nextLine();
        rawCode += line;
      }
      reader.close();
    } catch(FileNotFoundException e){
      System.out.println("An error occurred");
    }

    Scanner scanner = new Scanner(rawCode);
    ArrayList<String> processedCode = new ArrayList<>();
    while(scanner.hasNext()){
      String processedToken = scanner.next();
      processedCode.add(processedToken);
    }

    return processedCode;
  }
}