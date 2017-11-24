package ar.edu.unrn.lia;

import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.service.BackupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ar/edu/unrn/lia/config/applicationContext-test.xml"})
public class UsuarioTest {

    private static Logger LOG = LoggerFactory.getLogger(UsuarioTest.class);

    @Autowired
    private BackupService backupService;

    private List<Cliente> clientes = new ArrayList<Cliente>();

    @Test
    public void testSave() {
        backupService.backup();
    }

}
