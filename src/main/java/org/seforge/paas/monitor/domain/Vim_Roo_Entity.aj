// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.seforge.paas.monitor.domain;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.seforge.paas.monitor.domain.Vim;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Vim_Roo_Entity {
    
    declare @type: Vim: @Entity;
    
    @PersistenceContext
    transient EntityManager Vim.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Vim.id;
    
    @Version
    @Column(name = "version")
    private Integer Vim.version;
    
    public Long Vim.getId() {
        return this.id;
    }
    
    public void Vim.setId(Long id) {
        this.id = id;
    }
    
    public Integer Vim.getVersion() {
        return this.version;
    }
    
    public void Vim.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Vim.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Vim.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Vim attached = Vim.findVim(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Vim.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Vim.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Vim Vim.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Vim merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Vim.entityManager() {
        EntityManager em = new Vim().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Vim.countVims() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Vim o", Long.class).getSingleResult();
    }
    
    public static List<Vim> Vim.findAllVims() {
        return entityManager().createQuery("SELECT o FROM Vim o", Vim.class).getResultList();
    }
    
    public static Vim Vim.findVim(Long id) {
        if (id == null) return null;
        return entityManager().find(Vim.class, id);
    }
    
    public static List<Vim> Vim.findVimEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Vim o", Vim.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}