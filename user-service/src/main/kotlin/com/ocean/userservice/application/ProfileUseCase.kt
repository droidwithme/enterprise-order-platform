package com.ocean.userservice.application

import com.ocean.userservice.domain.UserProfile

interface ProfileUseCase {
    fun getProfile(userId: String): UserProfile
}