package com.example.domain.use_cases

import javax.inject.Inject

// Totally fake use case i would normally use UUid but to stay simple i just did it with Int
class GetUserIdUseCase @Inject constructor() {
    suspend operator fun invoke() = 333
}
