package com.actional.lg.interceptor.sdk.openedge.util;

import java.net.MalformedURLException;
import java.util.StringTokenizer;

public class PscURLParser {
	//private static final String m_defHost = "localhost";
	//private static final String m_defPath = "/";
	private static final String[] m_validSchemes = { "AppServer",
			"AppServerDC", "http", "https", "AppServerS", "AppServerDCS" };

	public static final String[] m_defPorts = { "5162", "3090", "80", "443",
			"5162", "3090" };

	private String m_scheme = m_validSchemes[0];
	private String m_authority = "localhost";
	private String m_authorityUser = null;
	private String m_authorityPwd = "";
	private String m_authorityHost = "localhost";
	private String m_authorityPort = null;
	private String m_path = "/";
	private String m_query = null;
	private String m_fragment = null;

	public PscURLParser() {
	}

	public PscURLParser(String paramString) throws MalformedURLException {
		if ((null != paramString) && (0 < paramString.length())) {
			parseURL(paramString);
		}
	}

	public String getScheme() {
		return null != this.m_scheme ? new String(this.m_scheme) : null;
	}

	public String getAuthority() {
		StringBuffer localStringBuffer = new StringBuffer(512);

		if (null != this.m_authorityUser) {
			localStringBuffer.append(this.m_authorityUser);
			if (null != this.m_authorityPwd) {
				localStringBuffer.append(":");
				localStringBuffer.append(this.m_authorityPwd);
			}
			localStringBuffer.append("@");
		}
		if (null != this.m_authorityHost) {
			localStringBuffer.append(this.m_authorityHost);
			if (null != this.m_authorityPort) {
				localStringBuffer.append(":");
				localStringBuffer.append(this.m_authorityPort);
			}
		}
		return 0 < localStringBuffer.length() ? new String(
				localStringBuffer.toString()) : null;
	}

	public String getPath() {
		return new String(this.m_path);
	}

	public String getQuery() {
		return null == this.m_query ? null : new String(this.m_query);
	}

	public String getFragment() {
		return null == this.m_fragment ? null : new String(this.m_fragment);
	}

	public String getUserId() {
		return null == this.m_authorityUser ? null : new String(
				this.m_authorityUser);
	}

	public String getUserPassword() {
		return null == this.m_authorityPwd ? null : new String(
				this.m_authorityPwd);
	}

	public String getHost() {
		return new String(this.m_authorityHost);
	}

	public int getPort() {
		if (null == this.m_authorityPort) {
			return Integer.parseInt(getDefaultPort(this.m_scheme));
		}

		return Integer.parseInt(this.m_authorityPort);
	}

	public void setScheme(String paramString) {
		if ((null != paramString) && (0 < paramString.length())) {
			this.m_scheme = paramString;
		} else {
			this.m_scheme = m_validSchemes[0];
		}
	}

	public String getService() {
		String str1 = null;

		if ((this.m_scheme.equalsIgnoreCase(m_validSchemes[0]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[1]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[4]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[5]))) {
			if (1 < this.m_path.length()) {
				str1 = this.m_path.substring(1);
			}

		} else if ((this.m_scheme.equalsIgnoreCase(m_validSchemes[2]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[3]))) {
			if (null != this.m_query) {
				StringTokenizer localStringTokenizer = new StringTokenizer(
						this.m_query, "&");
				while (localStringTokenizer.hasMoreTokens()) {
					String str2 = localStringTokenizer.nextToken();
					if (str2.startsWith("AppService=")) {
						str1 = str2.substring(str2.indexOf("=") + 1);
						break;
					}
				}
			}
		}

		return str1;
	}

	public void setPath(String paramString) {
		if ((null != paramString) && (0 < paramString.length())) {
			if (paramString.startsWith("/")) {
				this.m_path = paramString;
			} else {
				this.m_path = ("/" + paramString);
			}
		} else {
			this.m_path = "/";
		}
	}

	public void setQuery(String paramString) {
		if ((null != paramString) && (0 < paramString.length())) {
			if (paramString.startsWith("?")) {
				if (1 < paramString.length()) {
					this.m_query = paramString.substring(1);
				} else {
					this.m_query = null;
				}
			} else {
				this.m_query = paramString;
			}
		} else {
			this.m_query = null;
		}
	}

	public void setFragment(String paramString) {
		if ((null != paramString) && (0 < paramString.length())) {
			if (paramString.startsWith("#")) {
				if (1 < paramString.length()) {
					this.m_fragment = paramString.substring(1);
				} else {
					this.m_fragment = null;
				}
			} else {
				this.m_fragment = paramString;
			}
		} else {
			this.m_fragment = null;
		}
	}

