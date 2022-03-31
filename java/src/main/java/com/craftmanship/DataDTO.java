package com.craftmanship;

import java.util.List;

public class DataDTO {

  private final List<PersonDTO> personDTOList;

  public DataDTO(List<PersonDTO> personDTOList) {
    this.personDTOList = personDTOList;
  }

  public List<PersonDTO> getPersonDTOList() {
    return personDTOList;
  }

}
