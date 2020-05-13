package packageMain;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeneratorFeature {

	public static String delay;
	public static String heat;
	public static String dry;
	public static String wash;
	public static String codeFeature;
	
	public GeneratorFeature() {
		
		

		try {
			File featureModel = new File("src/FeatureModel/FeatureModel.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(featureModel);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("features");
			
			Node nNodeDoc = nList.item(0);
			
			if (nNodeDoc.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNodeDoc;
				
				wash = eElement.getAttribute("name");
				delay = eElement.getElementsByTagName("feature").item(0).getTextContent();
				heat = eElement.getElementsByTagName("feature").item(1).getTextContent();
				dry = eElement.getElementsByTagName("feature").item(2).getTextContent();		
	
		}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		ArrayList<String> array = new ArrayList<String>();
		array.add(wash);
		array.add(heat);
		array.add(delay);
		array.add(dry);
		DefinirSig objet = new DefinirSig();
		
		
		codeFeature = "//Definition generale\n\n";
		
		codeFeature += objet.DefinirSignature("Feature", "abstract");
		
		codeFeature += objet.DefinirSousSignature(array, "Feature", "one");
		
		codeFeature += "//Définition du feature model\n\n";
		
		codeFeature += "abstract sig FeatureModel {\n   feature : some Feature\n}\n\n";
		

		codeFeature += "// Wash est obligatoire\n\n";
		codeFeature += "fact {\n   all f: FeatureModel | " + wash + " in f. feature\n}\n\n";
		
		
		
		
		
	}
}
