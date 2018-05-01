package dataAccess;

import dataAccess.dao.WriterDAO;
import dataAccess.entity.Writer;
import org.junit.jupiter.api.Test;

public class WriterDAOTest {

    @Test
    public void findWriterTest() {
        WriterDAO dao = new WriterDAO();
        Writer actual = dao.findWriter("mino");
        System.out.println(actual);
    }
}
