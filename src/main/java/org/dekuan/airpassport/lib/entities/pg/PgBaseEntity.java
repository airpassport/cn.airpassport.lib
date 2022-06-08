package org.dekuan.airpassport.lib.entities.pg;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.dekuan.airpassport.lib.constants.CommonStatus;
import org.dekuan.airpassport.lib.entities.discriminator.TenantSupport;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;
import org.dekuan.airpassport.lib.utils.DeDateTimeUtils;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;

import javax.persistence.*;
import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


/**
 *	解决 Update timestamp when row is updated in PostgreSQL
 *	<a href="https://stackoverflow.com/questions/1035980/update-timestamp-when-row-is-updated-in-postgresql">https://stackoverflow.com/questions/1035980/update-timestamp-when-row-is-updated-in-postgresql</a>
 *
 * <p>
 * in MySQL:
 * `updated_at` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
 * <p>
 * in PostgreSQL:
 * step 1)
 * CREATE OR REPLACE FUNCTION update_updated_at_column()
 * RETURNS TRIGGER AS $$
 * BEGIN
 * NEW.updated_at = now();
 * RETURN NEW;
 * END;
 * $$ language 'plpgsql';
 * <p>
 * step 2)
 * CREATE TRIGGER update_updated_at_column BEFORE UPDATE
 * ON mails FOR EACH ROW EXECUTE PROCEDURE
 * update_updated_at_column();
 */
@Getter
@Setter
@ToString
@SuperBuilder( builderMethodName = "pgBaseEntityBuilder" )
@MappedSuperclass
public class PgBaseEntity implements Serializable, TenantSupport
{
	protected static Validator validator;

	static
	{
		//
		//	initialize a validator
		//
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		factory.close();
	}


	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE )
	@Column( name = "id", columnDefinition = "BIGSERIAL", updatable = false, nullable = false, unique = true )
	@Builder.Default
	protected long id = 0;

	//	UUID
	//	0e37df36-f698-11e6-8dd4-cb9ced3df976
	@Type( type = "pg-uuid" )
	@Column( name = "mid", columnDefinition = "uuid NOT NULL DEFAULT gen_random_uuid()", updatable = false, nullable = false, unique = true )
	@Builder.Default
	private UUID mid	= UUID.randomUUID();

	@Column( name = "tenant_id", columnDefinition = "BIGINT NOT NULL DEFAULT 0", updatable = false )
	@Builder.Default
	protected long tenantId	= 0;

	@Column( name = "u_id", columnDefinition = "BIGINT NOT NULL DEFAULT 0", updatable = false )
	@Builder.Default
	protected long userId = 0;

	//
	//	1	enabled
	//	0	disabled
	@Column( name = "enabled", columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE" )
	@Builder.Default
	protected boolean enabled = false;

	//	version lock
	//	enable optimistic locking version control
	@Version
	@Column( name = "version", columnDefinition = "INT NOT NULL DEFAULT 0" )
	@Builder.Default
	protected int version = 0;

	@Column( name = "status", columnDefinition = "SMALLINT NOT NULL DEFAULT 0" )
	@Builder.Default
	protected int status = CommonStatus.OKAY;

	//	Used with column status to mark the deletion status of the record
	@Column( name = "alt", columnDefinition = "CHAR(36) NOT NULL default ''" )
	@Builder.Default
	protected String alt = "";

	@CreationTimestamp
	@Column( name = "created_at", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", updatable = false )
	@Builder.Default
	protected LocalDateTime createdAt = DeDateTimeUtils.getLocalNowDateTime();

	@UpdateTimestamp
	@Column( name = "updated_at", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" )
	@Builder.Default
	protected LocalDateTime updatedAt	= null;


	public PgBaseEntity()
	{
		//
		//	initialize members
		//
		this.id		= 0;
		this.mid	= UUID.randomUUID();

		this.tenantId	= 0;
		this.userId	= 0;
		this.enabled	= true;

		this.setStatus( CommonStatus.OKAY );
		this.alt	= "";
		this.version	= 0;
		this.createdAt	= null;
		this.updatedAt	= null;
	}


	@Override
	public long getTenantId()
	{
		return this.tenantId;
	}

	@Override
	public void setTenantId( long tenantId )
	{
		if ( tenantId < 0 )
		{
			throw new AirExceptions.InvalidParameter( "invalid tenantId" );
		}

		this.tenantId = tenantId;
	}


	public void setRandomMid()
	{
		this.mid = UUID.randomUUID();
	}

	public void setUserId( long userId )
	{
		if ( userId < 0 )
		{
			throw new AirExceptions.InvalidParameter( "invalid userId" );
		}

		this.userId = userId;
	}


	public void setStatus( int status )
	{
		if ( ! CommonStatus.isValid( status ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid status" );
		}

		this.status = status;
	}


	@Override
	public boolean equals( Object o )
	{
		if ( this == o )
		{
			return true;
		}
		if ( o == null || Hibernate.getClass( this ) != Hibernate.getClass( o ) )
		{
			return false;
		}
		PgBaseEntity that = ( PgBaseEntity ) o;
		return Objects.equals( id, that.id );
	}

	@Override
	public int hashCode()
	{
		return getClass().hashCode();
	}
}
