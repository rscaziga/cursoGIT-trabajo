package net.pladema.hl7.supporting.exchange.documents;

import net.pladema.hl7.dataexchange.IResourceFhir;
import net.pladema.hl7.dataexchange.model.adaptor.FhirID;
import net.pladema.hl7.dataexchange.model.domain.BundleVo;
import net.pladema.hl7.supporting.conformance.InteroperabilityCondition;
import net.pladema.hl7.supporting.exchange.database.FhirPersistentStore;
import net.pladema.hl7.supporting.terminology.coding.CodingProfile;
import net.pladema.hl7.supporting.terminology.coding.CodingSystem;
import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
@Conditional(InteroperabilityCondition.class)
public class DocumentReferenceResource extends IResourceFhir {

    @Autowired
    public DocumentReferenceResource(FhirPersistentStore store){
        super(store);
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.DocumentReference;
    }

    public DocumentReference fetch(BundleVo bundle){
        DocumentReference resource = new DocumentReference();
        resource.setId(FhirID.autoGenerated());
        resource.setSubject(
                newReferenceAsIdentifier(getDominio(), bundle.getPatient().getId()));
        resource.setCustodian(
                newReferenceAsIdentifier(CodingSystem.FEDERADOR, getDominio())
        );
        resource.setType(newCodeableConcept(bundle.getType()));
        resource.setStatus(Enumerations.DocumentReferenceStatus.CURRENT);
        resource.setMeta(newMeta(CodingProfile.DocumentReference.URL));

        //================Content of document================
        DocumentReference.DocumentReferenceContentComponent content = new DocumentReference.DocumentReferenceContentComponent();
        content.setAttachment(new Attachment()
                .setContentType("application/fhir+json")
                .setUrl(fullRequestUrl(new Bundle().setId(bundle.getId())))
                .setTitle(bundle.getPatient().getSummary())
        );
        resource.addContent(content);
        return resource;
    }

    public Bundle.BundleEntryComponent fetchEntry(BundleVo bundle){
        return fetchRequestEntry(fetch(bundle));
    }

    public BundleVo getData(String id){
        return store.getDocumentReference(id);
    }
}