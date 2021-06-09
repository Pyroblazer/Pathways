package app.stefanny.pathway.api

import app.stefanny.pathway.request.JobRecommenderRequest
import app.stefanny.pathway.request.LoginRequest
import app.stefanny.pathway.request.UserRequest
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
        @Body userRequest: UserRequest
    ): Call<AllUsersResponse>

    @GET("api/all_users/insert/")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<AllUsersResponse>

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

    @FormUrlEncoded
    @POST("api/experience/insert/")
    fun postUserExperience(
        @Field("order_no") orderNo: String,
        @Field("username") username: String,
        @Field("position") position: String,
        @Field("company_name") companyName: String,
        @Field("start_date") startDate: String,
        @Field("end_date") endDate: String,
        @Field("position_description") positionDescription: String
    ) : Call<UserProfileExperienceResponse>

    @FormUrlEncoded
    @POST("api/user_general/insert/")
    fun postEditProfile(
        @Field("short_description") shortDescription: String,
        @Field("interest_job") interestJob: String,
        @Field("profile_picture") profilePicture: String,
        @Field("interest_subject") interestSubject: String,
        @Field("username") username: String
    ) : Call<UserGeneralResponse>

    @FormUrlEncoded
    @POST("api/challenges/list")
    fun postJobTask(
        @Field("order_number") orderNumber: String,
        @Field("job_opening_id") jobOpeningId: String,
        @Field("task_title") taskTitle: String,
        @Field("task_type") taskType: String,
        @Field("data_related") dataRelated: String
    ) : Call<JobTaskResponse>

    @POST("ml/jobRecommender")
    fun jobRecommender(
        @Body jobRecommenderRequest: JobRecommenderRequest
    ): Call<JobRecommenderResponse>
}