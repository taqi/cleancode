package com.keebraa.java.cleancode.core.reviewcreation.wizard.committable;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.keebraa.java.cleancode.core.exceptions.CommitTableBuilderException;
import com.keebraa.java.cleancode.core.extensionpoints.CommitRepository;
import com.keebraa.java.cleancode.core.model.Commit;

public class CommitTableBuilder
{
    private final String FOREIGNCOLUMN_TITLE = "foreign number (ID)";
    private final String COMMENT_TITLE = "comment";
    private Table commitTable;
    private CommitRepository repository;
    private IProject project;

    public CommitTableBuilder(CommitRepository repository, IProject project)
    {
	if(repository == null)
	{
	    throw new CommitTableBuilderException("CommitRepository can't be null");
	}
	this.project = project;
	this.repository = repository;
    }
    
    public void createTable(Composite parent)
    {
	commitTable = new Table(parent, SWT.CHECK | SWT.BORDER | SWT.MULTI);
	commitTable.setHeaderVisible(true);
	createCheckBoxColumn();	
	createForeignNumberColumn();
	createCommentColumn();
	fillTable();
    }

    public void setCommitRepository(CommitRepository repository)
    {
	this.repository = repository;
    }
    
    public void setTableSelectionListener(SelectionListener listener)
    {
	 getTable().addSelectionListener(listener);
    }
    
    public Table build()
    {
	return getTable();
    }
    
    private void createCheckBoxColumn()
    {
	TableColumn column = new TableColumn(getTable(), SWT.NONE);
	column.setText("");
	column.setWidth(20);
    }
    private void createForeignNumberColumn()
    {
	TableColumn column = new TableColumn(getTable(), SWT.NONE);
	column.setText(FOREIGNCOLUMN_TITLE);
	column.setWidth(200);
    }
    
    private void createCommentColumn()
    {
	TableColumn column = new TableColumn(getTable(), SWT.NONE);
	column.setText(COMMENT_TITLE);
	column.setWidth(300);
    }
    
    private Table getTable()
    {
	if (commitTable == null)
	{
	    throw new RuntimeException("CommitTableBuilder: table is null. Call createTable first.");
	}
	return commitTable;
    }

    private void fillTable()
    {
	for(Commit commit : repository.getAllCommits(project))
	{
	    TableItem item = new TableItem(getTable(), SWT.NONE);
	    item.setText(new String[]{"", commit.getForeignNumber(), commit.getDescription()});
	}
    }
}