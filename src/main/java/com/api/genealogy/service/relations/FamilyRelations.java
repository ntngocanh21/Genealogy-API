package com.api.genealogy.service.relations;

import com.api.genealogy.constant.Gender;

import java.util.HashMap;
import java.util.Map;

public class FamilyRelations {
    private Map<String, String> familyRelations;

    public Map<String, String> getFamilyRelations() {
        return familyRelations;
    }

    public void setFamilyRelations(Map<String, String> familyRelations) {
        this.familyRelations = familyRelations;
    }

    public FamilyRelations(){
        familyRelations = new HashMap<String, String>();

        familyRelations.put((new Key(-5, Gender.FEMALE, null, null)).getKey(), "Tổ tiên");
        familyRelations.put((new Key(-5, Gender.MALE, null, null)).getKey(), "Tổ tiên");

        familyRelations.put((new Key(-4, Gender.FEMALE, null, null)).getKey(), "Kỵ bà");
        familyRelations.put((new Key(-4, Gender.MALE, null, null)).getKey(), "Kỵ ông");

        familyRelations.put((new Key(-3, Gender.FEMALE, null, null)).getKey(), "Cụ bà");
        familyRelations.put((new Key(-3, Gender.MALE, null, null)).getKey(), "Cụ ông");

        familyRelations.put((new Key(-2, Gender.FEMALE, null, null)).getKey(), "Bà nội");
        familyRelations.put((new Key(-2, Gender.MALE, null, null)).getKey(), "Ông nội");

        familyRelations.put((new Key(-1, Gender.FEMALE, null, true)).getKey(), "Mẹ");
        familyRelations.put((new Key(-1, Gender.MALE, null, true)).getKey(), "Cha");
        familyRelations.put((new Key(-1, Gender.FEMALE, true, false)).getKey(), "Cô");
        familyRelations.put((new Key(-1, Gender.FEMALE, false, false)).getKey(), "O");
        familyRelations.put((new Key(-1, Gender.MALE, true, false)).getKey(), "Bác");
        familyRelations.put((new Key(-1, Gender.MALE, false, false)).getKey(), "Chú");

        familyRelations.put((new Key(0, Gender.FEMALE, true, null)).getKey(), "Chị");
        familyRelations.put((new Key(0, Gender.FEMALE, false, null)).getKey(), "Em gái");
        familyRelations.put((new Key(0, Gender.MALE, true, null)).getKey(), "Anh");
        familyRelations.put((new Key(0, Gender.MALE, false, null)).getKey(), "Em trai");

        familyRelations.put((new Key(1, Gender.FEMALE, null, true)).getKey(), "Con gái");
        familyRelations.put((new Key(1, Gender.MALE, null, true)).getKey(), "Con trai");
        familyRelations.put((new Key(1, Gender.FEMALE, null, false)).getKey(), "Cháu gái");
        familyRelations.put((new Key(1, Gender.MALE, null, false)).getKey(), "Cháu trai");

        familyRelations.put((new Key(2, Gender.FEMALE, null, null)).getKey(), "Cháu gái");
        familyRelations.put((new Key(2, Gender.MALE, null, null)).getKey(), "Cháu trai");

        familyRelations.put((new Key(3, Gender.FEMALE, null, null)).getKey(), "Chắt");
        familyRelations.put((new Key(3, Gender.MALE, null, null)).getKey(), "Chắt");

        familyRelations.put((new Key(4, Gender.FEMALE, null, null)).getKey(), "Chút");
        familyRelations.put((new Key(4, Gender.MALE, null, null)).getKey(), "Chút");

        familyRelations.put((new Key(5, Gender.FEMALE, null, null)).getKey(), "Chít");
        familyRelations.put((new Key(5, Gender.MALE, null, null)).getKey(), "Chít");
    }

    public String getFamilyRelation(String string){
        return familyRelations.get(string);
    }
}
