package sunauth;

import com.sun.xacml.Indenter;
import com.sun.xacml.ctx.ResponseCtx;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 *
 * @author marc.vila.gomez
 */
public class SunAuth {
    private static final String pathToPolicyFile = "E:/ISDCM_Practica/SunAuth/src/resources/XACMLPolicy";
    private static final String pathToRequestFile = "E:/ISDCM_Practica/SunAuth/src/resources/XACMLRequest";
    
    private static final String pathToSaveFile = "E:/ISDCM_Practica/SunAuth/src/resources/outputs/XACMLContextResponse";

    public static void main(String args[]) throws Exception {
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduzca un numero [1, 3] para seleccionar la Policy");
        int policyNumber = reader.nextInt();
        
        System.out.println("Introduzca un numero [1, 5] para seleccionar la Request");
        int requestNumber = reader.nextInt();
        
        String[] pathPolicies = new String[1];
        pathPolicies[0] = pathToPolicyFile + policyNumber + ".xml";
        System.out.println("Leyendo Policy: " + pathPolicies[0]);
        SimplePDP simplePDP = new SimplePDP(pathPolicies);

        String requestFile = pathToRequestFile + requestNumber + ".xml";
        System.out.println("Leyendo RequestFile: " + requestFile);
        ResponseCtx response = simplePDP.evaluate(requestFile);
        
        // Mostramos por consola el resultado
        Utils.printResult(System.out, new Indenter(), response.getResults());
        
        // Guardamos en XML el resultado
        String pathToSave = pathToSaveFile + "Policy" + policyNumber + "Request" + requestNumber;
        OutputStream outputStream = new FileOutputStream(pathToSave);
        Utils.printResult(outputStream, new Indenter(), response.getResults());
    }
}
