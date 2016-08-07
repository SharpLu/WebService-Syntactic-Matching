package wsmatching;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Mac
 */
@XmlRootElement(name = "Matching", namespace = "http://www.kth.se/ict/id2208/Matching")
@XmlType(propOrder = { "inputServiceName", "outputServiceName", "wsScore", "matchedOperations" })
public class MatchedWebService {

    private String OutputServiceName;
    private String InputServiceName;
    @XmlElement(name = "WsScore")
    private double wsScore;
    
    @XmlElement(name = "MatchedOperation")
    public ArrayList<MatchedOperation> matchedOperations;

    public String getOutputServiceName() {
        return OutputServiceName;
    }

    public String getInputServiceName() {
        return InputServiceName;
    }

    public double getWsScore() {
        return wsScore;
    }

    public void setInputServiceName(String inputServiceName) {
        this.InputServiceName = inputServiceName;
    }

    public void setOutputServiceName(String outputServiceName) {
        this.OutputServiceName = outputServiceName;
    }

    public void calculateWSScore() {
        wsScore = 0;
        if (matchedOperations.isEmpty()) {
            return;
        }
        
        for (MatchedOperation mo : matchedOperations) {
            wsScore += mo.getOpScore();
        }
        wsScore /= matchedOperations.size();
    }
}
