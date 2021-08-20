package app.stefanny.pathway.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRequest {
    @SerializedName("user_password")
    @Expose
    var userPassword: String? = null

    @SerializedName("type_of_user")
    @Expose
    var typeOfUser: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

}