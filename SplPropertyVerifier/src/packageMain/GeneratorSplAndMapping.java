package packageMain;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeneratorSplAndMapping {
	
	public ArrayList<String> array = new ArrayList<>();
	public ArrayList<String> arrayN = new ArrayList<>();
	public ArrayList<String> arrayS = new ArrayList<>();
	public ArrayList<String> arrayE = new ArrayList<>();
	public String codeSplMapping="";

	public GeneratorSplAndMapping() {	
		
		DefinirSig signature = new DefinirSig();
		
        codeSplMapping += "// définition d'un produit\r\n" + 
        		"abstract sig Product {\r\n" + 
        		"	domain: one DomainModel ,\r\n" + 
        		"	feature : one FeatureModel \r\n" + 
        		"}\n\n";
		
        array.add("P1");
        array.add("P2");
        array.add("P3");
        array.add("P4");
        array.add("P5");
        
        codeSplMapping += signature.DefinirSousSignature(array, "Product", "one");
        
        codeSplMapping += "// définition d'une ligne de produit.\r\n\n" + 
        		"abstract sig SPL{\r\n" + 
        		"product : some Product\r\n" + 
        		"}\r\n\n" + 
        		"fact {\r\n" + 
        		"	all p: Product |\r\n" + 
        		"		p in SPL.product \r\n" + 
        		"}\n\n";
        
        
        try {
			
        	File domainModel = new File("src/Mapping/mapping.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(domainModel);
			doc.getDocumentElement().normalize();
			
			NodeList nListT = doc.getElementsByTagName("map");
			
			
			for (int i = 0; i < nListT.getLength(); i++) {
				
				Node nNodeDocT = nListT.item(i);
			if (nNodeDocT.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNodeDocT;
			arrayS.add(eElement.getAttribute("start"));
			arrayE.add(eElement.getAttribute("end"));
			arrayN.add(eElement.getTextContent());
			
			}
		}
        	
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        
        codeSplMapping += "//considération du mapping \r\n\n";
        
        for (int i = 0; i < arrayN.size(); i++) {
        	codeSplMapping += MappingFactGenerator(arrayN.get(i), arrayS.get(i) ,arrayE.get(i) );
		}
		
	}
	
	static String MappingFactGenerator(String Feature, String mapStart, String mapEnd) {
    	return "fact {\r\n" + 
    			"	all p: Product |\r\n" + 
    			"		 (" + Feature + " in p.feature.feature ) =>\r\n" + 
    			"		(( " + mapStart + " in p.domain.transition ) and (" + mapEnd + " in p.domain.transition ))\r\n" + 
    			"		else (( " + mapStart + " not in p.domain.transition ) and (" + mapEnd + " not in p.domain.transition )) \r\n" + 
    			"}\n\n";
    }
}
