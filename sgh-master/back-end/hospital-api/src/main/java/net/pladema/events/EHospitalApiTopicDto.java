package net.pladema.events;

public enum EHospitalApiTopicDto {


	ALTA_MEDICA("CLINIC_HISTORY/HOSPITALIZATION/DISCHARGE/MEDICAL"),
	CLINIC_HISTORY__HOSPITALIZATION__DISCHARGE__PHYSIC("CLINIC_HISTORY/HOSPITALIZATION/DISCHARGE/PHYSIC"),
	PACIENTE_LLAMADO("PACIENTE_LLAMADO"),
	CLINIC_HISTORY__HOSPITALIZATION__SERVICE_RESQUEST("CLINIC_HISTORY/HOSPITALIZATION/SERVICE_RESQUEST"),
	CLINIC_HISTORY__HOSPITALIZATION__SERVICE_RESQUEST__IMAGE("CLINIC_HISTORY/HOSPITALIZATION/SERVICE_RESQUEST/IMAGE"),
	CLINIC_HISTORY__HOSPITALIZATION__SERVICE_RESQUEST__LABORATORY("CLINIC_HISTORY/HOSPITALIZATION/SERVICE_RESQUEST/LABORATORY");
	private String value;

	EHospitalApiTopicDto(String value)
	{
		this.value = value;
	}

	public String toString()
	{
		return this.value;
	}
}
