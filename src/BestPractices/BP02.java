package BestPractices;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import Utils.ChangeClassToConcreteFix;
import Utils.CreateSubclassFix;
import plugin_validar.views.Problem;

public class BP02 implements ICriterion {

	EPackage metamodelo;

	String title = "(BP02) Clase abstracta sin hijos";

	public BP02(EPackage metamodelo) {
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
			Boolean uninstantiable = true;
			if (classifier instanceof EClass) {
				if (((EClass) classifier).isAbstract()) {
					for (EClassifier classifier2 : metamodelo.getEClassifiers()) {
						if (((EClass) classifier).isSuperTypeOf((EClass) classifier2)
								&& !classifier2.equals(classifier)) {
							uninstantiable = false;
						}

					}
					if (uninstantiable == true) {
						Problem problem = new Problem();
						problem.setDescription("La clase " + classifier.getName() + "(" + classifier.getClassifierID()
								+ ")" + " es abstracta sin hijos");
						IQuickfix fix1 = new BP02Fix1(metamodelo, classifier);
						problem.addQuickfix(fix1);
						IQuickfix fix2 = new ChangeClassToConcreteFix(metamodelo, classifier);
						problem.addQuickfix(fix2);
						IQuickfix fix3 = new CreateSubclassFix(metamodelo, classifier);
						problem.addQuickfix(fix3);
						problems.add(problem);
					}

				}
			}

		}

		return problems;
	}

}
