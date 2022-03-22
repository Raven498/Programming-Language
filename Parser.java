import java.util.*;
public class Parser{
  private ArrayList<String> codeTokens = new ArrayList<>();
  private ArrayList<String> blockRef = new ArrayList<>(
    Arrays.asList("ACT", 
                  "IF", 
                   "ELSE",
                   "VAR")
  );
  ArrayList<String> blockCode = new ArrayList<String>();
  
  public ArrayList<String> parse(ArrayList<String> processedCode){
    for(int i = 0; i < processedCode.size(); i++){
      String currentToken = processedCode.get(i);
      if(currentToken.contains("'")){
        currentToken = currentToken.replace("'", "");
        processString(processedCode, currentToken, i+1);
      }
      if(blockRef.contains(processedCode.get(i))){
        processBlock(processedCode, i);
      }
    }
    return codeTokens;
  }

  private void processBlock(ArrayList<String> processedCode, int currentIndex){
    for(int i = currentIndex; i < processedCode.size(); i++){
      codeTokens.add(processedCode.get(i));
      if(processedCode.get(i).contains("}")){
        break;
      }
    }
  }

  private void processString(ArrayList<String> processedCode, int currentIndex){
    String string = "";
    for(int i = currentIndex; i < processedCode.size(); i++){
      String token = processedCode.get(i);
      if(token.contains("'")){
        token = token.replace("'", "");
        string = string + token;
        break;
      }
      string = string + token;
    }
    codeTokens.add(string);
  }
}