package balanaauth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
import org.wso2.balana.Balana;
import org.wso2.balana.Indenter;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.ParsingException;
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.finder.AttributeFinder;
import org.wso2.balana.finder.AttributeFinderModule;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

/**
 *
 * @author marc.vila.gomez
 */
public class BalanaAuth {
    private static final String pathToRequestFile = "E:/ISDCM_Practica/BalanaAuth/src/resources/request/XACMLRequest";
    private static final String pathToSaveFile = "E:/ISDCM_Practica/BalanaAuth/src/resources/outputs/XACMLContextResponse";
    
    private static Balana balana;

    public static void main(String[] args) throws ParsingException, FileNotFoundException, Exception {
        Scanner reader = new Scanner(System.in);
        System.out.println("Se utilizan todas las Policy");
        System.out.println("Introduzca un numero [1, 5] para seleccionar la Request");
        int requestNumber = reader.nextInt();
        
        initBalana();
        PDP pdp = getPDPNewInstance();        
        
        String requestFile = pathToRequestFile + requestNumber + ".xml";
        System.out.println("Leyendo RequestFile: " + requestFile);
        String response = pdp.evaluate(Utils.getXMLFromFilePath(requestFile));
        ResponseCtx responseCtx = ResponseCtx.getInstance(Utils.getXacmlResponse(response));
        
        // Mostramos por consola el resultado
        System.out.println(responseCtx.encode());
        
        // Guardamos en XML el resultado
        String pathToSave = pathToSaveFile + "PolicyRequest" + requestNumber;
        OutputStream outputStream = new FileOutputStream(pathToSave);
        Utils.printResult(outputStream, new Indenter(), responseCtx.getResults());
    }
    
    /**
     * Returns a new PDP instance with new XACML policies
     *
     * @return a  PDP instance
     */
    private static PDP getPDPNewInstance(){
        PDPConfig pdpConfig = balana.getPdpConfig();

        return new PDP(new PDPConfig(pdpConfig.getAttributeFinder(), pdpConfig.getPolicyFinder(), null, true));
    }
    
    private static void initBalana(){
        try {
            // using file based policy repository. so set the policy location as system property
            String policyLocation = (new File(".")).getCanonicalPath() + File.separator + "src" + File.separator + "resources" + File.separator + "policy";
            System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, policyLocation);
        } catch (IOException e) {
            System.err.println("Can not locate policy repository");
        }
        // create default instance of Balana
        balana = Balana.getInstance();
    }
}
