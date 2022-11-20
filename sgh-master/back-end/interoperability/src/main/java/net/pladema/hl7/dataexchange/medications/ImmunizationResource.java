package net.pladema.hl7.dataexchange.medications;

import net.pladema.hl7.dataexchange.IMultipleResourceFhir;
import net.pladema.hl7.dataexchange.model.adaptor.FhirDateMapper;
import net.pladema.hl7.dataexchange.model.adaptor.FhirID;
import net.pladema.hl7.supporting.conformance.InteroperabilityCondition;
import net.pladema.hl7.supporting.exchange.database.FhirPersistentStore;
import net.pladema.hl7.supporting.terminology.coding.CodingCode;
import net.pladema.hl7.supporting.terminology.coding.CodingProfile;
import net.pladema.hl7.supporting.terminology.coding.CodingSystem;
import net.pladema.hl7.dataexchange.model.domain.ImmunizationVo;
import org.apache.commons.lang3.tuple.Pair;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Conditional(InteroperabilityCondition.class)
public class ImmunizationResource extends IMultipleResourceFhir<Immunization> {

    @Autowired
    public ImmunizationResource(FhirPersistentStore store){
        super(store);
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.Immunization;
    }

    @Override
    public List<Immunization> fetch(String id, Map<ResourceType, Reference> references){
        List<ImmunizationVo> immunizations = store.findAllImmunizations(id);

        if(immunizations.isEmpty())
            return noInformationAvailable(references.get(ResourceType.Patient));

        List<Immunization> resources = new ArrayList<>();
        immunizations.forEach( immunization -> {
            Immunization resource = new Immunization();
            resource.setId(immunization.getId());
            resource.setStatus(Immunization.ImmunizationStatus.fromCode(immunization.getStatus()));
            resource.setVaccineCode(
                    newCodeableConcept(CodingSystem.SNOMED, immunization.get())
            );
            resource.setPatient(references.get(ResourceType.Patient));
            resource.getOccurrenceDateTimeType().setValue(FhirDateMapper.toDate(immunization.getAdministrationDate()));
            resource.setLocation(references.get(ResourceType.Location));

            Extension ext = new Extension(CodingProfile.Immunization.NOMIVAC.URL,
                    newCodeableConcept(CodingSystem.Immunization.NOMIVACESCHEMA,
                    immunization.get())
            );
            resource.addProtocolApplied()
                    .setSeries(immunization.getSeries())
                    .getDoseNumberPositiveIntType().setValue(immunization.getDoseNumber())
                    .addExtension(ext);
            resource.setMeta(newMeta(CodingProfile.Immunization.URL));

            addNonRequiredAttributes(resource, immunization);
            resources.add(resource);
        });
        return resources;
    }

    private List<Immunization> noInformationAvailable(Reference patientRef) {
        Immunization none = new Immunization();
        none.setId(FhirID.autoGenerated());
        none.setPatient(patientRef);
        none.setStatus(Immunization.ImmunizationStatus.COMPLETED);
        none.setVaccineCode(newCodeableConcept(CodingSystem.NODATA, CodingCode.Immunization.KNOWN_ABSENT));
        none.getOccurrenceDateTimeType().addExtension(newExtension(
                CodingProfile.DATA_ABSENT_REASON, CodingCode.ABSENT_REASON));
        return Collections.singletonList(none);
    }

    private void addNonRequiredAttributes(Immunization resource, ImmunizationVo immunization){
        resource.setPrimarySource(immunization.isPrimarySource());
        resource.setLotNumber(immunization.getBatchNumber());
        resource.setExpirationDate(FhirDateMapper.toDate(immunization.getExpirationDate()));
    }

    public static ImmunizationVo encode(Resource baseResource){
        ImmunizationVo data = new ImmunizationVo();
        Immunization resource = (Immunization) baseResource;

        data.setId(resource.getId());

        if(resource.hasStatus())
            data.setStatus(resource.getStatus().getDisplay());

        if(resource.hasOccurrenceDateTimeType())
            data.setAdministrationDate(FhirDateMapper.toLocalDate(resource.getOccurrenceDateTimeType().getValue()));

        data.setExpirationDate(FhirDateMapper.toLocalDate(resource.getExpirationDate()));

        if(resource.hasVaccineCode()){
            Pair<String, String> coding = decodeCoding(resource.getVaccineCode());
            data.setVaccineCode(coding.getKey());
            data.setVaccineTerm(coding.getValue());
        }

        if(resource.hasProtocolApplied()){
            Immunization.ImmunizationProtocolAppliedComponent protocol =
                    resource.getProtocolApplied().get(0);
            data.setSeries(protocol.getSeries());
            data.setDoseNumber(protocol.getDoseNumberPositiveIntType().getValue());
            data.setImmunizationCode(decodeCode(protocol.addTargetDisease()));
        }

        data.setPrimarySource(resource.getPrimarySource());
        data.setBatchNumber(resource.getLotNumber());

        return data;

    }
}
