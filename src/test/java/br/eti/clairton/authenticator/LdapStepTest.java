package br.eti.clairton.authenticator;

import static org.junit.Assert.fail;

import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.ldap.InitialLdapContext;

import org.junit.Test;

import com.github.trevershick.test.ldap.LdapServerResource;
import com.github.trevershick.test.ldap.annotations.LdapConfiguration;
import com.github.trevershick.test.ldap.annotations.LdapEntry;
import com.github.trevershick.test.ldap.annotations.Ldif;

/**
 * Teste para {@link LdapStep}.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.heinzen@cresolsicoper.com.br>
 */
@LdapConfiguration(ldifs = @Ldif("/ldap-test.ldif"), base = @LdapEntry(dn = "dc=br", objectclass = {
    "top", "domain"
}))
public class LdapStepTest {
    @Test
    public void iniciar() throws Exception {
        final LdapServerResource server = new LdapServerResource(this).start();
        final Properties config = new Properties();
        config.put(Context.SECURITY_PRINCIPAL, "cn=Fulano de tal,dc=sicoper,dc=com,dc=br");
        config.put(Context.SECURITY_CREDENTIALS, "teste");
        try {
            new InitialLdapContext(config, null);
        } catch (InvalidNameException | AuthenticationException e) {
            fail(e.getMessage());
        } finally {
            if (server != null) {
                server.stop();
            }
        }
    }
}
