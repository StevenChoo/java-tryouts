/*
 * Copyright (C) 2025 Steven Choo <info@stevenchoo.nl>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.choo.example.school.student.application.model

import java.util.UUID

sealed class RegisterResult {
    data class Success(
        val studentId: UUID,
        val isEligibleForAdvancedPlacement: Boolean,
    ) : RegisterResult()

    data class Failure(
        val reason: String,
    ) : RegisterResult()
}

sealed class UnregisterResult {
    data class Success(
        val studentId: UUID,
    ) : UnregisterResult()

    data class Failure(
        val reason: String,
    ) : UnregisterResult()
}
