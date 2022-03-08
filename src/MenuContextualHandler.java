import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import plugin_validar.views.SampleView;

public class MenuContextualHandler extends org.eclipse.core.commands.AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//Variables
		String nombres = "";
		
		//Criterios
		Map<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> clasesMinuscula = new ArrayList<String>();
		
		// Obtener el fichero seleccionado
		IFile file = null;
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
		Object first = ((IStructuredSelection)selection).getFirstElement();
		file = (IFile)Platform.getAdapterManager().getAdapter(first, IFile.class);
		if (file == null)
		if (first instanceof IAdaptable)
		file = (IFile)((IAdaptable)first).getAdapter(IFile.class);
		}
		try {
		// Cargar el fichero seleccionado en un objeto Resource
		ResourceSet rs = new ResourceSetImpl();
		Resource resource = rs.createResource(URI.createURI(file.getRawLocationURI().toString()));
		resource.load(null);
		
		// Obtener el paquete ra�z del metamodelo.
		EPackage metamodelo = (EPackage)(resource.getContents().get(0));
		
		//Obtener nombres de las clases y filtrar por primera letra minuscula
		for (int i = 0; i < metamodelo.getEClassifiers().size(); i++) {
			String nombre = metamodelo.getEClassifiers().get(i).getName();
			String primeraLetra = metamodelo.getEClassifiers().get(i).getName().substring(0, 1);
			if(primeraLetra.equals(primeraLetra.toLowerCase()))
				clasesMinuscula.add(nombre);
			nombres += nombre + " ";
		}
		//Pruebas por consola
		System.out.println(nombres);
		System.out.println(clasesMinuscula);
		
		// Mostrar nombre del metamodelo en un cuadro de di�logo
		MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindowChecked(event).getShell(),
		"Validador",
		metamodelo.getName() + " tiene " + metamodelo.getEClassifiers().size() + " clases "
		+ nombres);
		
		//Llamar a la vista y actualizarla
		SampleView view = (SampleView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView
				("plugin_validar.views.SampleView");
		//ArrayList<String> prueba = new ArrayList<String>();
		//prueba.add("Criterios de Calidad");
		
		map.put("Clases Minusculas", clasesMinuscula);
		
		view.update(metamodelo);
		
		}
		
		catch (Exception e) { e.printStackTrace(); }
		return Status.OK;

	}

}
