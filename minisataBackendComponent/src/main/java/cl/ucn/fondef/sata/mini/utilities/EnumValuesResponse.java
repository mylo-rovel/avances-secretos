package cl.ucn.fondef.sata.mini.utilities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Enum values response.
 */
@ToString
@EqualsAndHashCode
public class EnumValuesResponse {

    @Getter
    @Setter
    private List<EnumValuesItem> listaItemsEnum;

    /**
     * Instantiates a new Enum values response.
     */
    public EnumValuesResponse() {
        this.listaItemsEnum = new ArrayList<>();
    }

    /**
     * Add enum list.
     *
     * @param <ArrayEnum>        the type parameter
     * @param nombreEnum the nombre enum
     * @param enumArr    the enum arr
     */
    public <ArrayEnum> void addEnumList(String nombreEnum, ArrayEnum[] enumArr){
        EnumValuesItem enumValuesItemList = new EnumValuesItem();
        enumValuesItemList.setNombreEnum(nombreEnum);

        List<String> auxList= new ArrayList<>();
        for (int i = 0; i < enumArr.length-1; i++) {
            auxList.add((String) "" + enumArr[i]);
        }
        enumValuesItemList.setValoresEnum(auxList);
        this.listaItemsEnum.add(enumValuesItemList);
    }
}
