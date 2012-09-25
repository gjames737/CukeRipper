package com.cukeripper.plugin.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

public class TestView extends ViewPart {
	private Text txtRootFile;

	public TestView() {
	}

	@Override
	public void createPartControl(Composite parent) {

		Group group_file_path = new Group(parent, SWT.NONE);

		Group group_outline = new Group(parent, SWT.NONE);
		GroupLayout gl_parent = new GroupLayout(parent);
		gl_parent.setHorizontalGroup(gl_parent.createParallelGroup(
				GroupLayout.LEADING).add(
				GroupLayout.TRAILING,
				gl_parent
						.createSequentialGroup()
						.addContainerGap()
						.add(gl_parent
								.createParallelGroup(GroupLayout.TRAILING)
								.add(GroupLayout.LEADING, group_outline,
										GroupLayout.DEFAULT_SIZE, 292,
										Short.MAX_VALUE)
								.add(GroupLayout.LEADING, group_file_path,
										GroupLayout.DEFAULT_SIZE, 292,
										Short.MAX_VALUE)).addContainerGap()));
		gl_parent.setVerticalGroup(gl_parent.createParallelGroup(
				GroupLayout.LEADING).add(
				gl_parent
						.createSequentialGroup()
						.addContainerGap()
						.add(group_file_path, GroupLayout.PREFERRED_SIZE, 48,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(group_outline, GroupLayout.DEFAULT_SIZE, 387,
								Short.MAX_VALUE).addContainerGap()));

		TreeViewer treeViewer = new TreeViewer(group_outline, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		tree.setBounds(10, 10, 272, 367);

		txtRootFile = new Text(group_file_path, SWT.BORDER);
		txtRootFile.setBounds(10, 17, 191, 21);

		Button btnRefresh = new Button(group_file_path, SWT.NONE);
		btnRefresh.setBounds(207, 13, 75, 25);
		btnRefresh.setText("Refresh");
		parent.setLayout(gl_parent);
		// TODO Auto-generated method stub

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
