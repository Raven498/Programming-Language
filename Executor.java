import java.util.*;
import java.lang.reflect.*;
public class Executor{
  ArrayList<String> tokenRef = new ArrayList<>(
    Arrays.asList(
      "PRINT",
      "VAR"
    )
  );

  ArrayList<String> blockRef = new ArrayList<>(
          Arrays.asList(
            "IF",
            "ELSE",
            "ACT"
          )
  );

  ArrayList<Method> actionRef = new ArrayList<>(
    Arrays.asList(this.getClass().getDeclaredMethods())
  );

  ArrayList<StrVar> strVarList = new ArrayList<>();

  public void execute(ArrayList<String> codeTokens, ArrayList<String> blockTokens, Executor executor){
    for(int i = 0; i < codeTokens.size(); i++){
      if(blockTokens == null) {
        for (int j = 0; j < tokenRef.size(); j++) {
          if (codeTokens.get(i).contains(tokenRef.get(j))) {
            String params = codeTokens.get(i + 1);
            params = params.replace("'", "");
            for (Method method : actionRef) {
              if (tokenRef.get(j).toLowerCase().contains(method.getName())) {
                try {
                  method.invoke(executor, params);
                  break;
                } catch (Exception e) {
                  System.out.println("An error occurred.");
                  System.out.println(e.getClass().getSimpleName());
                } //try/catch end
              } //if end
            } //actionRef for-each end
          } //if end
        } //tokenRef for end
      } //blockTokens if end
      else {
        for (int j = 0; j < blockRef.size(); j++) {
          if (codeTokens.get(i).contains(blockRef.get(j))) {
            for (Method method : actionRef) {
              if (blockRef.get(j).toLowerCase().contains(method.getName())) {
                try {
                  method.invoke(executor, blockTokens);
                  break;
                } catch (Exception e) {
                  System.out.println("An error occurred.");
                  System.out.println(e.getClass().getSimpleName());
                } //try/catch end
              } //method if end
            } //method for end
          } //codeTokens if end
        } //for end
      } //else end
    } //codeTokens for end
  } //method end

  public void print(String item){
    char[] itemArray = item.toCharArray();
    ArrayList<String> output = new ArrayList<>();
    String charList = "";
    for(int i = 0; i < itemArray.length; i++){
      charList += itemArray[i];
      if(itemArray[i] == '+'|| i == itemArray.length - 1){
        charList = charList.replace("+", "");
        StrVar strVar = findVar(charList);
        if(strVar != null){
          output.add(strVar.getValue());
        }
        else {
          output.add(charList);
        }
        charList = "";
      }
    }
    for(String outputItem : output){
      System.out.print(outputItem);
    }
  }

  public void var(String varParams){
    //Copy variable name,  value
    char[] paramArray = varParams.toCharArray();
    String varName = "";
    String value = "";
    boolean processValue = false;
    for(char item : paramArray){
      if(item == '='){
        processValue = true;
        continue;
      }
      if(processValue){
        value = value + item;
      }
      else{
        varName = varName + item;
      }
    }
    
    //Create variable
    StrVar strVar = new StrVar();
    strVar.setName(varName);
    strVar.setValue(value);
    strVarList.add(strVar);
  }

  public void ifelse(ArrayList<String> blockCde){

  }

  //Internal method used for finding variables
  private StrVar findVar(String varName){
    for(StrVar strVar : strVarList){
      if(strVar.getName().equals(varName)){
        return strVar;
      }
    }
    return null;
  }
}