package org.nttdata.infraestructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
  private String numberCard;
  private int password;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
