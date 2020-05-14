package packageMain;

import java.util.ArrayList;

public class DefinirSig {
	
	public String DefinirSignature (ArrayList<String> arrayList, String debut){
		String string="";
		debut = debut.toLowerCase();
		if(debut.equals("abstract") || debut.equals("one")) {
			string += debut + " sig ";
		}
		else {
			string = " sig ";
		}
		for (int i = 0; i < arrayList.size() - 1; i++) {
			string +=  arrayList.get(i) + ",";
		}
		string += arrayList.get(arrayList.size() - 1);
		
		return string + "{}\n\n";
	}
	
	public String DefinirSignature (ArrayList<String> arrayList){
		String string = "sig ";
		for (int i = 0; i < arrayList.size() - 1; i++) {
			string +=  arrayList.get(i) + ",";
		}
		string += arrayList.get(arrayList.size() - 1);
		
		return string + "{}\n\n";
	}
	
	public String DefinirSignature (String string, String debut){
		String retour="";
		debut = debut.toLowerCase();
		if(debut.equals("abstract") || debut.equals("one")) {
			retour += debut + " sig ";
		}
		else {
			retour = " sig ";
		}
		retour += string; 
		return retour + "{}\n\n";
	}
	
	public String DefinirSignature (String string){
		return "sig " + string + "{}\n\n";
	}
	
	public String DefinirSousSignature (ArrayList<String> arrayList, String classeParente, String debut){
		String string="";
		debut = debut.toLowerCase();
		if(debut.equals("abstract") || debut.equals("one")) {
			string += debut + " sig ";
		}
		else {
			string = " sig ";
		}
		for (int i = 0; i < arrayList.size() - 1; i++) {
			string +=  arrayList.get(i) + ",";
		}
		string += arrayList.get(arrayList.size() - 1);
		
		return string + " extends " + classeParente +"{}\n\n";
	}
	
	public String DefinirSousSignature (ArrayList<String> arrayList, String classeParente){
		String string = "sig ";
		for (int i = 0; i < arrayList.size() - 1; i++) {
			string +=  arrayList.get(i) + ",";
		}
		string += arrayList.get(arrayList.size() - 1);
		
		return string + " extends " + classeParente + "{}\n\n";
	}
	
	
	public String DefinirSousSignature (String string, String classeParente, String debut){
		String retour="";
		debut = debut.toLowerCase();
		if(debut.equals("abstract") || debut.equals("one")) {
			retour += debut + " sig ";
		}
		else {
			retour = " sig ";
		}
		retour += string; 
		return retour + " extends " + classeParente + "{}\n\n";
	}
	
	public String DefinirSousSignature (String string, String classeParente){
		return "sig " + string + " extends " + classeParente + "{}\n\n";
	}
}
