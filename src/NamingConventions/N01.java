package NamingConventions;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import plugin_validar.views.IProblem;

public class N01 implements IProblem {
	
	EPackage metamodelo;
	
	String title = "Atributo con nombre parecido al de la clase (N01)";
	
	public N01 (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}
	
	public ProblemType getProblemType() { return ProblemType.NAMING_CONVENTION; }

	@Override
	public List<String> check() {
		List<String> problems = new ArrayList<String>();
		
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


}
