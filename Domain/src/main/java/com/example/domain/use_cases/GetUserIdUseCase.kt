package com.example.domain.use_cases

import javax.inject.Inject

class GetUserIdUseCase @Inject constructor() {
    suspend operator fun invoke() = 333
}
