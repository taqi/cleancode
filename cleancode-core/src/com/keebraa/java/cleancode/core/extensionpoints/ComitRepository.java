package com.keebraa.java.cleancode.core.extensionpoints;

import java.util.List;

import org.eclipse.team.core.RepositoryProvider;

import com.keebraa.java.cleancode.core.model.Comit;

public interface ComitRepository
{
    /**
     * This method will be called during registration of your factory. If your
     * factory can provides ChangeSets (commits) from current Version Control
     * System - this method should return TRUE. FALSE - otherwise.
     * 
     * @param provider
     *            - current provider.
     * @return true if your extension can provides commits (changesets) for
     *         current VCS.
     */
    public boolean canHandle(RepositoryProvider provider);

    public List<Comit> getAllCommits();
    
    public String getRealizationName();
    
    /**
     * May return null, if current Comit is the first.
     * @param comit
     * @return
     */
    public Comit getBefore(Comit comit);
}
