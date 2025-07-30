
/**
 * Abstract generic controller for managing entities and their repositories.
 * <p>
 * This class provides common operations for saving and retrieving entities,
 * delegating data access to a repository. It enforces validation before saving
 * and handles error reporting.
 *
 * <p>Type Parameters:
 * <ul>
 *   <li><b>R</b> the type of repository, must extend Repository&lt;E&gt;</li>
 *   <li><b>E</b> the type of entity, must extend Entity</li>
 * </ul>
 *
 * Example usage:
 * <pre>
 * public class UserController extends Controller&lt;UserRepository, User&gt; { ... }
 * </pre>
 *
 * @author cerimice
 */
package org.upemor.pruebaswing04.controller;

import java.util.List;
import org.upemor.pruebaswing04.model.entity.Entity;
import org.upemor.pruebaswing04.model.repository.Repository;

import lombok.Getter;

/** @author cerimice **/

public abstract class Controller<R extends Repository<E>,E extends Entity>{

    /**
     * The repository instance used for data access.
     */
    @Getter 
    protected R repository;

    /**
     * Validates the given entity before saving.
     * Subclasses must implement this method to provide specific validation logic.
     *
     * @param obj the entity to validate
     * @return true if valid, false otherwise
     * @throws Exception if validation fails
     */
    protected abstract boolean validate(E obj) throws Exception;
    
    /**
     * Saves the given entity.
     * <p>
     * If the entity's ID is 0, it creates a new record; otherwise, it updates the existing record.
     * Validation is performed before saving.
     *
     * @param obj the entity to save
     * @return true if the operation was successful, false otherwise
     * @throws Exception if an error occurs during saving
     */
    public boolean save(E obj) throws Exception {
        try {
            if (validate(obj)) {
                if (obj.getId() == 0)
                    return repository.create(obj);
                return repository.update(obj);
            }
            return false;
        } catch (Exception ex) {
            System.out.println(
                "Error: " + ex.getMessage()
                + " in method: save()"
                + " in class: " + this.getClass().getName()
            );
            throw ex;
        }
    }
    
    /**
     * Retrieves all entities from the repository.
     *
     * @return a list of all entities
     * @throws Exception if an error occurs during retrieval
     */
    public List<E> getAll() throws Exception {
        try {
            return repository.readAll();
        } catch (Exception ex) {
            System.out.println(
                "Error: " + ex.getMessage()
                + " in method: getAll()"
                + " in class: " + this.getClass().getName()
            );
            throw ex;
        }
    }
    
}
