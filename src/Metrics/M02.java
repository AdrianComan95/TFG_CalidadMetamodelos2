package Metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class M02 implements ICriterion {

	EPackage metamodelo;
	int maxReferences;

	public M02(EPackage metamodelo, int maxReferences) {
		this.metamodelo = metamodelo;
		this.maxReferences = maxReferences;
	}

	@Override
	public String getTitle() {
		return "(M02) Sobrecarca de referencias (" + maxReferences + "-max)";
	}

	public ProblemType getProblemType() {
		return ProblemType.METRIC;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			if (classifier instanceof EClass) {
				if (((EClass) classifier).getEReferences().size() >= maxReferences) {
					Problem problem = new Problem();
					problem.setDescription("La clase " + classifier.getName() 
							+"(" +classifier.getClassifierID()  +")" + "tiene mas de " + maxReferences
							+ " referencias.");
					IQuickfix fix = new M02Fix(metamodelo, classifier);
					problem.addQuickfix(fix);
					problems.add(problem);
				}

			}
		}

		return problems;
	}

}
