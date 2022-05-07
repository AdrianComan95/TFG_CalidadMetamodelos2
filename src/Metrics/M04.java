package Metrics;

import java.util.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import plugin_validar.views.Problem;

public class M04 implements ICriterion {

	EPackage metamodelo;
	int maxDepth;
	EClass root;

	public M04(EPackage metamodelo, int maxDepth) {
		this.metamodelo = metamodelo;
		this.maxDepth = maxDepth;
	}

	@Override
	public String getTitle() {
		return "(M04) Jerarquia con demasiada profundidad (" + maxDepth + "-max)";
	}

	public ProblemType getProblemType() {
		return ProblemType.METRIC;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();
		List<EClass> problemsClass = new ArrayList<EClass>();
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			if (classifier instanceof EClass) {
				if (getDepth((EClass) classifier) >= maxDepth) {
					if (!problemsClass.contains(root)) {
						problemsClass.add(root);
						problemsClass.add((EClass) classifier);
						Problem problem = new Problem();
						problem.setDescription("La clase raiz " + root.getName() + "(" + root.getClassifierID() + ")"
								+ "supera la profundidad maxima");
						problems.add(problem);
					}
					
				}
			}
		}

		return problems;
	}

	public int getDepth(EClass classifier) {
		root = classifier;
		int depth = 0;
		if (classifier.getESuperTypes().size() > 0) {
			for (EClass parent : classifier.getESuperTypes()) {
				if (!classifier.equals(parent)) {
					depth = Math.max(depth, getDepth(parent));
				}
			}
			return 1 + depth;
		} else {
			return depth;
		}
	}
	/*public List<EClass> getTree(EClass classifier) {
		List<EClass> allReferences = new ArrayList<EClass>();
		allReferences.add(classifier);
		int depth = 0;
		if (classifier.getESuperTypes().size() > 0) {
			for (EClass parent : classifier.getESuperTypes()) {
				if (!classifier.equals(parent)) {
					if (depth == Math.max(depth, getDepth(parent))) {
						
					}
					depth = Math.max(depth, getDepth(parent));
				}
			}
			return 1 + depth;
		} else {
			return allReferences;
		}
	}*/

}
