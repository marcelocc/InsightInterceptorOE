package com.actional.lg.interceptor.sdk.openedge;

import java.io.Serializable;

import com.actional.lg.interceptor.sdk.ClientInteraction;
import com.actional.lg.interceptor.sdk.ServerInteraction;
import com.actional.lg.interceptor.sdk.helpers.InterHelpBase;
import com.actional.lg.interceptor.sdk.openedge.util.PscURLParser;

public class OEInterceptor implements IActionalInterceptor, Serializable {
	protected String m_lgGroup = null;
	protected String m_lgService = null;
	protected String m_lgOperation = null;
	protected String m_lgURL = null;
	protected String m_lgPeer = null;
	protected String m_appService = null;

	protected boolean m_isAnalized = false;
	protected static final long serialVersionUID = -966498172848195846L;
	public static final String INTERCEPTOR_TYPE = "OpenEdge ESB Interceptor";
	public static final String INTERCEPTOR_GROUP = "OpenEdge";

	public OEInterceptor() {
		this.m_lgGroup = "OpenEdge";
	}

	public OEInterceptor(String paramString) {
		this.m_lgService = paramString;
	}
	
	
	@Override
	public void beginInteraction(String url, String group, String service,
			String operation) {
		Object localObject = null;
		ClientInteraction localClientInteraction = ClientInteraction.begin();

		localObject = url;
		if (null != localObject)
			this.m_lgURL = ((String) localObject);
		localClientInteraction.setUrl(this.m_lgURL);

		localObject = group;
		if (null != localObject)
			this.m_lgGroup = ((String) localObject);
		localClientInteraction.setGroupName(this.m_lgGroup);
		localClientInteraction.setAppType((short) 681);

		localObject = service;
		if (null != localObject)
			this.m_lgService = ((String) localObject);
		localClientInteraction.setServiceName(this.m_lgService);
		localClientInteraction.setSvcType((short) 686);

		localObject = operation;
		if (null != localObject)
			this.m_lgOperation = ((String) localObject);
		localClientInteraction.setOpName(this.m_lgOperation);

		localClientInteraction.setPeerAddr(this.m_lgPeer);
		this.m_isAnalized = false;
	}

	@Override
	public String analyze(String paramString) {
		String str = null;
		ClientInteraction localClientInteraction = ClientInteraction.get();
		if (null != paramString) {
			localClientInteraction.setOpName(paramString);
		}
		try {
			str = InterHelpBase.writeHeader(localClientInteraction);
		} catch (Exception localException) {
			str = null;
		}

		localClientInteraction.requestAnalyzed();
		this.m_isAnalized = true;
		return str;
	}

	@Override
	public void endInteraction(String paramString) {
		ClientInteraction localClientInteraction = ClientInteraction.get();
		if (null != paramString) {
			localClientInteraction.setFailure(paramString);
		}
		localClientInteraction.end();
		this.m_isAnalized = false;
	}

	@Override
	public String getHeader() {
		String str = null;
		if (this.m_isAnalized) {
			ClientInteraction localClientInteraction = ClientInteraction.get();
			try {
				str = InterHelpBase.writeHeader(localClientInteraction);
			} catch (Exception localException) {
				str = null;
			}
			this.m_isAnalized = false;
		}
		return str;
	}

	@Override
	public String getGroup() {
		return this.m_lgGroup;
	}

	@Override
	public void setGroup(String paramString) {
		this.m_lgGroup = paramString;
	}

	@Override
	public String getService() {
		return this.m_lgService;
	}

	@Override
	public void setService(String paramString) {
		this.m_lgService = paramString;
	}

	@Override
	public String getOperation() {
		return this.m_lgOperation;
	}

	@Override
	public void setOperation(String paramString) {
		this.m_lgOperation = paramString;
	}

	@Override
	public String getUrl() {
		return this.m_lgURL;
	}

	@Override
	public void setUrl(String paramString) {
		this.m_lgURL = paramString;
		try {
			PscURLParser localPscURLParser = new PscURLParser(this.m_lgURL);
			this.m_lgPeer = localPscURLParser.getHost();
			String str = localPscURLParser.getService();
			if (null != str) {
				this.m_lgService = str;
				this.m_appService = str;
			}
		} catch (Exception localException) {
			this.m_lgPeer = "localhost";
		}

	}

	@Override
	public String getPeer() {
		return this.m_lgPeer;
	}

	@Override
	public void setPeer(String paramString) {
		this.m_lgPeer = paramString;
	}

	public String toString() {
		String str1 = "";
		String str2 = "";
		ClientInteraction localClientInteraction = ClientInteraction.get();

		if (localClientInteraction != null) {
			str1 = "CI";
			str2 = localClientInteraction.getInteractionID();
		}

		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("OpenEdge ESB Interceptor").append(" ")
				.append(str1).append("(").append(str2).append(") on thread: ")
				.append(Thread.currentThread().getName());

		if (localClientInteraction != null) {
			localStringBuffer.append(" ==>");
			localStringBuffer.append(" PeerAddr: [ "
					+ localClientInteraction.getPeerAddr());
			localStringBuffer.append(" ] URL: [ "
					+ localClientInteraction.getUrl());
			localStringBuffer.append(" ] Type: [ "
					+ localClientInteraction.getSvcType());
			localStringBuffer.append(" ] Flow ID: [ "
					+ localClientInteraction.getFlowID());
			localStringBuffer.append(" ] Op ID: [ "
					+ localClientInteraction.getOpID());
			localStringBuffer.append(" ] Locus ID: [ "
					+ localClientInteraction.getParentID());
			localStringBuffer.append(" ] Chain ID: [ "
					+ localClientInteraction.getChainID());
			localStringBuffer.append(" ] OneWay: [ "
					+ localClientInteraction.getOneWay());
			localStringBuffer.append(" ] isFault: [ "
					+ (localClientInteraction.getFailure() != null));
			localStringBuffer.append(" ] G: "
					+ localClientInteraction.getGroupName());
			localStringBuffer.append(" S: "
					+ localClientInteraction.getServiceName());
			localStringBuffer.append(" O: "
					+ localClientInteraction.getOpName());
		}
		return localStringBuffer.toString();
	}

	public boolean isInstrumented() {
		ServerInteraction localServerInteraction = ServerInteraction.get();
		return localServerInteraction != null;
	}

}
