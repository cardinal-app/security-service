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

    @NotEmpty(message = "{firstName.empty}")
    @Size(max = 50, message = "{firstName.length}")
    var firstName: String? = null

    @NotEmpty(message = "{lastName.empty}")
    @Size(max = 50, message = "{lastName.length}")
    var lastName: String? = null

    @NotEmpty(message = "{email.empty}")
    @Email(message = "{email.format}")
    var email: String? = null

    @NotEmpty(message = "{password.empty}")
    @Size(max = 20, message = "{password.length}")
    var password: String? = null

}