	public void setUserId(String paramString) {
		if ((null != paramString) && (0 < paramString.length())) {
			this.m_authorityUser = paramString;
		} else {
			this.m_authorityUser = null;
			this.m_authorityPwd = null;
		}
	}

	public void setUserPassword(String paramString) {
		if ((null != paramString) && (0 < paramString.length())) {
			if ((null != this.m_authorityUser)
					&& (0 < this.m_authorityUser.length())) {
				this.m_authorityPwd = paramString;
			}

		} else if ((null != this.m_authorityUser)
				&& (0 < this.m_authorityUser.length())) {
			this.m_authorityPwd = "";
		} else {
			this.m_authorityPwd = null;
		}
	}

	public void setHost(String paramString) {
		if ((null != paramString) && (0 < paramString.length())) {
			this.m_authorityHost = paramString;
		} else {
			this.m_authorityHost = "localhost";
		}
	}

	public void setPort(int paramInt) {
		int i = paramInt;
		if (-1 == i) {
			this.m_authorityPort = null;
		} else {
			if (3 > i) {
				i = 3;
			}
			if (65536 < i) {
				i = 65536;
			}
			this.m_authorityPort = Integer.toString(paramInt);
		}
	}

	public void setPort(String paramString) {
		if ((null == paramString) || (0 == paramString.length())) {
			this.m_authorityPort = null;
		} else {
			try {
				int i = Integer.parseInt(paramString);
				this.m_authorityPort = Integer.toString(i);
			} catch (Exception localException) {
			}
		}
	}

	public void setService(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();

		if ((this.m_scheme.equalsIgnoreCase(m_validSchemes[0]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[1]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[4]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[5]))) {
			this.m_path = "/";
			if ((null != paramString) && (0 < paramString.length())) {
				localStringBuffer.append("/");
				localStringBuffer.append(paramString);
				this.m_path = localStringBuffer.toString();
			}

		} else if ((this.m_scheme.equalsIgnoreCase(m_validSchemes[2]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[3]))) {
			if (null != this.m_query) {
				this.m_query = null;
			}
			if ((null != paramString) && (0 < paramString.length())) {
				localStringBuffer.append("AppService=");
				localStringBuffer.append(paramString);
				this.m_query = localStringBuffer.toString();
			}
		}
	}

	public void parseURL(String paramString) throws MalformedURLException {
		int i = 0;
		int j = 0;
		int k = -1;
		int m = paramString.length();
		int n = 0;
		int i1 = m - 1;

		init();

		if (0 < m) {
			j = paramString.indexOf(":", i);
			k = paramString.indexOf("/", i);
			k = -1 == k ? m : k;
			if ((-1 != j) && (j < k) && (j > i)) {
				this.m_scheme = paramString.substring(i, j);
				n = j + 1;
			}

			if (n <= i1) {
				i = paramString.indexOf("//", n);
				if (-1 != i) {
					i += 2;

					j = paramString.indexOf("/", i);
					if (-1 == j) {
						j = paramString.indexOf("?", i);
					}
					if (-1 == j) {
						j = paramString.indexOf("#", i);
					}

					if (-1 != j) {
						if (j > i) {
							this.m_authority = paramString.substring(i, j);
						}
						n = j;
					} else if (0 < i1 - i) {
						this.m_authority = paramString.substring(i);
						n = i1 + 1;
					} else {
						n = i;
					}

					if ((null != this.m_authority)
							&& (0 < this.m_authority.length())) {
						parseAuthority();
					}
				}
			}

			if (n <= i1) {
				i = paramString.indexOf("/", n);
				if (-1 != i) {
					j = paramString.indexOf("?", i);
					if (-1 == j) {
						j = paramString.indexOf("#", i);
					}

					if (-1 != j) {
						if (j > i) {
							this.m_path = paramString.substring(i, j);
						}
						n = j;
					} else if (0 < i1 - i) {
						this.m_path = paramString.substring(i);
						n = i1 + 1;
					} else {
						n = i + 1;
					}
				}
			}

			if (n <= i1) {
				i = paramString.indexOf("?", n);
				if (-1 != i) {
					i++;

					j = paramString.indexOf("#", i);

					if (-1 != j) {
						if (j > i) {
							this.m_query = paramString.substring(i, j);
							n = j;
						}
						n = j;
					} else if (0 < i1 - i) {
						this.m_query = paramString.substring(i);
						n = i1 + 1;
					} else {
						n = i + 1;
					}
				}
			}

			if (n < i1) {
				if ((0 == this.m_path.compareTo("/")) && (null == this.m_query)) {
					this.m_path += paramString.substring(n);
				} else {
					this.m_fragment = paramString.substring(++n);
				}
			}
		}
	}

