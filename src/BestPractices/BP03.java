package BestPractices;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import plugin_validar.views.Problem;

public class BP03 implements ICriterion {

	EPackage metamodelo;

	String title = "(BP03) Hay una clase raiz que contiene a todas las demas";

	public BP03(EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	public ProblemType getProblemType() {
		return ProblemType.BEST_PRACTICE;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			Boolean classContainsAllOthers = false;
			if (classifier instanceof EClass) {
				for (EClassifier classifier2 : metamodelo.getEClassifiers()) {
					if ((classifier instanceof EClass) && !classifier2.equals(classifier) && ((EClass) classifier2).getEAllReferences().size() > 0) {
								for (EReference reference : ((EClass) classifier2).getEAllReferences()) {
									if (reference.isContainment()) {
										if (reference.getEType().equals(classifier)) {
											classContainsAllOthers = true;
											break;
										}
										classContainsAllOthers = false;
									}
								}
						if (!classContainsAllOthers)
							break;
					}
					else if (classifier2.equals(classifier)) {
						classContainsAllOthers = true;
					}
					else {
						classContainsAllOthers = false;
						break;
					}
				}
				if (classContainsAllOthers) {
					return problems;
				}
			}
		}
		Problem problem = new Problem();
		problem.setDescription("No existe ninguna clase que contenga a todas las demas");
		problems.add(problem);
		return problems;
	}
}
