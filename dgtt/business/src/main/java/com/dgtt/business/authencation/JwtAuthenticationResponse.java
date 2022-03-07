package com.dgtt.business.authencation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 *
 * @author Nguyen_Toan
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {

  @JsonProperty(value = "access_token")
  private String accessToken;
  
}