	public String toString() {
		StringBuffer localStringBuffer = new StringBuffer(512);

		localStringBuffer.append(this.m_scheme);
		localStringBuffer.append("://");
		if (null != this.m_authorityUser) {
			localStringBuffer.append(this.m_authorityUser);
			if (null != this.m_authorityPwd) {
				localStringBuffer.append(":");
				localStringBuffer.append(this.m_authorityPwd);
			}
			localStringBuffer.append("@");
		}
		if (null != this.m_authorityHost) {
			localStringBuffer.append(this.m_authorityHost);
			if (null != this.m_authorityPort) {
				localStringBuffer.append(":");
				localStringBuffer.append(this.m_authorityPort);
			} else if ((this.m_scheme.equalsIgnoreCase(m_validSchemes[0]))
					|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[1]))
					|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[4]))
					|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[5]))) {
				localStringBuffer.append(":");
				localStringBuffer.append(getDefaultPort(this.m_scheme));
			}
		}

		localStringBuffer.append(this.m_path);
		if (null != this.m_query) {
			localStringBuffer.append("?");
			localStringBuffer.append(this.m_query);
		}
		if (null != this.m_fragment) {
			localStringBuffer.append("#");
			localStringBuffer.append(this.m_fragment);
		}
		return new String(localStringBuffer.toString());
	}

	public String getURL() throws MalformedURLException {
		//Object localObject = null;

		int i = 0;
		for (int j = 0; j < m_validSchemes.length; j++) {
			if (this.m_scheme.equalsIgnoreCase(m_validSchemes[j])) {
				i = 1;
			}
		}
		if (i == 0) {
			throw new MalformedURLException("URL' scheme is not supported: "
					+ this.m_authorityPort);
		}

		int j = -1;
		try {
			if (null != this.m_authorityPort) {
				j = Integer.parseInt(this.m_authorityPort);
			} else {
				j = Integer.parseInt(getDefaultPort(this.m_scheme));
			}
		} catch (Exception localException) {
			throw new MalformedURLException("URL' port number is invalid: "
					+ this.m_authorityPort);
		}

		if ((2 > j) || (65536 < j)) {
			throw new MalformedURLException("URL' port number is invalid: "
					+ this.m_authorityPort);
		}

		return toString();
	}

	public String getAppServerURL(String paramString)
			throws MalformedURLException {
		if ((this.m_scheme.equalsIgnoreCase(m_validSchemes[0]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[4]))) {
			if (0 == this.m_path.compareTo("/")) {
				if ((null != paramString) && (0 < paramString.length())) {
					this.m_path += paramString;
				} else {
					throw new MalformedURLException("Missing service name");
				}

			}

			if (-1 != this.m_path.indexOf("/", 1)) {
				throw new MalformedURLException(
						"The URL's service name includes a sub-path");
			}

		} else if ((this.m_scheme.equalsIgnoreCase(m_validSchemes[2]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[3]))) {
			String str = getService();
			if (null == str) {
				setService(null);
			} else {
				setService(str);
			}
			if (null == this.m_query) {
				if ((null != paramString) && (0 < paramString.length())) {
					if (-1 != paramString.indexOf("/", 1)) {
						throw new MalformedURLException(
								"The URL's service name includes a sub-path");
					}
					this.m_query = ("AppService=" + paramString);
				} else {
					throw new MalformedURLException("Missing service name");
				}

			}

			int i = -1;
			try {
				if (null != this.m_authorityPort) {
					i = Integer.parseInt(this.m_authorityPort);
				} else {
					i = Integer.parseInt(getDefaultPort(this.m_scheme));
				}
			} catch (Exception localException) {
				throw new MalformedURLException("URL' port number is invalid: "
						+ this.m_authorityPort);
			}

			if ((2 > i) || (65536 < i)) {
				throw new MalformedURLException("URL' port number is invalid: "
						+ this.m_authorityPort);
			}

			if (0 == this.m_path.compareTo("/")) {
				throw new MalformedURLException(
						"The URL is missing the AIA adapter path");
			}

		} else if ((!this.m_scheme.equalsIgnoreCase(m_validSchemes[1]))
				&& (!this.m_scheme.equalsIgnoreCase(m_validSchemes[5]))) {
			throw new MalformedURLException("Unsupported network scheme: "
					+ this.m_scheme);
		}

		return toString();
	}

	public String getDefaultPort(String paramString) {
		String str = "";
		if ((null != paramString) && (0 < paramString.length())) {
			for (int i = 0; i < m_validSchemes.length; i++) {
				if (paramString.equalsIgnoreCase(m_validSchemes[i])) {
					str = m_defPorts[i];
					break;
				}
			}
		}
		return str;
	}

	public void run(String[] paramArrayOfString) throws Exception {
		if (1 > paramArrayOfString.length) {
			System.out.println("usage: PscURLParser <url> [def_service]");
		} else {
			parseURL(paramArrayOfString[0]);

			System.out.println("Results:");
			System.out.println("    scheme:    " + getScheme());
			System.out.println("    authority: " + getAuthority());
			if (null != this.m_authority) {
				if (null != this.m_authorityUser) {
					System.out.println("        user info:     ");
					System.out.println("            uid: " + getUserId());
					System.out.println("            pwd: " + getUserPassword());
				}
				if (null != this.m_authorityHost) {
					System.out.println("        net addr: ");
					System.out.println("            host: " + getHost());
					System.out.println("            port: "
							+ Integer.toString(getPort()));
				}
			}
			System.out.println("    path:      " + getPath());
			System.out.println("    query:     " + getQuery());
			System.out.println("    fragment:  " + getFragment());

			System.out.println("toString(): " + toString());
			System.out.println("getURL(): " + getURL());
			System.out
					.println("Resulting AppServer URL: "
							+ getAppServerURL(1 < paramArrayOfString.length ? paramArrayOfString[1]
									: null));
			System.out.println("Service: " + getService());
			setService(null);
			System.out.println("Remove Service: " + getURL());
			setService("newService");
			System.out.println("set Service: " + getURL());
		}
	}

	public static void main(String[] paramArrayOfString) {
		PscURLParser localPscURLParser = new PscURLParser();
		try {
			try {
				localPscURLParser.run(paramArrayOfString);
			} catch (Exception localException1) {
				localException1.printStackTrace();
			}
		} catch (Exception localException2) {
			System.out.println("Error: " + localException2.toString());
		}
	}

	protected void init() {
		this.m_scheme = m_validSchemes[0];
		this.m_authority = "localhost";
		this.m_authorityUser = null;
		this.m_authorityPwd = "";
		this.m_authorityHost = "localhost";
		this.m_authorityPort = null;
		this.m_path = "/";
		this.m_query = null;
		this.m_fragment = null;
	}

	protected void parseAuthority() throws MalformedURLException {
		int i = this.m_authority.indexOf("@");
		String str1 = null;
		String str2 = null;

		if (-1 != i) {
			str1 = this.m_authority.substring(0, i);
			str2 = this.m_authority.substring(++i);
		} else {
			str2 = this.m_authority;
		}

		if (null != str1) {
			i = str1.lastIndexOf(":");
			if (-1 != i) {
				this.m_authorityUser = str1.substring(0, i);
				this.m_authorityPwd = str1.substring(++i);
			} else {
				this.m_authorityUser = str1;
			}
		}

		if (null != str2) {
			if (str2.charAt(0) == '[') {
				int j = str2.indexOf("]");
				if (j == -1) {
					throw new MalformedURLException(
							"Invalid IPv6 literal address: " + str2);
				}
				this.m_authorityHost = str2.substring(1, j++);
				if (str2.length() > j) {
					if (str2.charAt(j) != ':') {
						throw new MalformedURLException(
								"Invalid IPv6 literal address: " + str2);
					}
					j++;
					this.m_authorityPort = parseAuthorityPort(str2.substring(j));
				}

			} else if ((i = str2.lastIndexOf(":")) != -1) {
				this.m_authorityHost = str2.substring(0, i);
				this.m_authorityPort = parseAuthorityPort(str2.substring(++i));
			} else {
				this.m_authorityHost = str2;
			}
		}
	}

	private String parseAuthorityPort(String paramString)
			throws MalformedURLException {
		String str = paramString;

		if (0 == str.compareTo(getDefaultPort(this.m_scheme))) {
			str = null;
		} else {
			int i = 0;
			try {
				i = Integer.parseInt(str);
			} catch (Exception localException) {
				throw new MalformedURLException("Invalid numeric port number: "
						+ this.m_authorityPort);
			}

			if ((2 >= i) || (65536 < i)) {
				throw new MalformedURLException("Invalid numeric port number: "
						+ this.m_authorityPort);
			}
		}

		return str;
	}

	public boolean isDirectConnect() {
		if ((this.m_scheme.equalsIgnoreCase(m_validSchemes[1]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[2]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[3]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[5]))) {
			return true;
		}

		return false;
	}

	public boolean isSSLConnect() {
		if ((this.m_scheme.equalsIgnoreCase(m_validSchemes[4]))
				|| (this.m_scheme.equalsIgnoreCase(m_validSchemes[5]))) {
			return true;
		}

		return false;
	}

}
