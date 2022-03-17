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
		
		ArrayList<String> problems = new ArrayList<String>();
		
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			String primeraLetra = classifier.getName().substring(0, 1);
			if(primeraLetra.equals(primeraLetra.toLowerCase()))
				problems.add("La clase '" + classifier.getName() + "' no empieza con mayúscula");
		}		
		
		return problems;
		
	}
	
	public ArrayList<String> getN01 () {
			
			ArrayList<String> problems = new ArrayList<String>();
			
			for (EClassifier classifier : metamodelo.getEClassifiers()) {
				String nClass = classifier.getName().toLowerCase();
				if (classifier instanceof EClass) {
				      for (EAttribute attribute : ((EClass)classifier).getEAllAttributes()) {
				    	  if (attribute.getName().contains(nClass))
				    		  problems.add("El atributo " + attribute.getName() + "." + classifier.getName() 
				              + " tiene el mismo nombre que su clase entidad");
				      }
				   }
			}		
			
			return problems;
	
	}
	
	public ArrayList<String> getN02 () {
		
		ArrayList<String> problems = new ArrayList<String>();
		
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			String nClass = classifier.getName().toLowerCase();
			if (classifier instanceof EClass) {
			      for (EAttribute attribute : ((EClass)classifier).getEAllAttributes()) {
			    	  if (attribute.getName().equals(nClass))
			    		  problems.add("El atributo " + attribute.getName() + "." + classifier.getName() 
					              + " puede ser una potencial asociación");
			      }
			   }
		}		
		
		return problems;
		
	}

}
