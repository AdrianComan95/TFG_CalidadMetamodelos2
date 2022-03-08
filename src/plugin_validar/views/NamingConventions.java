package plugin_validar.views;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EClass;

public class NamingConventions {
	
	EPackage metamodelo;
	
	public NamingConventions (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	public ArrayList<String> getLowerClass () {
		
		ArrayList<String> lowerClass = new ArrayList<String>();
		
		for (int i = 0; i < metamodelo.getEClassifiers().size(); i++) {
			String name = metamodelo.getEClassifiers().get(i).getName();
			String primeraLetra = metamodelo.getEClassifiers().get(i).getName().substring(0, 1);
			if(primeraLetra.equals(primeraLetra.toLowerCase()))
				lowerClass.add("La clase '" + name + "'no empieza con mayúscula");
		}		
		
		return lowerClass;
		
	}
	
	public ArrayList<String> getN01 () {
			
			ArrayList<String> attributes = new ArrayList<String>();
			
			for (EClassifier classifier : metamodelo.getEClassifiers()) {
				String nClass = classifier.getName().toLowerCase();
				if (classifier instanceof EClass) {
				      for (EAttribute attribute : ((EClass)classifier).getEAllAttributes()) {
				    	  if (attribute.getName().contains(nClass))
				    		  attributes.add(attribute.getName());
				      }
				   }
			}		
			
			return attributes;
	
	}
	
	public ArrayList<String> getN02 () {
		
		ArrayList<String> attributes = new ArrayList<String>();
		
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			String nClass = classifier.getName().toLowerCase();
			if (classifier instanceof EClass) {
			      for (EAttribute attribute : ((EClass)classifier).getEAllAttributes()) {
			    	  if (attribute.getName().equals(nClass))
			    		  attributes.add(attribute.getName());
			      }
			   }
		}		
		
		return attributes;
		
	}

}
