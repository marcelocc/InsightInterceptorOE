package com.actional.lg.interceptor.sdk.openedge;


public interface IActionalInterceptor {
	public static final short ACT_DT_OEGROUP = 681;
	public static final short ACT_DT_BATCH = 682;
	public static final short ACT_DT_MQADAPTER = 683;
	public static final short ACT_DT_WSA = 684;
	public static final short ACT_DT_WEBSPEED = 685;
	public static final short ACT_DT_APPSERVER = 686;
	public static final short ACT_DT_AIA = 687;

	public abstract void beginInteraction(String url, String group, String service, String operation);

	public abstract String analyze(String paramString);

	public abstract void endInteraction(String paramString);

	public abstract String getHeader();

	public abstract String getGroup();

	public abstract void setGroup(String paramString);

	public abstract String getService();

	public abstract void setService(String paramString);

	public abstract String getOperation();

	public abstract void setOperation(String paramString);

	public abstract String getUrl();

	public abstract void setUrl(String paramString);

	public abstract String getPeer();

	public abstract void setPeer(String paramString);
}
