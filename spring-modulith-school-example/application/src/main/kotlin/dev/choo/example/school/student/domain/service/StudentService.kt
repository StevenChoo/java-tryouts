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

package dev.choo.example.school.student.domain.service

import dev.choo.example.school.student.domain.model.Student
import dev.choo.example.school.student.domain.model.StudentStatus
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

@Service
class StudentService {
    /**
     * Determines if a student is eligible for regular enrollment based on age.
     * Students must be between 6 and 22 years old.
     */
    fun isEligibleForRegularEnrollment(student: Student): Boolean {
        val age = calculateAge(student.dateOfBirth)
        return age in 6..22
    }

    /**
     * Determines if a student is eligible for advanced placement.
     * Students must be at least 14 years old.
     */
    fun isEligibleForAdvancedPlacement(student: Student): Boolean {
        val age = calculateAge(student.dateOfBirth)
        return age >= 14
    }

    /**
     * Students can only unregister when they are not unregistered already or graduated since we need to keep historical data
     */
    fun isEligibleToUnregisterStudent(student: Student): Boolean =
        student.status != StudentStatus.UNREGISTERED && student.status != StudentStatus.GRADUATED

    private fun calculateAge(dateOfBirth: Instant): Int =
        Period.between(LocalDate.ofInstant(dateOfBirth, ZoneId.systemDefault()), LocalDate.now()).years
}
