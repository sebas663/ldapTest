package ldapTest;

import java.util.Date;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class Test {
	private static final String DOMAIN = "fleni.org.ar";

	private static final String URL = "fleni.org.ar";
//	private static final String URL = "172.16.1.2";
//	private static final String URL = "172.16.1.4";
//	private static final String URL = "172.16.1.7";
//	private static final String URL = "172.17.1.2";
//	private static final String URL = "172.17.1.1";
	
	private static final String DOMAIN_BASE = "DC=fleni,DC=org,DC=ar";
	private static final String[] RETURN_ATTRIBUTES = { "sAMAccountName", "givenName", "cn", "mail" };

	private static final String BASE_FILTER = "(&((&(objectCategory=Person)(objectClass=User)))";

	public static void main(String[] args) {
		int interval = 20000;
//		for (int i = 0; i < 100; i++) {
		while (true) {
			try {
				authenticateUsernamePasswordInternal("soportesidca","fleni#123");
				Thread.sleep(interval);
			} catch (AuthenticationException e) {
				System.out.println("AuthenticationException " + e.getMessage());
//				e.printStackTrace();
			} catch (InterruptedException e) {
				System.out.println("InterruptedException " + e.getMessage());
//				e.printStackTrace();
			}
		}

	}

	protected static void authenticateUsernamePasswordInternal(String username, String password)
			throws AuthenticationException {

		Properties properties = new Properties();

		properties.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
		properties.put("java.naming.provider.url", "LDAP://" + URL);
		properties.put("java.naming.security.principal", username + "@" + DOMAIN);
		properties.put("java.naming.security.credentials", password);

		DirContext dirContext = null;
		try {
			dirContext = new InitialDirContext(properties);

			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(2);
			searchCtls.setReturningAttributes(RETURN_ATTRIBUTES);

			NamingEnumeration<SearchResult> result = dirContext.search(DOMAIN_BASE,
					BASE_FILTER + "(samaccountname=" + username + "))", searchCtls);

			if (result.hasMore()) {
				SearchResult rs = (SearchResult) result.next();
				Attributes attrs = rs.getAttributes();
				String temp = attrs.get("cn").toString();
				System.out.println("cn --> " + temp +" hora del logueo --> "+ new Date());
			}else{
				System.out.println("NO RESULTS NO RESULTS NO RESULTS ---->  hora del logueo --> "+ new Date());
			}

		} catch (NamingException e) {
			System.out.println("NamingException " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
		} finally {
			try {
				if (dirContext != null)
					dirContext.close();
			} catch (NamingException e) {
				System.out.println("finally NamingException " + e.getMessage());
			}
		}
	}

}
