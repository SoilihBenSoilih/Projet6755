package packageMain;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeneratorDomain {

	
	public String codeDomain="";
	public ArrayList<String> array = new ArrayList<>();
	public ArrayList<String> arrayT = new ArrayList<>();
	public ArrayList<String> arrayDeparture = new ArrayList<>();
	public ArrayList<String> arrayArrival = new ArrayList<>();

	public GeneratorDomain() {

			
		try {
			

			File domainModel = new File("src/DomainModel/DomainModel.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(domainModel);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("components");
			NodeList nListT = doc.getElementsByTagName("link");
			
			
			for (int i = 0; i < nListT.getLength(); i++) {
				
				Node nNodeDocT = nListT.item(i);
			if (nNodeDocT.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNodeDocT;
			arrayDeparture.add(eElement.getAttribute("departure"));
			arrayArrival.add(eElement.getAttribute("arrival"));
			arrayT.add(eElement.getTextContent());
			
			}
		}
			Node nNodeDoc = nList.item(0);
		
				if (nNodeDoc.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNodeDoc;
					
					for (int j = 0; j < eElement.getElementsByTagName("component").getLength(); j++) {
						
						array.add(eElement.getElementsByTagName("component").item(j).getTextContent());					
					}
		
			    }	
		
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		DefinirSig objet = new DefinirSig();
		
		codeDomain +="\n\n";
		codeDomain += objet.DefinirSignature("State", "Abstract");
		codeDomain += objet.DefinirSousSignature(array, "State","one");
		codeDomain +="abstract sig Transition {\r\n" + 
				"	source : one State,\r\n" + 
				"	target : one State\r\n" + 
				"}\n\n";
		
		codeDomain += objet.DefinirSousSignature(arrayT, "Transition", "one");
		
		
		for (int i = 0; i < arrayT.size(); i++) {
			codeDomain += FactGenerator(arrayT.get(i), arrayDeparture.get(i), arrayArrival.get(i));
		}
		
		codeDomain += "// définition du domain model\r\n" + 
				"// contrainte des éléments du model\n";
		
		codeDomain += "sig DomainModel {\r\n" + 
				"	transition : some Transition ,\r\n" + 
				"	state : some State\r\n" + 
				"}\n\n";
		
		codeDomain += "fact {\r\n" + 
				"	all d: DomainModel | \r\n" + 
				"		all s : State | \r\n" + 
				"			some t: Transition |\r\n" + 
				"				(t in d. transition ) and ((s in t. source ) or (s in t. target )) =>\r\n" + 
				"					(s in d. state )\r\n" + 
				"					else (s not in d. state )\r\n" + 
				"}\n\n";
	
	}

	String FactGenerator  (String LinkName, String Departure, String Arrival) {
     	return "fact {\r\n" + 
    		"	(" + LinkName + ".source = " + Departure + " and " + LinkName + ".target = " + Arrival + ")\r\n" + 
    		"}\n\n";
     }




	
}
