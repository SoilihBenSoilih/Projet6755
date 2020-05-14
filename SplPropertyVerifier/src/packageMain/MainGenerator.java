package packageMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainGenerator {

	public static void main(String[] args) {
		
		String codeAlloy="";
		String propeties="";
		GeneratorFeature feature = new GeneratorFeature();
		GeneratorDomain domain = new GeneratorDomain();
		GeneratorSplAndMapping splmap = new GeneratorSplAndMapping();
		
		codeAlloy += feature.codeFeature + domain.codeDomain + splmap.codeSplMapping; 
		

		try {
			String line;
			BufferedReader in = new BufferedReader(new FileReader("src/Propeties/propeties.txt"));
			
			while ((line = in.readLine()) != null)
			{
				propeties += line + "\n";
				
			}
			in.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		codeAlloy += propeties;
		
        try {
	         File file = new File("src\\Output\\codeAlloy.als");
	         if (!file.exists()) {
	            file.createNewFile();
	         } 
	         FileWriter fw = new FileWriter(file.getAbsoluteFile());
	         BufferedWriter bw = new BufferedWriter(fw);
	         bw.write(codeAlloy);
	         bw.close();
	         
	         System.out.println("Code alloy généré avec succès !!!");
		} catch (Exception e) {
			// TODO: handle exception
		}
        
	}

}
