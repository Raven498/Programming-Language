import java.util.*;
public class Parser{
  private ArrayList<String> codeTokens = new ArrayList<>();
  private ArrayList<String> blockRef = new ArrayList<>(
    Arrays.asList("ACT", 
                  "IF", 
                   "ELSE")
  );
  private char[] exemptCharRef = {'}', '>'};
  ArrayList<String> blockCode = new ArrayList<String>();
  
  public void parse(ArrayList<String> processedCode, Executor executor){
    for(int i = 0; i < processedCode.size(); i++){
      String currentToken = processedCode.get(i);
      //String Processing
      if(currentToken.contains("'")){
        //Remove current token from codeTokens if it exists, drop quotes
        if(codeTokens.indexOf(currentToken) > -1){
          codeTokens.remove(codeTokens.indexOf(currentToken));
        }
        currentToken = currentToken.replace("'", "");

        //Process string, get new index
        i = processString(processedCode, currentToken, i+1) - 1; //Subtract one because of the increment at the top

        //If new index is out of bounds, break
        if(i >= processedCode.size()){
          break;
        }
        continue;
      }

      if(currentToken.contains("+")){
        String token1 = codeTokens.get(1);
        String token2 = processedCode.get(i + 1); //May need to use processString as well
        currentToken = token1 + "+" + token2;
        codeTokens.add(currentToken);
        codeTokens.remove(token1);
        i += 2;
        continue;
      }

      //Block Processing - WIP
      if(blockRef.contains(processedCode.get(i))){
        i = processBlock(processedCode, i);
        continue;
      }
      codeTokens.add(currentToken);
    }
    executor.execute(codeTokens, executor);
    codeTokens.clear();
  }

  //WIP - implementing block processing
  private int processBlock(ArrayList<String> processedCode, int currentIndex){
    int i = currentIndex;
    ArrayList<String> blockTokens = new ArrayList<>();
    for(; i < processedCode.size(); i++){
      if(processedCode.get(i).contains(">")){
        continue;
      }
      blockTokens.add(processedCode.get(i));
      if(processedCode.get(i).contains("}")){
        break;
      }
    }
    return i;
  }

  private int processString(ArrayList<String> processedCode, String currentToken, int currentIndex){
    //Initialize string with currentToken
    String string = currentToken;

    //Go through rest of string
    for(int i = currentIndex; i < processedCode.size(); i++){
      //Get current token, remove from codeTokens
      String token = processedCode.get(i);
      if(codeTokens.indexOf(token) > -1){
          codeTokens.remove(codeTokens.indexOf(token));
      }

      //If end of string is reached
      if(token.contains("'")){
        token = token.replace("'", "");
        string = string + " " + token;
        currentIndex++;
        break;
      }

      //Update string, current index
      string = string + " " + token;
      currentIndex++;
    }

    //Add processed string to codeTokens
    codeTokens.add(string);
    return currentIndex;
  }

  //Check if a character is exempt - if it is, parser will skip over the char
  private boolean isExempt(char target){
    for(char refChar : exemptCharRef){
      if(refChar == target){
        return true;
      }
    }
    return false;
  }
}