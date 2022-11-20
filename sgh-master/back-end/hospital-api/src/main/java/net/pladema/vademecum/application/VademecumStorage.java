package net.pladema.vademecum.application;

import net.pladema.vademecum.domain.SnomedBo;

import java.util.List;

public interface VademecumStorage {

	List<SnomedBo> searchConcepts(String term, String ecl, Integer institutionId);

	Long getTotalConcepts(String term, String ecl, Integer institutionId);
}
