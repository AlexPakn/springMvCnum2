package org.example.springmvcnum2.Models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* wtf
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Store {
  private Integer id;
  private String name;
  private String address;
  private String phone;
  private String email;
  private String website;
  private String category;
  private String description;
  private String logo;
}
*/

public class Store {
  private Integer id;

  @NotBlank(message = "Название магазина обязательно")
  @Size(min = 3, max = 100, message = "Название должно быть от 3 до 100 символов")
  private String name;

  @NotBlank(message = "Адрес обязателен")
  private String address;

  @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Некорректный номер телефона")
  private String phone;

  @Email(message = "Некорректный email")
  private String email;

  @NotBlank(message = "Сайт обязателен")
  private String website;

  @NotBlank(message = "Категория обязательна")
  private String category;

  @Size(max = 255, message = "Описание не должно превышать 255 символов")
  private String description;
  private String logo;

  public Store() {}

  public Store(Integer id, String name, String address, String phone, String email, String website, String category, String description, String logo) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.email = email;
    this.website = website;
    this.category = category;
    this.description = description;
    this.logo = logo;
  }

  public Integer getId() { return id; }
  public void setId(Integer id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getWebsite() { return website; }
  public void setWebsite(String website) { this.website = website; }

  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public String getLogo() { return logo; }
  public void setLogo(String logo) { this.logo = logo; }
}