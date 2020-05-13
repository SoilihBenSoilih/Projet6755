package packageMain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class GeneratorSplAndMapping {

	public static void main(String[] args) {
		
		
		GeneratorFeature feature = new GeneratorFeature();
		GeneratorDomain domain = new GeneratorDomain();
		DefinirSig signature = new DefinirSig();
		ArrayList<String> array = new ArrayList<>();
		
		String heat = feature.heat;
		String wash = feature.wash;
		String delay = feature.delay;
		String dry = feature.dry;
		String code1 = feature.codeFeature;
		
		String locking=domain.locking, waiting=domain.waiting,washing=domain.washing,drying=domain.drying,unlocking=domain.unlocking;
		String startPrewash=domain.startPrewash,startWash=domain.startWash,endPrewash=domain.endPrewash,endWash=domain.endWash,startDrying=domain.startDrying,endDrying=domain.endDrying;
		String code2=domain.codeDomain;
		String code="",code3="";
		
        code3 += "// définition d'un produit\r\n" + 
        		"abstract sig Product {\r\n" + 
        		"	domain: one DomainModel ,\r\n" + 
        		"	feature : one FeatureModel \r\n" + 
        		"}\n\n";
		
        array.add("P1");
        array.add("P2");
        array.add("P3");
        array.add("P4");
        array.add("P5");
        
        code3 += signature.DefinirSousSignature(array, "Product", "one");
        
        code3 += "// définition d'une ligne de produit.\r\n\n" + 
        		"abstract sig SPL{\r\n" + 
        		"product : some Product\r\n" + 
        		"}\r\n\n" + 
        		"fact {\r\n" + 
        		"	all p: Product |\r\n" + 
        		"		p in SPL.product \r\n" + 
        		"}\n\n";
        
        code3 += "//considération du mapping \r\n\n" +  
        		"fact {\r\n" + 
        		"all p: Product |\r\n" + 
        		"	(" + wash + " in p. feature . feature )=>\r\n" + 
        		"		(( " + startWash + " in p.domain. transition ) and (" + endWash + " in p.domain. transition ))\r\n" + 
        		"		else (( " + startWash + " not in p.domain. transition ) and (" + endWash + " not in p.domain. transition ))\r\n" + 
        		"}\r\n\n";
        	 
        code3 += "fact {\r\n" + 
        		"	all p: Product |\r\n" + 
        		"		(" + dry + " in p. feature . feature )=>\r\n" + 
        		"		(( " + startDrying + " in p.domain. transition ) and (" + endDrying + " in p.domain. transition ))\r\n" + 
        		"		else (( " + startDrying + " not in p.domain. transition ) and (" + endDrying + " not in p.domain. transition ))\r\n" + 
        		"}\r\n\n";
        
        code3 +="fact {\r\n" + 
        		"	all p: Product |\r\n" + 
        		"		 (" + heat + " in p. feature . feature ) or (" + delay + " in p. feature . feature ) =>\r\n" + 
        		"		(( " + startPrewash + " in p.domain. transition ) and (" + endPrewash + " in p.domain. transition ))\r\n" + 
        		"		else (( " + startPrewash + "  not in p.domain. transition ) and (" + endPrewash + " not in p.domain. transition )) \r\n" + 
        		"}";
        
        code = code1 + code2 +code3;
       //System.out.println(code);
		
        try {
	         File file = new File("src\\Output\\codeAlloy.als");
	         if (!file.exists()) {
	            file.createNewFile();
	         } 
	         FileWriter fw = new FileWriter(file.getAbsoluteFile());
	         BufferedWriter bw = new BufferedWriter(fw);
	         bw.write(code);
	         bw.close();
	         
	         System.out.println("Done");
		} catch (Exception e) {
			// TODO: handle exception
		}
        
	  
	}

}
