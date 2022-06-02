package org.dekuan.airpassport.lib.entities.discriminator;

public interface TenantSupport
{
	long getTenantId();

	void setTenantId( long tenantId );
}