package app.stefanny.pathway.api

import app.stefanny.pathway.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api/job_opening/list")
    fun getJobOpening(): Call<ArrayList<JobOpeningResponse>>

    @GET("api/suggested_courses/list")
    fun getCourses(): Call<ArrayList<SuggestedCourseResponse>>

    @GET("api/user_general_applies_job_opening/list")
    fun getPeople(): Call<ArrayList<UserGeneralAppliesJobOpeningResponse>>

    @GET("api/job_opening/get_or_delete/{id}")
    fun getJobDesc(@Path("id") id: String) : Call<JobOpeningResponse>

    @GET("api/user_general/list")
    fun getQualifiedPeople(): Call<ArrayList<UserGeneralResponse>>

    @GET("api/experience/list")
    fun getUserProfileExperience(): Call<ArrayList<UserProfileExperienceResponse>>

    @GET("api/user_general/get_or_delete/{username}")
    fun getUserGeneral(@Path("username") username: String) : Call<UserGeneralResponse>

    @GET("/api/custom_query/list_by_username/{id}")
    fun getCompletedTask(@Path("id") id: String) : Call<ArrayList<CompletedTaskResponseItem>>


    @POST("api/all_users/insert/")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("type_of_user") typeOfUser: String,
        @Field("user_password") password: String
    ): Call<AllUsersResponseItem>

    @POST("api/all_users/insert/")
    fun login(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("type_of_user") typeOfUser: String,
    ): Call<AllUsersResponseItem>

    @FormUrlEncoded
    @POST("api/job_opening/insert/")
    fun postJobOpening(
        @Field("job_description") jobDescription: String,
        @Field("username_of_creator") usernameOfCreator: String?,
        @Field("company") company: String?,
        @Field("location") location: String?,
        @Field("id") id: String?,
        @Field("title") title: String?,
        @Field("salary") salary: String?
    ) : Call<JobOpeningResponse>
}