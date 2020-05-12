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

	public static void main(String[] args) {
		
		String locking="", waiting="",washing="",drying="",unlocking="";
		String startPrewash="",startWash="",endPrewash="",endWash="",startDrying="",endDrying="";
		String codeDomain="";
		
		try {
			
			File domainModel = new File("src/DomainModel/DomainModel.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(domainModel);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("component");
			NodeList nList2 = doc.getElementsByTagName("link");
			
			Node nNode = nList.item(0);
			Node nNode1 = nList.item(1);
			Node nNode2 = nList.item(2);
			Node nNode3 = nList.item(3);
			Node nNode4 = nList.item(4);
			
			Node nNodeL = nList2.item(0);
			Node nNodeL1 = nList2.item(1);
			Node nNodeL2 = nList2.item(2);
			Node nNodeL3 = nList2.item(3);
			Node nNodeL4 = nList2.item(4);
			Node nNodeL5 = nList2.item(5);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				Element eElement1 = (Element) nNode1;
				Element eElement2 = (Element) nNode2;
				Element eElement3 = (Element) nNode3;
				Element eElement4 = (Element) nNode4;
				
				Element eElementL = (Element) nNodeL;
				Element eElementL1 = (Element) nNodeL1;
				Element eElementL2 = (Element) nNodeL2;
				Element eElementL3 = (Element) nNodeL3;
				Element eElementL4 = (Element) nNodeL4;
				Element eElementL5 = (Element) nNodeL5;
				
				locking = eElement.getAttribute("name");
				waiting = eElement1.getAttribute("name");
				washing = eElement2.getAttribute("name");
				drying = eElement3.getAttribute("name");
				unlocking = eElement4.getAttribute("name");
				
				
				
				startPrewash = eElementL.getAttribute("name");
				startWash  = eElementL1.getAttribute("name");
				endPrewash = eElementL2.getAttribute("name");
				startDrying = eElementL3.getAttribute("name");
				endWash = eElementL4.getAttribute("name");
				endDrying = eElementL5.getAttribute("name");
				
			
		}
			} catch (Exception e) {
			// TODO: handle exception
		}
		
		ArrayList<String> array = new ArrayList<String>();
		array.add(locking);
		array.add(waiting);
		array.add(washing);
		array.add(drying);
		array.add(unlocking);
		DefinirSig objet = new DefinirSig();
		
		ArrayList<String> arrayT = new ArrayList<String>();
		arrayT.add(startWash);
		arrayT.add(endWash);
		arrayT.add(startPrewash);
		arrayT.add(endPrewash);
		arrayT.add(startDrying);
		arrayT.add(endDrying);
		
		codeDomain +="\n\n";
		codeDomain += objet.DefinirSignature("State", "Abstract");
		codeDomain += objet.DefinirSousSignature(array, "State","one");
		codeDomain +="abstract sig Transition {\r\n" + 
				"	source : one State,\r\n" + 
				"	target : one State\r\n" + 
				"}\n\n";
		
		codeDomain += objet.DefinirSousSignature(arrayT, "Transition", "one");
		
		codeDomain += "fact {\r\n" + 
				"	(" + startWash + ".source = " + locking + " and " + startWash + ".target = " + waiting + ")\r\n" + 
				"}\n\n";
		
		codeDomain +="fact {\r\n" + 
				"	(" + endWash + ".source = " + washing + " and " + endWash + ".target = " + unlocking + ")\r\n" + 
				"}\n\n";
		
		codeDomain += "fact {\r\n" + 
				"	(" + startPrewash + ".source = " + locking + " and " + startPrewash + ".target = " + waiting + ")\r\n" + 
				"}\n\n";
		
		codeDomain += "fact {\r\n" + 
				"	(" + endPrewash + ".source = " + waiting + " and " + endPrewash + ".target = " + washing + ")\r\n" + 
				"}\n\n";
		
		codeDomain += "fact {\r\n" + 
				"	(" + startDrying + ".source = " + washing + " and " + startDrying + ".target = " + drying + ")\r\n" + 
				"}\n\n";
		
		codeDomain += "fact {\r\n" + 
				"	(" + endDrying + ".source = " + drying + " and " + endDrying + ".target = " + unlocking + ")\r\n" + 
				"}\n\n";
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
		System.out.println(codeDomain);
	}

}
