package Package;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class Mapping {

	public static void main(String[] args) {
		
		String delay="", heat="",dry="",wash="";
		String locking="", waiting="",washing="",dying="",unlocking="";
		
		try {

			File domainModel = new File("src/DomainModel/DomainModel.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(domainModel);
			doc.getDocumentElement().normalize();

			File featureModel = new File("src/FeatureModel/FeatureModel.xml");
			DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder2 = dbFactory2.newDocumentBuilder();
			Document doc2 = dBuilder2.parse(featureModel);
			doc2.getDocumentElement().normalize();
			
			System.out.println("=======================================================");
			
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("component");
			
			Node nNode = nList.item(0);
			Node nNode1 = nList.item(1);
			Node nNode2 = nList.item(2);
			Node nNode3 = nList.item(3);
			Node nNode4 = nList.item(4);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				Element eElement1 = (Element) nNode1;
				Element eElement2 = (Element) nNode2;
				Element eElement3 = (Element) nNode3;
				Element eElement4 = (Element) nNode4;
				
				locking = eElement.getAttribute("name");
				waiting = eElement1.getAttribute("name");
				washing = eElement2.getAttribute("name");
				dying = eElement3.getAttribute("name");
				unlocking = eElement4.getAttribute("name");
	
		}
			
			System.out.println("=======================================================");
			
			System.out.println(locking);
			System.out.println(waiting);
			System.out.println(washing);
			System.out.println(dying);
			System.out.println(unlocking);
			
			System.out.println("=======================================================");
			
			System.out.println("Root element :" + doc2.getDocumentElement().getNodeName());
			
			System.out.println("=======================================================");

			NodeList nList2 = doc2.getElementsByTagName("features");
			
			Node nNodeDoc2 = nList2.item(0);
			
			if (nNodeDoc2.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNodeDoc2;
				
				wash = eElement.getAttribute("name");
				delay = eElement.getElementsByTagName("feature").item(0).getTextContent();
				heat = eElement.getElementsByTagName("feature").item(1).getTextContent();
				dry = eElement.getElementsByTagName("feature").item(2).getTextContent();		
	
		}
			System.out.println(wash);
			
			System.out.println(delay);
			System.out.println(heat);
			System.out.println(dry);
			
			
			
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		
			try {
			 
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
	 
	        // élément de racine
	        Document doc = docBuilder.newDocument();
	        Element racine = doc.createElement("mapping");
	        doc.appendChild(racine);
	 
	        //startPrewash
	        Element p1 = doc.createElement("presenceCondition");
	        racine.appendChild(p1);
	 
	        // 1er attribut
	        Attr attr = doc.createAttribute("element");
	        attr.setValue("startPrewash");
	        p1.setAttributeNode(attr);
	        
	        // 2eme attribut
	        Attr attr2 = doc.createAttribute("condition");
	        attr2.setValue(heat + " or " + delay);
	        p1.setAttributeNode(attr2);
	        
	        //Waiting
	        Element p2 = doc.createElement("presenceCondition");
	        racine.appendChild(p2);
	 
	        // 1er attribut
	        Attr attr3 = doc.createAttribute("element");
	        attr3.setValue("waiting");
	        p2.setAttributeNode(attr3);
	        
	        // 2eme attribut
	        Attr attr4 = doc.createAttribute("condition");
	        attr4.setValue(heat + " or " + delay);
	        p2.setAttributeNode(attr4);
	        
	      //endPrewash
	        Element p3 = doc.createElement("presenceCondition");
	        racine.appendChild(p3);
	 
	        // 1er attribut
	        Attr attr5 = doc.createAttribute("element");
	        attr5.setValue("endPrewash");
	        p3.setAttributeNode(attr5);
	        
	        // 2eme attribut
	        Attr attr6 = doc.createAttribute("condition");
	        attr6.setValue(heat + " or " + delay);
	        p3.setAttributeNode(attr6);
	        
	      //Washing
	        Element p4 = doc.createElement("presenceCondition");
	        racine.appendChild(p4);
	 
	        // 1er attribut
	        Attr attr7 = doc.createAttribute("element");
	        attr7.setValue("washing");
	        p4.setAttributeNode(attr7);
	        
	        // 2eme attribut
	        Attr attr8 = doc.createAttribute("condition");
	        attr8.setValue(heat);
	        p4.setAttributeNode(attr8);
	        
	      //startDrying
	        Element p5 = doc.createElement("presenceCondition");
	        racine.appendChild(p5);
	 
	        // 1er attribut
	        Attr attr9 = doc.createAttribute("element");
	        attr9.setValue("startDrying");
	        p5.setAttributeNode(attr9);
	        
	        // 2eme attribut
	        Attr attr10 = doc.createAttribute("condition");
	        attr10.setValue(dry);
	        p5.setAttributeNode(attr10);
	        
	      //Drying
	        Element p6 = doc.createElement("presenceCondition");
	        racine.appendChild(p6);
	 
	        // 1er attribut
	        Attr attr11 = doc.createAttribute("element");
	        attr11.setValue("drying");
	        p6.setAttributeNode(attr11);
	        
	        // 2eme attribut
	        Attr attr12 = doc.createAttribute("condition");
	        attr12.setValue(dry);
	        p6.setAttributeNode(attr12);
	        
	      //endDrying
	        Element p7 = doc.createElement("presenceCondition");
	        racine.appendChild(p7);
	 
	        // 1er attribut
	        Attr attr13 = doc.createAttribute("element");
	        attr13.setValue("endDrying");
	        p7.setAttributeNode(attr13);
	        
	        // 2eme attribut
	        Attr attr14 = doc.createAttribute("condition");
	        attr14.setValue(dry);
	        p7.setAttributeNode(attr14);
	        
	      //startWash
	        Element p8 = doc.createElement("presenceCondition");
	        racine.appendChild(p8);
	 
	        // 1er attribut
	        Attr attr15 = doc.createAttribute("element");
	        attr15.setValue("startWash");
	        p8.setAttributeNode(attr15);
	        
	        // 2eme attribut
	        Attr attr16 = doc.createAttribute("condition");
	        attr16.setValue(locking + " and " + washing);
	        p8.setAttributeNode(attr16);
	        
	      //endWash
	        Element p9 = doc.createElement("presenceCondition");
	        racine.appendChild(p9);
	 
	        // 1er attribut
	        Attr attr17 = doc.createAttribute("element");
	        attr17.setValue("endWash");
	        p9.setAttributeNode(attr17);
	        
	        // 2eme attribut
	        Attr attr18 = doc.createAttribute("condition");
	        attr18.setValue(washing + " and " + unlocking);
	        p9.setAttributeNode(attr18);
	        
	        
	        // write the content into xml file
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(doc);
	        StreamResult resultat = new StreamResult(new File("src/map/mapping.xml"));
	 
	        transformer.transform(source, resultat);
	 
	        System.out.println("Fichier sauvegardé avec succès!");
	 
	     } catch (ParserConfigurationException pce) {
	         pce.printStackTrace();
	     } catch (TransformerException tfe) {
	         tfe.printStackTrace();
	     }
		}

}
