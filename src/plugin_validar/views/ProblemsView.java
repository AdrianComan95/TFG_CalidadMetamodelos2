package plugin_validar.views;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import BestPractices.*;
import Design.*;
import Interfaces.ICriterion;
import Interfaces.IQuickfix;
import Metrics.M01;
import Metrics.M02;
import Metrics.M03;
import Metrics.M04;
import Metrics.M05;
import Interfaces.ICriterion.ProblemType;
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

public class ProblemsView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "plugin_validar.views.ProblemsView";

	@Inject IWorkbench workbench;
	
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private EPackage metamodel;
	 
	class TreeObject implements IAdaptable {
		private String name;
		private Problem problem = null;
		private TreeParent parent;
		
		public TreeObject(Problem problem) {
			this.problem = problem;
		}
		
		public TreeObject(String name) {
			this.name = name;
		}
		public String getName() {
			if (problem != null)
				return problem.getDescription();
			else 
				return name;
		}
		public Problem getProblem() {
			return problem;
		}
		public List<IQuickfix> getFixes() {
			if(problem != null) {
				return problem.getQuickfixes();
			}
			return null;
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
		public TreeParent(Problem problem) {
			super(problem);
			children = new ArrayList();
		}
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
			//COMPROBAMOS SI EXISTE EL FICHERO DE CONFIGURACIÓN Y 
			//EN CASO CONTRARIO LO CREAMOS CON CON LOS VALORES POR DEFECTO
			FileReader fr = null;
			String directory = System.getProperty("user.dir");
			File file = new File(directory + "/conf.txt");
			//Variables de configuración por defecto
			int confN05 = 10, confM01 = 10, confM02 = 5, confM03 = 5, confM04 = 5, confM05 = 10;
			if(file.exists()) {
				System.out.println("Fichero de configuración encontrado en " + directory + "/conf.txt");
				try {
			         // Apertura del fichero y creacion de BufferedReader para poder
			         // hacer una lectura comoda (disponer del metodo readLine()).
			         
			         fr = new FileReader (file);
			         BufferedReader br = new BufferedReader(fr);

			         // Lectura del fichero
			         String line;
			         int index = 0;
			         while((line=br.readLine())!=null) {
			        	 String dataConf = line.substring(0, line.indexOf(' '));
			        	 if (index == 0)
			        		 confN05 = Integer.parseInt(dataConf);
			        	 if (index == 1)
			        		 confM01 = Integer.parseInt(dataConf);
			        	 if (index == 2)
			        		 confM02 = Integer.parseInt(dataConf);
			        	 if (index == 3)
			        		 confM03 = Integer.parseInt(dataConf);
			        	 if (index == 4)
			        		 confM04 = Integer.parseInt(dataConf);
			        	 if (index == 5)
			        		 confM05 = Integer.parseInt(dataConf);
			        	 index ++;
			         }
			            
			      }
			      catch(Exception e){
			         e.printStackTrace();
			      }finally{
			         // En el finally cerramos el fichero, para asegurarnos
			         // que se cierra tanto si todo va bien como si salta 
			         // una excepcion.
			         try{                    
			            if( null != fr ){   
			               fr.close();     
			            }                  
			         }catch (Exception e2){ 
			            e2.printStackTrace();
			         }
			      }
			}
			else {
				String[] lines = { 
						"10 /N05. Nº Maximo de caracteres para los nombres de los elementos",
						"10 /M01. Nº Maximo de atributos de una clase",
						"5 /M02. Nº Maximo de referencias de una clase",
						"5 /M03. Nº Maximo de veces que una clase es referenciada",
						"5 /M04. Nº maximo de niveles de profundidad de una jerarquia",
						"10 /M05. Nº numero maximo de hijos directos" 
						};

				/** ESCRITURA **/
				FileWriter fileWrite = null;
				try {
					
					fileWrite = new FileWriter(directory + "/conf.txt");

					// Escribimos linea a linea en el fichero
					for (String line : lines) {
						fileWrite.write(line + "\n");
					}
					
					System.out.println("Fichero de configuración creado en " + directory + "/conf.txt");

					fileWrite.close();

				} catch (Exception ex) {
					System.out.println("Exception: " + ex.getMessage());
				}
			}
			
			
			TreeParent root = new TreeParent("Criterios de calidad no superados");
			
			//---CRITERIOS
			
			List<ICriterion> problems = List.of(new D03(metamodel), new BP01(metamodel),
					new BP02(metamodel), new LowerClass(metamodel), new N01(metamodel),
					new N02(metamodel), new D01(metamodel), new BP01(metamodel), new BP05(metamodel),
					new D04(metamodel), new D05(metamodel), new D06(metamodel), new D07(metamodel),
					new D08(metamodel), new M01(metamodel, confM01), new M02(metamodel, confM02),
					new M03(metamodel, confM03), new M04(metamodel, confM04), new M05(metamodel, confM05),
					new N05(metamodel, confN05)
					);
			TreeParent createParentDesign = null;
			TreeParent createParentBestPractice = null;
			TreeParent createParentNamingConvection = null;
			TreeParent createParentMetric = null;
			for (ICriterion p : problems) {
				List<Problem>  detectedProblems = p.check();
				if (p.getProblemType() == ProblemType.DESIGN && detectedProblems.size() > 0) {
					if (createParentDesign == null) {
						createParentDesign = new TreeParent("Diseño");
						root.addChild(createParentDesign);
					}
					TreeParent child = new TreeParent(p.getTitle());
					for (Problem problem : detectedProblems) {
						TreeObject problemTreeObject = new TreeObject(problem);
						child.addChild(problemTreeObject);
					}
					createParentDesign.addChild(child);	
				}
				if (p.getProblemType() == ProblemType.BEST_PRACTICE && detectedProblems.size() > 0) {
					if (createParentBestPractice == null) {
						createParentBestPractice = new TreeParent("Buenas Practicas");
						root.addChild(createParentBestPractice);
					}
					TreeParent child = new TreeParent(p.getTitle());
					for (Problem problem : detectedProblems) {
						TreeObject problemTreeObject = new TreeObject(problem);
						child.addChild(problemTreeObject);
					}
					createParentBestPractice.addChild(child);
				}
				if (p.getProblemType() == ProblemType.NAMING_CONVENTION && detectedProblems.size() > 0) {
					if (createParentNamingConvection == null) {
						createParentNamingConvection = new TreeParent("Convención de nombres");
						root.addChild(createParentNamingConvection);
					}
					TreeParent child = new TreeParent(p.getTitle());
					for (Problem problem : detectedProblems) {
						TreeObject problemTreeObject = new TreeObject(problem);
						child.addChild(problemTreeObject);
					}
					createParentNamingConvection.addChild(child);
				}
				if (p.getProblemType() == ProblemType.METRIC && detectedProblems.size() > 0) {
					if (createParentMetric == null) {
						createParentMetric = new TreeParent("Metrica");
						root.addChild(createParentMetric);
					}
					TreeParent child = new TreeParent(p.getTitle());
					for (Problem problem : detectedProblems) {
						TreeObject problemTreeObject = new TreeObject(problem);
						child.addChild(problemTreeObject);
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
			TreeObject to1 = new TreeObject("No tiene ningun metamodelo seleccionado");
			TreeObject to2 = new TreeObject("Click derecho sobre un metamodelo y seleccione 'Validar Metadelo'");
			
			invisibleRoot = new TreeParent("");
			invisibleRoot.addChild(to1);
			invisibleRoot.addChild(to2);
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
		this.metamodel = metamodel;
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
		contributeToActionBars();
	}

	/*private void hookContextMenu() {
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
	}*/
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
			      ISelection selection = viewer.getSelection();
			      if (selection instanceof TreeSelection &&
			        ((TreeSelection)selection).getFirstElement() instanceof TreeObject) {
			          TreeObject obj = (TreeObject)((TreeSelection)selection).getFirstElement();
			          List<IQuickfix> quickfixes = obj.getFixes();
			          if (quickfixes != null) {
			        	  for (IQuickfix quickfix : quickfixes) {
			        		  action1 = new Action() {
			        				public void run() {
			        					quickfix.execute();
			        					update(metamodel);
			        				}
			        			};
			        			action1.setText(quickfix.getDescription());
			        			//action1.setToolTipText(quickfix.getDescription());
			        			action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			        				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
			        			manager.add(action1);
			        			
			        	  }
			          }
			      }
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
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				if (metamodel != null) {
					update(metamodel);
				}
				
			}
		};
		action1.setText("Refresh");
		action1.setToolTipText("Refresh");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
		
		/*action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(workbench.getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};*/
	}

	/*private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}*/
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Criterios de Calidad EMF",
			message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
