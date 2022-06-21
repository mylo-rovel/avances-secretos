package cl.ucn.fondef.sata.mini.utilities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class EnumValuesResponse {

    @Getter
    @Setter
    private List<EnumValuesItem> listaItemsEnum;

    public EnumValuesResponse() {
        this.listaItemsEnum = new ArrayList<>();
    }

    public <T> void addEnumList(String nombreEnum, T[] enumArr){
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
