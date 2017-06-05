package sunauth;

import com.sun.xacml.Indenter;
import com.sun.xacml.ctx.Result;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author marc.vila.gomez
 */
public class Utils {    
    public static void printResult(OutputStream output, Indenter indenter, Set results) {
        // Make a PrintStream for a nicer printing interface
        PrintStream out = new PrintStream(output);

        // Prepare the indentation string
        String indent = indenter.makeString();

        // Now write the XML...
        out.println(indent + "<Response>");

        // Go through all results
        Iterator it = results.iterator();
        indenter.in();
        while (it.hasNext()) {
            Result result = (Result)(it.next());
            result.encode(out, indenter);
        }
        indenter.out();

        // Finish the XML for a response
        out.println(indent + "</Response>");
    }
}
