package plugin_validar.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import BestPractices.*;
import Design.*;
import NamingConventions.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EPackage;

import javax.inject.Inject;

import plugin_validar.views.IProblem;
import plugin_validar.views.IProblem.ProblemType;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ProblemsView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "plugin_validar.views.ProblemsView";

	@Inject IWorkbench workbench;
	
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	 
	class TreeObject implements IAdaptable {
		private String name;
		private TreeParent parent;
		
		public TreeObject(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public void setParent(TreeParent parent) {
			this.parent = parent;
		}
		public TreeParent getParent() {
			return parent;
		}
		@Override
		public String toString() {
			return getName();
		}
		@Override
		public <T> T getAdapter(Class<T> key) {
			return null;
		}
	}
	
	class TreeParent extends TreeObject {
		private ArrayList children;
		public TreeParent(String name) {
			super(name);
			children = new ArrayList();
		}
		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}
		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}
		public TreeObject [] getChildren() {
			return (TreeObject [])children.toArray(new TreeObject[children.size()]);
		}
		public boolean hasChildren() {
			return children.size()>0;
		}
	}

	class ViewContentProvider implements ITreeContentProvider {
		private TreeParent invisibleRoot;

		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot==null) initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}
		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent)parent).hasChildren();
			return false;
		}
		
/*
 * We will set up a dummy model to initialize tree heararchy.
 * In a real code, you will connect to a real model and
 * expose its hierarchy.
 */

		///////////////
		public void update(EPackage metamodel) {
			TreeParent root = new TreeParent("Criterios de calidad no superados");
			//TreeParent root = new TreeParent(problems.get(0));
			
			//---DISEÑO
			
			List<IProblem> problems = List.of(new D03(metamodel), new BP01(metamodel),
					new BP02(metamodel), new LowerClass(metamodel), new N01(metamodel),
					new N02(metamodel));
			TreeParent createParentDesign = null;
			TreeParent createParentBestPractice = null;
			TreeParent createParentNamingConvection = null;
			TreeParent createParentMetric = null;
			for (IProblem p : problems) {
				List<String>  detectedErrors = p.check();
				if (p.getProblemType() == ProblemType.DESIGN && detectedErrors.size() > 0) {
					if (createParentDesign == null) {
						createParentDesign = new TreeParent("Diseño");
						root.addChild(createParentDesign);
					}
					TreeParent child = new TreeParent(p.getTitle());
					for (String error : detectedErrors) {
						TreeObject problem = new TreeObject(error);
						child.addChild(problem);
					}
					createParentDesign.addChild(child);	
				}
				if (p.getProblemType() == ProblemType.BEST_PRACTICE && detectedErrors.size() > 0) {
					if (createParentBestPractice == null) {
						createParentBestPractice = new TreeParent("Buenas Practicas");
						root.addChild(createParentBestPractice);
					}
					TreeParent child = new TreeParent(p.getTitle());
					for (String error : detectedErrors) {
						TreeObject problem = new TreeObject(error);
						child.addChild(problem);
					}
					createParentBestPractice.addChild(child);
				}
				if (p.getProblemType() == ProblemType.NAMING_CONVENTION && detectedErrors.size() > 0) {
					if (createParentNamingConvection == null) {
						createParentNamingConvection = new TreeParent("Convención de nombres");
						root.addChild(createParentNamingConvection);
					}
					TreeParent child = new TreeParent(p.getTitle());
					for (String error : detectedErrors) {
						TreeObject problem = new TreeObject(error);
						child.addChild(problem);
					}
					createParentNamingConvection.addChild(child);
				}
				if (p.getProblemType() == ProblemType.METRIC && detectedErrors.size() > 0) {
					if (createParentMetric == null) {
						createParentMetric = new TreeParent("Metrica");
						root.addChild(createParentMetric);
					}
					TreeParent child = new TreeParent(p.getTitle());
					for (String error : detectedErrors) {
						TreeObject problem = new TreeObject(error);
						child.addChild(problem);
					}
					createParentMetric.addChild(child);
				}
			}
									
			
			if(invisibleRoot == null) {
				invisibleRoot = new TreeParent("");
			}
			else {
				TreeObject[] children = invisibleRoot.getChildren();
				for (TreeObject child : children)
					invisibleRoot.removeChild(child);
			}
			invisibleRoot.addChild(root);
		}
		///////////////
		private void initialize() {
			TreeObject to1 = new TreeObject("Leaf 1");
			TreeObject to2 = new TreeObject("Leaf 2");
			TreeObject to3 = new TreeObject("Leaf 3");
			TreeParent p1 = new TreeParent("Parent 1");
			p1.addChild(to1);
			p1.addChild(to2);
			p1.addChild(to3);
			
			TreeObject to4 = new TreeObject("Leaf 4");
			TreeParent p2 = new TreeParent("Parent 2");
			p2.addChild(to4);
			
			TreeParent root = new TreeParent("Root");
			root.addChild(p1);
			root.addChild(p2);
			
			invisibleRoot = new TreeParent("");
			invisibleRoot.addChild(root);
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}
		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeParent)
			   imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return workbench.getSharedImages().getImage(imageKey);
		}
	}
	
	/////////////
//	public void update (Map<String,ArrayList<String>> problems) {
//		ViewContentProvider provider = (ViewContentProvider)viewer.getContentProvider();
//	     provider.update(problems);
//	     viewer.refresh();
//	}
	
	public void update (EPackage metamodel) {
		ViewContentProvider provider = (ViewContentProvider)viewer.getContentProvider();
	    provider.update(metamodel);
	    viewer.refresh();
	}
	////////////

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		
	viewer.setContentProvider(new ViewContentProvider());
	viewer.setInput(getViewSite());
	viewer.setLabelProvider(new ViewLabelProvider());

		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "Plugin_Validar.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ProblemsView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(workbench.getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Criterio Calidad",
			message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
