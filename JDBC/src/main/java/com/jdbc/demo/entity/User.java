package com.jdbc.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
   private Integer id;
   private String first_name;
   private String last_name;
   private Integer p_id;
   private Integer a_id;
   private String name;
   private Integer address_id;
   private String street;

   public User(int id, String first_name, String last_name) {
   }
}
