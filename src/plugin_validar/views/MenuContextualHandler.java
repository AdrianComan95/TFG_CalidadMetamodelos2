package plugin_validar.views;


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

public class MenuContextualHandler extends org.eclipse.core.commands.AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
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
		
		// Obtener el paquete raíz del metamodelo.
		EPackage metamodelo = (EPackage)(resource.getContents().get(0));
		
		// Mostrar nombre del metamodelo en un cuadro de diálogo
		/*MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindowChecked(event).getShell(),
		"Validador",
		metamodelo.getName() + " tiene " + metamodelo.getEClassifiers().size() + " clases: "
		+ nombres);*/
		
		//Llamar a la vista y actualizarla
		ProblemsView view = (ProblemsView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView
				("plugin_validar.views.ProblemsView");
		//ArrayList<String> prueba = new ArrayList<String>();
		//prueba.add("Criterios de Calidad");
		
		view.update(metamodelo);
		
		}
		
		catch (Exception e) { e.printStackTrace(); }
		return Status.OK;

	}

}
