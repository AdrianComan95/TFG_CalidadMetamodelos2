package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import Interfaces.ICriterion;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import plugin_validar.views.Problem;

public class N04 implements ICriterion {

	EPackage metamodelo;
	IDictionary dictionary;

	public N04(EPackage metamodelo, IDictionary dictionary) {
		this.metamodelo = metamodelo;
		this.dictionary = dictionary;
	}

	@Override
	public String getTitle() {
		return "(N04) Cada clase se nombra en pascal-case, con un sintagma nominal en singular";
	}

	public ProblemType getProblemType() {
		return ProblemType.NAMING_CONVENTION;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			String[] words = classifier.getName().split("(?=[A-Z])");
			if (words.length == 2) {
				IIndexWord idxWord = dictionary.getIndexWord(words[1].toLowerCase(), POS.NOUN);
				if (idxWord != null) {
					break;
				}
			}
			Problem problem = new Problem();
			problem.setDescription(
					"La clase con el nombre " + classifier.getName() + " no es un sintagma nominal");
			problems.add(problem);
		}

		return problems;
	}

}
