package facades;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApiFacadeTest {
    /**
     * Test of fetch method, of class ApiFacade.
     */
    @Test
    public void testFetch_String() {
        ApiFacade f = new ApiFacade();
        boolean data = false;
        boolean expected = true;
        String url = "https://www.swapi.co/api/people/1";
        if(f.fetch(url) != null)
            data = true;
        
        assertEquals(expected, data);
    }
    
    @Test
    public void testFetch_String_String() {
        ApiFacade f = new ApiFacade();
        boolean data = false;
        boolean expected = true;
        String url = "https://www.swapi.co/api/";
        String select = "planets/1";
        if(f.fetch(url, select) != null)
            data = true;
        
        assertEquals(expected, data);
    }

    
    
}
