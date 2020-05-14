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

	public String codeFeature;
	public ArrayList<String> array = new ArrayList<>();
	
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
				
				array.add(eElement.getAttribute("name"));
				
				for (int i = 0; i < eElement.getElementsByTagName("feature").getLength(); i++) {
					
					array.add(eElement.getElementsByTagName("feature").item(i).getTextContent());
					
				}
	
		    }
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//ArrayList<String> array = new ArrayList<String>();
		//array.add(wash);
		//array.add(heat);
		//array.add(delay);
		//array.add(dry);
		DefinirSig objet = new DefinirSig();
		
		
		codeFeature = "//Definition generale\n\n";
		
		codeFeature += objet.DefinirSignature("Feature", "abstract");
		
		codeFeature += objet.DefinirSousSignature(array, "Feature", "one");
		
		codeFeature += "//Définition du feature model\n\n";
		
		codeFeature += "abstract sig FeatureModel {\n   feature : some Feature\n}\n\n";
		

		codeFeature += "// " + array.get(0) + " est obligatoire\n\n";
		codeFeature += "fact {\n   all f: FeatureModel | " + array.get(0)  + " in f. feature\n}\n\n";
		
		
		
	}
}
