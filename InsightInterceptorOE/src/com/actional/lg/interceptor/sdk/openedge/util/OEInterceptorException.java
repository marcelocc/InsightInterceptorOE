package com.actional.lg.interceptor.sdk.openedge.util;

import java.io.Serializable;

public class OEInterceptorException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;

	public OEInterceptorException() {
	}

	public OEInterceptorException(String msg) {
		super(msg);
	}

	public OEInterceptorException(Throwable e) {
		super(e);
	}

	public OEInterceptorException(String msg, Throwable e) {
		super(msg, e);
	}

	public OEInterceptorException(String msg, Throwable e, boolean arg2,
			boolean arg3) {
		super(msg, e, arg2, arg3);
	}

}
