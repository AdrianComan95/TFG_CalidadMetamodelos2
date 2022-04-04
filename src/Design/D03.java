package Design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import NamingConventions.LowerClassFix;
import QuickFixes.IQuickfix;
import plugin_validar.views.ICriterion;
import plugin_validar.views.Problem;

public class D03 implements ICriterion {
	
	EPackage metamodelo;
	
	String title = "Clase abstracta con un solo hijo (D03)";

	public D03 (EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}

	
	public ProblemType getProblemType() { return ProblemType.DESIGN; }

	@Override
	public List<Problem> check() {
		
		List<Problem> problems = new ArrayList<Problem>();
		
		EList<EClassifier> classifiers = metamodelo.getEClassifiers();
		
		for (EClassifier classifier : classifiers) {
			if (classifier instanceof EClass) {
				if((((EClass) classifier).getESuperTypes()).size() == 1) {
					Problem problem = new Problem();
					problem.setDescription("La clase " + (((EClass) classifier).getESuperTypes()).get(0).getName() + " es abstracta con un solo hijo");	
					IQuickfix fix = new D03Fix(metamodelo,classifier);
					problem.addQuickfix(fix);
					problems.add(problem);
				}
			}
			
		}		
		
		return problems;
	}

}
