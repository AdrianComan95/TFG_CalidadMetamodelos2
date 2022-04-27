package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import plugin_validar.views.Problem;

public class N02 implements ICriterion {

	EPackage metamodelo;

	String title = "(N02) Atributo con nombre identico al de una clase";

	public N02(EPackage metamodelo) {
		this.metamodelo = metamodelo;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	public ProblemType getProblemType() {
		return ProblemType.NAMING_CONVENTION;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			String nClass = classifier.getName().toLowerCase();
			if (classifier instanceof EClass) {
				for (EClassifier classifier2 : metamodelo.getEClassifiers()) {
					for (EAttribute attribute : ((EClass) classifier2).getEAllAttributes()) {
						if (attribute.getName().toLowerCase().equals(nClass)) {
							Problem problem = new Problem();
							problem.setDescription("El atributo " + classifier2.getName() + "." + attribute.getName()
									+ " puede ser una potencial asociación");
							IQuickfix fix = new N02Fix(metamodelo, classifier2, attribute, classifier);
							problem.addQuickfix(fix);
							problems.add(problem);
						}

					}
				}
			}
		}

		return problems;
	}

}
