package facades;

import java.util.List;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Martin Frederiksen
 */
public interface IFacade <T> {
    public List<T> getAll();
    public T getById(long id) throws WebApplicationException;
    public T add(T t) throws WebApplicationException;
    public T delete(long id) throws WebApplicationException;
    public T edit(T t) throws WebApplicationException; 
}
