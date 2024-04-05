package io.jrsmth.cardinal.security.auth.login

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import lombok.Data
import java.io.Serializable

@Data
@JsonIgnoreProperties
class LoginData : Serializable {

    companion object {
        private const val serialVersionUID = -1777615681547361636L
    }

    @NotEmpty(message = "email {validation.empty}")
    @Email(message = "email {validation.format}")
    var email: String? = null

    @NotEmpty(message = "password {validation.empty}")
    var password: String? = null

}
