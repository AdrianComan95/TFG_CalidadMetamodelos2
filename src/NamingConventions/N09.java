package NamingConventions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import plugin_validar.views.Problem;

public class N09 implements ICriterion {

	EPackage metamodelo;
	IDictionary dictionary;

	public N09(EPackage metamodelo, IDictionary dictionary) {
		this.metamodelo = metamodelo;
		this.dictionary = dictionary;
	}

	@Override
	public String getTitle() {
		return "(N09) Clases con un nombre que es sininimo de otra";
	}

	public ProblemType getProblemType() {
		return ProblemType.NAMING_CONVENTION;
	}

	@Override
	public List<Problem> check() {
		List<Problem> problems = new ArrayList<Problem>();

		List<EClass> classifiersWithOriblems = new ArrayList<EClass>();
		for (EClassifier classifier : metamodelo.getEClassifiers()) {
			if (classifier instanceof EClass && !classifiersWithOriblems.contains(classifier.eClass())) {
				IIndexWord idxWord = dictionary.getIndexWord(classifier.getName(), POS.NOUN);
				if (idxWord != null) {
					IWordID wordID = idxWord.getWordIDs().get(0); // 1st meaning
					IWord word = dictionary.getWord(wordID);
					ISynset synset = word.getSynset();

					// iterate over words associated with the synset

					for (EClassifier classifier2 : metamodelo.getEClassifiers()) {
						if (classifier instanceof EClass) {
							for (IWord w : synset.getWords()) {
								if (w.getLemma().equals(classifier2.getName())
										&& !w.getLemma().equals(classifier.getName())) {
									Problem problem = new Problem();
									problem.setDescription("Existen dos clases con nombres que son sinonimos "
											+ classifier.getName() + " y " + classifier2.getName());
									IQuickfix fix = new N09Fix(metamodelo, classifier);
									problem.addQuickfix(fix);
									IQuickfix fix2 = new N09Fix(metamodelo, classifier2);
									problem.addQuickfix(fix2);
									problems.add(problem);
								}
								classifiersWithOriblems.add((EClass) classifier);
								classifiersWithOriblems.add((EClass) classifier2);
							}

						}

					}
				}

			}
		}

		return problems;
	}

}
