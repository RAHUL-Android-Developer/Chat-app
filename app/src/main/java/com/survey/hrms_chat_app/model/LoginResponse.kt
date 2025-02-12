package com.survey.hrms_chat_app.model


data class LoginResponse(
    val token: String,
    val data: UserData
)

data class UserData(
    val userFullInfo: UserFullInfo,
    val roles: List<Role>
)

data class UserFullInfo(
    val userId: String,
    val userFullName: String,
    val username: String,
    val mobileNo: String,
    val emailId: String,
    val userPassword: String,
    val adminUser: String,
    val profile_image_name: String,
    val designation: String,
    val usernameOrMobileNo: String,
    val roleMasters: List<RoleMaster>
)

data class RoleMaster(
    val roleCode: String,
    val dscr: String
)

data class Role(
    val module_name: String,
    val role_code: String,
    val link: String,
    val status: String,
    val dscr: String,
    val icon_path: String
)
