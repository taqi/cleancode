package com.keebraa.java.cleancode.core;

import org.eclipse.core.resources.IProject;

import com.keebraa.java.cleancode.core.exceptionhandling.ExceptionHandlingTool;
import com.keebraa.java.cleancode.core.exceptions.CommitRepositoryFactoryNotFoundException;
import com.keebraa.java.cleancode.core.exceptions.CodeReviewCreationException;
import com.keebraa.java.cleancode.core.exceptions.CodeReviewSavingException;
import com.keebraa.java.cleancode.core.extensionpoints.ComitRepository;
import com.keebraa.java.cleancode.core.extensionpoints.ComitRepositoryProvider;
import com.keebraa.java.cleancode.core.model.CodeReview;
import com.keebraa.java.cleancode.core.reviewcreation.wizard.ReviewCreationController;
import com.keebraa.java.cleancode.core.storage.CodeReviewStorage;
import com.keebraa.java.cleancode.core.storage.CodeReviewStorageFactory;

/**
 * Main class of whole project. Will create reviews, etc.
 * 
 * @author taqi
 * 
 */
public class CleanCodeEngine
{
   /**
    * Only one reason for me to create two different methods "createCodeReview"
    * and "createCodeReviewForProject" - I want to handle exception out of my
    * working method. Thats why I made this method - to call useful code and
    * control exceptions.
    * 
    * @param project
    */
   public static void createCodeReview(IProject project)
   {
	try
	{
	   CodeReview codeReview = createCodeReviewForProject(project);
	   storeCodeReview(codeReview);
	}
	catch (Throwable exception)
	{
	   ExceptionHandlingTool.getInstance().handleException(exception);
	}
   }

   private static CodeReview createCodeReviewForProject(IProject project) throws CommitRepositoryFactoryNotFoundException,
	   CodeReviewCreationException
   {
	ComitRepository commitRepository = ComitRepositoryProvider.getCommitRepository(project);
	ReviewCreationController wizardController = new ReviewCreationController(project, commitRepository);
	CodeReview review = wizardController.performReviewCreationWizard();
	if (review == null)
	{
	   throw new CodeReviewCreationException();
	}
	return review;
   }

   private static void storeCodeReview(CodeReview codeReview) throws CodeReviewSavingException
   {
	CodeReviewStorage storage = CodeReviewStorageFactory.getCodeReviewStorage();
	storage.storeCodeReview(codeReview);
   }
}
