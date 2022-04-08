package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class LowerClass implements ICriterion  {
	
	EPackage metamodelo;
	
	String title = "Clase empieza por letra en minuscula";

	public LowerClass (EPackage metamodelo) {
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
			String primeraLetra = classifier.getName().substring(0, 1);
			if(primeraLetra.equals(primeraLetra.toLowerCase())) {
				Problem problem = new Problem();
				problem.setDescription("La clase '" + classifier.getName() + "' no empieza con mayúscula");
				IQuickfix fix = new LowerClassFix(metamodelo,classifier);
				problem.addQuickfix(fix);
				problems.add(problem);
			}
				
		}		
		
		return problems;
	}
}
