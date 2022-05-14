package Design;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import plugin_validar.views.Problem;

public class D10 implements ICriterion {

	EPackage metamodelo;

	String title = "(D10) Ninguna clase contiene una de sus superclases, con cardinalidad 1 al final de la composición";

	public D10(EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	public ProblemType getProblemType() {
		return ProblemType.DESIGN;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			if (classifier instanceof EClass) {
				for (EClassifier classifier2 : ((EClass) classifier).getEAllSuperTypes()) {
					if ((classifier instanceof EClass) && !classifier2.equals(classifier)
							&& ((EClass) classifier2).getEReferences().size() > 0) {
						for (EReference reference : ((EClass) classifier2).getEReferences()) {
							if (reference.isContainment() && reference.getEOpposite() != null
									&& reference.getEType().equals(classifier)) {
								if (reference.getEOpposite().getLowerBound() != 0) {
									Problem problem = new Problem();
									problem.setDescription("La clase " + classifier.getName() + "("
											+ classifier.getClassifierID() + ")" + " no cumple D10 ");
									problems.add(problem);
								}
							}
						}
					}
				}
			}
		}
		return problems;
	}
}
