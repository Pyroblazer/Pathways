package app.stefanny.pathway.response

import com.google.gson.annotations.SerializedName

data class PathwayResponse(

	@field:SerializedName("api/job_opening/list")
	val jobOpening: JobOpeningResponse
)

data class AllUsersResponse(

	@field:SerializedName("AllUsersResponse")
	val allUsersResponse: List<AllUsersResponseItem>
)

data class AllUsersResponseItem(

	@field:SerializedName("user_password")
	var userPassword: String,

	@field:SerializedName("type_of_user")
	var typeOfUser: String,

	@field:SerializedName("email")
	var email: String,

	@field:SerializedName("username")
	var username: String
)

data class UserGeneralResponse(

	@field:SerializedName("short_description")
	val shortDescription: String,

	@field:SerializedName("interest_job")
	val interestJob: String,

	@field:SerializedName("profile_picture")
	val profilePicture: String,

	@field:SerializedName("interest_subject")
	val interestSubject: String,

	@field:SerializedName("username")
	val username: String
)

data class CompanyResponse(

	@field:SerializedName("company_description")
	val companyDescription: List<String>,

	@field:SerializedName("avg_processing_time")
	val avgProcessingTime: List<String>,

	@field:SerializedName("size")
	val size: List<String>,

	@field:SerializedName("company_name")
	val companyName: List<String>,

	@field:SerializedName("industry")
	val industry: List<String>,

	@field:SerializedName("location")
	val location: List<String>,

	@field:SerializedName("id")
	val id: List<String>
)

data class UserCompanyResponse(

	@field:SerializedName("company_id")
	val companyId: List<String>,

	@field:SerializedName("username")
	val username: List<String>
)

data class JobOpeningResponse(

	@field:SerializedName("job_description")
	val jobDescription: String?,

	@field:SerializedName("username_of_creator")
	val usernameOfCreator: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("location")
	val location: String?,

	@field:SerializedName("id")
	val id: String?,

	@field:SerializedName("title")
	val title: String?,

	@field:SerializedName("salary")
	val salary: String?
)

data class ChallengesResponse(

	@field:SerializedName("data_related")
	val dataRelated: List<String>,

	@field:SerializedName("order_number")
	val orderNumber: List<String>,

	@field:SerializedName("job_opening_id")
	val jobOpeningId: List<String>,

	@field:SerializedName("task_title")
	val taskTitle: List<String>,

	@field:SerializedName("task_type")
	val taskType: List<String>
)

data class SuggestedCourseResponse(

	@field:SerializedName("course_id")
	val courseId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("link_to_course")
	val linkToCourse: String
)

data class JobOpeningHasSuggestedCoursesResponse(

	@field:SerializedName("course_id")
	val courseId: List<String>,

	@field:SerializedName("job_opening_id")
	val jobOpeningId: String
)

data class UserGeneralAppliesJobOpeningResponse(

	@field:SerializedName("job_id")
	val jobId: String,

	@field:SerializedName("done_challenges")
	val doneChallenges: String,

	@field:SerializedName("username")
	val username: String
)

data class UserProfileExperienceResponse(

	@field:SerializedName("order_no")
	val orderNo: String,

	@field:SerializedName("end_date")
	val endDate: String,

	@field:SerializedName("position_description")
	val positionDescription: String,

	@field:SerializedName("company_name")
	val companyName: String,

	@field:SerializedName("position")
	val position: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("start_date")
	val startDate: String
)

data class TasksResponse(

	@field:SerializedName("TasksResponse")
	val tasksResponse: List<CompletedTaskResponseItem>
)

data class CompletedTaskResponseItem(

	@field:SerializedName("data_related")
	val dataRelated: String,

	@field:SerializedName("task_title")
	val taskTitle: String
)

data class JobRecommenderResponse(

	@field:SerializedName("text")
	var text: String,

	@field:SerializedName("job")
	val job: String
)

data class JobTaskResponse(

	@field:SerializedName("data_related")
	val dataRelated: String,

	@field:SerializedName("order_number")
	val orderNumber: String,

	@field:SerializedName("job_opening_id")
	val jobOpeningId: String,

	@field:SerializedName("task_title")
	val taskTitle: String,

	@field:SerializedName("task_type")
	val taskType: String
)
