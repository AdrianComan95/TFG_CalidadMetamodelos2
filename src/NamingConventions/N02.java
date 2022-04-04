package NamingConventions;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import QuickFixes.IQuickfix;
import plugin_validar.views.ICriterion;
import plugin_validar.views.Problem;

public class N02 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "Atributo con nombre identico al de la clase (N02)";
	
	public N02 (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}
	
	public ProblemType getProblemType() { return ProblemType.NAMING_CONVENTION; }


	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();
		
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			String nClass = classifier.getName().toLowerCase();
			if (classifier instanceof EClass) {
			      for (EAttribute attribute : ((EClass)classifier).getEAllAttributes()) {
			    	  if (attribute.getName().equals(nClass)) {
			    		  Problem problem = new Problem();
			    		  problem.setDescription("El atributo " + attribute.getName() + "." + classifier.getName() 
			              + " puede ser una potencial asociación");
			    		  IQuickfix fix = new N02Fix(metamodelo,classifier);
						  problem.addQuickfix(fix);
						  problems.add(problem);
			    	  }
			    		  
			      }
			   }
		}		
		
		return problems;
	}


}
