package NamingConventions;

import java.io.IOException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.swt.widgets.Shell;

import Interfaces.IQuickfix;
import Utils.AreaDialogN01Fix;

public class N01Fix implements IQuickfix {
	
	private EClassifier classifier;
	private EPackage metamodelo;
	private EAttribute attribute;
	
	public N01Fix (EPackage metamodelo, EClassifier classifier,EAttribute attribute) {
		this.classifier = classifier;
		this.metamodelo = metamodelo;
		this.attribute = attribute;
	}

	@Override
	public void execute() {
		AreaDialogN01Fix dialog = new AreaDialogN01Fix(Shell.internal_new(null, 0), attribute.getName());
		dialog.open();
		String newName = dialog.getNewName();
		System.out.println(newName);
		if (newName != null) {
			if (!newName.isEmpty()) {
				attribute.setName(newName);
				try {
					metamodelo.eResource().save(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public String getDescription() {
		return "Cambiar el nombre del atributo"; 
	}

}
