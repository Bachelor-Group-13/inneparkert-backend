package no.bachelorgroup13.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateDto {
    private String text;
    private List<Integer> bbox;
}
