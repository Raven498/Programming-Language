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

  public Executor(){
    for(Method method : actionRef){
      if(method.getName() == "execute"){
        actionRef.remove(actionRef.indexOf(method));
      }
    }
  }

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
    System.out.println(item);
  }

  public void var(String varName){
    System.out.println(varName);
  }
}