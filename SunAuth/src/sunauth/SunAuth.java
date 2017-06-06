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
    private static final String pathToPolicyFile = "E:/ISDCM_Practica/SunAuth/src/resources/policy/XACMLPolicy";
    private static final String pathToRequestFile = "E:/ISDCM_Practica/SunAuth/src/resources/request/XACMLRequest";
    
    private static final String pathToSaveFile = "E:/ISDCM_Practica/SunAuth/src/resources/outputs/XACMLContextResponse";

    public static void main(String args[]) throws Exception {
        Scanner reader = new Scanner(System.in);
        System.out.println("Introduzca un numero [1, 3] para seleccionar la Policy, 0 para introducir todas");
        int policyNumber = reader.nextInt();
        
        System.out.println("Introduzca un numero [1, 5] para seleccionar la Request");
        int requestNumber = reader.nextInt();
        
        String[] pathPolicies;
        if (policyNumber == 0) {
            pathPolicies = new String[3];
            pathPolicies[0] = pathToPolicyFile + 1 + ".xml";
            pathPolicies[1] = pathToPolicyFile + 2 + ".xml";
            pathPolicies[2] = pathToPolicyFile + 3 + ".xml";
            System.out.println("Leyendo Todas las Policy");
        } else {
            pathPolicies = new String[1];
            pathPolicies[0] = pathToPolicyFile + policyNumber + ".xml";
            System.out.println("Leyendo Policy: " + pathPolicies[0]);
        }
        SimplePDP simplePDP = new SimplePDP(pathPolicies);

        String requestFile = pathToRequestFile + requestNumber + ".xml";
        System.out.println("Leyendo RequestFile: " + requestFile);
        ResponseCtx response = simplePDP.evaluate(requestFile);
        
        // Mostramos por consola el resultado
        Utils.printResult(System.out, new Indenter(), response.getResults());
        
        // Guardamos en XML el resultado
        String pathToSave = pathToSaveFile + "Policy" + policyNumber + "Request" + requestNumber;
        System.out.println("Escribiendo resultado en: " + pathToSave);
        OutputStream outputStream = new FileOutputStream(pathToSave);
        Utils.printResult(outputStream, new Indenter(), response.getResults());
    }
}
