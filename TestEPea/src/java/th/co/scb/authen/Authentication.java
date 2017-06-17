/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.authen;

import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import th.co.scb.service.ConfigFileLocationConfig;
import th.co.scb.util.Constants;

/**
 *
 * @author s62177
 */
public class Authentication {
    //    scbcorp.co.th

    public static String[] activedirectory(String user, String password) {
        try {
            Properties properties = new Properties();
            ConfigFileLocationConfig configFileLocationConfig = new ConfigFileLocationConfig();
            String path = configFileLocationConfig.getLocation() + Constants.ConfigFile.LDAP_PROPERTIES;
            properties.load(new FileInputStream(path));
            String domain = properties.getProperty("LDAP_HOST");
            String port = properties.getProperty("LDAP_PORT");
            System.out.println("LDAP_HOST : " + domain);
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://" + domain + ":" + port);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, user + "@" + domain);
            env.put(Context.SECURITY_CREDENTIALS, password);
            InitialDirContext ctx = new InitialDirContext(env);
            System.out.println("ctx: " + ctx);

            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(&(sAMAccountName=" + user + "))";
            String temp = domain.replace(".", ",DC=");
            String searchBase = "DC=" + temp;

            System.out.println("searchBase=" + searchBase);
            System.out.println("searchFilter=" + searchFilter);
            System.out.println("searchCtls=" + searchCtls);
            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);

            System.out.println("answer=" + answer);
            SearchResult sr = null;

            String username = null;
            String oc = null;
            String fullname = null;
            while (answer.hasMoreElements()) {
                System.out.println("answer.hasMoreElements()==" + answer.hasMoreElements());
                sr = (SearchResult) answer.next();
                javax.naming.directory.Attributes attrss = sr.getAttributes();
                NamingEnumeration attrs = attrss.getAll();
                Attribute attr = null;
                String value = null;

                while (attrs.hasMoreElements()) {
                    attr = (Attribute) attrs.nextElement();
                    if("scbcorp.co.th".equals(domain)){
                    	//production (scbcorp.co.th)
                    	oc = "";
                        if (attr.getID().trim().equalsIgnoreCase("employeeID")) {
                            value = (String) attr.get();
                            username = "S" + value.trim();
                        }
                        if (attr.getID().trim().equalsIgnoreCase("name")) {
                            value = (String) attr.get();
                            fullname = value.trim();
                        }
                    }else{
                    	//develop (se.scb.co.th)
                        if (attr.getID().trim().equalsIgnoreCase("description")) {
                            value = (String) attr.get();
                            int i = value.indexOf("[");
                            if (i == -1) {
                                i = value.length();
                            }
                            value = value.substring(0, i).trim();
                            oc = value;
                        }
                        if (attr.getID().trim().equalsIgnoreCase("name")) {
                            value = (String) attr.get();
                            username = value.trim();
                            fullname = username;
                        }
                    }
                }
            }
            if (username != null && oc != null) {
                String s[] = new String[4];
                s[0] = "000";
                s[1] = oc;
                s[2] = username;
                s[3] = fullname;
                return s;
            }
            String s[] = new String[1];
            s[0] = "???";
            return s;
        } catch (Exception ex) {
            String errAll = ex.getMessage();
            int startIndex = errAll.indexOf("data", 0) + 5;
            String errCode = errAll.substring(startIndex, startIndex + 3);
            String s[] = new String[1];
            s[0] = errCode;
            return s;
        }
    }
    
}
