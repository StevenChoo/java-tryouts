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

package dev.choo.example.school.student.infrastructure.web

import dev.choo.example.school.student.domain.model.Student
import java.time.Instant
import java.util.UUID

data class StudentRegistrationRequest(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Instant,
)

data class StudentIdResponse(
    val id: UUID,
) {
    companion object {
        fun fromDomain(id: UUID): StudentIdResponse =
            StudentIdResponse(
                id = id,
            )
    }
}

data class StudentResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Instant,
) {
    companion object {
        fun fromDomain(student: Student): StudentResponse =
            StudentResponse(
                id = student.id,
                firstName = student.firstName,
                lastName = student.lastName,
                dateOfBirth = student.dateOfBirth,
            )
    }
}
