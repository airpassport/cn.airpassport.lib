package org.dekuan.airpassport.lib.models.rest;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder( builderMethodName = "bodyDataBaseBuilder" )
public class RestBodyDataBase
{
	@Builder.Default
	protected long tenantId = 0;

	@Builder.Default
	protected long id = 0;

	@Builder.Default
	private UUID mid = UUID.randomUUID();

	@Builder.Default
	protected long userId = 0;

	@Builder.Default
	protected boolean enabled = false;

	@Builder.Default
//	protected LocalDateTime createdAt = DateTimeUtils.getLocalNowDateTime();
	protected String createdAt = null;

	@Builder.Default
	protected String updatedAt = null;
}
