package swsmatching;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.Service;
import org.xml.sax.InputSource;
import wsmatching.MatchedOperation;
import wsmatching.MatchedWebService;
import wsmatching.WSMatching;
import ontology.OntologyMatcher;
import wsmatching.WSMatchMaking;

/**
 *
 * @author Mac
 */
public class WSSemanticMatching {

    public static void testOntologyMatcher() {
        OntologyMatcher ontologyMatcher = OntologyMatcher.getInstance();
        System.out.println(ontologyMatcher.getScore("Anglican", "Christian"));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, Exception {
        Scanner scanner = new Scanner(System.in);
        File folder = new File("src/SAWSDL");
        //  File folder = new File("src/WSDLs");
        File[] listOfFiles = folder.listFiles();
        System.out.println("The available WSDLs are:");
        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {
                String file_name;
                file_name = listOfFiles[i].getName();
                System.out.println("    " + i + " - " + file_name);
            }
        }
        System.out.println();
        System.out.print("please input web service file 1 ");

        FileInputStream inWs = new FileInputStream(listOfFiles[scanner.nextInt()]);

        System.out.print("please input web service file 2 ");

        FileInputStream outWs = new FileInputStream(listOfFiles[scanner.nextInt()]);

      
        WSMatching wsmatching = new WSMatching();
        wsmatching.matchedWebServices = new ArrayList<>();

        MatchedWebService mws = new MatchedWebService();
        mws.matchedOperations = new ArrayList<>();

        Description d_i = WSDLFactory.newInstance().newWSDLReader().read(new InputSource(inWs));
        Description d_o = WSDLFactory.newInstance().newWSDLReader().read(new InputSource(outWs));

        Service s_i = null;
        for (Service s : d_i.getServices()) {
            s_i = s;
            mws.setInputServiceName(s_i.getQName().getLocalPart());

        }
        createOutput(mws);
        if (s_i == null) {
            throw new Exception("No services found on the input");
        }

        Service s_o = null;
        for (Service s : d_o.getServices()) {
            s_o = s;

            mws.setOutputServiceName(s_o.getQName().getLocalPart());
        }

        if (s_o == null) {
            throw new Exception("No services found on the output");
        }

        Endpoint p_i = null;
        for (Endpoint ep : s_i.getEndpoints()) {
            p_i = ep;
        }

        if (p_i == null) {
            throw new Exception("No ports found on the input");
        }

        Endpoint p_o = null;
        for (Endpoint ep : s_o.getEndpoints()) {
            p_o = ep;
        }

        if (p_o == null) {
            throw new Exception("No ports found on the output");
        }

        for (BindingOperation o_i : p_i.getBinding().getBindingOperations()) {
            for (BindingOperation o_o : p_o.getBinding().getBindingOperations()) {
                MatchedOperation mo = new MatchedOperation();
                mo.matchedElements = new ArrayList<>();

                mo.setInputOperationName(o_i.getQName().getLocalPart());
                mo.setOutputOperationName(o_o.getQName().getLocalPart());

                System.out.println("inputOperation:" + mo.getInputOperationName() + "");
                System.out.println("outputOperation:" + mo.getOutputOperationName());

                // change input to output to see the same service score....
                for (Part e_i : o_i.getOperation().getInput().getParts()) {

                    System.out.println("InputElement: " + e_i.getPartQName().getLocalPart());
                    String input_element = e_i.getPartQName().getLocalPart();

                    for (Part e_o : o_o.getOperation().getOutput().getParts()) {
                        System.out.println("OutputElement " + e_o.getPartQName().getLocalPart());
                        String output_element = e_o.getPartQName().getLocalPart();

                        OntologyMatcher ontologyMatcher = OntologyMatcher.getInstance();
                        System.out.println(ontologyMatcher.getScore(input_element, output_element));
                    }

                }

            }
        }

    }

    private static void createOutput(MatchedWebService wsm) {
        JAXBContext context;
        Marshaller m;
        try {
            context = JAXBContext.newInstance(WSMatching.class);
            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(wsm, new File("src/output.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(WSMatchMaking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
