package BestPractices;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import plugin_validar.views.Problem;

public class BP04 implements ICriterion {

	EPackage metamodelo;

	String title = "(BP04) Ninguna clase puede estar contenida en dos clases";

	public BP04(EPackage metamodelo) {
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
			int nClassContained = 0;
			if (classifier instanceof EClass) {
				for (EClassifier classifier2 : metamodelo.getEClassifiers()) {
					if ((classifier instanceof EClass) && !classifier2.equals(classifier) && ((EClass) classifier2).getEAllReferences().size() > 0) {
								for (EReference reference : ((EClass) classifier2).getEAllReferences()) {
									if (reference.isContainment()) {
										if (reference.getEType().equals(classifier)) {
											nClassContained++;
											break;
										}
									}
								}
					}
				}
				if (nClassContained > 1) {
					Problem problem = new Problem();
					problem.setDescription("La clase " + classifier.getName() + "(" + classifier.getClassifierID()
					+ ")" + " es contenida en dos o mas clases");
					problems.add(problem);
				}
			}
		}
		return problems;
	}
}
