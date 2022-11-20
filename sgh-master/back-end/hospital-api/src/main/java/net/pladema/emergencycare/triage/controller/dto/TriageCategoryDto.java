package net.pladema.emergencycare.triage.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
@ToString
@AllArgsConstructor
public class TriageCategoryDto implements Serializable {

    Short id;

    String name;

    String description;

    TriageColorDto color;

}
