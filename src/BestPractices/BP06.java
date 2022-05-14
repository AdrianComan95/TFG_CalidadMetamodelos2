package BestPractices;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import plugin_validar.views.Problem;

public class BP06 implements ICriterion {

	EPackage metamodelo;

	String title = "(BP06) Dos clases no se refieren entre si con referencias no opuestas";

	public BP06(EPackage metamodelo) {
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
			if (classifier instanceof EClass) {
				for (EClassifier classifier2 : ((EClass) classifier).getEAllSuperTypes()) {
					if ((classifier instanceof EClass) && !classifier2.equals(classifier)
							&& ((EClass) classifier2).getEReferences().size() > 0
							&& ((EClass) classifier).getEReferences().size() > 0) {
						for (EReference reference : ((EClass) classifier).getEReferences()) {
							for (EReference reference2 : ((EClass) classifier2).getEReferences()) {
								if (reference.getEOpposite() == null && reference2.getEOpposite() == null) {
									if (reference.getEType().equals(classifier2)
											&& reference2.getEType().equals(classifier)) {
										Problem problem = new Problem();
										problem.setDescription("La clase " + classifier.getName() + "("
												+ classifier.getClassifierID() + ")" + " y la clase "
												+ classifier2.getName() + "(" + classifier.getClassifierID() + ")"
												+ "  se refieren entre si con referencias no opuestas");
										problems.add(problem);
									}
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
