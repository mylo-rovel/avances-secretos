package cl.ucn.fondef.sata.mini.utilities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
public class EnumValuesItem {

    @Getter
    @Setter
    private String nombreEnum;

    @Getter
    @Setter
    private List<String> valoresEnum;
}
