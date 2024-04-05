package io.jrsmth.cardinal.security.auth.registration

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import lombok.Data
import java.io.Serializable

@Data
@JsonIgnoreProperties
class RegistrationData : Serializable {

    companion object {
        private const val serialVersionUID = 7177615681547361636L
    }

    @NotEmpty(message = "firstName {validation.empty}")
    @Size(max = 50, message = "firstName {validation.length}")
    var firstName: String? = null

    @NotEmpty(message = "lastName {validation.empty}")
    @Size(max = 50, message = "lastName {validation.length}")
    var lastName: String? = null

    @NotEmpty(message = "email {validation.empty}")
    @Email(message = "email {validation.format}")
    var email: String? = null

    @NotEmpty(message = "password {validation.empty}")
    @Size(max = 50, message = "password {validation.length}")
    var password: String? = null

}
