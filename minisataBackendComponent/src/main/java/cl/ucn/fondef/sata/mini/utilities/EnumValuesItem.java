package cl.ucn.fondef.sata.mini.utilities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Enum values item.
 */
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
