package Metrics;

import java.util.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class M05 implements ICriterion {

	EPackage metamodelo;
	int maxChildrens;
	EClass root;

	public M05(EPackage metamodelo, int maxChildrens) {
		this.metamodelo = metamodelo;
		this.maxChildrens = maxChildrens;
	}

	@Override
	public String getTitle() {
		return "(M05) Clase con demasiados hijos directos (" + maxChildrens + "-max)";
	}

	public ProblemType getProblemType() {
		return ProblemType.METRIC;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			int childrens = 0;
			if (classifier instanceof EClass) {
				for (EClassifier classifier2 : metamodelo.getEClassifiers()) {
					if (classifier instanceof EClass) {						
						if (((EClass) classifier).isSuperTypeOf((EClass) classifier2) && !((EClass) classifier).equals(classifier2)) {
							childrens ++;
						}
					}
				}
			}
			if (childrens >= maxChildrens) {
				Problem problem = new Problem();
				problem.setDescription("La clase " + root.getName() + "(" + root.getClassifierID() + ")"
						+ "supera el maximo de hijos directos");
				IQuickfix fix1 = new M05Fix(metamodelo, classifier);
				problem.addQuickfix(fix1);
				problems.add(problem);
			}
		}

		return problems;
	}

}
