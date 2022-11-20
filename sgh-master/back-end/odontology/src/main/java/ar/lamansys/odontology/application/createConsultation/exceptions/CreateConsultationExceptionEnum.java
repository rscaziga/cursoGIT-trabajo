package ar.lamansys.odontology.application.createConsultation.exceptions;

public enum CreateConsultationExceptionEnum {
    NULL_CONSULTATION,
    NULL_INSTITUTION_ID,
    NULL_PATIENT_ID,
    DENTAL_DIAGNOSTIC_NOT_FOUND,
    DIAGNOSTIC_NOT_APPLICABLE_TO_TOOTH,
    DIAGNOSTIC_NOT_APPLICABLE_TO_SURFACE,
    DENTAL_PROCEDURE_NOT_FOUND,
    PROCEDURE_NOT_APPLICABLE_TO_TOOTH,
    PROCEDURE_NOT_APPLICABLE_TO_SURFACE,
    INVALID_DOCTOR,
    NULL_CLINICAL_SPECIALTY_ID,
    INVALID_CLINICAL_SPECIALTY_ID,
    REPEATED_CONCEPTS,
    WRONG_TEETH_QUANTITY
}
