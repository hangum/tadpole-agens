//package com.bitnine.angens.manager.core.editors.parts;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.eclipse.jface.viewers.LabelProvider;
//import org.eclipse.jface.viewers.TableViewerColumn;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Composite;
//
//import com.bitnine.agens.manager.engine.core.AgensManagerSQLImpl;
//import com.bitnine.agens.manager.engine.core.dao.domain.Instance;
//import com.hangum.tadpole.engine.query.dao.system.UserDBDAO;
//
///**
// * database table composite
// * 
// * @author hangum
// *
// */
//public class RecoveryConflictsTableComposite extends AgensTableComposite {
//	private static final Logger logger = Logger.getLogger(RecoveryConflictsTableComposite.class);
//	
//	/**
//	 * Create the composite.
//	 * @param parent
//	 * @param title
//	 * @param userDB
//	 * @param instance
//	 */
//	public RecoveryConflictsTableComposite(Composite parent, UserDBDAO userDB, Instance instance, LabelProvider labelProvider) {
//		super(parent, "Recovery Conflicts", userDB, instance, labelProvider);
//	}
//	
//	/**
//	 * get cpu data
//	 * @return
//	 * @throws Exception
//	 */
//	public List<?> getUIData() throws Exception {
//		return AgensManagerSQLImpl.getDatabaseInfo(userDB, instance);
//	}
//	
//	/**
//	 * make columns
//	 */
//	public void createTableColumn() {
//		String[] columnName = {"Database", "conflict tables space", "conflict lock", "conflict snapshot", "conflict bufferprint", "conflict deadlock"};
//		int[] columnSize = {100, 80, 80, 80, 80, 80};
//		int[] align = {SWT.LEFT, SWT.RIGHT, SWT.RIGHT, SWT.RIGHT, SWT.RIGHT, SWT.RIGHT};
//		
//		for(int i=0; i<columnName.length; i++) {
//			final TableViewerColumn tableColumn = new TableViewerColumn(tableView, align[i]);
//			tableColumn.getColumn().setText(columnName[i]);
//			tableColumn.getColumn().setWidth(columnSize[i]);
//			tableColumn.getColumn().setAlignment(columnSize[i]);
//			tableColumn.getColumn().setResizable(true);
//			tableColumn.getColumn().setMoveable(false);
//		}
//	}
//}