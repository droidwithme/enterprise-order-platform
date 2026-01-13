package com.ocean.userservice.domain.exception

import java.util.UUID

class UserNotFoundException(uuid: UUID) :
    RuntimeException("User not found with id: ${uuid}")
