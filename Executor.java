import java.util.*;
import java.lang.reflect.*;
public class Executor{
  ArrayList<String> tokenRef = new ArrayList<>(
    Arrays.asList(
      "PRINT",
      "VAR"
    )
  );

  ArrayList<Method> actionRef = new ArrayList<>(
    Arrays.asList(this.getClass().getDeclaredMethods())
  );

  ArrayList<StrVar> strVarList = new ArrayList<>();

  public void execute(ArrayList<String> codeTokens, Executor executor){
    for(int i = 0; i < codeTokens.size(); i++){
      for(int j = 0; j < tokenRef.size(); j++){
        if(codeTokens.get(i).equals(tokenRef.get(j))){
          String params = codeTokens.get(i + 1);
          params = params.replace("}", "");
          for(Method method : actionRef){
            if(method.getName().equals(tokenRef.get(j).toLowerCase())){
              try{
                method.invoke(executor, params);
                break;
              } catch(Exception e){
                System.out.println("An error occurred.");
                System.out.println(e.getClass().getSimpleName());
              } //try/catch end
            } //if end
          } //actionRef for-each end
        } //if end
      } //tokenRef for end
    } //codeTokens for end
  } //method end

  public void print(String item){
    Scanner itemScanner = new Scanner(item);
    while(itemScanner.hasNext()){
      String printItem = itemScanner.next();
      StrVar strVar = findVar(printItem);
      if(strVar != null){
        System.out.print(strVar.getValue());
      }
      else{
        System.out.print(printItem);
      }
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