package NamingConventions;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class N01 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "(N01) Atributo con nombre parecido al de la clase";
	
	public N01 (EPackage metamodelo) {
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
			    	  if ((attribute.getName()!=null && attribute.getName().contains(nClass))) {
			    		  Problem problem = new Problem();
			    		  problem.setDescription("El nombre del atributo " + classifier.getName() + "." + attribute.getName() 
			              + " contiene el nombre de la clase donde est? definido");
			    		  IQuickfix fix = new N01Fix(metamodelo,classifier, attribute);
						  problem.addQuickfix(fix);
						  problems.add(problem);
			    	  }
			    		  
			      }
			   }
		}		
		
		return problems;
	}


}
