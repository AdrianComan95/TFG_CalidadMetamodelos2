package Design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import plugin_validar.views.IProblem;

public class D03 implements IProblem {
	
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
	public List<String> check() {
		ArrayList<String> problem = new ArrayList<String>();
		
		EList<EClassifier> classifiers = metamodelo.getEClassifiers();
		
		for (EClassifier classifier : classifiers) {
			if (classifier instanceof EClass) {
				if((((EClass) classifier).getESuperTypes()).size() == 1) {
					   problem.add("La clase " + (((EClass) classifier).getESuperTypes()).get(0).getName() + " es abstracta con un solo hijo");		
				}
			}
			
		}		
		
		return problem;
	}

}
