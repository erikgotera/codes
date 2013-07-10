/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package funciones;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author albert
 */
public class Rule {
    List<Pair> leftRules = new ArrayList<Pair>();
    Pair rightRules;
    int counter = 0;
    
    public Rule(List<String> leftReceivedRules, String rightReceivedRules) {
        for (int i = 0; i < leftReceivedRules.size(); i++) {
            String leftReceivedRulesArray[] = leftReceivedRules.get(i).split("=");
            Pair newpair = new Pair();
            newpair.attribute = leftReceivedRulesArray[0];
            newpair.value = leftReceivedRulesArray[1];
            leftRules.add(newpair);
        }
        
        String rightReceivedRulesArray[] = rightReceivedRules.split("=");
        Pair newpair = new Pair();
        newpair.attribute = rightReceivedRulesArray[0];
        newpair.value = rightReceivedRulesArray[1];
        rightRules = newpair;
    }
    
    public class Pair {
        String attribute;
        String value;
    }
    
    public void addOne() {
        counter++;
    }
    
    @Override
    public String toString() {
        String toReturn = "";
        for (int i = 0; i < leftRules.size(); i++) {
            if (i > 0)
                toReturn = toReturn.concat(", ");
            
            toReturn = toReturn.concat(leftRules.get(i).attribute + "=" + leftRules.get(i).value);
        }
        toReturn = toReturn.concat(" ==> ");
        toReturn = toReturn.concat(rightRules.attribute + "=" + rightRules.value);
        toReturn = toReturn.concat(" || " + counter);
        return toReturn;
    }
}
